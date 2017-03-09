package my.project.parser;

import my.project.data.ReportingTask;
import my.project.data.ReportingUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * Created by michele on 3/7/17.
 */
public class MarkusExcelParser {
    private static final String NO_ACTIVITY = "No activity specified";
    private static int USER_NAME_COL = 1;
    private static int USER_NAME_ROW = 0;
    private static int TASK_COL = 0;
    private static int ACTIVITY_COL = 1;
    private static int DAYS_ROW = 4;
    private static int WEEKDAYS_ROW = 5;
    private static int DATA_STARTING_ROW = 6;
    private static int DATA_STARTING_COL = 2;
    private XSSFWorkbook workbook;
    private ReportingUser reportingUser;

    public MarkusExcelParser(String filePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(filePath));
        workbook = new XSSFWorkbook(excelFile);

    }

    public void parse() {
        String userName = parseUserName();
        reportingUser = new ReportingUser();
        reportingUser.setName(userName);
        parseTaskList();
    }

    private void parseTaskList() {
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        String currentTaskName = "";
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            Iterator<Row> rowIterator = sheet.iterator();
            String currentActivityName;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() >= DATA_STARTING_ROW) {
                    Iterator<Cell> cellIterator = row.iterator();
                    String taskName = row.getCell(TASK_COL).getStringCellValue();
                    String activityName = row.getCell(ACTIVITY_COL).getStringCellValue();
                    // We need to deal with situations like this
                    // TASK     | ACTIVITY
                    // Task1    | activiy1
                    //          | activity2
                    // which means that activity2 is referred to Task1
                    // furthermore there are tasks without activities and
                    // blank lines.
                    // But there should not be activities without tasks.
                    if (!StringUtils.isEmpty(taskName)) {
                        if (!currentTaskName.equals(taskName)) {
                            currentTaskName = taskName;
                        }
                    }
                    if (!StringUtils.isEmpty(activityName)) {
                        currentActivityName = activityName;
                    } else {
                        currentActivityName = NO_ACTIVITY;
                    }
                    if (!StringUtils.isEmpty(taskName)
                            && !StringUtils.isEmpty(activityName)) {
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            if (cell.getAddress().getColumn() >= DATA_STARTING_COL) {
                                if (cell.getCellTypeEnum().equals(NUMERIC)) {
                                    Date date = findDateFromColumnNumber(sheet, cell.getAddress().getColumn());
                                    Float hours = new Float(cell.getNumericCellValue());
                                    reportingUser
                                            .findReportingTaskByName(currentTaskName)
                                            .reportHours(currentActivityName, date, hours);
                                }
                            }
                        }
                    }
                }
            }
        }
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

    public ReportingUser getReportingUser() {
        return reportingUser;
    }
}
