package app;

import entitys.*;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViolationTableCellRender extends DefaultTableCellRenderer {

    private EntityManager em;
    private int index; // для какой из таблиц вызван рендер

    public ViolationTableCellRender(EntityManager _em, int _index) {
        super();
        em = _em;
        index = _index;
    }
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
