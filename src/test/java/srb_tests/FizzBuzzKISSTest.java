package srb_tests;

import io.qameta.allure.Description;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class FizzBuzzKISSTest {
    @Test
    @Tag("FizzBuzz")
    @Description("Type numbers from 1 to 100."
            + "The program should output the word \"Fizz\" instead of numbers divisible by 3,"
            + "and the word \"Buzz\" instead of numbers divisible by 5."
            + "If the number is a multiple of both 3 and 5, the program should output the word \"FizzBuzz\".")
    public void fizzBuzzKISStest() {
        String s;
        int i = 1;
        for(;i < 101; i++)
        {
            if (i % 3 > 0)
            { //non divisible on 3
                if (i % 5 > 0)
                { //non divisible on 5
                    s = String.valueOf(i);
                }
                else { //divisible on 5
                    s = "Buzz";
                }
            }
            else
            {
                s = "Fizz";
                if (i % 5 == 0)
                    s = s.concat("Buzz");
            }
            System.out.println(s + "\n");
        }
    }
}
