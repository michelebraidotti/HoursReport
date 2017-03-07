package my.project.parser;

import my.project.data.ReportingUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by michele on 3/7/17.
 */
public class ExcelParser {

    public ReportingUser parse(String filePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet datatypeSheet = sheetIterator.next();
            System.out.print("===== " + datatypeSheet.getSheetName() + "\n");
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        if (!StringUtils.isEmpty(currentCell.getStringCellValue()))
                            System.out.print("(" + currentCell.getAddress().getColumn() + "," + currentCell.getAddress().getRow() + ") " + currentCell.getStringCellValue() + " ");
                    }
                    else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print("(" + currentCell.getAddress().getColumn() + "," + currentCell.getAddress().getRow() + ") " + currentCell.getNumericCellValue() + " ");
                    }
                    else {
                        // todo: log skipping cell
                    }
                }
                System.out.println();
            }
        }
        return null;
    }
}
