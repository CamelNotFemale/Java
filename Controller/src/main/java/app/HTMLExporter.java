package app;

import java.awt.FileDialog;
import java.io.*;

import javax.swing.JFrame;

public class HTMLExporter extends JFrame {
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
