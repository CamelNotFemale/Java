package com.leti;

import java.util.*;
import javax.persistence.*;

@Entity // перевод класса к сущности БД
@Table(name="controller.route")
public class Route {
    // поля
    @Id
    @Column(name="idRoute")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="number")
    private int number; // номер маршрута
    @Column(name="start")
    private String start; // начало маршрута
    @Column(name="finish")
    private String finish; // конец маршрута
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "idSchedule")
    private Schedule schedule; // расписание движения по дням недели
    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER, orphanRemoval = false)
    private List<Bus> buses; // список автобусов на маршруте
    private static List<Route> routes = new ArrayList<>(); // список всех маршрутов в единственном виде
    private static int cnt = 1;
    // конструктор(ы)
    public Route() {
        number = cnt++;
        start = "start";
        finish = "finish";
        buses = new ArrayList<>();
        schedule = new Schedule();
        Route.routes.add(this); // добавил новый созданный маршрут к списку всех
        System.out.println("create Route №" + number + " " + start + "->" + finish);
    }
    public Route(int _num, String _start, String _finish) {
        cnt++;
        number = _num;
        start = _start;
        finish = _finish;
        buses = new ArrayList<>();
        schedule = new Schedule();
        Route.routes.add(this); // добавил новый созданный маршрут к списку всех
        System.out.println("create Route №" + number + " " + start + "->" + finish);
    }
    public Route(int _num, String _start, String _finish, String _schedule) {
        cnt++;
        number = _num;
        start = _start;
        finish = _finish;
        buses = new ArrayList<>();
        schedule = new Schedule(_schedule);
        Route.routes.add(this); // добавил новый созданный маршрут к списку всех
        System.out.println("create Route №" + number + " " + start + "->" + finish);
    }
    // методы
    public int getId() { return id; }
    public int getNumber() {
        return number;
    }
    public String getStart() {
        return start;
    }
    public String getFinish() {
        return finish;
    }
    public Schedule getSchedule() { return schedule; }
    public List<Bus> getBuses() {
        return buses;
    }
    public void setNumber(int _number) { number = _number; }
    public void setStart(String _start) { start = _start; }
    public void setFinish(String _finish) { finish = _finish; }
    public void setSchedule(String _schedule) {
            schedule = new Schedule(_schedule);
        }
    boolean addBus(Bus _bus) {
        if (_bus != null) {
            buses.add(_bus);
            System.out.println("add " + _bus.getRegistr() + " for path №" + this.getNumber()); // удалить потом
            return true;
        }
        else return false;
    }
    boolean deleteBus(Bus _bus) {
        if (_bus != null) {
            System.out.println("delete " + _bus.getRegistr() + " from path №" + this.getNumber()); // удалить потом
            return buses.remove(_bus);
        }
        else return false;
    }
    public boolean exclude() {
        if (buses.size() > 0) {
            while (buses.size() != 0) {
                buses.get(0).chooseRoute(null);
            }
            System.out.println("exclude path №" + this.getNumber() + " from work"); // удалить потом
            return true;
        }
        else return false;
    }
    public static void showReport() {
        System.out.println("Routes:");
        if (routes.size() != 0) {
            for (Route rt: routes) {
                System.out.println("  №" + rt.getNumber() + ' ' + rt.getStart() + "->" + rt.getFinish());
                if (rt.buses.size() != 0) {
                    System.out.println("  buses on route: " + rt.buses.size());
                    rt.schedule.print();
                }
                else
                    System.out.println("  temporaly not working...");
            }
        }
        else System.out.println("  is Empty");
    }
    public String toString() {
        return Integer.toString(id) + " " + Integer.toString(number) + " " + start + " " + finish;
    }
    public String[] toTableFormat() {
        String[] res = new String[] {String.valueOf(id), String.valueOf(number), start, finish, schedule.getDays()};
        return res;
    }
}