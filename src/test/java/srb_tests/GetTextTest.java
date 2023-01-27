package srb_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class GetTextTest {
    @Test
    public void testGetText () {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_text")
                .andReturn();
        System.out.println(response.prettyPrint());
    }
}
