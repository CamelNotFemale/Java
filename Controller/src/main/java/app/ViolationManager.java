package app;

import entitys.Bus;
import entitys.Driver;
import entitys.Route;
import entitys.Violation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class ViolationManager {
    private final JTable table;
    private final int ID;
    private String violation_description;
    public ViolationManager(JTable _table, int _ID, String _description) {
        table = _table;
        ID = _ID;
        violation_description = _description;
    }
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
