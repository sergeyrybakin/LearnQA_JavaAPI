package srb_tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex10CheckForShortPhrase
{
    @ParameterizedTest
    @CsvFileSource(resources = "/Ex10/dataForEx10.csv", numLinesToSkip = 1)
    public void testCheckForShortPhrase (String name, String value){
        int length = (value!=null) ? value.length() : 0;
        System.out.println("\nString is: \"" + value + "\"\t\tthe length is: " + length);
        assertTrue(length>15, "***ERROR: The length of this string doesn't exceed 15 characters!");
    }
}
