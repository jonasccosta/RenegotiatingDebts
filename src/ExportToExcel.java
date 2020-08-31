/**
 * Creates an Excel file with the information from a JTable
 *
 * @author Jonas C. Costa
 */

import java.io.File;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExportToExcel {
    static String fileDictName = "";

    public void toExcel(TableModel dtm)  {
        try {
            //Open a window from the system in which the user will choose a folder to save the file
            JFileChooser fileChooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Files", ".xls");
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setDialogTitle("Save");
            fileChooser.setSelectedFile(new File(fileDictName));
            fileChooser.setVisible(true);
            fileChooser.setFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(fileChooser);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                fileDictName = fileChooser.getSelectedFile().getAbsolutePath();
            } else {
                return;
            }

            File file = new File(fileDictName+ ".xls");

            if (!file.exists()) {
                //Creates a xls file with the information from a JTable
                Workbook wb = new HSSFWorkbook();
                CreationHelper createhelper = wb.getCreationHelper();
                Sheet sheet = wb.createSheet("new sheet");
                Row row = null;
                Cell cell = null;
                for (int i = 0; i < dtm.getRowCount(); i++) {
                    row = sheet.createRow(i);
                    for (int j = 0; j < dtm.getColumnCount(); j++) {
                        cell = row.createCell(j);
                        if(i == 0){
                            cell.setCellValue(dtm.getColumnName(j));
                        }
                        else{
                            cell.setCellValue((String) dtm.getValueAt(i-1, j));
                        }
                    }
                }

                FileOutputStream out = new FileOutputStream(file);
                wb.write(out);
                out.close();

            }

        } catch (IOException ex) {
            Logger.getLogger(ExportToExcel.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
