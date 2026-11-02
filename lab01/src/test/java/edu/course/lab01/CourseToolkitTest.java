package edu.course.lab01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseToolkitTest {

    @Test
    void returnsTrueForNegativeEvenNumber() {
        assertTrue(CourseToolkit.isEven(-8));
    }

    @Test
    void isPrimeReturnsFalseForNumbersLessThanTwo() {
        assertFalse(CourseToolkit.isPrime(1));
        assertFalse(CourseToolkit.isPrime(0));
        assertFalse(CourseToolkit.isPrime(-5));
    }

    @Test
    void isPrimeReturnsTrueForTwo() {
        assertTrue(CourseToolkit.isPrime(2));
    }

    @Test
    void isPrimeReturnsTrueForSquareOfPrime() {
        assertFalse(CourseToolkit.isPrime(49));
        assertFalse(CourseToolkit.isPrime(9)); 
    }

    @Test
    void isPrimeReturnsTrueForActualPrimes() {
        assertTrue(CourseToolkit.isPrime(3));
        assertTrue(CourseToolkit.isPrime(17));
        assertTrue(CourseToolkit.isPrime(97));
    }

    @Test
    void isPalindromeReturnsTrueForValidPalindrome() {
        assertTrue(CourseToolkit.isPalindrome("abba"));
        assertTrue(CourseToolkit.isPalindrome("a"));
        assertTrue(CourseToolkit.isPalindrome(""));
    }

        @Test
    void isPalindromeIsCaseAndSpaceSensitive() {
        assertFalse(CourseToolkit.isPalindrome("Abba"));
        assertFalse(CourseToolkit.isPalindrome("abc ba"));
    }
    @Test
    void isPalindromeThrowsExceptionForNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.isPalindrome(null));
    }

    @Test
    void averageReturnsCorrectDoubleResult() {
        assertEquals(2.5, CourseToolkit.average(new int[]{1, 2, 3, 4}), 1e-9);
    }

    @Test
    void averageHandlesNegativeNumbers() {
        assertEquals(-1.0, CourseToolkit.average(new int[]{-3, -1, 1}), 1e-9);
    }

    @Test
    void averageThrowsExceptionForNullOrEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(null));
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(new int[]{}));
    }
    
    @Test
    void averageDoesNotModifyInputArray() {
        int[] original = {10, 20, 30};
        int[] copy = original.clone();
        CourseToolkit.average(original);
        assertArrayEquals(copy, original);
    }
}