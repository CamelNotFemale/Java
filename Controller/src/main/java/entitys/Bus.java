package entitys;

import javax.persistence.*;

@Entity // перевод класса к сущности БД
@Table(name="controller.bus")
public class Bus {
    // поля класса
    @Id
    @Column(name="idBus")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="registr")
    private String registr; // регистрационный номер
    @Column(name="capacity")
    private int capacity; // вместительность автобуса
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", referencedColumnName = "idDriver")
    private Driver driver; // ссылка на водителя
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")
    private Route route; // ссылка на маршрут
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "violation_id")
    private Violation violation; // нарушение
    private static int cnt = 1;
    // конструктор(ы)
    public Bus() {
        registr = "AAA" + cnt++;
        capacity = 48;
        driver = null;
        route = null;
        violation = null;
        //System.out.println("create Bus №" + registr);
    }
    public Bus(String _reg, int _capacity) {
        cnt++;
        registr = _reg;
        capacity = _capacity;
        driver = null;
        route = null;
        violation = null;
        //System.out.println("create Bus №" + registr);
    }
    // методы
    public int getId() { return id; }
    public String getRegistr() { return registr; }
    public int getCapacity() { return capacity; }
    public Driver getDriver() { return driver; }
    public Route getRoute() { return route; }
    public Violation getViolation() { return violation; }
    // сеттеры
    public boolean setRegistr(String reg_) {
        if (reg_.length()>0 && reg_.length()<7 && Character.isLetter(reg_.charAt(0))) {
            registr = reg_; return true;
        }
        else return false;
    }
    public boolean setCapacity(int cap_) {
        if (cap_ > 0) {
            capacity = cap_;
            return true;
        }
        else return false;
    }
    public boolean hireToDriver(Driver _driver) {
        if (_driver.getBus() != null) // если водитель работает на другом автобусе
            _driver.getBus().fireToDriver(); // то уволим его с того автобуса
        if (driver != null) // если у автобуса уже есть водитель
            this.fireToDriver();
        driver = _driver;
        //System.out.println("hire " + driver.getName() + " on " + this.getRegistr()); // удалить потом
        return driver.chooseBus(this);
    }
    public boolean fireToDriver() {
        if (driver != null) {
            driver.chooseBus(null);
            //System.out.println("fire " + driver.getName() + " from " + this.getRegistr()); // удалить потом
            driver = null;
            if (route != null) {
                route.deleteBus(this);
                route = null;
            }
            return true;
        }
        else return false;
    }
    public boolean chooseRoute(Route _route) {
        if (_route == null) {
            //System.out.println("delete from path №" + route.getNumber() + " bus " + this.getRegistr()); // удалить потом
            if (route != null) {
                route.deleteBus(this);
            }
            route = null;
            return true;
        }
        else {
            if (driver != null) { // если у автобуса нет водителя
                if (route != null) { // если автобус на другом маршруте
                    route.deleteBus(this);
                }
                route = _route;
                //System.out.println("choosen path №" + route.getNumber() + " for " + this.getRegistr()); // удалить потом
                return route.addBus(this);
            }
            return false;
        }
    }
    public void setViolation(Violation _violation) {
        violation = _violation;
        // если замечено нарушение
        if (violation != null) {
            if (driver != null) {
                fireToDriver();
            }
            if (route != null) {
                chooseRoute(null);
            }
        }
    }
    // методы
    public String[] toTableFormat() {
        String[] res = new String[] {String.valueOf(id), registr, String.valueOf(capacity), "Отсутствует", "Отсутствует", "Ок"};;
        if (driver != null) res[3] = driver.getName();
        if (route != null) res[4] = "№" + route.getNumber() + " " + route.getStart() + " - " + route.getFinish();
        if (violation != null) res[5] = violation.getDescription();
        return res;
    }
}