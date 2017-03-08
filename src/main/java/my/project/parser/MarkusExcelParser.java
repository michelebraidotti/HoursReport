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

import static org.apache.poi.ss.usermodel.CellType.*;
import static org.apache.poi.ss.usermodel.CellType.STRING;

/**
 * Created by michele on 3/7/17.
 */
public class MarkusExcelParser {

    public static ReportingUser parse(String filePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(excelFile);
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet datatypeSheet = sheetIterator.next();
            System.out.print("===== " + datatypeSheet.getSheetName() + "\n");
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    if ( cell.getCellTypeEnum().equals(FORMULA)) {
                        evaluator.evaluateInCell(cell);
                    }
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            System.out.print("(" + cell.getAddress().getColumn() + "," + cell.getAddress().getRow() + ") " + cell.getRichStringCellValue().getString() + " ");
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                System.out.print("(" + cell.getAddress().getColumn() + "," + cell.getAddress().getRow() + ") " + cell.getDateCellValue() + " ");
                            } else {
                                System.out.print("(" + cell.getAddress().getColumn() + "," + cell.getAddress().getRow() + ") " + cell.getNumericCellValue() + " ");
                            }
                            break;
                        case BOOLEAN:
                            System.out.print("(" + cell.getAddress().getColumn() + "," + cell.getAddress().getRow() + ") " + cell.getBooleanCellValue() + " ");
                            break;
                        case FORMULA:
                            // not really happening
                            break;
                        case BLANK:
                            break;
                        default:
                    }
                }
                System.out.println();
            }
        }
        return null;
    }
}
