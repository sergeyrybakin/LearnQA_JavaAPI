import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.Headers;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CheckTypeTest
{
    @Test
    public void testCheckType() {

        Response response = RestAssured
                .given()
                .queryParam("param1", "value1")
                .queryParam("param2", "value2")
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }

    @Test
    public void testPostCheckType() {
        Map<String,Object> body = new HashMap<>();
        body.put("param1","value1");
        body.put("param2","value2");

        Response response = RestAssured
                .given()
                .body(body)
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }

    @Test
    public void testGetStatusCode() {

        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        int statusCode = response.statusCode();
        System.out.println(statusCode);
    }

    @Test
    public void testGet303StatusCode() {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(true) //follow to redirect. If .follow(false) -> statusCode will 303
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.statusCode();
        System.out.println(statusCode);
    }

    @Test
    public void testShowAllHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeaders1", "myValue1");
        headers.put("myHeaders2", "myValue2");

        Response response = RestAssured
                .given()
                .headers(headers)
                .when()
                .get("https://playground.learnqa.ru/api/show_all_headers")
                .andReturn();
        response.prettyPrint();

        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);
    }

    @Test
    public void testGetLocationHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeaders1", "myValue1");
        headers.put("myHeaders2", "myValue2");

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();
        response.prettyPrint();

        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }
}
