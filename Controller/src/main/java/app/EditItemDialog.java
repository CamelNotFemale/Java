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
 * Класс окна для редактирования записей
 * Редактор Элементов Таблицы
 * @author Дмитрий Дементьев 9308
 * @version 0.2
 */
public class EditItemDialog extends JDialog {
    /**
     * Конструктор объекта EditItemDialog
     * @param frame - фрейм-родитель, из которого создаётся Редактор Элементов Таблицы
     * @param _table - таблица данных для редактирования
     * @throws ParseException - некорректная запись в поле с форматированным вводом
     */
    protected EditItemDialog(App frame, JTable _table) throws ParseException {
        super(frame, "Редактирование записи", true);
        DefaultTableModel dtm = (DefaultTableModel) _table.getModel();
        int rowIndex = _table.getSelectedRow();
        if (rowIndex > -1) {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setLocation(150, 150);
            // панель создания/редактирования элемента
            JPanel editPanel = new EditPanel(this);
            this.add(editPanel, BorderLayout.CENTER);
            // получаем все компоненты панели ввода данных
            Component[] components = editPanel.getComponents();
            // кнопка редактирования элемента
            JButton confirm = new JButton("Изменить запись");
            this.add(confirm, BorderLayout.SOUTH);
            // достаём ID редактируемого элемента
            int ID = Integer.parseInt((String) dtm.getValueAt(rowIndex, 0));
            if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();
            // заполняем editPanel исходными данными
            switch (App.tableSelect.getSelectedIndex()) {
                // автобус
                case 0:
                    Bus bus = App.em.find(Bus.class, ID);
                    ((JTextField) components[1]).setText(bus.getRegistr());
                    ((JTextField) components[3]).setText(Integer.toString(bus.getCapacity()));
                    if (bus.getDriver() != null)
                        ((JComboBox) components[5]).setSelectedItem(bus.getDriver().getId() + " " + bus.getDriver().getName());
                    if (bus.getRoute() != null)
                        ((JComboBox) components[7]).setSelectedItem(bus.getRoute().toString());
                    break;
                // водитель
                case 1:
                    Driver driver = App.em.find(Driver.class, ID);
                    ((JTextField) components[1]).setText(driver.getName());
                    ((JTextField) components[3]).setText(Integer.toString(driver.getAge()));
                    ((JTextField) components[5]).setText(Integer.toString(driver.getExp()));
                    break;
                // маршрут
                case 2:
                    Route route = App.em.find(Route.class, ID);
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
                    switch (App.tableSelect.getSelectedIndex()) {
                        // автобус
                        case 0:
                            Bus bus = App.em.find(Bus.class, ID);
                            editBus(bus, components, this);
                            break;
                        // водитель
                        case 1:
                            Driver driver = App.em.find(Driver.class, ID);
                            editDriver(driver, components, this);
                            break;
                        // маршрут
                        case 2:
                            Route route = App.em.find(Route.class, ID);
                            editRoute(route, components, this);
                            break;
                    }
                    // закрыть окно JDialog и вывести сообщение об успешном добавлении
                    JOptionPane.showMessageDialog(this, "<html><h2>Успешно</h2><i>Элемент отредактирован</i>");
                    // обновим таблицу для отображения изменений
                    frame.selectTable((String) App.tableSelect.getSelectedItem());
                    // закроем диалоговое окно, т.к. оно выполнило свою функцию
                    this.dispose();
                }
                catch (WritingError exc) {
                    JOptionPane.showMessageDialog(this,"<html><h2>Ошибка</h2><i>Элемент не может быть изменён, " +
                            "попробуйте снова</i>", exc.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
                catch (ValidationError exc) {
                    JOptionPane.showMessageDialog(this,"<html><h2>Ошибка</h2>"+exc.getHelp(),
                            exc.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            };
            confirm.addActionListener(actionEditItem);

            this.pack();
            this.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "<html><h2>Ошибка</h2><i>Для редактирования элемента " +
                    "выделите соответствующую строку!</i>", "Ошибка Редактирования", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Функция, редактирующая объект Автобуса
     * Редактирование осуществляется сразу и в БД, и визуально в приложении
     * @param model - ссылка на автобус для редактирования
     * @param components - компоненты с данными о новых введённых параметрах (компоненты EditPanel)
     * @param component - окно-родитель, из которого производится редактирование (EditItemDialog)
     * @throws WritingError - ошибка при неудачном редактировании записи
     * @throws ValidationError - ошибка некорректного ввода данных для информационных полей
     */
    private void editBus(Bus model, Component[] components, Component component) throws WritingError, ValidationError {
        // получаем введённые данные(если они были)
        String registr = ((JTextField)components[1]).getText();
        int capacity = Integer.parseInt( ((JTextField)components[3]).getText() );
        int id_driver = Integer.parseInt(((String)((JComboBox)components[5]).getSelectedItem()).split(" ")[0]);
        int id_route = Integer.parseInt(((String)((JComboBox)components[7]).getSelectedItem()).split(" ")[0]);

        // изменяем модель
        if (!model.setRegistr(registr)) throw new ValidationError("Формат рег.номера должен быть вида А777АА");
        if (!model.setCapacity(capacity)) throw new ValidationError("Вместимость должна быть неотрицательна");
        if (model.getViolation() != null) throw new ValidationError(model.getViolation().getDescription());
        if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();
        App.em.persist(model);
        // Выбор(удаление) водителя при изменении
        Driver newDriver = App.em.find(Driver.class, id_driver);
        if (newDriver != null) {
            if (newDriver.getViolation() != null) throw new ValidationError(newDriver.getViolation().getDescription());
            // если водитель работает на другом автобусе, то создаем подтверждающее диалоговое окно
            if (newDriver.getBus() != null && newDriver.getBus() != model) {
                int result = JOptionPane.showConfirmDialog(component, "Выбранный водитель уже работает на другом автобусе. Вы уверены?",
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
        Route newRoute = App.em.find(Route.class, id_route);
        if (newRoute != null) {
            if (newRoute.getViolation() != null) throw new ValidationError(newRoute.getViolation().getDescription());
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
        App.em.getTransaction().commit();
    }
    /**
     * Функция, редактирующая объект Водитель
     * Редактирование осуществляется сразу и в БД, и визуально в приложении
     * @param model - ссылка на водителя для редактирования
     * @param components - компоненты с данными о новых введённых параметрах (компоненты EditPanel)
     * @param component - окно-родитель, из которого производится редактирование (EditItemDialog)
     */
    private void editDriver(Driver model, Component[] components, Component component) {
        // получаем введённые данные
        String name = ((JTextField)components[1]).getText();
        int age = Integer.parseInt( ((JTextField)components[3]).getText() );
        int exp = Integer.parseInt( ((JTextField)components[5]).getText() );

        // изменяем модель
        model.setName(name);
        model.setAge(age);
        model.setExp(exp);

        if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();
        App.em.persist(model);
        App.em.getTransaction().commit();
    }
    /**
     * Функция, редактирующая объект Маршрут
     * Редактирование осуществляется сразу и в БД, и визуально в приложении
     * @param model - ссылка на маршрут для редактирования
     * @param components - компоненты с данными о новых введённых параметрах (компоненты EditPanel)
     * @param component - окно-родитель, из которого производится редактирование (EditItemDialog)
     */
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

        if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();
        App.em.persist(model);
        App.em.getTransaction().commit();
    }
}
