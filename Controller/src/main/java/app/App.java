package app;

import entitys.*;

import javax.persistence.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
/**
 * Класс главного интерфейса программы
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class App extends JFrame {
    /** Статическое поле фабрики сущностей */
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
    /** Статическое поле менеджера сущностей */
    protected static final EntityManager em = emf.createEntityManager();
    /** Ссылка на созданный интерфейс (на себя)*/
    final private App App;
    /** Статическое поле выбора текущей таблицы данных */
    protected static JComboBox tableSelect;
    /** Поле скролл панели для таблицы */
    private JScrollPane scroll;
    /** Поле текущей выбранной таблицы данных */
    private JTable table;

    /**
     * Конструктор объекта App по-умолчанию
     */
    public App() {
        super("Диспетчерская");
        App = this;
        showGUI();
    }
    /**
     * Функция задания интерфейса и его демонстрации
     */
    public void showGUI() {
// Создание окна
        App.setSize(500, 300);
        App.setLocation(100, 100);
        App.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// Создание кнопок и прикрепление иконок на панель инструментов
        tableSelect = new JComboBox(new String[]{"Автобусы", "Водители",
                "Маршруты"});
        JButton add = new JButton(new ImageIcon("./src/main/resources/img/add.png")); //new ImageIcon("./src/main/resources/img/add.png")
        JButton edit = new JButton(new ImageIcon("./src/main/resources/img/edit.png")); //new ImageIcon("./src/main/resources/img/edit.png")
        JButton remove = new JButton(new ImageIcon("./src/main/resources/img/delete.png")); //new ImageIcon("./src/main/resources/img/delete.png")
        JButton export = new JButton(new ImageIcon("./src/main/resources/img/export.png")); //new ImageIcon("./src/main/resources/img/export.png")

// Добавление слушателей
        ActionListener actionSelect = e -> {
            JComboBox box = (JComboBox)e.getSource();
            String item = (String)box.getSelectedItem();
            selectTable(item);
        };
        tableSelect.addActionListener(actionSelect);

        add.addActionListener(e -> {
            try {
                //createTableItem();
                new CreateItemDialog(App, table);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });
        edit.addActionListener(e -> {
            try {
                new EditItemDialog(App, table);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });
        remove.addActionListener(e -> new RemovalManager(App, table));
        export.addActionListener(e -> new PDFExporter(table));

// Настройка подсказок для кнопок
        tableSelect.setToolTipText("Выберите таблицу");
        add.setToolTipText("Добавить запись");
        edit.setToolTipText("Редактировать запись");
        remove.setToolTipText("Удалить запись(-и)");
        export.setToolTipText("Экспорт текущей таблицы в PDF");

// Добавление кнопок на панель инструментов
        JToolBar toolBar = new JToolBar("Панель инструментов");
        toolBar.add(tableSelect);
        toolBar.add(add);
        toolBar.add(edit);
        toolBar.add(remove);
        toolBar.add(export);

// Размещение панели инструментов
        App.setLayout(new BorderLayout());
        App.add(toolBar, BorderLayout.NORTH);

// Создание таблицы с данными по-умолчанию
        selectTable("Автобусы");

// Подготовка компонентов поиска
        JButton report = new JButton("Справка и нарушения");
        report.addActionListener(e -> new ReportThread("report", em).start());

// Добавление компонентов на панель
        JPanel reportPanel = new JPanel();
        reportPanel.add(report);

// Размещение панели поиска внизу окна
        App.add(reportPanel, BorderLayout.SOUTH);

// Визуализация экранной формы
        App.setVisible(true);
    }
    /**
     * Функция выбора активной таблицы
     * @param select - название выбираемой таблицы данных
     */
    protected void selectTable(String select) { // выбор отображаемой таблицы
        if (scroll != null) App.remove(scroll); // для случая инициализации
        String[] columns = null;
        String[][] data = null;
        // заполнение даными
        switch (select) {
            case ("Автобусы"):
                columns = new String[] {"ID", "Номер", "Вместимость", "Водитель", "Маршрут", "Состояние"};
                List<Bus> busList = em.createQuery("SELECT b FROM Bus b").getResultList();
                data = new String[busList.size()][columns.length];
                for (int i = 0; i < busList.size(); i++) {
                    data[i] = busList.get(i).toTableFormat();
                }
                break;
            case ("Водители"):
                columns = new String[] {"ID", "ФUО", "Возраст", "Опыт", "Зарплата", "Состояние"};
                List<Driver> driverList = em.createQuery("SELECT d FROM Driver d").getResultList();
                data = new String[driverList.size()][columns.length];
                for (int i = 0; i<driverList.size(); i++) {
                    data[i] = driverList.get(i).toTableFormat();
                }
                break;
            case ("Маршруты"):
                columns = new String[] {"ID", "№", "Откуда", "Куда", "Расписание", "Состояние"};
                List<Route> routeList = em.createQuery("SELECT r FROM Route r").getResultList();
                data = new String[routeList.size()][columns.length];
                for (int i = 0; i<routeList.size(); i++) {
                    data[i] = routeList.get(i).toTableFormat();
                }
                break;
        }
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override // запрет на редактирование ячеек
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        // настройка отрисовки таблицы
        table.setDefaultRenderer(Object.class, new ViolationTableCellRender(em, tableSelect.getSelectedIndex()));
        // Добавление попап меню для таблицы
        table.setComponentPopupMenu(new TablePopupMenu(table, (String)tableSelect.getSelectedItem()));
        // Добавление упорядочивания в таблице
        table.setRowSorter(new TableRowSorter<>(model));
        scroll = new JScrollPane(table);
        // Размещение таблицы с данными
        App.add(scroll, BorderLayout.CENTER);
        // обновляем окно для корректного отображения новой таблицы
        App.setVisible(true);
    }
}
