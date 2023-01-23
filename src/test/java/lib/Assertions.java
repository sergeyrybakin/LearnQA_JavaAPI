package lib;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static org.junit.jupiter.api.Assertions.*;

public class Assertions {

    public static void assertJsonByName(Response Response, String name, int expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON " + name + " value " + value + " is not equal to expected value " + expectedValue);
    }

    public static void assertJsonByName(Response Response, String name, String expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        String value = Response.jsonPath().getString(name);
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

    public static void assertHeaderByName(Response response, String expectedNameOfHeader,
            String expectedValueOfHeader) {
        response.then().assertThat().header(expectedNameOfHeader, is(notNullValue()));

        String headerValue = response.getHeader(expectedNameOfHeader);
        assertEquals(expectedValueOfHeader,headerValue,"Header \""
                + expectedNameOfHeader
                + "\" doesn't have expected value: \""
                + expectedValueOfHeader
                + "\"!");
    }

    public static void assertBySize(int size, int expectedSize) {
        assertEquals( expectedSize, size, "Digit1 should be equal Digit2!");
    }

    public static void assertResponseTextEquals(Response Response, String expectedAnswer) {
        assertEquals(expectedAnswer
                , Response.asString()
                ,"Response text is not as expected!");
    }

    public static void assertResponseCodeEquals(Response Response, int expectedStatusCode) {
        assertEquals(expectedStatusCode
                , Response.statusCode()
                ,"Response code is not as expected!");
    }

    public static void assertJsonHasField(Response Response, String expectedFieldName) {
        Response.then().assertThat().body("$", hasKey(expectedFieldName));
    }

    public static void assertJsonHasFields(Response Response, String[] expectedFieldNames) {
        for (String expectedFieldName : expectedFieldNames) {
            Assertions.assertJsonHasField(Response,expectedFieldName);
        }
    }

    public static void assertJsonHasNotField(Response Response, String unexpectedFieldName) {
        Response.then().assertThat().body("$",not(hasKey(unexpectedFieldName)));
    }

    public static void assertJsonHasNotFields(Response Response, String[] unexpectedFieldNames) {
        for (String unexpectedFieldName : unexpectedFieldNames) {
            Assertions.assertJsonHasNotField(Response,unexpectedFieldName);
        }
    }
}
