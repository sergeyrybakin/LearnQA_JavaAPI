package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.Verifications;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;


public class Ex13UserAgentTest extends BaseTestCase {

    private ArrayList<String> listOfHeaders = new ArrayList<>();
    private ArrayList<String> listOfExpectations = new ArrayList<>();

    @Test
    public void testUserAgent() throws IOException {
        readUserAgentFromFile("src/test/resources/homeworkdata/some.txt");

        Assertions.assertBySize(listOfHeaders.size(),listOfExpectations.size());

        for(int i=0; i<listOfHeaders.size(); i++) {
            Response response = RestAssured
                    .given()
                    .header("user-agent", listOfHeaders.get(i))
                    .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                    .andReturn();
            verificationOfParameters(response, i);
        }
    }

    private void verificationOfParameters(Response response, int i)
    {
        Map<String,String> expectations = getMapOfExpectations(i);
        for (Map.Entry<String, String> entry : expectations.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            String answer = Verifications.assertJsonByName(response, k.trim(), v.trim());
            if (answer!=null) {
                System.out.printf("%d. =================================================%n", i + 1);
                System.out.println("User Agent is: " + listOfHeaders.get(i));
                System.out.println("ANSWER: JSON parameter \"" + k.trim() + "\" has value \"" + answer
                        + "\"   It's not equal to expected value \"" + v.trim() + "\"!");
            }
        }
    }

    private Map<String, String> getMapOfExpectations(int i) {
        Map<String,String> expectations = new HashMap<>();
        String str = listOfExpectations.get(i).replaceAll("'","");
        String[] arr = str.split(",");

        for(int j=0; j<arr.length; j++) {
            String[] s = arr[j].split(": ");
            expectations.put(s[0],s[1]);
        }
        return expectations;
    }

    private void readUserAgentFromFile(String fullFileName) throws IOException {
        // read file into a stream of lines.
        Path fileName = Paths.get(fullFileName);
        listOfHeaders = (ArrayList<String>) Files.lines(fileName)
                .filter(l -> (l.startsWith("'Mozilla")) )
                .collect(Collectors.toList());
        listOfExpectations = (ArrayList<String>) Files.lines(fileName)
                .filter(l -> (l.startsWith("'platform")) )
                .collect(Collectors.toList());
    }
}
