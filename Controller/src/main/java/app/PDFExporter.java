package app;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.log4j.Logger;

import java.awt.FileDialog;
import java.io.*;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 * Класс экспортёра PDF
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class PDFExporter extends JFrame {
    /** Статическое поле для логгера */
    public static final Logger logger = Logger.getLogger(PDFExporter.class);
    /**
     * Конструктор - создание нового объекта HTMLExporter
     * @param TableData - таблица данных для экспорта
     */
    public PDFExporter(JTable TableData)
    {
        FileDialog fileDial = new FileDialog(this, "Export PDF", FileDialog.SAVE);
        fileDial.setFile("*.pdf");
        fileDial.setVisible(true);
        if (fileDial.getFile() != null)
            if (!fileDial.getFile().contains("."))
                fileDial.setFile(fileDial.getFile()+".pdf");
        String fileFullName = fileDial.getDirectory() + fileDial.getFile();

        if(!fileFullName.equals("nullnull"))
        {
            Document document = new Document(PageSize.A4, 15, 15, 15, 15);
            PdfPTable pdfTable = new PdfPTable(TableData.getColumnCount());

            try {
                PdfWriter.getInstance(document, new FileOutputStream(fileFullName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                logger.error("File Not Found", e);
            } catch (DocumentException e) {
                e.printStackTrace();
                logger.error("IO exception occured", e);
            }

            Font font = FontFactory.getFont("/fonts/DejaVuSans.ttf", "cp1251", BaseFont.EMBEDDED, 10);

            // add info
            Paragraph info = new Paragraph("Report from " + new Date() + " \n\n");

            // table header
            for(int i =0; i < TableData.getColumnCount(); ++i) {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(TableData.getModel().getColumnName(i), font));
                pdfTable.addCell(header);
            }

            // table data
            String data[][] = getTableData(TableData);
            for(int i = 0; i < TableData.getRowCount(); i++){
                for(int j =0; j < TableData.getColumnCount(); j++)
                {
                    String dat = (data[i][j] == null? "" : data[i][j]);
                    pdfTable.addCell(new Phrase(dat, font));
                }
            }

            document.open();

            try {
                document.add(info);
                document.add(pdfTable);
                logger.debug("File exported successfully");
            } catch (DocumentException e) {
                e.printStackTrace();
                logger.error("Write Error", e);
            }

            document.close();
        }
        else {
            logger.warn("User aborted file loading operation");
        }
    }
    /**
     * Функция получения данных из таблицы
     * @param table - таблица данных
     * @return данные исходной таблицы в формате двумерного массива строк
     */
    private String[][] getTableData(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        String[][] tableData = new String[nRow][nCol];
        for (int i = 0; i < nRow; i++)
            for (int j = 0; j < nCol; j++)
                tableData[i][j] = (String) dtm.getValueAt(i, j);
        return tableData;
    }
}