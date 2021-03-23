import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import  org.junit.jupiter.api.Test;

class calculaterTest {
    calculater calc = new calculater();

    @Test
    @DisplayName("test add  method with empty String ")
    void testAddMethodWithEmptyString () {
        int numString = calc.add("");
        assertEquals(numString,0);
    }
    @Test
    @DisplayName("test add  method with ome number String ")
    void testAddMethodWithOneNumber () {
        int numString = calc.add("50");
        assertEquals(numString,50);
    }
    @Test
    @DisplayName("test add  method with Two numbers String ")
    void testAddMethodWithTowNumbers () {
        int numString = calc.add("5,6");
        assertEquals(numString,11);
    }
    @Test
    @DisplayName("test add  method with Two numbers String Contains Spaces ")
    void testAddMethodWithTowNumbersContainsSpaces () {
        int numString = calc.add("5 ,6 ");
        assertEquals(numString,11);
    }
    @Test
    @DisplayName("test add  method with one Empty Number Contains Spaces ")
    void testAddMethodWithOneEmptyNumber () {
        int numString = calc.add("5 , ");
        assertEquals(numString,5);
    }
    @Test
    @DisplayName("test add  method with Unkown Number  ")
    void testAddMethodWithUnkownNumbers () {
        int numString = calc.add("5,6,7");
        assertEquals(numString,18);
    }
    @Test
    @DisplayName("Allow the add method to handle new lines between numbers ")
    void testAddMethodWithLine () {
        int numString = calc.add("5,6,7\n1");
        assertEquals(numString,19);
    }
    @Test
    @DisplayName("Allow the add method to handle new lines between numbers ")
    void testAddMethodWithLinesAfterComa () {
        int numString = calc.add("5,6,7\n1,\n");
        assertEquals(numString,19);
    }
    @Test
    @DisplayName("change a delimiter, the beginning of the string will contain a separate line ")
    void testAddMethodWithLinesAfterComa2 () {
        int numString = calc.add("//;1,2,3\n1;\n;2");
        assertEquals(numString,9);
    }
   /* @Test
    @DisplayName("change a delimiter, the beginning of the string will contain a separate line ")
    void testAddMethodWithLinesAfterComa3 () {
        int numString = calc.add("//;1,2//3\n1;\n;2");
        assertEquals(numString,9);
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
       assertEquals("Negatives not allowed: [-1, -2]",exeption.getMessage());
   }
    @Test
    @DisplayName("Numbers bigger than 1000 ")
    void testAddMethodWithNumbersbiggerthan1000 () {
        int numString = calc.add("//;1001,2,3\n1;\n;2");
        assertEquals(numString,8);
    }
    @Test
    @DisplayName("Delimiters can be of any length ")
    void testAddMethodWithDelimitersCanBeOfAnylength () {
        int numString = calc.add("//[***]\n1***2***3");
        assertEquals(numString,6);
    }
    @Test
    @DisplayName("Allow multiple delimiters l ")
    void testAddMethodWithAllowMultipleDelimiters () {
        int numString = calc.add("//[*][%]\n1*2%3");
        assertEquals(numString,6);
    }
    @Test
    @DisplayName("Make sure you can also handle multiple delimiters with length longer than one char ")
    void testAddMethodWithAllowMultipleDelimitersLongerThanOneChar () {
        int numString = calc.add("//[%*][*%]\n1*%*2*%3");
        assertEquals(numString,6);
    }
   }
