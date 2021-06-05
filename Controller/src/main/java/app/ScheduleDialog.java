package app;

import entitys.Days;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
/**
 * Класс диалогового окна для задания расписания
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class ScheduleDialog extends JDialog {
    /**
     * Конструктор - создание нового объекта ScheduleDialog
     * @param parent - ссылка на окно-родитель, вызывающего новый объект ScheduleDialog
     * @param schedule - текстовое поле, содержащее расписание
     * @throws ParseException - некорректные данные в поле расписания
     */
    protected ScheduleDialog(JDialog parent, JTextField schedule) throws ParseException {
        super(parent, "Составление расписания", true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation(150, 150);

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
        this.add(schedulePanel, BorderLayout.CENTER);
        // добавляем кнопку подтверждения и слушателя к ней
        JButton confirm = new JButton("Подтвердить");
        ActionListener actionConfirmSchedule = e -> {
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
                // если время начала позже времени окончания или указанное время некорректно или интервал <3 минут
                if (from.compareTo(to) >= 0 || from.compareTo("23:59") > 0 ||
                        to.compareTo("23:59") > 0 || interval.compareTo("03") < 0) {
                    validData = false;
                }
            }
            if (validData == true) {
                // закрыть окно JDialog и вывести сообщение об успешном добавлении
                JOptionPane.showMessageDialog(this,"<html><h2>Успешно</h2><i>Расписание сформировано!</i>");
                // выставляем составленное расписание
                schedule.setText(str);
                // закроем диалоговое окно, т.к. оно выполнило свою функцию
                this.dispose();
            }
            else {
                str = "09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;09:00 21:00 5;";
                JOptionPane.showMessageDialog(this,"<html><h2>Ошибка</h2><i>Расписание составлено некорректно, " +
                        "попробуйте снова</i>", "Ошибка формирования", JOptionPane.ERROR_MESSAGE);
            }
        };
        confirm.addActionListener(actionConfirmSchedule);
        this.add(confirm, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }
}
