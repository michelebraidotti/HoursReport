package my.project;

import my.project.data.ReportingUser;
import my.project.parser.MarkusExcelParser;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by michele on 3/7/17.
 */
public class ReadFilesTest {

    @Test
    public void testReadFile() throws IOException {
        String filePath = "src/test/resources/sample_data/test_Leistungsnachweis_2017.xlsx";
        ReportingUser expectedUser = new ReportingUser();
        ReportingUser resultUser = MarkusExcelParser.parse(filePath);

        assertEquals(expectedUser, resultUser);
    }
}
