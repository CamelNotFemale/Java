package app;

import entitys.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * Класс поп-ап меню для таблицы
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class TablePopupMenu extends JPopupMenu {
    /**
     * Конструктор - создание нового объекта TablePopupMenu
     * @param table - таблица данных, для которой создается поп-ап меню
     * @param select - идентификатор выбранной таблицы(название)
     */
    public TablePopupMenu(JTable table, String select) {
        super();
        // автоматически выбрать строку, в которой был сделан щелчок правой кнопкой мыши
        JPopupMenu p = this; // сохранение ссылки для последующей работы
        this.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(p, new Point(0, 0), table));
                        if (rowAtPoint > -1) {
                            table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // TODO Auto-generated method stub
                //System.out.println("i will be back");
            }
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                // TODO Auto-generated method stub
                //System.out.println("good bye");
            }
        });

        // добавление пунктов "Справка" в PopupMenu
        switch (select) {
            case "Автобусы":
                break;
            case "Водители":
                break;
            case "Маршруты":
                JMenuItem viewSchedule = new JMenuItem("Расписание");
                viewSchedule.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String num = table.getValueAt(table.getSelectedRow(), 1).toString(),
                        schedule = table.getValueAt(table.getSelectedRow(), 4).toString();
                        new ScheduleViewer(num, schedule);
                    }
                });
                this.add(viewSchedule);
                break;
        }
        // добавление пунктов "Нарушений" в PopupMenu
        ArrayList<JMenuItem> problems_list = new ArrayList<>();
        switch (select) {
            case "Автобусы":
                problems_list.add(new JMenuItem("Поломка"));
                problems_list.add(new JMenuItem("ДТП"));
                break;
            case "Водители":
                problems_list.add(new JMenuItem("Прогул"));
                problems_list.add(new JMenuItem("Болезнь"));
                break;
            case "Маршруты":
                problems_list.add(new JMenuItem("Ремонт"));
                problems_list.add(new JMenuItem("Крупное ДТП"));
                break;
        }
        if (problems_list.size() > 0) {
            for (JMenuItem problem: problems_list) {
                this.add(problem);
                problem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // вызываем фиксацию нарушения
                        switch (select) {
                            case "Автобусы":
                                App.fixABusViolation(table, Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0)), problem.getText());
                                break;
                            case "Водители":
                                App.fixADriverViolation(table, Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0)), problem.getText());
                                break;
                            case "Маршруты":
                                App.fixARouteViolation(table, Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0)), problem.getText());
                                break;
                        }
                    }
                });
            }
        }
    }
}
/**
 * Класс просмотра расписания маршрута
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
class ScheduleViewer extends JFrame {
    /**
     * Конструктор - создание нового объекта ScheduleViewer
     * @param num - номер маршрута, для которого вызывается расписание
     * @param schedule - расписание маршрута
     */
    ScheduleViewer(String num, String schedule) {
        super("Маршрут №" + num);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(150, 150);

        String[] initicalSchedule = schedule.split(";");

        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(7, 1, 5, 1));
        // добавляем все элементы на панель в формате сетки 7x1 и заполняем(если данные уже есть)
        for (int i=0; i<7; i++) {
            JPanel day = new JPanel();
            day.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel dayFrom = new JLabel(Days.values()[i] + " с"); // добавляем подпись дня недели
            // Определение маски и содание поля ввода времени телефона
            JTextField from = new JTextField(); // ввод времени начала работы
            JLabel toLabel = new JLabel("до"); // добавляем подпись дня недели
            JTextField to = new JTextField(); // ввод времени окончания работы
            JLabel intervalLabel = new JLabel("каждые"); // добавляем подпись дня недели
            JTextField interval = new JTextField(); // форматированный ввод интервала
            JLabel minLabel = new JLabel("минут"); // добавляем подпись дня недели
            // Добавляем все на панель
            day.add(dayFrom); day.add(from); day.add(toLabel); day.add(to);
            day.add(intervalLabel); day.add(interval); day.add(minLabel);
            // заполняем поля для случая с редактированием записи
            from.setText(initicalSchedule[i].split(" ")[0]);
            to.setText(initicalSchedule[i].split(" ")[1]);
            interval.setText(initicalSchedule[i].split(" ")[2]);
            schedulePanel.add(day);
        }
        add(schedulePanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }
}

