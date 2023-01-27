package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Hometasks")
@Feature("Ex10: Short phrase verification")
public class Ex10CheckForShortPhraseTest
{
    @Owner("Sergey Rybakin")
    @Link(name="Hometask Ex10", url="https://software-testing.ru/lms/mod/assign/view.php?id=307992")
    @Description("Set of tests for phrase length verification")
    @DisplayName("Short phrase test")
    @ParameterizedTest
    @CsvFileSource(resources = "/homeworkdata/dataForEx10.csv", numLinesToSkip = 1) //Read from file and skip the 1st line.
    public void testCheckForShortPhrase (String name, String value){
        int length = (value!=null) ? value.length() : 0;
        System.out.println("\nString is: \"" + value + "\"\t\tthe length is: " + length);
        assertTrue(length>15, "***ERROR: The length of this string doesn't exceed 15 characters!");
    }
}
