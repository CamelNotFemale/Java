package entitys;

import java.util.ArrayList;
import javax.persistence.*;

/**
 * Класс водителя
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
@Entity // перевод класса к сущности БД
@Table(name="controller.driver")
public class Driver {
    /** Поле уникального идентификатора */
    @Id
    @Column(name="idDriver")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /** Поле имени водителя */
    @Column(name="name")
    private String name;
    /** Поле возраста идентификатора */
    @Column(name="age")
    private int age;
    /** Поле опыта работы водителя */
    @Column(name="exp")
    private int exp;
    /** Статичное поле с базовой зарплатой */
    private static int salary_base = 21000; // базовая ставка для всех водителей
    /** Поле зарплаты водителя */
    @Column(name="salary")
    private int salary;
    /** Поле со ссылкой на рабочий автобус данного водителя */
    @OneToOne(optional = true, mappedBy = "driver")
    private Bus bus; // какой автобус водит
    /** Поле со ссылкой на имеющееся нарушение */
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "violation_id")
    private Violation violation; // нарушение
    /** Статическое поле счётчик автобусов */
    private static int cnt = 1;
    /**
     * Конструктор объекта Driver по-умолчанию
     */
    public Driver() {
        name = "Anonymous_" + cnt++;
        age = 21;
        exp = 0;
        setSalary();
        bus = null;
        violation = null;
        //System.out.println("create Driver " + name);
    }
    /**
     * Конструктор - создание нового объекта Driver
     * @param _name - ФUО
     * @param _age - возраст
     * @param _exp - опыт работы
     */
    public Driver(String _name, int _age, int _exp) {
        cnt++;
        name = _name;
        age = _age;
        exp = _exp;
        setSalary();
        bus = null;
        violation = null;
        //System.out.println("create Driver " + name);
    }
    /**
     * Функция получения значения поля {@link Driver#id}
     * @return возвращает уникальный идентификатор водителя
     */
    public int getId() { return id; }
    /**
     * Функция получения значения поля {@link Driver#name}
     * @return возвращает ФUО водителя
     */
    public String getName() {
        return name;
    }
    /**
     * Функция получения значения поля {@link Driver#age}
     * @return возвращает возраст водителя
     */
    public int getAge() {
        return age;
    }
    /**
     * Функция получения значения поля {@link Driver#exp}
     * @return возвращает опыт работы водителя
     */
    public int getExp() {
        return exp;
    }
    /**
     * Функция получения значения поля {@link Driver#salary}
     * @return возвращает зарплату водителя
     */
    public int getSalary() {
        return salary;
    }
    /**
     * Функция получения значения поля {@link Driver#bus}
     * @return возвращает ссылку на рабочий автобус водителя
     */
    public Bus getBus() {
        return bus;
    }
    /**
     * Функция получения значения поля {@link Bus#violation}
     * @return возвращает ссылку на нарушение
     */
    public Violation getViolation() { return violation; }
    /**
     * Функция определения значения поля {@link Driver#name}
     * @param new_name - новое ФUО водителя
     */
    public void setName(String new_name) { name = new_name; }
    /**
     * Функция определения значения поля {@link Driver#salary} в пересчёте по базовой ставке
     */
    private void setSalary() { if (exp<10) salary=salary_base*(10+exp)/10; else salary=salary_base*2; }
    /**
     * Функция определения значения поля {@link Driver#salary_base}
     * @param new_salary - новая базовая ставка по зарплате
     */
    public static void setSalaryBase(int new_salary) {
        salary_base = new_salary;
    }
    /**
     * Функция определения значения поля {@link Driver#age}
     * @param new_age - новый возраст водителя
     */
    public void setAge(int new_age) {
        age = new_age;
    }
    /**
     * Функция определения значения поля {@link Driver#exp}
     * @param new_exp - новый опыт работы водителя
     */
    public void setExp(int new_exp) {
        exp = new_exp;
        setSalary();
    }
    /**
     * Функция определения значения поля {@link Driver#violation}
     * @param _violation - ссылка на выявленное нарушение
     */
    public void setViolation(Violation _violation) {
        violation = _violation;
        // если замечено нарушение
        if (violation != null) {
            if (bus != null) {
                bus.fireToDriver();
            }
        }
    }
    /**
     * Функция определения значения поля {@link Driver#bus}
     * @param _bus - ссылка на автобус
     */
    void chooseBus(Bus _bus) {
        bus = _bus;
    }
    /**
     * Функция преобразования информации о водителе в табличный формат
     * @return список строк со значениями всех информационных полей водителя
     */
    public String[] toTableFormat() {
        String[] res = new String[] {String.valueOf(id), name, String.valueOf(age), String.valueOf(exp), String.valueOf(salary), "Ок"};
        if (violation != null) res[5] = violation.getDescription();
        return res;
    }
}