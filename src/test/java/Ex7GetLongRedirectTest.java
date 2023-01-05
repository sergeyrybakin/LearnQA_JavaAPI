import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class Ex7GetLongRedirectTest
{
    @Test
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
