package app;

import java.awt.FileDialog;
import java.io.*;

import javax.swing.JFrame;

/**
 * Класс экспортёра HTML
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class HTMLExporter extends JFrame {
    /**
     * Конструктор - создание нового объекта HTMLExporter
     * @param html - строка размеченная в стиле HTML
     */
    public HTMLExporter(String html) {
        FileDialog fileDial = new FileDialog(this, "Export HTML", FileDialog.SAVE);
        fileDial.setFile("*.html");
        fileDial.setVisible(true);
        if (fileDial.getFile() != null)
            if (!fileDial.getFile().contains("."))
                fileDial.setFile(fileDial.getFile()+".html");
        String fileFullName = fileDial.getDirectory() + fileDial.getFile();
        {
            try {
                FileWriter newHtmlFile = new FileWriter(fileFullName, false);
                newHtmlFile.write(html);
                newHtmlFile.flush();
                newHtmlFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
