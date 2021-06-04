package app;

import entitys.Bus;
import entitys.Driver;
import entitys.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RemovalManager {
    protected RemovalManager(App frame, JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int indexes[] = table.getSelectedRows();
        // достаем ID удаляемого объекта в выделенной строке
        if (indexes.length > 0) {
            // проверка, чтобы случайно не удалить
            int result = JOptionPane.showConfirmDialog(frame, "Вы точно хотите удалить выбранную строку(-и)?",
                    "Окно подтверждения", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                for (int rowIndex: indexes) {
                    // удаление элемента из БД и сразу визуально из таблицы
                    int ID = Integer.parseInt((String) dtm.getValueAt(rowIndex, 0));
                    if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();
                    // увольняем водителя(и если автобус был на маршруте, то он также удалится)
                    // снимаем все автобусы с маршрута, чтобы не удалить вместе
                    switch (App.tableSelect.getSelectedIndex()) {
                        // удаляем автобус
                        case 0:
                            Bus bus = App.em.find(Bus.class, ID);
                            bus.fireToDriver();
                            App.em.remove(bus);
                            break;
                        // удаляем водителя
                        case 1:
                            Driver driver = App.em.find(Driver.class, ID);
                            if (driver.getBus() != null)
                                driver.getBus().fireToDriver();
                            App.em.remove(driver);
                            break;
                        // удаляем маршрут
                        case 2:
                            Route route = App.em.find(Route.class, ID);
                            route.exclude();
                            App.em.remove(route);
                            break;
                    }
                    App.em.getTransaction().commit();
                }
                // удаляем визуально из таблицы в приложении
                for (int i = indexes.length - 1; i>=0; --i) {
                    dtm.removeRow(indexes[i]);
                }
                dtm.fireTableDataChanged();
            }
        }
        else {
            JOptionPane.showMessageDialog(frame,"<html><h2>Ошибка</h2><i>Для удаления элемента " +
                    "выделите соответствующую строку!</i>", "Ошибка Удаления", JOptionPane.ERROR_MESSAGE);
        }
    }
}
