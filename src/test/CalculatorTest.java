import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    Calculator calc = new Calculator();

    @Test
    @DisplayName("test add method with empty string")
    void testAddMethodWithEmptySting(){
        int numString = calc.add("");
        assertEquals(numString,0);
    }
    @Test
    @DisplayName("test add method with one number string")
    void testAddMethodWithOneNumberSting(){
        int numString = calc.add("50");
        assertEquals(numString,50);
    }
    @Test
    @DisplayName("test add method with two numbers")
    void testAddMethodWithTwoNumbers(){
        int numString = calc.add("5,6");
        assertEquals(numString,11);
    }

    @Test
    @DisplayName("test add method with two numbers containing spaces")
    void testAddMethodWithTwoNumbersContainSpaces(){
        int numString = calc.add("5 ,6 ");
        assertEquals(numString,11);
    }

    @Test
    @DisplayName("test add method with two numbers one of them is just space")
    void testAddMethodWithOneEmptyNumber(){
        int numString = calc.add("5 , ");
        assertEquals(numString,5);
    }

    @Test
    @DisplayName("test add method with unknown number of numbers")
    void testAddMethodWithMoreThanThreeNumbers(){
        int numString = calc.add("5 ,6 , 7 ");
        assertEquals(numString,18);
    }

    @Test
    @DisplayName("Allow the add method to handle new lines between numbers")
    void testAddMethodWithLinesDelimiters(){
        int numString = calc.add("1 ,2 , 3\n1 ");
        assertEquals(numString,7);
    }
    @Test
    @DisplayName("Allow the add method to handle new lines between numbers and new line comes after comma")
    void testAddMethodWithLines(){
        int numString = calc.add("1 ,2 , 3\n1,\n ");
        assertEquals(numString,7);
    }

    @Test
    @DisplayName("To change a delimiter, the beginning of the string will contain a separate line")
    void testAddMethodWithLines2(){
        int numString = calc.add("//;1 ,2 , 3\n1,\n ;1");
        assertEquals(numString,8);
    }
    /*
    @Test
    @DisplayName("To change a delimiter, the beginning of the string will contain a separate line")
    void testAddMethodWithLines3(){
        int numString = calc.add("//;1 ,2 // 3\n1,\n ;1");
        assertEquals(numString,8);
    }
     */
    @Test
    @DisplayName("negative numbers are not allowed")
    void testAddMethodWithNegativeNumbers(){
        RuntimeException exeption = null;
        try{
            int numString = calc.add("//;-1 ,-2 , 3\n1,\n ;1");
        }catch( RuntimeException ex){
            exeption = ex;
        }
        assertNotNull(exeption);
        assertEquals(exeption.getMessage(),"Negatives are not allowed [-1, -2]");
    }
    @Test
    @DisplayName("Numbers bigger than 1000 should be ignored")
    void testAddMethodWithNumbersBiggerThan1000(){
        int numString = calc.add("//;1001 ,2 , 3\n1,\n ;1");
        assertEquals(numString,7);
    }


    @Test
    @DisplayName("Delimiters can be of any length with the following format: “//[delimiter]\n”")
    void testAddMethodWithDelimiterAnyLength(){
        int numString = calc.add("//[***]\n1***2***3");
        assertEquals(numString,6);
    }

    @Test
    @DisplayName("Allow multiple delimiters like this: “//[delim1][delim2]\n”")
    void testAddMethodWithMultiDelimeters(){
        int numString = calc.add("//[*]\n//[%]\n1*2%3");
        assertEquals(numString,6);
    }

    @Test
    @DisplayName("Make sure you can also handle multiple delimiters with length longer than one char.")
    void testAddMethodWithMultiDelimetersLongerThanOneChar(){
        int numString = calc.add("//[*]\n//[%]\n1**2%%3");
        assertEquals(numString,6);
    }

}