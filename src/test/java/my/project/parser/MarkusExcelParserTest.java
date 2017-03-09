package my.project.parser;

import my.project.data.ReportingUser;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 3/9/17.
 */
public class MarkusExcelParserTest {

    @Test
    public void parse() throws IOException {
        String filePath = "src/test/resources/sample_data/test_Leistungsnachweis_2017.xlsx";
        ReportingUser expectedUser = new ReportingUser();
        expectedUser.setName("Michele");
        MarkusExcelParser markusExcelParser = new MarkusExcelParser(filePath);
        markusExcelParser.parse();
        ReportingUser resultUser = markusExcelParser.getReportingUser();

        assertEquals(expectedUser, resultUser);
    }
}
