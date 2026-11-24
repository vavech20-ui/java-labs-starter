package edu.course.lab01;

public final class CourseToolkit {


    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        for (int divisor = 3; divisor * divisor <= number; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }


    public static boolean isPalindrome(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input string must not be null");
        }
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static double average(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return (double) sum / values.length;
    }
}