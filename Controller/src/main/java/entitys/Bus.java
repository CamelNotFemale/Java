package entitys;

import javax.persistence.*;

/**
 * Класс автобуса
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
@Entity // перевод класса к сущности БД
@Table(name="controller.bus")
public class Bus {
    /** Поле уникального идентификатора */
    @Id
    @Column(name="idBus")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /** Поле регистрационного номера */
    @Column(name="registr")
    private String registr; // регистрационный номер
    /** Поле вместительности */
    @Column(name="capacity")
    private int capacity; // вместительность автобуса
    /** Поле со ссылкой на текущего водителя */
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", referencedColumnName = "idDriver")
    private Driver driver; // ссылка на водителя
    /** Поле со ссылкой на текущий маршрут */
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")
    private Route route; // ссылка на маршрут
    /** Поле со ссылкой на имеющееся нарушение */
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "violation_id")
    private Violation violation; // нарушение
    /** Статическое поле счётчик автобусов */
    private static int cnt = 0;
    /**
     * Конструктор объекта Bus по-умолчанию
     */
    public Bus() {
        registr = "AAA" + cnt++;
        capacity = 48;
        driver = null;
        route = null;
        violation = null;
        //System.out.println("create Bus №" + registr);
    }
    /**
     * Конструктор - создание нового объекта Bus
     * @param _reg - регистрационный номер
     * @param _capacity - вместительность
     */
    public Bus(String _reg, int _capacity) {
        cnt++;
        registr = _reg;
        capacity = _capacity;
        driver = null;
        route = null;
        violation = null;
        //System.out.println("create Bus №" + registr);
    }
    /**
     * Функция получения значения поля {@link Bus#id}
     * @return возвращает уникальный идентификатор автобуса
     */
    public int getId() { return id; }
    /**
     * Функция получения значения поля {@link Bus#registr}
     * @return возвращает регистрационный номер автобуса
     */
    public String getRegistr() { return registr; }
    /**
     * Функция получения значения поля {@link Bus#capacity}
     * @return возвращает вместительность
     */
    public int getCapacity() { return capacity; }
    /**
     * Функция получения значения поля {@link Bus#driver}
     * @return возвращает ссылку на водителя
     */
    public Driver getDriver() { return driver; }
    /**
     * Функция получения значения поля {@link Bus#route}
     * @return возвращает ссылку на маршрут
     */
    public Route getRoute() { return route; }
    /**
     * Функция получения значения поля {@link Bus#violation}
     * @return возвращает ссылку на нарушение
     */
    public Violation getViolation() { return violation; }
    /**
     * Функция определения значения поля {@link Bus#registr}
     * @param reg_ - новый регистрационный номер автобуса
     * @return булево значение, означающее успешность изменения регистрационного номера
     */
    public boolean setRegistr(String reg_) {
        if (reg_.length()>0 && reg_.length()<7 && Character.isLetter(reg_.charAt(0))) {
            registr = reg_; return true;
        }
        else return false;
    }
    /**
     * Функция определения значения поля {@link Bus#capacity}
     * @param cap_ - новая вместительность автобуса
     * @return булево значение, означающее успешность изменения вместительность
     */
    public boolean setCapacity(int cap_) {
        if (cap_ > 0) {
            capacity = cap_;
            return true;
        }
        else return false;
    }
    /**
     * Функция наёма {@link Bus#driver} для автобуса
     * @param _driver - ссылка на нанимаего водителя
     */
    public void hireToDriver(Driver _driver) {
        if (_driver.getBus() != null) // если водитель работает на другом автобусе
            _driver.getBus().fireToDriver(); // то уволим его с того автобуса
        if (driver != null) // если у автобуса уже есть водитель
            this.fireToDriver();
        driver = _driver;
        //System.out.println("hire " + driver.getName() + " on " + this.getRegistr()); // удалить потом
        driver.chooseBus(this);
    }
    /**
     * Функция увольнения текущего работника
     * @return булево значение, означающее успешность увольнения водителя
     */
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
    /**
     * Функция определения значения поля {@link Bus#route}
     * @param _route - ссылка на выбираемый маршрут
     * @return булево значение, означающее успешность выбора нового маршрута
     */
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
    /**
     * Функция определения значения поля {@link Bus#violation}
     * @param _violation - ссылка на выявленное нарушение
     */
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
    /**
     * Функция преобразования информации об автобусе в табличный формат
     * @return список строк со значениями всех информационных полей автобуса
     */
    public String[] toTableFormat() {
        String[] res = new String[] {String.valueOf(id), registr, String.valueOf(capacity), "Отсутствует", "Отсутствует", "Ок"};;
        if (driver != null) res[3] = driver.getName();
        if (route != null) res[4] = "№" + route.getNumber() + " " + route.getStart() + " - " + route.getFinish();
        if (violation != null) res[5] = violation.getDescription();
        return res;
    }
}