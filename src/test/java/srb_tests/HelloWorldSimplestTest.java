package srb_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

public class HelloWorldSimplestTest
{
    @Test
    public void testRestAssured() {
        Response response = RestAssured
                .given()
                .queryParam("name","Sergey")
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();
            response.prettyPrint();
    }
}
