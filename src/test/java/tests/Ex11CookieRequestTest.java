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

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@Epic("Hometasks")
@Feature("Ex11: Cookie request")
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

    @Owner("Sergey Rybakin")
    @Link(name="Hometask Ex11", url="https://software-testing.ru/lms/mod/assign/view.php?id=307993")
    @Description("Set of tests for cookie request")
    @DisplayName("Cookie request test")
    @ParameterizedTest
    @MethodSource("stringAndStringProvider")
    public void cookieVerificationTest(String expectedNameOfCookie, String expectedValueOfCookie) {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Assertions.assertCookieByName(response, expectedNameOfCookie, expectedValueOfCookie);
    }

}
