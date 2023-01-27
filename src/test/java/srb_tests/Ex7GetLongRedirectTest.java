package srb_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Hometasks")
@Feature("Ex7: Long redirect")
public class Ex7GetLongRedirectTest
{
    @Test
    @Owner("Sergey Rybakin")
    @Link(name="Hometask Ex7", url="https://software-testing.ru/lms/mod/assign/view.php?id=307980")
    @Description("This test has loop for waiting of redirection URL in response.")
    @DisplayName("Long redirect using URL in response.")
    public void testEx7GetLongRedirect(){
        Map<Integer, String> links = new HashMap<>();
        int i=0;
        int statusCode;
        links.put(i,"https://playground.learnqa.ru/api/long_redirect");
        String redirectionURL;

        do
        {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)//to stop redirection it should be false
                    .when()
                    .get(links.get(i))
                    .andReturn();

            redirectionURL = response.getHeader("Location");
            statusCode = response.getStatusCode();
            i = i+1;
            if(redirectionURL!=null){
                links.put(i, redirectionURL);
            }
        } while (statusCode!=200);
        //Output of all links
        links.forEach((k, v) -> {
            System.out.println(" " + k + "   Link: " + v);
        });
        System.out.println("Total amount of redirections is: " + links.size());
    }
}
