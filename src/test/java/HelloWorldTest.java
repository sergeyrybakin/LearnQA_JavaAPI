import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HelloWorldTest
{
    @Test
    public void testRestAssured() {
        Map<String, String> params = new HashMap<>();
        params.put("name","Sergey");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        String answer = response.get("answer");
        System.out.println(answer);
    }
}
