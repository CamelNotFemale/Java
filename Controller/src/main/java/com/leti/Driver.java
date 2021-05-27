package com.leti;

import java.util.ArrayList;
import javax.persistence.*;

@Entity // перевод класса к сущности БД
@Table(name="controller.driver")
public class Driver {
    // поля класса
    @Id
    @Column(name="idDriver")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="age")
    private int age;
    @Column(name="exp")
    private int exp;
    private static int salary_base = 21000; // базовая ставка для всех водителей
    @Column(name="salary")
    private int salary;
    @OneToOne(optional = true, mappedBy = "driver")
    private Bus bus; // какой автобус водит
    private static ArrayList<Driver> drivers = new ArrayList<>(); // список всех водителей в единственном виде
    private static int cnt = 1;
    // конструктор(ы)
    public Driver() {
        name = "Anonymous_" + cnt++;
        age = 21;
        exp = 0;
        setSalary();
        bus = null;
        drivers.add(this);
        System.out.println("create Driver " + name);
    }
    public Driver(String _name, int _age, int _exp) {
        cnt++;
        name = _name;
        age = _age;
        exp = _exp;
        setSalary();
        bus = null;
        drivers.add(this);
        System.out.println("create Driver " + name);
    }
    // методы
    public int getId() { return id; }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getExp() {
        return exp;
    }
    public int getSalary() {
        return salary;
    }
    public Bus getBus() {
        return bus;
    }

    public void setName(String new_name) { name = new_name; }
    private void setSalary() { if (exp<10) salary=salary_base*(10+exp)/10; else salary=salary_base*2; }
    public static void setSalaryBase(int new_salary) {
        salary_base = new_salary;
    }
    public void setAge(int new_age) {
        age = new_age;
    }
    public void setExp(int new_exp) {
        exp = new_exp;
        setSalary();
    }

    boolean chooseBus(Bus _bus) {
        bus = _bus;
        return true;
    }
    public static void printList() {
        System.out.println("List of drivers:");
        for (Driver driver: drivers) {
            System.out.print("  " + driver.getName() + " have " + driver.getExp() + " year(-s) of work-experience, ");
            if (driver.getBus() != null)
                System.out.println("is a driver of " + driver.getBus().getRegistr());
            else
                System.out.println("not busy");
        }
    }
    public String[] toTableFormat() {
        String[] res = new String[] {String.valueOf(id), name, String.valueOf(age), String.valueOf(exp), String.valueOf(salary)};
        return res;
    }
}