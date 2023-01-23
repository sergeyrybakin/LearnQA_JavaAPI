package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {

    @Step("Make a two POST requests for creation a new user and login as it.")
    public static Response generateNewUserAndLogin() {
        //GENERATE NEW USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user")
                .jsonPath();


        String userId = responseCreateAuth.getString("id");
        //LOGIN AS NEW GENERATED USER
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));
        System.out.println("New generated user's data:\nUserId " + userId + " User name " + userData.get("firstName"));

        return given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
    }

    @Step("Make a GET-request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET-request with auth cookie only")
    public Response makeGetRequestWithCookie(String url, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET-request with token only")
    public Response makeGetRequestWithToken(String url, String token) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }

    @Step("Make a POST-request")
    public Response makePostRequest(String url, Map<String,String> authData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("Make a GET-request user data by it's userId")
    public Response makeGetRequestWithUserId(String url, String userId) {
        return RestAssured
                .get(url + userId)
                .andReturn();
    }

    @Step("Make a GET-request user data by it's userId as authorized user with token and cookie")
    public Response makeGetRequestWithUserId(String url, String token, String cookie, String userId) {
        return RestAssured
                .given()
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .get(url + userId)
                .andReturn();
    }

    @Step("Make a PUT-request to edit user with userId as authorized user with token and cookie.")
    public Response makePutRequestToEditUserWithId(String url, String token, String cookie, Map<String, String> editData) {
        return  RestAssured
                .given()
                .header("x-csrf-token",token)
                .cookie("auth_sid", cookie)
                .body(editData)
                .put(url)
                .andReturn();
    }
}
