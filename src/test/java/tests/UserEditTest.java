package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@Epic("Edit user's data cases")
@Feature("Edit user's data")
public class UserEditTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    private final String[] unexpectedFields = {"firstName", "lastName", "email, password"};


    @Test
    @Description("Positive PUT test. Edit the first name of user.")
    @DisplayName("Positive PUT test. Edit the first name of user.")
    public void editJustCreatedTest() {
        //CREATE A NEW USER AND LOGIN
        Response responseGetAuth = apiCoreRequests.createNewUserAndLogin();
        String userId = responseGetAuth.jsonPath().getString("user_id");
        String token = getHeader(responseGetAuth, "x-csrf-token");
        String cookie = getCookie(responseGetAuth, "auth_sid");

        //EDIT
        String newName = "Changed Name";
        Map<String,String> editData = new HashMap<>();
        editData.put("firstName", newName);

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

    @Description("Negative PUT test. Non authorized user can't edit user data.")
    @DisplayName("Negative PUT test. Non authorized user can't edit the data of another user.")
    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    public void nonAuthorizedUserCantEditTest(String parameter) {
        String userId = "2";

        //EDIT
        String newValue = "changed" + parameter;
        System.out.println("Parameter: " + parameter + "   newValue: " + newValue);
        Map<String,String> editData = new HashMap<>();
        editData.put(parameter, newValue);

        Response response = apiCoreRequests.makePutRequestToEditUserWithId(
                "https://playground.learnqa.ru/api/user/" + userId
                , editData);

        Assertions.assertResponseTextEquals(response,"Auth token not supplied");

        Response responseUserData = apiCoreRequests.makeGetRequestWithUserId(
                "https://playground.learnqa.ru/api/user/"
                , ""
                , ""
                , userId);

        Assertions.assertJsonByName(responseUserData,"username", "Vitaliy");
        Assertions.assertJsonHasNotFields(responseUserData,unexpectedFields);
    }

    @Description("Negative PUT test. Edit the username of another user.")
    @DisplayName("Negative PUT test. Edit the username of another user as authorized user.")
    @ParameterizedTest
    @ValueSource(strings = {"email", "username", "firstName", "lastName", "password"})
    public void editAnotherJustCreatedUserTest(String parameter) {
        //GENERATE NEW USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        String originalEmail = userData.get("email");
        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user"
                , userData);
        Assertions.assertResponseCodeEquals(responseCreateAuth,200);
        String userId = responseCreateAuth.jsonPath().getString("id");
        System.out.println("New created user for login id: "+ userId + "\t\tOriginal email " + originalEmail);

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

        //EDIT
        String originalValue = testUserData.get(parameter);
        String newValueForTestUser = "changed" + originalValue;
        Map<String,String> editData = new HashMap<>();
        editData.put(parameter, newValueForTestUser);
        System.out.println("New created test user id: "+ testUserId + "\t\tit's "+ parameter + ": " + newValueForTestUser);

        Response responseAfterPut = apiCoreRequests.makePutRequestToEditUserWithId(
                "https://playground.learnqa.ru/api/user/" + testUserId
                , token
                , cookie
                , editData);
        Assertions.assertResponseCodeEquals(responseAfterPut, 200);

        //VERIFY USER DATA
        Response responseForVerification = apiCoreRequests.makeGetRequestWithUserId("https://playground.learnqa.ru/api/user/",token,cookie,testUserId);
        System.out.println("testUserId: " + testUserId);
        Assertions.assertResponseCodeEquals(responseAfterPut, 200);
        Assertions.assertJsonHasField(responseForVerification,"username");
        Assertions.assertJsonHasNotFields(responseForVerification,unexpectedFields);
    }

    @Description("Negative PUT test. Can't save illegal parameter.")
    @DisplayName("Negative PUT test. User can't save the illegal format of own parameter.")
    @ParameterizedTest
    @CsvSource({
            "email,     @, 400, Invalid email format",
            "firstName, f, 400, {\"error\":\"Too short value for field firstName\"}"
    })
    public void justCreatedUserEditOwnParametersTest(String parameter, String newValue, int code, String errorMessage) {
        //GENERATE NEW USER
        Map<String, String> userData = DataGenerator.getRegistrationData();
        String originalEmail = userData.get("email");
        String originalValue = userData.get(parameter);
        JsonPath responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user"
                , userData).jsonPath();
        String userId = responseCreateAuth.getString("id");

        //LOGIN AS NEW GENERATED USER
        Map<String, String> authData = new HashMap<>();
        authData.put("email", originalEmail);
        authData.put("password", userData.get("password"));
        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        String token = getHeader(responseGetAuth, "x-csrf-token");
        String cookie = getCookie(responseGetAuth, "auth_sid");

        //EDIT
        String newEditedValue = "";
        if(parameter.equals("email")) {
            int pos = originalEmail.indexOf(newValue);
            newEditedValue =
                    originalEmail.substring(0, pos) + originalEmail.substring(pos + 1);
        } else if(parameter.equals("firstName")) {
            newEditedValue = newValue;
        }

        Map<String,String> editData = new HashMap<>();
        editData.put(parameter, newEditedValue);
        System.out.println("Original " + parameter + ": '" + originalValue + "'\t\tnew " + parameter + ": '" + newEditedValue
                + "'\t\tcode: " + code + "\t" + errorMessage);

        Response responseAfterPut = apiCoreRequests.makePutRequestToEditUserWithId(
                "https://playground.learnqa.ru/api/user/" + userId
                , token
                , cookie
                , editData);

        Assertions.assertResponseCodeEquals(responseAfterPut, code);
        Assertions.assertResponseTextEquals(responseAfterPut, errorMessage);
    }

}
