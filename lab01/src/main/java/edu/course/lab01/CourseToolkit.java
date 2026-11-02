package edu.course.lab01;

public final class CourseToolkit {

    // Метод уже был в шаблоне, оставляем его для совместимости
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * Проверяет, является ли число простым.
     * Контракт: false для n < 2, корректная обработка квадратов простых чисел.
     */
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
        // Перебираем только нечетные делители до sqrt(number) включительно
        for (int divisor = 3; divisor * divisor <= number; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяет, является ли строка палиндромом.
     * Контракт: регистр и пробелы значимы, null вызывает IllegalArgumentException.
     */
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

    /**
     * Вычисляет среднее арифметическое массива.
     * Контракт: возвращает double, не меняет массив, 
     * null или пустой массив вызывают IllegalArgumentException.
     */
    public static double average(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        long sum = 0L; // Используем long для защиты от переполнения при суммировании
        for (int value : values) {
            sum += value;
        }
        return (double) sum / values.length;
    }
}