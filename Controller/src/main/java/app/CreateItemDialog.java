package app;

import entitys.Bus;
import entitys.Driver;
import entitys.Route;
import utils.ValidationError;
import utils.WritingError;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
/**
 * Класс окна для создания новых записей
 * @author Дмитрий Дементьев 9308
 * @version 0.2
 */
public class CreateItemDialog extends JDialog {
    /** Поле со ссылкой на редактируемую таблицу данных */
    private JTable table = null;
    /**
     * Конструктор объекта CreateItemDialog
     * @param frame - фрейм-родитель, из которого создаётся Редактор Элементов Таблицы
     * @param _table - таблица данных для добавления новых записей
     * @throws ParseException - некорректная запись в поле с форматированным вводом
     */
    protected CreateItemDialog(App frame, JTable _table) throws ParseException {
        super(frame, "Добавление записи", true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation(150, 150);
        // сохранение ссылки на таблицу для функций добавления
        table = _table;
        // панель создания/редактирования элемента
        JPanel editPanel = new EditPanel(this);
        this.add(editPanel, BorderLayout.CENTER);
        // кнопка добавления элемента
        JButton confirm = new JButton("Добавить запись");
        this.add(confirm, BorderLayout.SOUTH);
        ActionListener actionAddItem = e -> {
            try {
                // получаем все компоненты панели ввода данных
                Component[] components = editPanel.getComponents();
                // в зависимости от выбранной таблицы получаем введенные данные и передаем в функцию создания новой записи
                switch (App.tableSelect.getSelectedIndex()) {
                    // автобус
                    case 0:
                        String registr = ((JTextField) components[1]).getText();
                        int capacity = Integer.parseInt(((JTextField) components[3]).getText());
                        int id_driver = Integer.parseInt(((String) ((JComboBox) components[5]).getSelectedItem()).split(" ")[0]);
                        int id_route = Integer.parseInt(((String) ((JComboBox) components[7]).getSelectedItem()).split(" ")[0]);
                        addBus(registr, capacity, id_driver, id_route, this);
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
                JOptionPane.showMessageDialog(this, "<html><h2>Успешно</h2><i>Элемент добавлен</i>");
                // обновим таблицу для отображения изменений
                frame.selectTable((String) App.tableSelect.getSelectedItem());
                // закроем диалоговое окно, т.к. оно выполнило свою функцию
                this.dispose();
            }
            catch (WritingError exc) {
                JOptionPane.showMessageDialog(this,"<html><h2>Ошибка</h2><i>Элемент не может быть добавлен, " +
                        "попробуйте снова</i>", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
            catch (ValidationError exc) {
                JOptionPane.showMessageDialog(this,"<html><h2>Ошибка</h2>"+exc.getHelp(),
                        exc.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        };
        confirm.addActionListener(actionAddItem);

        this.pack();
        this.setVisible(true);
    }
    /**
     * Функция, создающая новый элемент Автобус
     * Добавление записи осуществляется сразу и в БД, и визуально в приложении
     * @param registr - регистрационный номер автобуса
     * @param capacity - вместительность автобуса
     * @param id_driver - уникальный идентификатор нанимаемого водителя
     * @param id_route - уникальный идентификатор выбираемого маршрута
     * @param component - окно-родитель, из которого производится редактирование (EditItemDialog)
     * @throws WritingError - ошибка при неудачном редактировании записи
     * @throws ValidationError - ошибка некорректного ввода данных для информационных полей
     */
    private void addBus(String registr, int capacity, int id_driver, int id_route, Component component) throws WritingError, ValidationError {
        if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();
        Bus model = new Bus();
        if (!model.setRegistr(registr)) throw new ValidationError("Формат рег.номера должен быть вида А777АА");
        if (!model.setCapacity(capacity)) throw new ValidationError("Вместимость должна быть неотрицательна");
        App.em.persist(model);
        // Выбор водителя при добавлении
        Driver driver;
        if (id_driver > 0) {
            driver = App.em.find(Driver.class, id_driver);
            if (driver.getViolation() != null) throw new ValidationError(driver.getViolation().getDescription());
            // если водитель работает на другом автобусе, то создаем подтверждающее диалоговое окно
            if (driver.getBus() != null) {
                int result = JOptionPane.showConfirmDialog(this, "Выбранный водитель уже работает на другом автобусе. Вы уверены?",
                        "Окно подтверждения", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result != JOptionPane.YES_OPTION)
                    throw new WritingError();
            }
            model.hireToDriver(driver);
        }
        // Выбор маршрута при добавлении
        Route route;
        if (id_route > 0 && id_driver > 0) { // без водителя маршрут не выбрать!
            route = App.em.find(Route.class, id_route);
            if (route.getViolation() != null) throw new ValidationError(route.getViolation().getDescription());
            model.chooseRoute(route);
        }
        else if (id_route > 0 && id_driver <= 0) { // попытка выбрать маршрут без водителя
            JOptionPane.showMessageDialog(component,"<html><h2>Ошибка</h2><i>Автобус без водителя " +
                    "выставлять на маршрут запрещено!</i>", "Ошибка добавления", JOptionPane.ERROR_MESSAGE);
            throw new WritingError();
        }
        App.em.getTransaction().commit();
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.addRow(model.toTableFormat());
        // обновляем окно для корректного отображения новой таблицы
        dtm.fireTableDataChanged();
    }
    /**
     * Функция, создающая новый элемент Водитель
     * Добавление записи осуществляется сразу и в БД, и визуально в приложении
     * @param name - ФИО водителя
     * @param age - возраст
     * @param exp - опыт работы
     */
    private void addDriver(String name, int age, int exp) {
        if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();
        Driver model = new Driver(name, age, exp);
        App.em.persist(model);
        App.em.getTransaction().commit();
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.addRow(model.toTableFormat());
        // обновляем окно для корректного отображения новой таблицы
        dtm.fireTableDataChanged();
    }
    /**
     * Функция, создающая новый элемент Маршрут
     * Добавление записи осуществляется сразу и в БД, и визуально в приложении
     * @param number - номер маршрута
     * @param start - точка начала движения
     * @param finish - точка окончания движения
     * @param schedule - недельное расписание движения
     *                 формат задания расписания, где расписание дня недели выглядит так: 09:00 21:00;
     */
    private void addRoute(int number, String start, String finish, String schedule) {
        if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();
        Route model = new Route(number, start, finish, schedule);
        App.em.persist(model);
        App.em.getTransaction().commit();
        // получаем модель таблицы и добавляем в неё новую запись
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.addRow(model.toTableFormat());
        // обновляем окно для корректного отображения новой таблицы
        dtm.fireTableDataChanged();
    }
}
