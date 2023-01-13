package srb_tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.junit.jupiter.api.Test;

public class GetJsonHomeworkTest {
    @Test
    public void testGetJsonHomework (){
        JsonPath jsonPath = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        System.out.println("\nWhole JSON text is below:");
        jsonPath.prettyPrint();

        System.out.println("\nThe second message text is below:");
        String secondMessage = jsonPath.get("messages[1].message");
        System.out.println(secondMessage);
    }
}
