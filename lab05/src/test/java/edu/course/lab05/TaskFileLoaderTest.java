package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileLoaderTest {

    private final TaskFileLoader loader = new TaskFileLoader();

    @TempDir
    Path tempDir;

    @Test
    void testCorrectFile() throws Exception {
        Path file = tempDir.resolve("tasks.txt");
        List<String> lines = List.of(
                "id;title;category;estimatedHours;status",
                "T-101;Prepare release;backend;8;READY",
                "T-102;Fix bug;frontend;4;DONE"
        );
        Files.write(file, lines, StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(2, result.getTasks().size());
        assertEquals(0, result.getErrors().size());
        
        Task task1 = result.getTasks().get(0);
        assertEquals("T-101", task1.getId());
        assertEquals("Prepare release", task1.getTitle());
        assertEquals("backend", task1.getCategory());
        assertEquals(8, task1.getEstimatedHours());
        assertEquals("READY", task1.getStatus());
    }
    @Test
    void testCyrillicText() throws Exception {
        Path file = tempDir.resolve("tasks_cyrillic.txt");
        List<String> lines = List.of(
                "id;title;category;estimatedHours;status",
                "T-201;Подготовка релиза;бэкенд;10;READY",
                "T-202;Исправление ошибки;фронтенд;5;DONE"
        );
        Files.write(file, lines, StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(2, result.getTasks().size());
        assertEquals(0, result.getErrors().size());
        
        Task task1 = result.getTasks().get(0);
        assertEquals("T-201", task1.getId());
        assertEquals("Подготовка релиза", task1.getTitle());
        assertEquals("бэкенд", task1.getCategory());
    }

    @Test
    void testEmptyField() throws Exception {
        Path file = tempDir.resolve("tasks_empty_field.txt");
        List<String> lines = List.of(
                "id;title;category;estimatedHours;status",
                "T-301;;backend;8;READY"
        );
        Files.write(file, lines, StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(0, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        
        ValidationError error = result.getErrors().get(0);
        assertEquals(2, error.getLineNumber());
        assertTrue(error.getMessage().contains("title"));
    }

    @Test
    void testInvalidNumber() throws Exception {
        Path file = tempDir.resolve("tasks_invalid_number.txt");
        List<String> lines = List.of(
                "id;title;category;estimatedHours;status",
                "T-401;Task;backend;abc;READY"
        );
        Files.write(file, lines, StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(0, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        
        ValidationError error = result.getErrors().get(0);
        assertEquals(2, error.getLineNumber());
        assertTrue(error.getMessage().contains("estimatedHours"));
    }
    @Test
    void testUnknownStatus() throws Exception {
        Path file = tempDir.resolve("tasks_unknown_status.txt");
        List<String> lines = List.of(
                "id;title;category;estimatedHours;status",
                "T-501;Task;backend;8;UNKNOWN"
        );
        Files.write(file, lines, StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(0, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        
        ValidationError error = result.getErrors().get(0);
        assertEquals(2, error.getLineNumber());
        assertTrue(error.getMessage().contains("status"));
    }

    @Test
    void testWrongNumberOfColumns() throws Exception {
        Path file = tempDir.resolve("tasks_wrong_columns.txt");
        List<String> lines = List.of(
                "id;title;category;estimatedHours;status",
                "T-601;Task;backend;8"
        );
        Files.write(file, lines, StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(0, result.getTasks().size());
        assertEquals(1, result.getErrors().size());
        
        ValidationError error = result.getErrors().get(0);
        assertEquals(2, error.getLineNumber());
        assertTrue(error.getMessage().contains("fields"));
    }

    @Test
    void testMixedCorrectAndIncorrectLines() throws Exception {
        Path file = tempDir.resolve("tasks_mixed.txt");
        List<String> lines = List.of(
                "id;title;category;estimatedHours;status",
                "T-701;Good task;backend;8;READY",
                "T-702;Bad number;backend;abc;READY",
                "T-703;Another good;frontend;4;DONE",
                "T-704;Bad status;backend;5;INVALID"
        );
        Files.write(file, lines, StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertEquals(2, result.getTasks().size());
        assertEquals(2, result.getErrors().size());
        
        assertEquals("T-701", result.getTasks().get(0).getId());
        assertEquals("T-703", result.getTasks().get(1).getId());
    }

    @Test
    void testMissingFile() {
        Path nonExistentFile = tempDir.resolve("nonexistent.txt");
        
        assertThrows(DataLoadingException.class, () -> {
            loader.load(nonExistentFile);
        });
    }
}