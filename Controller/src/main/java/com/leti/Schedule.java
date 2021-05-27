package com.leti;

import javax.persistence.*;

enum Days { Понедельник, Вторник, Среда, Четверг, Пятница, Суббота, Воскресенье }
@Entity // перевод класса к сущности БД
@Table(name="schedule")
public class Schedule {
    // поля
    @Id
    @Column(name="idSchedule")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="days")
    private String days;
    @OneToOne(optional = true, mappedBy = "schedule", orphanRemoval = true)
    private Route route;
    private static int cnt = 1;
    // конструктор(-ы)
    public Schedule() {
        days = new String();
        for (int i=0; i<7; ++i) {
            days += "9:00 21:00 " + cnt++ + ";";
        }
        System.out.println("create Schedule №" + id);
    }
    public Schedule(String _days) {
        days = _days;
        System.out.println("create Schedule №" + id);
    }
    // методы
    public int getId() { return id; }
    public String getDays() { return days; }
    public String getStart(int day) { return days.split(";")[day].split(" ")[0]; }
    public String getFinish(int day) {
        return days.split(";")[day].split(" ")[1];
    }
    public int getInterval(int day) { return Integer.parseInt(days.split(";")[day].split(" ")[2]); }
    public void print() {
        for (int i=0; i<7; ++i) {
            System.out.println("    On " + Days.values()[i] + " from " + getStart(i) + " to "
                    + getFinish(i) + " with " + getInterval(i) + " minutes interval");
        }
    }
    public void edit() {

    }
    public String[] toTableFormat() {
        String[] res = new String[] {String.valueOf(id), days};
        return res;
    }
}

