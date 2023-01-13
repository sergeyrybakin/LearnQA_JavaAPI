package srb_tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class Ex9PasswordBumpingTest
{
    @Test
    public void testEx9PasswordBumping() {
        Map<String, String> data = new HashMap<>();
        data.put("login", "super_admin");
        List<String> passwords = new ArrayList<>();
        fillPasswords(passwords);
        passwords.forEach(p -> {
            System.out.println(p);
            data.put("password", p);

            Response response = RestAssured
                    .given()
                    .body(data)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

//            System.out.println("\nOnly value of auth_cookie:");
            String authCookie = response.getCookie("auth_cookie");
//            System.out.println(authCookie);

            Map<String, String> cookies = new HashMap<>();
            if (authCookie != null)
            {
                cookies.put("auth_cookie", authCookie);
            }

            Response responseForCheck = RestAssured
                    .given()
                    .body(data)
                    .cookies(cookies)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            //responseForCheck.print(); //awaiting "You are authorized"
            if(!responseForCheck.asString().contains("NOT"))
            {
                responseForCheck.print();
                System.out.println("Login: " + data.get("login") + " Password: " + p);
            }
        });



/*
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
*/
    }

    private void fillPasswords(List<String> passwords){
        passwords.add("password");
        passwords.add("1234");
        passwords.add("12345");
        passwords.add("123456");
        passwords.add("1234567");
        passwords.add("12345678");
        passwords.add("123456789");
        passwords.add("1234567890");
        passwords.add("qwerty");
        passwords.add("abc123");
        passwords.add("football");
        passwords.add("monkey");
        passwords.add("111111");
        passwords.add("letmein");
        passwords.add("dragon");
        passwords.add("baseball");
        passwords.add("sunshine");
        passwords.add("iloveyou");
        passwords.add("trustno1");
        passwords.add("princess");
        passwords.add("adobe123");
        passwords.add("admin");
        passwords.add("welcome");
        passwords.add("login");
        passwords.add("1q2w3e4r");
        passwords.add("master");
        passwords.add("letmein");
        passwords.add("666666");

    }
/*
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

 */
}
