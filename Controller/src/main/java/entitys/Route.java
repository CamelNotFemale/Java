package entitys;

import java.util.*;
import javax.persistence.*;

/**
 * Класс маршрута
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
@Entity // перевод класса к сущности БД
@Table(name="controller.route")
public class Route {
    /** Поле уникального идентификатора */
    @Id
    @Column(name="idRoute")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /** Поле номера маршрута */
    @Column(name="number")
    private int number; // номер маршрута
    /** Поле локации начала движения на маршруте */
    @Column(name="start")
    private String start; // начало маршрута
    /** Поле локации окончания движения на маршруте */
    @Column(name="finish")
    private String finish; // конец маршрута
    /** Поле со ссылкой на текущее расписание движения маршрута */
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "idSchedule")
    private Schedule schedule; // расписание движения по дням недели
    /** Поле массива ссылок на все автобусы, работающие на маршруте */
    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER, orphanRemoval = false)
    private List<Bus> buses; // список автобусов на маршруте
    /** Поле со ссылкой на имеющееся нарушение */
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "violation_id")
    private Violation violation; // нарушение
    /** Статическое поле счётчик автобусов */
    private static int cnt = 1;
    /**
     * Конструктор объекта Route по-умолчанию
     */
    public Route() {
        number = cnt++;
        start = "start";
        finish = "finish";
        buses = new ArrayList<>();
        schedule = new Schedule();
        violation = null;
        //System.out.println("create Route №" + number + " " + start + "->" + finish);
    }
    /**
     * Конструктор - создание нового объекта Route
     * @param _num - номер маршрута
     * @param _start - точка начала движения
     * @param _finish - точка окончания движения
     */
    public Route(int _num, String _start, String _finish) {
        cnt++;
        number = _num;
        start = _start;
        finish = _finish;
        buses = new ArrayList<>();
        schedule = new Schedule();
        violation = null;
        //System.out.println("create Route №" + number + " " + start + "->" + finish);
    }
    /**
     * Конструктор - создание нового объекта Route
     * @param _num - номер маршрута
     * @param _start - точка начала движения
     * @param _finish - точка окончания движения
     * @param _schedule - маршрут движения
     */
    public Route(int _num, String _start, String _finish, String _schedule) {
        cnt++;
        number = _num;
        start = _start;
        finish = _finish;
        buses = new ArrayList<>();
        schedule = new Schedule(_schedule);
        violation = null;
        //System.out.println("create Route №" + number + " " + start + "->" + finish);
    }
    /**
     * Функция получения значения поля {@link Route#id}
     * @return возвращает уникальный идентификатор маршрута
     */
    public int getId() { return id; }
    /**
     * Функция получения значения поля {@link Route#number}
     * @return возвращает номер маршрута
     */
    public int getNumber() {
        return number;
    }
    /**
     * Функция получения значения поля {@link Route#start}
     * @return возвращает точку начала движения
     */
    public String getStart() {
        return start;
    }
    /**
     * Функция получения значения поля {@link Route#finish}
     * @return возвращает точку окончания движения
     */
    public String getFinish() {
        return finish;
    }
    /**
     * Функция получения значения поля {@link Route#schedule}
     * @return возвращает ссылку на текущее расписание
     */
    public Schedule getSchedule() { return schedule; }
    /**
     * Функция получения значения поля {@link Route#buses}
     * @return возвращает список ссылок на все автобусы на маршруте
     */
    public List<Bus> getBuses() {
        return buses;
    }
    /**
     * Функция получения значения поля {@link Route#violation}
     * @return возвращает ссылку на нарушение
     */
    public Violation getViolation() { return violation; }
    /**
     * Функция определения значения поля {@link Route#number}
     * @param _number - новый номер маршрута
     */
    public void setNumber(int _number) { number = _number; }
    /**
     * Функция определения значения поля {@link Route#start}
     * @param _start - новая точка начала движения
     */
    public void setStart(String _start) { start = _start; }
    /**
     * Функция определения значения поля {@link Route#finish}
     * @param _finish- новая точка окончания движения
     */
    public void setFinish(String _finish) { finish = _finish; }
    /**
     * Функция определения значения поля {@link Route#schedule}
     * @param _schedule- ссылка на новое расписание движения
     */
    public void setSchedule(String _schedule) {
            schedule = new Schedule(_schedule);
        }
    /**
     * Функция определения значения поля {@link Route#violation}
     * @param _violation - ссылка на выявленное нарушение
     */
    public void setViolation(Violation _violation) {
        violation = _violation;
        // если замечено нарушение
        if (violation != null) {
            exclude();
        }
    }
    /**
     * Функция добавления рабочего автобуса на маршрут
     * @param _bus - ссылка на добавляемый автобус
     * @return булево значение, определяющее успешность добавления автобуса на маршрут
     */
    boolean addBus(Bus _bus) {
        if (_bus != null) {
            buses.add(_bus);
            //System.out.println("add " + _bus.getRegistr() + " for path №" + this.getNumber()); // удалить потом
            return true;
        }
        else return false;
    }
    /**
     * Функция удаления рабочего автобуса на маршрут
     * @param _bus - ссылка на удаляемый автобус с маршрута
     * @return булево значение, определяющее успешность удаления автобуса с маршрута
     */
    boolean deleteBus(Bus _bus) {
        if (_bus != null) {
            //System.out.println("delete " + _bus.getRegistr() + " from path №" + this.getNumber()); // удалить потом
            return buses.remove(_bus);
        }
        else return false;
    }
    /**
     * Функция исключения всех работающих автобусов на маршруте
     * @return булево значение, определяющее успешность исключения автобусов с маршрута
     */
    public boolean exclude() {
        if (buses.size() > 0) {
            while (buses.size() != 0) {
                buses.get(0).chooseRoute(null);
            }
            //System.out.println("exclude path №" + this.getNumber() + " from work"); // удалить потом
            return true;
        }
        else return false;
    }
    /**
     * Функция преобразования к строковому типу
     * @return строка, содержащая всю информацию о маршруте
     */
    public String toString() {
        return Integer.toString(id) + " " + Integer.toString(number) + " " + start + " " + finish;
    }
    /**
     * Функция преобразования информации о водителе в табличный формат
     * @return список строк со значениями всех информационных полей маршрута
     */
    public String[] toTableFormat() {
        String[] res = new String[] {String.valueOf(id), String.valueOf(number), start, finish, schedule.getDays(), "Ок"};
        if (violation != null) res[5] = violation.getDescription();
        return res;
    }
    /**
     * Функция преобразования информации о маршруте к HTML формату
     * @return строка содержащая всю информацию о маршруте с разметкой формата HTML
     */
    public String toHTMLFormat() {
        StringBuffer buff = new StringBuffer();
        buff.append("<span><b>" + "Маршрут №" + getNumber() + ' ' + getStart() + "->" + getFinish() + "</span><br>");
        if (buses.size() != 0) {
            buff.append(schedule.toHTMLFormat());
            buff.append("<pre>    автобусов на маршруте: " + buses.size() + "</pre>");
        }
        else
            buff.append("<pre style=\"color:red\">    в настоящее время не работает" + "</pre>");
        return buff.toString();
    }
}