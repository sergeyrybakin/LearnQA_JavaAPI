package srb_tests.streams;

import io.qameta.allure.Description;

import java.util.Comparator;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class StreamTests {

    protected int a[] = {11,15,15,12,17,13,14,19,20,16,18,17};

    //    @Test
//    @Description("Print all values of integer array from 1 to 10")
//    void integerArrayTest() {
//        IntStream.of(a)
//                .sorted()
//                .forEach(x -> System.out.println(">" + x))
//    }

//    @Test
//    @Description("Sort values of integer array from min to max")
//    void integerArrayRemoveDuplicationElements() {
//        Comparator comparatorReverseOrder = Comparator.reverseOrder();
//
//        System.out.print("\nOriginal array: \n");
//        IntStream.of(a).forEach(x -> {System.out.print(" " + x);});
//        System.out.print("\nTotal amount of original array: " + IntStream.of(a).count());
//        System.out.print("\n\n");
//        IntStream.of(a)
//                .distinct() //remove duplicated values
//                .sorted(comparatorReverseOrder)
//                .forEach(x -> {System.out.print(" " + x);});
//        System.out.print("\nTotal amount of array after duplication removing: " + IntStream.of(a).distinct().count());
//    }


}
