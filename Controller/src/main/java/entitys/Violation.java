package entitys;

import javax.persistence.*;
import java.util.Date;

/**
 * Класс нарушения
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
@Entity // перевод класса к сущности БД
@Table(name="violation")
public class Violation {
    /** Поле уникального идентификатора */
    @Id
    @Column(name="idViolation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /** Поле описания нарушения */
    @Column(name="description")
    private String description; // регистрационный номер
    /** Поле времени нарушения */
    @Column(name="datetime")
    private Date datetime; // время нарушения
    /** Поле времени исправления нарушения */
    @Column(name="fixdatetime")
    private Date fixdatetime; // время нарушения
    /**
     * Конструктор - создание нового объекта Violation
     * @param violation_descript - строка с описанием нарушения
     */
    public Violation(String violation_descript) {
        description = violation_descript;
        datetime = new Date();
        //System.out.println("Проблемка " + ": " + description);
    }
    /**
     * Конструктор объекта Schedule по-умолчанию
     */
    public Violation() {
        description = "Нераспознанная ошибка";
        datetime = new Date();
        //System.out.println("Проблемка " + ": " + description);
    }
    /**
     * Функция получения значения поля {@link Violation#id}
     * @return уникальный идентификатор нарушения
     */
    public int getId() {
        return id;
    }
    /**
     * Функция получения значения поля {@link Violation#description}
     * @return описание нарушения
     */
    public String getDescription() {
        return description;
    }
    /**
     * Функция получения значения поля {@link Violation#datetime}
     * @return время нарушения
     */
    public Date getDate() {
        return datetime;
    }
    /**
     * Функция получения значения поля {@link Violation#fixdatetime}
     * @return время исправления нарушения
     */
    public Date getFixDate() {
        return fixdatetime;
    }
    /**
     * Функция определения значения поля {@link Violation#description}
     * @param _description - новое описание нарушения
     */
    public void setDescription(String _description) {
        description = _description;
    }
    /**
     * Функция определения значения поля {@link Violation#datetime}
     * @param _datetime - новое время нарушения
     */
    public void setDate(Date _datetime) {
        datetime = _datetime;
    }
    /**
     * Функция определения значения поля {@link Violation#fixdatetime}
     * @param _fixdatetime - новое время исправления нарушения
     */
    public void setFixDate(Date _fixdatetime) {
        fixdatetime = _fixdatetime;
    }
    /**
     * Функция преобразования информации о нарушении к HTML формату
     * @return строка содержащая всю информацию о нарушении с разметкой формата HTML
     */
    public String toHTMLFormat() {
        StringBuffer buff = new StringBuffer();
        String date = "<a style=\"color:red\">["+getDate().toString()+"]</a>";
        buff.append("<p>" + date + " " + getDescription() + "</p>");
        return buff.toString();
    }
}
