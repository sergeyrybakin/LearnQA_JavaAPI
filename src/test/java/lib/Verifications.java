package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;

public class Verifications {
    public static String assertJsonByName(Response Response, String name, String expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        String value = Response.jsonPath().get(name);
        if (expectedValue.equals(value) != true) {
            return value;
        } else
            return null;
    }
}
