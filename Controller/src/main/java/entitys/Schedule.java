package entitys;

import javax.persistence.*;

/**
 * Класс расписания
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
@Entity // перевод класса к сущности БД
@Table(name="schedule")
public class Schedule {
    /** Поле уникального идентификатора */
    @Id
    @Column(name="idSchedule")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /** Поле со строкой недельного расписания движения */
    @Column(name="days")
    private String days;
    /** Поле со ссылкой на маршрут */
    @OneToOne(optional = true, mappedBy = "schedule", orphanRemoval = true)
    private Route route;
    /** Статическое поле счётчик расписаний */
    private static int cnt = 1;
    /**
     * Конструктор объекта Schedule по-умолчанию
     */
    public Schedule() {
        days = new String();
        for (int i=0; i<7; ++i) {
            days += "9:00 21:00 " + cnt++ + ";";
        }
        //System.out.println("create Schedule №" + id);
    }
    /**
     * Конструктор - создание нового объекта Schedule
     * @param _days - строка с недельным расписанием движения
     */
    public Schedule(String _days) {
        days = _days;
        //System.out.println("create Schedule №" + id);
    }
    /**
     * Функция получения значения поля {@link Schedule#id}
     * @return уникальный идентификатор расписания
     */
    public int getId() { return id; }
    /**
     * Функция получения значения поля {@link Schedule#days}
     * @return строка недельного расписания
     */
    public String getDays() { return days; }
    /**
     * Функция получения значения времени начала движения в определенный день
     * @param day - порядковый номер дня недели
     * @return строка с временем начала движения
     */
    public String getStart(int day) { return days.split(";")[day].split(" ")[0]; }
    /**
     * Функция получения значения времени окончания движения в определенный день
     * @param day - порядковый номер дня недели
     * @return строка с временем окончания движения
     */
    public String getFinish(int day) {
        return days.split(";")[day].split(" ")[1];
    }
    /**
     * Функция получения значения интервала движения в определенный день
     * @param day - порядковый номер дня недели
     * @return строка с интервалом движения
     */
    public int getInterval(int day) { return Integer.parseInt(days.split(";")[day].split(" ")[2]); }
    /**
     * Функция преобразования информации о расписании к HTML формату
     * @return строка содержащая всю информацию о расписании с разметкой формата HTML
     */
    public String toHTMLFormat() {
        StringBuffer buff = new StringBuffer();
        buff.append("<span>");
        for (int i=0; i<7; ++i) {
            buff.append(Days.values()[i] + ": с " + getStart(i) + " до "
                    + getFinish(i) + " с интервалом в " + getInterval(i) + " минут<br>");
        }
        buff.append("</span>");
        return buff.toString();
    }
}

