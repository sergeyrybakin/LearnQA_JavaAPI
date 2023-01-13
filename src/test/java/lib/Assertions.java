package lib;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {

    public static void assertJsonByName(Response Response, String name, int expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

    public static void assertCookieByName(Response response, String expectedNameOfCookie, String expectedValueOfCookie) {
        response.then().assertThat().cookie(expectedNameOfCookie, is(notNullValue()));

        String cookieValue = response.getCookie(expectedNameOfCookie);
        assertEquals(expectedValueOfCookie, cookieValue,"Cookie \""
                + expectedNameOfCookie
                + "\" doesn't have expected value: \""
                + expectedValueOfCookie
                + "\"!");
    }
}
