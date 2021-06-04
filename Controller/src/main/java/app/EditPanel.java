package app;

import entitys.Route;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс панели для редактирования объектов
 * Предназанчена для добавления в уже созданное диалоговое окно parent
 * @author Дмитрий Дементьев 9308
 * @version 0.2
 */
public class EditPanel extends JPanel{
    /**
     * Конструктор объекта EditPanel
     * @param parent - диалоговое окно-родитель, из которого создаётся панель редактирования
     * @throws ParseException - некорректная запись в поле с форматированным вводом
     */
    protected EditPanel(JDialog parent) throws ParseException {
        super();
        switch (App.tableSelect.getSelectedIndex()) {
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
                List<String> driverNameList = App.em.createQuery("SELECT name FROM Driver d WHERE violation=null").getResultList();
                List<Integer> driverIDList = App.em.createQuery("SELECT id FROM Driver d WHERE violation=null").getResultList();
                // добавим вариант отсутствия водителя при создании
                driverNameList.add(0, "0 Отсутствует"); driverIDList.add(0, 0);
                // допишем ID каждому из имен для избежания путаницы
                for (int i=1; i<driverNameList.size(); i++) {
                    driverNameList.set(i, driverIDList.get(i).toString() + " " + driverNameList.get(i));
                }
                JComboBox driverSelect = new JComboBox(driverNameList.toArray());

                JLabel routeLabel = new JLabel("Доступные маршруты:");
                List<Route> routes = App.em.createQuery("SELECT r FROM Route r WHERE violation=null").getResultList();
                // сформируем варианты выбора для JComboBox'а в формате "НомерМаршрута Старт Финиш"
                List<String> routeBox = new ArrayList<>();
                routeBox.add("0 Отсутствует");
                for (Route route: routes) {
                    routeBox.add(route.toString());
                }
                JComboBox routeSelect = new JComboBox(routeBox.toArray());

                this.setLayout(new GridLayout(4, 2, 5, 1));
                // добавляем все элементы на панель в формате сетки 4х2
                this.add(regLabel); this.add(registr);
                this.add(capLabel); this.add(capacity);
                this.add(driverLabel); this.add(driverSelect);
                this.add(routeLabel); this.add(routeSelect);
                break;
            case 1: // водитель
                JLabel nameLabel = new JLabel("ФUО");
                JFormattedTextField name = new JFormattedTextField();
                JLabel ageLabel = new JLabel("Возраст:");
                MaskFormatter ageFormatter = new MaskFormatter("##");
                ageFormatter.setPlaceholderCharacter('0');
                JFormattedTextField age = new JFormattedTextField(ageFormatter);
                JLabel expLabel = new JLabel("Стаж работы:");
                JFormattedTextField exp = new JFormattedTextField(ageFormatter);

                this.setLayout(new GridLayout(3, 2, 5, 1));
                // добавляем все элементы на панель в формате сетки 3х2
                this.add(nameLabel); this.add(name);
                this.add(ageLabel); this.add(age);
                this.add(expLabel); this.add(exp);
                break;
            case 2: // маршрут
                JLabel numLabel = new JLabel("Номер маршрута:");
                MaskFormatter numFormatter = new MaskFormatter("###");
                numFormatter.setPlaceholderCharacter('0');
                numFormatter.setValidCharacters("0123456789");
                JFormattedTextField number = new JFormattedTextField(numFormatter);
                JLabel startLabel = new JLabel("Начало маршрута: ");
                JFormattedTextField start = new JFormattedTextField();
                JLabel finishLabel = new JLabel("Конечная остановка: ");
                JFormattedTextField finish = new JFormattedTextField();
                JLabel scheduleLabel = new JLabel("Расписание: ");
                JTextField schedule = new JTextField();
                JLabel emptyLabel = new JLabel(" ");
                JButton makeSchedule = new JButton("Составить расписание");
                ActionListener actionMakeSchedule = e -> {
                    try {
                        new ScheduleDialog(parent, schedule);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                };
                makeSchedule.addActionListener(actionMakeSchedule);

                this.setLayout(new GridLayout(5, 2, 5, 1));
                // добавляем все элементы на панель в формате сетки 5x2
                this.add(numLabel); this.add(number);
                this.add(startLabel); this.add(start);
                this.add(finishLabel); this.add(finish);
                this.add(scheduleLabel); this.add(schedule);
                this.add(emptyLabel); this.add(makeSchedule);
                break;
        }
    }
}
