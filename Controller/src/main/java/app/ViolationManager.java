package app;

import entitys.Bus;
import entitys.Driver;
import entitys.Route;
import entitys.Violation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
/**
 * Класс менеджера фиксации нарушений
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class ViolationManager {
    /** Поле со ссылкой на таблицу данных, в которой производится фиксация нарушения */
    private final JTable table;
    /** Уникальный идентификатор объекта с нарушением */
    private final int ID;
    /** Описание нарушения */
    private String violation_description;
    /**
     * Конструктор - создание нового объекта ViolationManager
     * @param _table - ссылка на таблицу, где фиксируется нарушение
     * @param _ID - уникальный идентификатор объекта с нарушением
     * @param _description - описание нарушения
     */
    public ViolationManager(JTable _table, int _ID, String _description) {
        table = _table;
        ID = _ID;
        violation_description = _description;
    }

    /**
     * Функция фиксации нарушения для автобуса
     */
    public void applyToBus() {
        if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();

        entitys.Bus model = App.em.find(Bus.class, ID);
        violation_description = violation_description + ": " + model.getRegistr();
        Violation violation = null;
        if (model.getViolation() != null) {
            Violation fix = App.em.find(Violation.class, model.getViolation().getId());
            fix.setFixDate(new Date());
            if (model.getViolation().getDescription().equals(violation_description)) violation_description = "";
        }
        if (violation_description.length() > 0) {
            violation = new Violation(violation_description);
        }
        model.setViolation(violation);

        App.em.getTransaction().commit();
        // обновляем данные в таблице приложения
        DefaultTableModel dtm = ((DefaultTableModel) table.getModel());
        dtm.setValueAt(violation_description.length() > 0 ? violation_description : "Ок", table.getSelectedRow(), table.getColumnCount()-1);
        dtm.fireTableDataChanged();
    }
    /**
     * Функция фиксации нарушения для водителя
     */
    public void applyToDriver() {
        if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();

        Driver model = App.em.find(Driver.class, ID);
        violation_description = violation_description + ": " + model.getName();
        Violation violation = null;
        if (model.getViolation() != null) {
            Violation fix = App.em.find(Violation.class, model.getViolation().getId());
            fix.setFixDate(new Date());
            if (model.getViolation().getDescription().equals(violation_description)) violation_description = "";
        }
        if (violation_description.length() > 0) {
            violation = new Violation(violation_description);
        }
        model.setViolation(violation);

        App.em.getTransaction().commit();
        // обновляем данные в таблице приложения
        DefaultTableModel dtm = ((DefaultTableModel) table.getModel());
        dtm.setValueAt(violation_description.length() > 0 ? violation_description : "Ок", table.getSelectedRow(), table.getColumnCount()-1);
        dtm.fireTableDataChanged();
    }
    /**
     * Функция фиксации нарушения для маршрута
     */
    public void applyToRoute() {
        if (!App.em.getTransaction().isActive()) App.em.getTransaction().begin();

        Route model = App.em.find(Route.class, ID);
        violation_description = violation_description + ": №" + model.getNumber();
        Violation violation = null;
        if (model.getViolation() != null) {
            Violation fix = App.em.find(Violation.class, model.getViolation().getId());
            fix.setFixDate(new Date());
            if (model.getViolation().getDescription().equals(violation_description)) violation_description = "";
        }
        if (violation_description.length() > 0) {
            violation = new Violation(violation_description);
        }
        model.setViolation(violation);

        App.em.getTransaction().commit();
        // обновляем данные в таблице приложения
        DefaultTableModel dtm = ((DefaultTableModel) table.getModel());
        dtm.setValueAt(violation_description.length() > 0 ? violation_description : "Ок", table.getSelectedRow(), table.getColumnCount()-1);
        dtm.fireTableDataChanged();
    }
}
