package app;

import entitys.*;
import utils.ValidationError;
import utils.WritingError;

import javax.persistence.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.swing.WindowConstants.*;

public class App {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
    private static final EntityManager em = emf.createEntityManager();
    // Объявления графических компонентов
    private JFrame App;
    private DefaultTableModel model;
    private JComboBox tableSelect;
    private JButton add;
    private JButton edit;
    private JButton remove;
    private JButton export;

    private JToolBar toolBar;
    private JScrollPane scroll;
    private JTable table;
    private JButton report;

    public void show() {
// Создание окна
        App = new JFrame("Диспетчерская");
        App.setSize(500, 300);
        App.setLocation(100, 100);
        App.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// Создание кнопок и прикрепление иконок
        tableSelect = new JComboBox(new String[]{"Автобусы", "Водители",
                "Маршруты"});
        add = new JButton(new ImageIcon("./src/main/resources/img/add.png")); //new ImageIcon("./src/main/resources/img/add.png")
        edit = new JButton(new ImageIcon("./src/main/resources/img/edit.png")); //new ImageIcon("./src/main/resources/img/edit.png")
        remove = new JButton(new ImageIcon("./src/main/resources/img/delete.png")); //new ImageIcon("./src/main/resources/img/delete.png")
        export = new JButton(new ImageIcon("./src/main/resources/img/export.png")); //new ImageIcon("./src/main/resources/img/export.png")

// Добавление слушателей
        ActionListener actionSelect = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                String item = (String)box.getSelectedItem();
                selectTable(item);
            }
        };
        tableSelect.addActionListener(actionSelect);

        add.addActionListener(e -> {
            try {
                createTableItem();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });
        edit.addActionListener(e -> {
            try {
                editTableItem();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });
        remove.addActionListener(e -> removeTableItem());
        export.addActionListener(e -> new PDFExporter(table));

// Настройка подсказок для кнопок
        tableSelect.setToolTipText("Выберите таблицу");
        add.setToolTipText("Добавить запись");
        edit.setToolTipText("Редактировать запись");
        remove.setToolTipText("Удалить запись(-и)");
        export.setToolTipText("Экспорт текущей таблицы в PDF");

// Добавление кнопок на панель инструментов
        toolBar = new JToolBar("Панель инструментов");
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
        //author = new JComboBox(new String[]{"Автор", "Александр Дюма",
        //        "Алексей Толстой"});
        //bookName = new JTextField("Название книги");
        report = new JButton("Справка и нарушения");

// Добавление компонентов на панель
        JPanel reportPanel = new JPanel();
        //reportPanel.add(author);
        //reportPanel.add(bookName);
        reportPanel.add(report);

// Размещение панели поиска внизу окна
        App.add(reportPanel, BorderLayout.SOUTH);

// Визуализация экранной формы
        App.setVisible(true);
    }
    // выбор активной таблицы
    private void selectTable(String select) { // выбор отображаемой таблицы
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
                columns = new String[] {"ID", "ФИО", "Возраст", "Опыт", "Зарплата", "Состояние"};
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
        model = new DefaultTableModel(data, columns) {
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
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
        scroll = new JScrollPane(table);
        // Размещение таблицы с данными
        App.add(scroll, BorderLayout.CENTER);
        // обновляем окно для корректного отображения новой таблицы
        App.setVisible(true);
    }
    // панель редактирования/добавления элементов
    private JPanel createEditPanel() throws ParseException {
        // создание панели с вводом данных для каждого из типов объектов
        JPanel editPanel = new JPanel();
        switch (tableSelect.getSelectedIndex()) {
            case 0: // автобус
                JLabel regLabel = new JLabel("Регистрационный номер:");
                MaskFormatter regFormatter = new MaskFormatter("U###UU");
                regFormatter.setPlaceholderCharacter('#');
                JFormattedTextField registr = new JFormattedTextField(regFormatter);
                JLabel capLabel = new JLabel("Вместительность:");
                MaskFormatter capFormatter = new MaskFormatter("##");
                capFormatter.setPlaceholderCharacter('0');
                JFormattedTextField capacity = new JFormattedTextField(capFormatter);

                JLabel driverLabel = new JLabel("Доступные водители:");
                List<String> driverNameList = em.createQuery("SELECT name FROM Driver d WHERE violation=null").getResultList();
                List<Integer> driverIDList = em.createQuery("SELECT id FROM Driver d WHERE violation=null").getResultList();
                // добавим вариант отсутствия водителя при создании
                driverNameList.add(0, "0 Отсутствует"); driverIDList.add(0, 0);
                // допишем ID каждому из имен для избежания путаницы
                for (int i=1; i<driverNameList.size(); i++) {
                    driverNameList.set(i, driverIDList.get(i).toString() + " " + driverNameList.get(i));
                }
                JComboBox driverSelect = new JComboBox(driverNameList.toArray());

                JLabel routeLabel = new JLabel("Доступные маршруты:");
                List<Route> routes = em.createQuery("SELECT r FROM Route r WHERE violation=null").getResultList();
                // сформируем варианты выбора для JComboBox'а в формате "НомерМаршрута Старт Финиш"
                List<String> routeBox = new ArrayList<>();
                routeBox.add("0 Отсутствует");
                for (Route route: routes) {
                    routeBox.add(route.toString());
                }
                JComboBox routeSelect = new JComboBox(routeBox.toArray());

                editPanel.setLayout(new GridLayout(4, 2, 5, 1));
                // добавляем все элементы на панель в формате сетки 4х2
                editPanel.add(regLabel); editPanel.add(registr);
                editPanel.add(capLabel); editPanel.add(capacity);
                editPanel.add(driverLabel); editPanel.add(driverSelect);
                editPanel.add(routeLabel); editPanel.add(routeSelect);
                break;
            case 1: // водитель
                JLabel nameLabel = new JLabel("ФИО");
                //MaskFormatter nameFormatter = new MaskFormatter("********************");
                //nameFormatter.setValidCharacters(" '-абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯqwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM");
                JFormattedTextField name = new JFormattedTextField();
                JLabel ageLabel = new JLabel("Возраст:");
                MaskFormatter ageFormatter = new MaskFormatter("##");
                ageFormatter.setPlaceholderCharacter('0');
                JFormattedTextField age = new JFormattedTextField(ageFormatter);
                JLabel expLabel = new JLabel("Стаж работы:");
                JFormattedTextField exp = new JFormattedTextField(ageFormatter);

                editPanel.setLayout(new GridLayout(3, 2, 5, 1));
                // добавляем все элементы на панель в формате сетки 3х2
                editPanel.add(nameLabel); editPanel.add(name);
                editPanel.add(ageLabel); editPanel.add(age);
                editPanel.add(expLabel); editPanel.add(exp);
                break;
            case 2: // маршрут
                JLabel numLabel = new JLabel("Номер маршрута:");
                MaskFormatter numFormatter = new MaskFormatter("###");
                numFormatter.setPlaceholderCharacter('0');
                numFormatter.setValidCharacters("0123456789");
                JFormattedTextField number = new JFormattedTextField(numFormatter);
                //MaskFormatter placeFormatter = new MaskFormatter("********************");
                //placeFormatter.setValidCharacters(" '-абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯqwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM");
                JLabel startLabel = new JLabel("Начало маршрута: ");
                JFormattedTextField start = new JFormattedTextField();
                JLabel finishLabel = new JLabel("Конечная остановка: ");
                JFormattedTextField finish = new JFormattedTextField();
                JLabel scheduleLabel = new JLabel("Расписание: ");
                JTextField schedule = new JTextField();
                JLabel emptyLabel = new JLabel(" ");
                JButton makeSchedule = new JButton("Составить расписание");
                ActionListener actionMakeSchedule = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            makeScheduleDialog(schedule);
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                    }
                };
                makeSchedule.addActionListener(actionMakeSchedule);

                editPanel.setLayout(new GridLayout(5, 2, 5, 1));
                // добавляем все элементы на панель в формате сетки 5x2
                editPanel.add(numLabel); editPanel.add(number);
                editPanel.add(startLabel); editPanel.add(start);
                editPanel.add(finishLabel); editPanel.add(finish);
                editPanel.add(scheduleLabel); editPanel.add(schedule);
                editPanel.add(emptyLabel); editPanel.add(makeSchedule);
                break;
        }
        return editPanel;
    }
    // панель составления расписания
    private void makeScheduleDialog(JTextField schedule) throws ParseException {
        JDialog dialog = new JDialog(App, "Составление расписания", true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setLocation(150, 150);

        String[] initicalSchedule = schedule.getText().split(";");
        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(7, 1, 5, 1));
        // добавляем все элементы на панель в формате сетки 7x1 и заполняем(если данные уже есть)
        for (int i=0; i<7; i++) {
            JPanel day = new JPanel();
            day.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel dayFrom = new JLabel(Days.values()[i] + " с"); // добавляем подпись дня недели
            // Определение маски и содание поля ввода времени телефона
            MaskFormatter timeFormatter = new MaskFormatter("##:##");
            timeFormatter.setPlaceholderCharacter('0');
            JFormattedTextField from = new JFormattedTextField(timeFormatter); // ввод времени начала работы
            JLabel toLabel = new JLabel("до"); // добавляем подпись дня недели
            JFormattedTextField to = new JFormattedTextField(timeFormatter); // ввод времени окончания работы
            JLabel intervalLabel = new JLabel("каждые"); // добавляем подпись дня недели
            MaskFormatter intervalFormatter = new MaskFormatter("##");
            intervalFormatter.setPlaceholderCharacter('0');
            JFormattedTextField interval = new JFormattedTextField(intervalFormatter); // форматированный ввод интервала
            JLabel minLabel = new JLabel("минут"); // добавляем подпись дня недели
            // Добавляем все на панель
            day.add(dayFrom); day.add(from); day.add(toLabel); day.add(to);
            day.add(intervalLabel); day.add(interval); day.add(minLabel);
            // заполняем поля для случая с редактированием записи
            if (initicalSchedule.length == 7) {
                from.setText(initicalSchedule[i].split(" ")[0]);
                to.setText(initicalSchedule[i].split(" ")[1]);
                interval.setText(initicalSchedule[i].split(" ")[2]);
            }
            schedulePanel.add(day);
        }
        dialog.add(schedulePanel, BorderLayout.CENTER);
        // добавляем кнопку подтверждения и слушателя к ней
        JButton confirm = new JButton("Подтвердить");
        ActionListener actionConfirmSchedule = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // задание расписания по умолчанию
                String str = "";
                boolean validData = true; // проверка корректности введённых данных
                // распаковали 7 дней
                Component[] daysComponents = schedulePanel.getComponents();
                for (int i=0; i<7; i++) {
                    // распакуем каждый из дней
                    Component[] dayComponents = ((JPanel) daysComponents[i]).getComponents();
                    // преобразуем все введённые данные в единую строку принятого формата
                    String from = ((JFormattedTextField)dayComponents[1]).getText(),
                            to = ((JFormattedTextField)dayComponents[3]).getText(),
                            interval = ((JFormattedTextField)dayComponents[5]).getText();
                    str += from + " " + to + " " + interval + ";";
                    // если время начала позже времени окончания ИЛИ указанное время некорректно ИЛИ интервал <3 минут
                    if (from.compareTo(to) >= 0 || from.compareTo("23:59") > 0 ||
                            to.compareTo("23:59") > 0 || interval.compareTo("03") < 0) {
                        validData = false;
                    }
                }
                if (validData == true) {
                    // закрыть окно JDialog и вывести сообщение об успешном добавлении
                    JOptionPane.showMessageDialog(dialog,"<html><h2>Успешно</h2><i>Расписание сформировано!</i>");
                    // выставляем составленное расписание
                    schedule.setText(str);
                    // закроем диалоговое окно, т.к. оно выполнило свою функцию
                    dialog.dispose();
                }
                else {
                    str = "09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;";
                    JOptionPane.showMessageDialog(dialog,"<html><h2>Ошибка</h2><i>Расписание составлено некорректно, " +
                            "попробуйте снова</i>", "Ошибка формирования", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        confirm.addActionListener(actionConfirmSchedule);
        dialog.add(confirm, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setVisible(true);
    }
    // добавление записей в каждую из таблиц
    private void addBus(String registr, int capacity, int id_driver, int id_route, Component component) throws WritingError, ValidationError {
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        Bus model = new Bus();
        if (!model.setRegistr(registr)) throw new ValidationError("Формат рег.номера должен быть вида А777АА");
        if (!model.setCapacity(capacity)) throw new ValidationError("Вместимость должна быть неотрицательна");
        em.persist(model);
        // Выбор водителя при добавлении
        Driver driver;
        if (id_driver > 0) {
            driver = em.find(Driver.class, id_driver);
            // если водитель работает на другом автобусе, то создаем подтверждающее диалоговое окно
            if (driver.getBus() != null) {
                int result = JOptionPane.showConfirmDialog(App, "Выбранный водитель уже работает на другом автобусе. Вы уверены?",
                        "Окно подтверждения", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result != JOptionPane.YES_OPTION)
                    throw new WritingError();
            }
            model.hireToDriver(driver);
        }
        // Выбор маршрута при добавлении
        Route route;
        if (id_route > 0 && id_driver > 0) { // без водителя маршрут не выбрать!
            route = em.find(Route.class, id_route);
            model.chooseRoute(route);
        }
        else if (id_route > 0 && id_driver <= 0) { // попытка выбрать маршрут без водителя
            JOptionPane.showMessageDialog(component,"<html><h2>Ошибка</h2><i>Автобус без водителя " +
                    "выставлять на маршрут запрещено!</i>", "Ошибка добавления", JOptionPane.ERROR_MESSAGE);
            throw new WritingError();
        }
        em.getTransaction().commit();
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.addRow(model.toTableFormat());
        // обновляем окно для корректного отображения новой таблицы
        dtm.fireTableDataChanged();
    }
    private void addDriver(String name, int age, int exp) {
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        Driver model = new Driver(name, age, exp);
        em.persist(model);
        em.getTransaction().commit();
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.addRow(model.toTableFormat());
        // обновляем окно для корректного отображения новой таблицы
        dtm.fireTableDataChanged();
    }
    private void addRoute(int number, String start, String finish, String schedule) {
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        Route model = new Route(number, start, finish, schedule);
        em.persist(model);
        em.getTransaction().commit();
        // получаем модель таблицы и добавляем в неё новую запись
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.addRow(model.toTableFormat());
        // обновляем окно для корректного отображения новой таблицы
        dtm.fireTableDataChanged();
    }
    private void createTableItem() throws ParseException {
        JDialog dialog = new JDialog(App, "Добавление записи", true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setLocation(150, 150);
        // панель создания/редактирования элемента
        JPanel editPanel = createEditPanel();
        dialog.add(editPanel, BorderLayout.CENTER);
        // кнопка добавления элемента
        JButton confirm = new JButton("Добавить запись");
        dialog.add(confirm, BorderLayout.SOUTH);
        ActionListener actionAddItem = e -> {
            try {
                // получаем все компоненты панели ввода данных
                Component[] components = editPanel.getComponents();
                // в зависимости от выбранной таблицы получаем введенные данные и передаем в функцию создания новой записи
                switch (tableSelect.getSelectedIndex()) {
                    // автобус
                    case 0:
                        String registr = ((JTextField) components[1]).getText();
                        int capacity = Integer.parseInt(((JTextField) components[3]).getText());
                        int id_driver = Integer.parseInt(((String) ((JComboBox) components[5]).getSelectedItem()).split(" ")[0]);
                        int id_route = Integer.parseInt(((String) ((JComboBox) components[7]).getSelectedItem()).split(" ")[0]);
                        addBus(registr, capacity, id_driver, id_route, dialog);
                        break;
                    // водитель
                    case 1:
                        String name = ((JTextField) components[1]).getText();
                        int age = Integer.parseInt(((JTextField) components[3]).getText());
                        int exp = Integer.parseInt(((JTextField) components[5]).getText());
                        addDriver(name, age, exp);
                        break;
                    // маршрут
                    case 2:
                        int number = Integer.parseInt(((JTextField) components[1]).getText());
                        String start = ((JTextField) components[3]).getText();
                        String finish = ((JTextField) components[5]).getText();
                        String schedule = ((JTextField) components[7]).getText();
                        addRoute(number, start, finish, schedule);
                        break;
                }
                // закрыть окно JDialog и вывести сообщение об успешном добавлении
                JOptionPane.showMessageDialog(dialog, "<html><h2>Успешно</h2><i>Элемент добавлен</i>");
                // обновим таблицу для отображения изменений
                selectTable((String) tableSelect.getSelectedItem());
                // закроем диалоговое окно, т.к. оно выполнило свою функцию
                dialog.dispose();
            }
            catch (WritingError exc) {
                JOptionPane.showMessageDialog(dialog,"<html><h2>Ошибка</h2><i>Элемент не может быть добавлен, " +
                        "попробуйте снова</i>", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
            catch (ValidationError exc) {
                JOptionPane.showMessageDialog(dialog,"<html><h2>Ошибка</h2>"+exc.getHelp(),
                        exc.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        };
        confirm.addActionListener(actionAddItem);

        dialog.pack();
        dialog.setVisible(true);
    }
    // редактирование записей в каждой из таблиц
    private void editBus(Bus model, Component[] components, Component component) throws WritingError, ValidationError {
        // получаем введённые данные(если они были)
        String registr = ((JTextField)components[1]).getText();
        int capacity = Integer.parseInt( ((JTextField)components[3]).getText() );
        int id_driver = Integer.parseInt(((String)((JComboBox)components[5]).getSelectedItem()).split(" ")[0]);
        int id_route = Integer.parseInt(((String)((JComboBox)components[7]).getSelectedItem()).split(" ")[0]);

        // изменяем модель
        if (!model.setRegistr(registr)) throw new ValidationError("Формат рег.номера должен быть вида А777АА");
        if (!model.setCapacity(capacity)) throw new ValidationError("Вместимость должна быть неотрицательна");
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        em.persist(model);
        // Выбор(удаление) водителя при изменении
        Driver newDriver = em.find(Driver.class, id_driver);
        if (newDriver != null) {
            // если водитель работает на другом автобусе, то создаем подтверждающее диалоговое окно
            if (newDriver.getBus() != null && newDriver.getBus() != model) {
                int result = JOptionPane.showConfirmDialog(App, "Выбранный водитель уже работает на другом автобусе. Вы уверены?",
                        "Окно подтверждения", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result != JOptionPane.YES_OPTION)
                    throw new WritingError();
            }
            model.hireToDriver(newDriver);
        }
        // иначе если требуется уволить водителя
        else if (model.getDriver() != null) {
            model.fireToDriver();
        }
        // Выбор(удаление) маршрута при изменении
        Route newRoute = em.find(Route.class, id_route);
        if (newRoute != null) {
            if ( id_driver > 0 ) {
                model.chooseRoute(newRoute);
            }
            else if (id_driver == 0) {
                JOptionPane.showMessageDialog(component,"<html><h2>Ошибка</h2><i>Автобус без водителя " +
                        "выставлять на маршрут запрещено!</i>", "Ошибка редактирования", JOptionPane.ERROR_MESSAGE);
                throw new WritingError();
            }
        }
        else if (model.getRoute() != null) {
            model.chooseRoute(null);
        }
        em.getTransaction().commit();
    }
    private void editDriver(Driver model, Component[] components, Component component) {
        // получаем введённые данные
        String name = ((JTextField)components[1]).getText();
        int age = Integer.parseInt( ((JTextField)components[3]).getText() );
        int exp = Integer.parseInt( ((JTextField)components[5]).getText() );

        // изменяем модель
        model.setName(name);
        model.setAge(age);
        model.setExp(exp);

        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        em.persist(model);
        em.getTransaction().commit();
    }
    private void editRoute(Route model, Component[] components, Component component) {
        // получаем введённые данные
        int number = Integer.parseInt( ((JTextField)components[1]).getText() );
        String start = ((JTextField)components[3]).getText();
        String finish = ((JTextField)components[5]).getText();
        String schedule = ((JTextField)components[7]).getText();

        // изменяем модель
        model.setNumber(number);
        model.setStart(start);
        model.setFinish(finish);
        model.setSchedule(schedule);

        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        em.persist(model);
        em.getTransaction().commit();
    }
    private void editTableItem() throws ParseException {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int rowIndex = table.getSelectedRow();
        if (rowIndex > -1) {
            // создаём диалоговое окно редактирования
            JDialog dialog = new JDialog(App, "Редактирование записи", true);
            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dialog.setLocation(150, 150);
            // панель создания/редактирования элемента
            JPanel editPanel = createEditPanel();
            dialog.add(editPanel, BorderLayout.CENTER);
            // получаем все компоненты панели ввода данных
            Component[] components = editPanel.getComponents();
            // кнопка редактирования элемента
            JButton confirm = new JButton("Изменить запись");
            dialog.add(confirm, BorderLayout.SOUTH);
            // достаём ID редактируемого элемента
            int ID = Integer.parseInt((String) dtm.getValueAt(rowIndex, 0));
            if (!em.getTransaction().isActive()) em.getTransaction().begin();
            // заполняем editPanel исходными данными
            switch (tableSelect.getSelectedIndex()) {
                // автобус
                case 0:
                    Bus bus = em.find(Bus.class, ID);
                    ((JTextField) components[1]).setText(bus.getRegistr());
                    ((JTextField) components[3]).setText(Integer.toString(bus.getCapacity()));
                    if (bus.getDriver() != null)
                        ((JComboBox) components[5]).setSelectedItem(bus.getDriver().getId() + " " + bus.getDriver().getName());
                    if (bus.getRoute() != null)
                        ((JComboBox) components[7]).setSelectedItem(bus.getRoute().toString());
                    break;
                // водитель
                case 1:
                    Driver driver = em.find(Driver.class, ID);
                    ((JTextField) components[1]).setText(driver.getName());
                    ((JTextField) components[3]).setText(Integer.toString(driver.getAge()));
                    ((JTextField) components[5]).setText(Integer.toString(driver.getExp()));
                    break;
                // маршрут
                case 2:
                    Route route = em.find(Route.class, ID);
                    ((JTextField) components[1]).setText(Integer.toString(route.getNumber()));
                    ((JTextField) components[3]).setText(route.getStart());
                    ((JTextField) components[5]).setText(route.getFinish());
                    ((JTextField) components[7]).setText(route.getSchedule().getDays());
                    break;
            }
            // создаём слушатель для кнопки-подтверждения
            ActionListener actionEditItem = e -> {
                try {
                    // в зависимости от выбранной таблицы получаем введенные данные и передаем в функцию создания новой записи
                    switch (tableSelect.getSelectedIndex()) {
                        // автобус
                        case 0:
                            Bus bus = em.find(Bus.class, ID);
                            editBus(bus, components, dialog);
                            break;
                        // водитель
                        case 1:
                            Driver driver = em.find(Driver.class, ID);
                            editDriver(driver, components, dialog);
                            break;
                        // маршрут
                        case 2:
                            Route route = em.find(Route.class, ID);
                            editRoute(route, components, dialog);
                            break;
                    }
                    // закрыть окно JDialog и вывести сообщение об успешном добавлении
                    JOptionPane.showMessageDialog(dialog, "<html><h2>Успешно</h2><i>Элемент отредактирован</i>");
                    // обновим таблицу для отображения изменений
                    selectTable((String) tableSelect.getSelectedItem());
                    // закроем диалоговое окно, т.к. оно выполнило свою функцию
                    dialog.dispose();
                }
                catch (WritingError exc) {
                    JOptionPane.showMessageDialog(dialog,"<html><h2>Ошибка</h2><i>Элемент не может быть изменён, " +
                            "попробуйте снова</i>", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
                catch (ValidationError exc) {
                    JOptionPane.showMessageDialog(dialog,"<html><h2>Ошибка</h2>"+exc.getHelp(),
                            exc.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            };
            confirm.addActionListener(actionEditItem);

            dialog.pack();
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(App, "<html><h2>Ошибка</h2><i>Для редактирования элемента " +
                    "выделите соответствующую строку!</i>", "Ошибка Редактирования", JOptionPane.ERROR_MESSAGE);
        }
    }
    // удаление записей
    private boolean removeTableItem() {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        //int rowIndex = table.getSelectedRow();
        int indexes[] = table.getSelectedRows();

        // достаем ID удаляемого объекта в выделенной строке
        if (indexes.length > 0) {
            // проверка, чтобы случайно не удалить
            int result = JOptionPane.showConfirmDialog(App, "Вы точно хотите удалить выбранную строку(-и)?",
                    "Окно подтверждения", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                for (int rowIndex: indexes) {
                    // удаление элемента из БД и сразу визуально из таблицы
                    int ID = Integer.parseInt((String) dtm.getValueAt(rowIndex, 0));
                    if (!em.getTransaction().isActive()) em.getTransaction().begin();
                    // увольняем водителя(и если автобус был на маршруте, то он также удалится)
                    // снимаем все автобусы с маршрута, чтобы не удалить вместе
                    switch (tableSelect.getSelectedIndex()) {
                        // удаляем автобус
                        case 0:
                            Bus bus = em.find(Bus.class, ID);
                            bus.fireToDriver();
                            em.remove(bus);
                            break;
                        // удаляем водителя
                        case 1:
                            Driver driver = em.find(Driver.class, ID);
                            if (driver.getBus() != null)
                                driver.getBus().fireToDriver();
                            em.remove(driver);
                            break;
                        // удаляем маршрут
                        case 2:
                            Route route = em.find(Route.class, ID);
                            route.exclude();
                            em.remove(route);
                            break;
                    }
                    em.getTransaction().commit();
                }
                // удаляем визуально из таблицы в приложении
                for (int i = indexes.length - 1; i>=0; --i) {
                    dtm.removeRow(indexes[i]);
                }
                dtm.fireTableDataChanged();

                return true;
            }
            else return false;
        }
        else {
            JOptionPane.showMessageDialog(App,"<html><h2>Ошибка</h2><i>Для удаления элемента " +
                    "выделите соответствующую строку!</i>", "Ошибка Удаления", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    // фиксация нарушений
    protected static void fixABusViolation(JTable table, int ID, String violation_description) {
        if (!em.getTransaction().isActive()) em.getTransaction().begin();

        Bus model = em.find(Bus.class, ID);
        violation_description = violation_description + ": " + model.getRegistr();
        Violation violation = null;
        if (model.getViolation() != null) {
            Violation fix = em.find(Violation.class, model.getViolation().getId());
            fix.setFixDate(new Date());
            if (model.getViolation().getDescription().equals(violation_description)) violation_description = "";
        }
        if (violation_description.length() > 0) {
            violation = new Violation(violation_description);
        }
        model.setViolation(violation);

        em.getTransaction().commit();
        // обновляем данные в таблице приложения
        DefaultTableModel dtm = ((DefaultTableModel) table.getModel());
        dtm.setValueAt(violation_description.length() > 0 ? violation_description : "Ок", table.getSelectedRow(), table.getColumnCount()-1);
        dtm.fireTableDataChanged();
    }
    protected static void fixADriverViolation(JTable table, int ID, String violation_description) {
        if (!em.getTransaction().isActive()) em.getTransaction().begin();

        Driver model = em.find(Driver.class, ID);
        violation_description = violation_description + ": " + model.getName();
        Violation violation = null;
        if (model.getViolation() != null) {
            Violation fix = em.find(Violation.class, model.getViolation().getId());
            fix.setFixDate(new Date());
            if (model.getViolation().getDescription().equals(violation_description)) violation_description = "";
        }
        if (violation_description.length() > 0) {
            violation = new Violation(violation_description);
        }
        model.setViolation(violation);

        em.getTransaction().commit();
        // обновляем данные в таблице приложения
        DefaultTableModel dtm = ((DefaultTableModel) table.getModel());
        dtm.setValueAt(violation_description.length() > 0 ? violation_description : "Ок", table.getSelectedRow(), table.getColumnCount()-1);
        dtm.fireTableDataChanged();
    }
    protected static void fixARouteViolation(JTable table, int ID, String violation_description) {
        if (!em.getTransaction().isActive()) em.getTransaction().begin();

        Route model = em.find(Route.class, ID);
        violation_description = violation_description + ": №" + model.getNumber();
        Violation violation = null;
        if (model.getViolation() != null) {
            Violation fix = em.find(Violation.class, model.getViolation().getId());
            fix.setFixDate(new Date());
            if (model.getViolation().getDescription().equals(violation_description)) violation_description = "";
        }
        if (violation_description.length() > 0) {
            violation = new Violation(violation_description);
        }
        model.setViolation(violation);

        em.getTransaction().commit();
        // обновляем данные в таблице приложения
        DefaultTableModel dtm = ((DefaultTableModel) table.getModel());
        dtm.setValueAt(violation_description.length() > 0 ? violation_description : "Ок", table.getSelectedRow(), table.getColumnCount()-1);
        dtm.fireTableDataChanged();
    }
}
