package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Epic("Delete cases")
@Feature("Delete")
public class UserDeleteTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("Negative test. DELETE user with id=2.")
    @DisplayName("Negative test. DELETE user with id=2 using its autorisation.")
    public void deleteUserWithId2Test() {
        //Login as user with id=2
        String userId = "2";
        Map<String, String> userData = new HashMap<>();
        userData.put("email","vinkotov@example.com");
        userData.put("password","1234");
        Response responseOfAuthorization = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", userData);
        Assertions.assertResponseCodeEquals(responseOfAuthorization,200);

        Response responseOfDelete = apiCoreRequests.makeDeleteRequest("https://playground.learnqa.ru/api/user/" + userId);

        Assertions.assertResponseCodeEquals(responseOfDelete,400);
        Assertions.assertResponseTextEquals(responseOfDelete,"Auth token not supplied");
        //Verify that user id=2 is existed
        Response responseOfVerification = apiCoreRequests.makeGetRequestWithUserId("https://playground.learnqa.ru/api/user/", userId);
        Assertions.assertResponseCodeEquals(responseOfAuthorization,200);
        Assertions.assertJsonByName(responseOfVerification,"username", "Vitaliy");
    }

    @Test
    @Description("Positive test. DELETE own user. Create a new user. Login. DELETE itself.")
    @DisplayName("Positive test. DELETE own user.")
    public void deleteOwnUserById() {
        //Create a new user
        Map<String,String> newUserData = DataGenerator.getRegistrationData();
        Response responseOfCreation = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", newUserData);
        Assertions.assertResponseCodeEquals(responseOfCreation,200);
        String userId = responseOfCreation.jsonPath().getString("id");

        //Login as new created user
        Map<String, String> authData = new HashMap<>();
        authData.put("email", newUserData.get("email"));
        authData.put("password", newUserData.get("password"));
        Response responseAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        Assertions.assertResponseCodeEquals(responseOfCreation,200);
        String token = responseAuth.getHeader("x-csrf-token");
        String cookie = responseAuth.getCookie("auth_sid");

        //Get own user data
        Response responseForVerification = apiCoreRequests.makeGetRequestWithUserId(
                "https://playground.learnqa.ru/api/user/"
                ,token
                ,cookie
                ,userId );
        Assertions.assertResponseCodeEquals(responseForVerification,200);
        Assertions.verificationOfUserData(responseForVerification, newUserData);

        //Removing of user
        Response responseOfDeleteUser = apiCoreRequests.makeDeleteRequest("https://playground.learnqa.ru/api/user/" + userId);
        Assertions.assertResponseCodeEquals(responseForVerification,200);
        //Get own user data after removing of user
        Response responseAfterRemoving = apiCoreRequests.makeGetRequestWithUserId(
                "https://playground.learnqa.ru/api/user/"
                ,token
                ,cookie
                ,userId );
        Assertions.assertResponseCodeEquals(responseAfterRemoving,200);
        Assertions.verificationOfUserData(responseAfterRemoving, newUserData);
    }

    @Test
    @Description("Negative DELETE test. Delete another user, just created.")
    @DisplayName("Negative DELETE test. Delete another, just created, user as authorized user.")
    public void deleteAnotherJustCreatedUserTest() {
        //GENERATE NEW USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        String originalEmail = userData.get("email");
        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user"
                , userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth,200);
        String userId = responseCreateAuth.jsonPath().getString("id");
//        System.out.println("New created user for login id: "+ userId + "\t\tOriginal email " + originalEmail);

        //LOGIN AS NEW GENERATED USER
        Map<String, String> authData = new HashMap<>();
        authData.put("email", originalEmail);
        authData.put("password", userData.get("password"));
        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        Assertions.assertResponseCodeEquals(responseGetAuth,200);

        String token = getHeader(responseGetAuth, "x-csrf-token");
        String cookie = getCookie(responseGetAuth, "auth_sid");

        //CREATE NEW TEST USER
        Map<String, String> testUserData = DataGenerator.getRegistrationData();
        Response responseCreateTestUser = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user"
                , testUserData);
        Assertions.assertResponseCodeEquals(responseCreateTestUser,200);
        String testUserId = responseCreateTestUser.jsonPath().getString("id");
//        System.out.println("New created user for removing has id: " + testUserId);

        //Get another user data before removing
        Response responseForVerification = apiCoreRequests.makeGetRequestWithUserId(
                "https://playground.learnqa.ru/api/user/"
                ,token
                ,cookie
                ,testUserId );
        Assertions.assertResponseCodeEquals(responseForVerification,200);
        Assertions.assertJsonByName(responseForVerification,"username","learnqa");

        //Delete another user
        Response responseOfDeleteUser = apiCoreRequests.makeDeleteRequest("https://playground.learnqa.ru/api/user/" + testUserId);
        Assertions.assertResponseCodeEquals(responseForVerification,200);
        //Get own user data after removing of user
        Response responseAfterRemoving = apiCoreRequests.makeGetRequestWithUserId(
                "https://playground.learnqa.ru/api/user/"
                ,token
                ,cookie
                ,testUserId );
        Assertions.assertResponseCodeEquals(responseAfterRemoving,200);
        Assertions.assertJsonByName(responseForVerification,"username","learnqa");
    }
}
