package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class TaskFileLoader {
    
    private static final String EXPECTED_HEADER = "id;title;category;estimatedHours;status";
    private static final int EXPECTED_FIELDS_COUNT = 5;
    private static final String SEPARATOR = ";";
    
    private static final List<String> VALID_STATUSES = List.of("READY", "IN_PROGRESS", "DONE");

    public LoadResult load(Path path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DataLoadingException("Cannot read " + path, e);
        }

        if (lines.isEmpty()) {
            throw new DataLoadingException("File is empty: " + path, null);
        }

        String header = lines.get(0);
        if (!header.equals(EXPECTED_HEADER)) {
            throw new DataLoadingException("Invalid header: " + header, null);
        }

        List<Task> tasks = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineNumber = i + 1; 

            if (line.trim().isEmpty()) {
                continue;
            }

            try {
                Task task = parseLine(line, lineNumber);
                tasks.add(task);
            } catch (Exception e) {
                errors.add(new ValidationError(lineNumber, "row", e.getMessage()));
            }
        }

        return new LoadResult(tasks, errors);
    }

    private Task parseLine(String line, int lineNumber) {
        String[] fields = line.split(SEPARATOR, -1);

        if (fields.length != EXPECTED_FIELDS_COUNT) {
            throw new IllegalArgumentException("Expected " + EXPECTED_FIELDS_COUNT + 
                    " fields, but got " + fields.length);
        }

        String id = fields[0].trim();
        String title = fields[1].trim();
        String category = fields[2].trim();
        String hoursStr = fields[3].trim();
        String status = fields[4].trim();

        if (id.isEmpty()) {
            throw new IllegalArgumentException("Field 'id' is empty");
        }
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Field 'title' is empty");
        }
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Field 'category' is empty");
        }
        if (hoursStr.isEmpty()) {
            throw new IllegalArgumentException("Field 'estimatedHours' is empty");
        }
        if (status.isEmpty()) {
            throw new IllegalArgumentException("Field 'status' is empty");
        }

        int estimatedHours;
        try {
            estimatedHours = Integer.parseInt(hoursStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number for estimatedHours: " + hoursStr, e);
        }

        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Unknown status: " + status);
        }

        return new Task(id, title, category, estimatedHours, status);
    }
}