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

@Epic("Edit user's data cases")
@Feature("Edit user's data")
public class UserEditTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("Positive PUT test. Edit the first name of user.")
    @DisplayName("Positive PUT test. Edit the first name of user.")
    public void testEditJustCreatedTest() {
        //CREATE A NEW USER AND LOGIN
        Response responseGetAuth = apiCoreRequests.generateNewUserAndLogin();
        String userId = responseGetAuth.jsonPath().getString("user_id");

        //EDIT
        String newName = "Changed Name";
        Map<String,String> editData = new HashMap<>();
        editData.put("firstName", newName);

        String token = getHeader(responseGetAuth, "x-csrf-token");
        String cookie = getCookie(responseGetAuth, "auth_sid");

        apiCoreRequests.makePutRequestToEditUserWithId(
                "https://playground.learnqa.ru/api/user/" + userId
                , token
                , cookie
                , editData);

        Response responseUserData = apiCoreRequests.makeGetRequestWithUserId(
                "https://playground.learnqa.ru/api/user/"
                ,token
                , cookie
                , userId);

        System.out.println(responseUserData.asString());
        Assertions.assertJsonByName(responseUserData,"firstName",newName);
    }
}
