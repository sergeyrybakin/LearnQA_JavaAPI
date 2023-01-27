package srb_tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class GetRedirectTest {
    @Test
    public void testGetRedirect(){
        Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)//to stop redirection it should be false
                    .when()
                    .get("https://playground.learnqa.ru/api/long_redirect")
                    .andReturn();

        System.out.println("\nAll Headers are below:");
        Headers allHeaders = response.getHeaders();
        System.out.println(allHeaders);

        System.out.println("\n\nRedirection URL is below:");
        String redirectionURL = response.getHeader("Location");
        System.out.println(redirectionURL);
    }
}
