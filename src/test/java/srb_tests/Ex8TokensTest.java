package srb_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;

@Epic("Hometasks")
@Feature("Ex8: Token test")
public class Ex8TokensTest{

    @Test
    @Owner("Sergey Rybakin")
    @Link(name="Ex8: Token test", url="https://software-testing.ru/lms/mod/url/view.php?id=307974")
    @Description("Get token header and delay from response")
    @DisplayName("Get token header and delay from response")
    public void testEx8Tokens(){
        Map<Integer,String> statusMessage = new HashMap<>();
        statusMessage.put(1, "Job is NOT ready");
        statusMessage.put(2, "Job is ready");

        JsonPath jsonPath = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        System.out.println("\nWhole JSON text is below:");
        jsonPath.prettyPrint();

        String token = jsonPath.get("token");
        int delay = jsonPath.get("seconds");

        for(int i=1; i<3; i++){
            String status = "";
            String result = "";
            jsonPath = RestAssured
                    .given()
                    .queryParam("token", token)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();
            result = jsonPath.getString("result");
            status = jsonPath.getString("status");
            System.out.println(
                    "\nExpected status is: '" + statusMessage.get(i) + "' -->  Actual status is: '" + status + "'");

            if(result != null){
                System.out.println("Result is: '" + result + "'");
            }
            else{
                System.out.println("Waiting for: " + delay + " sec");
                try{
                    sleep(delay*1000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
