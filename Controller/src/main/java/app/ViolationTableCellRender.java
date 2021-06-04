package app;

import entitys.*;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
/**
 * Класс рендера таблицы данных
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class ViolationTableCellRender extends DefaultTableCellRenderer {
    /** Поле, содержащее ссылку на EntityManager */
    private EntityManager em;
    /** Поле, содержащее индекс таблицы для рендера */
    private int index; // для какой из таблиц вызван рендер
    /**
     * Конструктор - создание нового объекта ViolationTableCellRender
     * @param _em - ссылка на EntityManager
     * @param _index - индекс таблицы для рендера
     */
    public ViolationTableCellRender(EntityManager _em, int _index) {
        super();
        em = _em;
        index = _index;
    }
    /**
     * Функция выделения строки таблицы в определенный цвет
     * @param table - таблица
     * @param value - значение ячейки
     * @param isSelected - выбор ячейки
     * @param hasFocus - наведение на ячейку
     * @param row - номер строки
     * @param column - номер столбца
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        switch (index) {
            case 0: // автобусы
                Bus bus = em.find(Bus.class, Integer.parseInt((String) table.getValueAt(row, 0)));
                if (bus.getViolation() != null) {
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, i);
                        c.setBackground(Color.RED);
                    }
                }
                else if (bus.getDriver() == null || bus.getRoute() == null) c.setBackground(Color.LIGHT_GRAY);
                else c.setBackground(Color.WHITE);
                break;
            case 1: // водители
                Driver driver = em.find(Driver.class, Integer.parseInt((String) table.getValueAt(row, 0)));
                if (driver.getViolation() != null) {
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, i);
                        c.setBackground(Color.RED);
                    }
                }
                else c.setBackground(Color.WHITE);
                break;
            case 2: // маршруты
                Route route = em.find(Route.class, Integer.parseInt((String) table.getValueAt(row, 0)));
                if (route.getViolation() != null) {
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, i);
                        c.setBackground(Color.RED);
                    }
                }
                else c.setBackground(Color.WHITE);
                break;
        }
        return c;
    }
}
