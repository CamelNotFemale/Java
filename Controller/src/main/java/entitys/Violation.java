package entitys;

import javax.persistence.*;
import java.util.Date;

@Entity // перевод класса к сущности БД
@Table(name="violation")
public class Violation {
    // поля класса
    @Id
    @Column(name="idViolation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="description")
    private String description; // регистрационный номер
    @Column(name="datetime")
    private Date datetime; // время нарушения
    @Column(name="fixdatetime")
    private Date fixdatetime; // время нарушения
    // конструкторы
    public Violation(String violation_descript) {
        description = violation_descript;
        datetime = new Date();
        //System.out.println("Проблемка " + ": " + description);
    }
    public Violation() {
        description = "Нераспознанная ошибка";
        datetime = new Date();
        //System.out.println("Проблемка " + ": " + description);
    }
    // геттеры
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public Date getDate() {
        return datetime;
    }
    public Date getFixDate() {
        return fixdatetime;
    }
    // сеттеры
    public void setDescription(String _description) {
        description = _description;
    }
    public void setDate(Date _datetime) {
        datetime = _datetime;
    }
    public void setFixDate(Date _fixdatetime) {
        fixdatetime = _fixdatetime;
    }
}
