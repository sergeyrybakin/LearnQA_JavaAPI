package srb_tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CookieTest
{
    @Test
    public void testGetCookie() {
        Map<String, String> date = new HashMap<>();
        date.put("login", "secret_login");
        date.put("password", "secret_pass");

        Response response = RestAssured
                .given()
                .body(date)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        System.out.println("\nPretty text:");
        response.prettyPrint();

        System.out.println("\nHeaders:");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        System.out.println("\nCookies:");
        Map<String,String> responseCookies = response.getCookies();
        System.out.println(responseCookies);

        System.out.println("\nOnly value of auth_cookie:");
        String auth_cookie = response.getCookie("auth_cookie");
        System.out.println(auth_cookie);
    }

    @Test
    public void testCheckAuthCookie() {
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        Response responseForGet = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        String responseAuthCookie = responseForGet.getCookie("auth_cookie");

        Map<String,String> cookies = new HashMap<>();
        if(responseAuthCookie != null) {
            cookies.put("auth_cookie", responseAuthCookie);
        }

        Response responseForCheck = RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .post("https://playground.learnqa.ru/api/check_auth_cookie")
                .andReturn();
        responseForCheck.print(); //awaiting "You are authorized"
    }
}
