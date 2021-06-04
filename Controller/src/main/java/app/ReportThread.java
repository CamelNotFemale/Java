package app;

import entitys.Route;
import entitys.Violation;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
/**
 * Класс поток для генерации отчёта по маршрутам и нарушениям
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class ReportThread extends Thread{
    /** Поле со ссылкой на EntityManager */
    final private EntityManager em;
    /** Поле текстового редактора для отображения отчёта */
    final private JTextPane textEditor = new JTextPane();
    /** Поле для кнопки экспорта */
    final private JButton export = new JButton(new ImageIcon("./src/main/resources/img/html.png"));
    /** Поле для кнопки обновления данных */
    final private JButton refresh = new JButton(new ImageIcon("./src/main/resources/img/refresh.png"));
    /** Поле для указания даты последнего обновления */
    final private JLabel date = new JLabel();
    /**
     * Конструктор - создание нового потока ReportThread
     * @param name - имя потока
     * @param _em - ссылка на EntityManager
     */
    public ReportThread(String name, EntityManager _em) {
        super(name);
        em = _em;
    }
    /**
     * Функция запуска потока
     */
    public void run() {
        createUI();
        synchronized (textEditor) {
            // Загрузка документа в приложении
            loadTextToHTML();
        }
    }
    /**
     * Функция создания интерфейса отчёта
     */
    private void createUI() {
        JFrame frame = new JFrame("Справка и выявленные нарушения");
        frame.setSize(400, 300);
        frame.setLocation(100, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Создание редактора
        //textEditor = new JTextPane();
        textEditor.setEditable(false);
        textEditor.setContentType("text/html");
        // возможные действия с отчётом и информация
        export.addActionListener(e -> {
            synchronized (textEditor) {
                // Запись отчёта в HTML файл
                new HTMLExporter(textEditor.getText());
            }
        });
        refresh.addActionListener(e -> {
            synchronized (textEditor) {
                // Загрузка документа в приложении
                loadTextToHTML();
            }
            setDate();
        });
        export.setToolTipText("Экпорт в HTML");
        refresh.setToolTipText("Обновить данные");
        setDate();
        JPanel actionPanel = new JPanel();
        actionPanel.add(export);
        actionPanel.add(refresh);
        actionPanel.add(date);
        // добавление в фрейм
        frame.add(new JScrollPane(textEditor), BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        //pack();
        frame.setSize(480, 560);
        frame.setVisible(true);
    }
    /**
     * Функция задания текущего времени
     */
    private void setDate() {
        date.setText("recent change " + new Date().toString());
    }
    /**
     * Функция загрузки отчёта HTML формата в {@link ReportThread#textEditor}
     */
    private void loadTextToHTML() {
        StringBuilder doc = new StringBuilder();
        // начало HTML файла
        doc.append("<html><body style=\"font-size:12px\" style=\"font-family: FreeSans\">");
        doc.append("<head>" + "<meta http-equiv=\"content-type\" content=\"application/xhtml+xml; charset=UTF-8\"/>" + "</head>");
        doc.append("<p style=\"margin: 5px\">");
        // заголовок отчёта
        doc.append("<h1><strong><span style=\"font-size:16px\">" + "Отчёт от " + new Date() + "</span></strong></h1><hr>");
        // добавляем информацию о маршрутах
        doc.append("<h3><strong><span style=\"font-size:14px\">" + "Uнформация о маршрутах" + "</span></strong></h3>");
        java.util.List<Route> routeList = em.createQuery("SELECT r FROM Route r").getResultList();
        for (int i = 0; i < routeList.size(); ++i) {
            doc.append("<p style=\"margin-left: 10px\">");
            doc.append(routeList.get(i).toHTMLFormat());
            doc.append("</p>");
        }
        // информация о нарушениях
        doc.append("<h3><strong><span style=\"font-size:14px\">" + "Выявленные нарушения" + "</span></strong></h3>");
        List<Violation> violationList = em.createQuery("SELECT v FROM Violation v WHERE fixdatetime=null").getResultList();
        for (int i = 0; i < violationList.size(); ++i) {
            doc.append(violationList.get(i).toHTMLFormat());
        }
        doc.append("</p>");
        // конец HTML файла
        doc.append("</html></body>");
        textEditor.setText(doc.toString());
        textEditor.setCaretPosition(0);
    }
}
