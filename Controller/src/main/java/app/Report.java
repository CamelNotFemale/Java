package app;

import entitys.Route;
import entitys.Violation;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class Report extends JFrame {

    // Текстовый редактор
    private  JTextPane textEditor = null;
    // Действия с отчётом
    private JButton export = new JButton(new ImageIcon("./src/main/resources/img/html.png"));
    private JButton refresh = new JButton(new ImageIcon("./src/main/resources/img/refresh.png"));
    private JLabel date = new JLabel();

    public Report(EntityManager em) {
        super("Справка и выявленные нарушения");
        setSize(400, 300);
        setLocation(100, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // расположение компонентов фрейма
        setLayout(new BorderLayout());
        // сам отчёт
        // Создание редактора
        textEditor = new JTextPane();
        textEditor.setEditable(false);
        // Определение стилей
        textEditor.setContentType("text/html");
        // Загрузка документа в приложении
        loadTextToHTML(textEditor, em);
        // добавление в фрейм
        add(new JScrollPane(textEditor), BorderLayout.CENTER);

        // возможные действия с отчётом и информация
        JPanel actionPanel = new JPanel();
        export.addActionListener(e -> {
            new HTMLExporter(textEditor.getText());
        });
        refresh.addActionListener(e -> {
            loadTextToHTML(textEditor, em);
            setDate();
        });
        export.setToolTipText("Экпорт в HTML");
        refresh.setToolTipText("Обновить данные");
        setDate();
        actionPanel.add(export);
        actionPanel.add(refresh);
        actionPanel.add(date);
        add(actionPanel, BorderLayout.SOUTH);

        //pack();
        setSize(480, 560);
        setVisible(true);
    }
    private void setDate() {
        date.setText("recent change " + new Date().toString());
    }
    private void loadTextToHTML(JTextPane editor, EntityManager em) {
        StringBuffer doc = new StringBuffer();
        // начало HTML файла
        doc.append("<html><body style=\"font-size:12px\" style=\"font-family: FreeSans\">");
        doc.append("<head>\n" + "<meta http-equiv=\"content-type\" content=\"application/xhtml+xml; charset=UTF-8\"/>\n" + "</head>");
        doc.append("<p style=\"margin: 5px\">");
        // заголовок отчёта
        doc.append("<h1><strong><span style=\"font-size:16px\">" + "Отчёт от " + new Date() + "</span></strong></h1><hr>");
        // добавляем информацию о маршрутах
        doc.append("<h3><strong><span style=\"font-size:14px\">" + "Информация о маршрутах" + "</span></strong></h3>");
        List<Route> routeList = em.createQuery("SELECT r FROM Route r").getResultList();
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
        editor.setText(doc.toString());
        textEditor.setCaretPosition(0);
    }
}
