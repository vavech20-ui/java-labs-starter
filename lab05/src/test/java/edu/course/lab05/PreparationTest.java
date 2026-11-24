package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreparationTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadFileAndParseNumbers() throws Exception {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

        assertEquals(3, lines.size());
        assertEquals("10", lines.get(0));
        assertEquals("bad", lines.get(1));
        assertEquals("20", lines.get(2));
    }

    @Test
    void testParseNumbersWithCycle() throws Exception {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

        List<Integer> numbers = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        
        for (String line : lines) {
            try {
                int number = Integer.parseInt(line);
                numbers.add(number);
            } catch (NumberFormatException e) {
                errors.add(line);
            }
        }

        assertEquals(2, numbers.size());
        assertEquals(10, numbers.get(0));
        assertEquals(20, numbers.get(1));
        assertEquals(1, errors.size());
        assertEquals("bad", errors.get(0));
    }

    @Test
    void testBadStringThrowsNumberFormatException() {
        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("bad");
        });
    }
}