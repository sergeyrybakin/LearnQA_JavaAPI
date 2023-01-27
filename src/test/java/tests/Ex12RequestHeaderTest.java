package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Epic("Hometasks")
@Feature("Ex12: Request header test")
public class Ex12RequestHeaderTest extends BaseTestCase {

    @Owner(value="Sergey Rybakin")
    @Link(name="Hometask Ex12", url="https://software-testing.ru/lms/mod/assign/view.php?id=307982")
    @Description("Positive GET test. Request header and verification of it's value")
    @DisplayName("Positive GET test. Request header")
    @ParameterizedTest
    @CsvSource("x-secret-homework-header,Some secret value")
    public void headerVerificationTest(String expectedNameOfHeader
            , String expectedValueOfHeader) {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Assertions.assertHeaderByName(response, expectedNameOfHeader, expectedValueOfHeader);
    }
}
