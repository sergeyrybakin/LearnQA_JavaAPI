package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Get user's data cases")
@Feature("User's data")
public class UserGetTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test get user data w/o authorizing")
    @DisplayName("Test positive. This test get user data w/o authorizing")
    public void testGetUserDataNotAuth() {
        Response responseUserData = apiCoreRequests.makeGetRequestWithUserId(
                "https://playground.learnqa.ru/api/user/"
                , "2");

        System.out.println(responseUserData.asString());
        Assertions.assertJsonHasField(responseUserData,"username");
        String[] unexpectedFields = {"firstName", "lastName", "email"};
        Assertions.assertJsonHasNotFields(responseUserData,unexpectedFields);
    }

    @Test
    @Description("This test successfully authorize user and get own user data")
    @DisplayName("Test positive. Getting of own user data")
    public void testGetUserDetailsAuthAsSameUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login"
                , authData);

        String requestedUserId = String.valueOf(getIntFromJson(responseGetAuth,"user_id"));

        //GET USER DATA (with userId=2)
        Response responseUserData = apiCoreRequests.makeGetRequestWithUserId(
                "https://playground.learnqa.ru/api/user/"
                , getHeader(responseGetAuth, "x-csrf-token")
                , getCookie(responseGetAuth, "auth_sid")
                , requestedUserId);

        String[] expectedFields = {"username", "firstName", "lastName", "email"};
        Assertions.assertJsonHasFields(responseUserData,expectedFields);
    }


    @Test
    @Description("This test successfully authorize user and get data of another user")
    @DisplayName("Test positive. Getting of another user data")
    public void testGetUserDetailsAuthAsAnotherUser() {
        Response responseGetAuth = apiCoreRequests.generateNewUserAndLogin();

        //GET ANOTHER USER DATA (with userId=1)
        String requestedUserId = "1";
        Response responseUserData = apiCoreRequests.makeGetRequestWithUserId(
                "https://playground.learnqa.ru/api/user/"
                , this.getHeader(responseGetAuth, "x-csrf-token")
                , this.getCookie(responseGetAuth, "auth_sid")
                , requestedUserId);

        System.out.println(
                "RequestedUserId " + requestedUserId + "   Requested user data: " + responseUserData.asString());
        Assertions.assertJsonHasField(responseUserData,"username");
        String[] unexpectedFields = {"firstName", "lastName", "email"};
        Assertions.assertJsonHasNotFields(responseUserData,unexpectedFields);

    }
}
