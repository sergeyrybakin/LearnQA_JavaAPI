package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Ex12RequestHeaderTest extends BaseTestCase {

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
