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
}