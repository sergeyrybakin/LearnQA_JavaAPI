package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import lib.ApiCoreRequests;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Epic("Registration cases")
@Feature("Registration")
public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This is negative test creation of user with existed email")
    @DisplayName("Negative POST test. Creation of user with existed email")
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        System.out.println(responseCreateUser.statusCode() + " " + responseCreateUser.asString());
        Assertions.assertResponseCodeEquals(responseCreateUser,400);
        Assertions.assertResponseTextEquals(responseCreateUser,"Users with email '" + email + "' already exists");
    }

    @Test
    @Description("This test successfully creates of a new user with the full set of valid data")
    @DisplayName("Test positive creation of user")
    public void testCreateUserSuccessfully() {
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        System.out.println(responseCreateUser.statusCode() + " " + responseCreateUser.asString());
        Assertions.assertResponseCodeEquals(responseCreateUser,200);
        Assertions.assertJsonHasField(responseCreateUser,"id");
    }

    @Test
    @Description("This is negative test creation of user with invalid email (without @)")
    @DisplayName("Negative POST test. Creation of user with invalid email")
    public void testCreateUserWithInvalidEmail() {
        String email = "vinkotov_example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        System.out.println(responseCreateUser.statusCode() + " " + responseCreateUser.asString());
        Assertions.assertResponseCodeEquals(responseCreateUser,400);
        Assertions.assertResponseTextEquals(responseCreateUser,"Invalid email format");
    }

    @Description("This is negative test creation of user w/o required parameter")
    @DisplayName("Negative POST test. Creation of user w/o one parameter")
    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    public void testNegativeAuthUser(String condition)
    {
        Map<String, String> userData = new HashMap<>();
        userData.put(condition,"");
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        System.out.println(responseCreateUser.statusCode() + " " + responseCreateUser.asString());
        Assertions.assertResponseCodeEquals(responseCreateUser,400);
        Assertions.assertResponseTextEquals(responseCreateUser,"The value of '" + condition + "' field is too short");
    }

    @Test
    @Description("This is negative test creation of user with short username (1 char)")
    @DisplayName("Negative POST test. Creation of user with short username")
    public void testCreateUserWithShortUsername() {
        String username = "v";

        Map<String, String> userData = new HashMap<>();
        userData.put("username", username);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        System.out.println(responseCreateUser.statusCode() + " " + responseCreateUser.asString());
        Assertions.assertResponseCodeEquals(responseCreateUser,400);
        Assertions.assertResponseTextEquals(responseCreateUser,"The value of 'username' field is too short");
    }

    @Test
    @Description("This is negative test: creation of user with long username (more then 250 chars)")
    @DisplayName("Negative POST test. Creation of user with 250 chars username")
    public void testCreateUserWithLongUsername() {
        String username = DataGenerator.getStringOf255Chars();

        Map<String, String> userData = new HashMap<>();
        userData.put("username", username);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        System.out.println(responseCreateUser.statusCode() + " " + responseCreateUser.asString());
        Assertions.assertResponseCodeEquals(responseCreateUser,400);
        Assertions.assertResponseTextEquals(responseCreateUser,"The value of 'username' field is too long");

    }
}
