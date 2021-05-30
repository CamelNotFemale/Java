package app;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ViolationTableCellRender extends DefaultTableCellRenderer {

    private int index; // для какой из таблиц вызван рендер

    public ViolationTableCellRender(int _index) {
        super();
        index = _index;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        switch (index) {
            case 0: // автобусы
                if (table.getValueAt(row, 3)=="Отсутствует") {
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, i);
                        c.setBackground(Color.RED);
                    }
                }
                else c.setBackground(Color.WHITE);
                break;
            case 1: // водители
                c.setBackground(row % 2 == 0 ? Color.RED : Color.WHITE);
                break;
            case 2: // маршруты
                c.setBackground(row % 2 == 0 ? Color.RED : Color.WHITE);
                break;
        }
        return c;
    }
}
