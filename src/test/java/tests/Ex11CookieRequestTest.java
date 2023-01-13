package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Ex11CookieRequestTest extends BaseTestCase {
//HomeWork=hw_value; expires=Mon, 13-Feb-2023 14:56:24 GMT; Max-Age=2678400; path=/; domain=playground.learnqa.ru; HttpOnly
    static Stream<Arguments> stringAndStringProvider() {
        return Stream.of(
                arguments("HomeWork", "hw_value")
//                arguments("Expires", "Mon, 13-Feb-2023 14:56:24 GMT"),
//                arguments("Max-Age", "2678400"),
//                arguments("Path", "/"),
//                arguments("Domain", "playground.learnqa.ru"),
//                arguments("HttpOnly","")
        );
    }

    @ParameterizedTest
    @MethodSource("stringAndStringProvider")
    public void cookieVerificationTest(String expectedNameOfCookie, String expectedValueOfCookie) {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Assertions.assertCookieByName(response, expectedNameOfCookie, expectedValueOfCookie);
    }

}
