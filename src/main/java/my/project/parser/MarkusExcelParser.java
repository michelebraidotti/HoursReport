package my.project.parser;

import my.project.data.ReportingException;
import my.project.data.ReportingUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * Created by michele on 3/7/17.
 */
public class MarkusExcelParser {
    private static final String NO_ACTIVITY = "No activity specified";
    private static int USER_NAME_COL = 1;
    private static int USER_NAME_ROW = 0;
    private static int DATE_COL = 0;
    private static int DATE_ROW = 3;
    private static int TASK_COL = 0;
    private static int ACTIVITY_COL = 1;
    private static int DAYS_ROW = 4;
    private static int DATA_STARTING_ROW = 6;
    private static int DATA_STARTING_COL = 2;
    private XSSFWorkbook workbook;
    private ReportingUser reportingUser;

    public MarkusExcelParser(String filePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(filePath));
        workbook = new XSSFWorkbook(excelFile);

    }

    public ReportingUser getReportingUser() {
        return reportingUser;
    }

    public void parse() throws ReportingException {
        reportingUser = new ReportingUser(parseYear(), parseUserName());
        parseTaskList();
    }

    private int parseYear() {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(DATE_ROW);
        Cell cell = row.getCell(DATE_COL);
        Date date = cell.getDateCellValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    private void parseTaskList() throws ReportingException {
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() >= DATA_STARTING_ROW) {
                    String taskName= "";
                    if ( row.getCell(TASK_COL) != null  && row.getCell(TASK_COL).getCellTypeEnum().equals(STRING) ) {
                        taskName = row.getCell(TASK_COL).getStringCellValue();
                    }
                    String activityName = "";
                    if ( row.getCell(ACTIVITY_COL) != null  && row.getCell(ACTIVITY_COL).getCellTypeEnum().equals(STRING) ) {
                        activityName = row.getCell(ACTIVITY_COL).getStringCellValue();
                    }
                    // We need to deal with situations like this
                    // TASK     | ACTIVITY      <- Header
                    // Task1    | activity1     <- Row x
                    //          | activity2     <- Row x + 1
                    // which means that activity2 is referred to Task1
                    // furthermore there are tasks without activities and
                    // blank lines.
                    // But there should not be activities without tasks.
                    if ( StringUtils.isNotEmpty(taskName) && StringUtils.isNotEmpty(activityName) ) {
                        parseBookHours(sheet, row, taskName, activityName);
                    }
                    else if ( StringUtils.isNotEmpty(taskName) && StringUtils.isEmpty(activityName) ) {
                        parseBookHours(sheet, row, taskName, NO_ACTIVITY);
                    }
                    else if ( StringUtils.isEmpty(taskName) && StringUtils.isNotEmpty(activityName)) {
                        String actualTaskName = findTaskName(sheet, row);
                        parseBookHours(sheet, row, actualTaskName, activityName);
                    }
                    else {
                        // both empty nothing to do here
                    }
                }
            }
        }
    }

    private String findTaskName(Sheet sheet, Row row) {
        String taskName = "";
        // We look for the latest not null task name
        // going backwards until the beginning of data rows
        for ( int i = row.getRowNum() - 1; i > DATA_STARTING_ROW; i-- ) {
            Cell taskCell = sheet.getRow(i).getCell(TASK_COL);
            if ( taskCell != null && taskCell.getCellTypeEnum().equals(STRING)) {
                if ( StringUtils.isNotEmpty(taskCell.getStringCellValue()) ) {
                    return taskCell.getStringCellValue();
                }
            }
        }
        return taskName;
    }

    private void parseBookHours(Sheet sheet, Row row, String taskName, String activityName) throws ReportingException {
        Iterator<Cell> cellIterator = row.iterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getAddress().getColumn() >= DATA_STARTING_COL) {
                if (cell.getCellTypeEnum().equals(NUMERIC)) {
                    Date date = findDateFromColumnNumber(sheet, cell.getAddress().getColumn());
                    Float hours = new Float(cell.getNumericCellValue());
                    reportingUser.reportHours(taskName, activityName, date, hours);
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
}
