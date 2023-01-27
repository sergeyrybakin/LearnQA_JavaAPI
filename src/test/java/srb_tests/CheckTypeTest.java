package srb_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.Headers;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Query params tests")
@Feature("Query params")
public class CheckTypeTest
{
    @Owner("Sergey Rybakin")
    @Link(name="Get params", url="https://software-testing.ru/lms/mod/url/view.php?id=307970")
    @Description("GET request with parameters in URL")
    @DisplayName("Positive GET request test. Get request with parameters in URL")
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
    @Link(name="POST with params", url="https://software-testing.ru/lms/mod/url/view.php?id=307970")
    @Description("POST request with parameters in body")
    @DisplayName("Positive POST request test. Send POST request with parameters in body")
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
    @Owner("Sergey Rybakin")
    @Link(name="GET with code 200", url="https://software-testing.ru/lms/mod/url/view.php?id=307973")
    @Description("GET request with code 200")
    @DisplayName("Positive GET request test. Send GET request and get response code 200")
    public void testGetStatusCode() {

        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        int statusCode = response.statusCode();
        System.out.println(statusCode);
    }

    @Test
    @Owner("Sergey Rybakin")
    @Link(name="GET with redirection", url="https://software-testing.ru/lms/mod/url/view.php?id=307973")
    @Description("GET request with redirection")
    @DisplayName("Positive GET request test. Send GET request, follow redirection and get code 200")
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
    @Owner("Sergey Rybakin")
    @Link(name="GET and show all response headers", url="https://software-testing.ru/lms/mod/url/view.php?id=307974")
    @Description("GET request and show all response headers")
    @DisplayName("Positive GET request test. Send GET request and show all response headers")
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
    @Owner("Sergey Rybakin")
    @Link(name="GET and show response body", url="https://software-testing.ru/lms/mod/url/view.php?id=307975")
    @Description("GET request and show response body")
    @DisplayName("Positive GET request test. Send GET request and show response body")
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
