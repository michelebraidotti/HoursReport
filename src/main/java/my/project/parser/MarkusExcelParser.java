package my.project.parser;

import my.project.data.ReportingTask;
import my.project.data.ReportingUser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * Created by michele on 3/7/17.
 */
public class MarkusExcelParser {
    private static int USER_NAME_COL = 1;
    private static int USER_NAME_ROW = 0;
    private static int TASK_COL = 0;
    private static int ACTIVITY_COL = 1;
    private static int DAYS_ROW = 4;
    private static int WEEKDAYS_ROW = 5;
    private static int DATA_STARTING_ROW = 6;
    private XSSFWorkbook workbook;

    public MarkusExcelParser(String filePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(filePath));
        workbook = new XSSFWorkbook(excelFile);

    }

    public ReportingUser parse() {
        String userName = parseUserName();
        ReportingUser reportingUser = new ReportingUser();
        reportingUser.setName(userName);
        List<ReportingTask> reportingTaskList = parseTaskList();
        reportingUser.setReportingTaskList(reportingTaskList);

        return reportingUser;
    }

    private List<ReportingTask> parseTaskList() {
        // TODO !!!!
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        String currentTaskName = "";
        String currentActivityName = "";
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                while (cellIterator.hasNext()) {
                }
            }
        }
        return null;
    }

    private Date findDateFromColumnNumber(Sheet sheet, int colNumber) {
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Row row = sheet.getRow(DAYS_ROW);
        Cell cell = row.getCell(colNumber);
        evaluator.evaluateInCell(cell);
        return cell.getDateCellValue() ;
    }

    private String parseUserName() {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(USER_NAME_ROW);
        Cell cell = row.getCell(USER_NAME_COL);
        return cell.getStringCellValue();
    }

    private void temp() {
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
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
    }
}
