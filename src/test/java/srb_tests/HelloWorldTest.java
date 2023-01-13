package srb_tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest
{
    @ParameterizedTest
    @ValueSource(strings = {"", "Sergey", "Pete"})
    public void testHelloMethodWithName(String name) {
        Map<String, String> queryParams = new HashMap<>();

        if(name.length() > 0) {
            queryParams.put("name", name);
        }

        JsonPath jsonPath = RestAssured
                .given()
                .queryParams(queryParams)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        String answer = jsonPath.getString("answer");
        String expectedName = (name.length() > 0) ? name : "someone";
        assertEquals("Hello, " + expectedName, answer, "The answer is not expected");
    }
}
