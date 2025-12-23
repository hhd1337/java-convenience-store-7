package store.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DelimitedFileReader {

    private static final String DELIMITER = ",";

    public static <T> List<T> read(Path filePath, RowMapper<T> mapper) {
        List<String> lines = readLinesWithoutHeader(filePath);
        List<T> result = new ArrayList<>();

        for (String line : lines) {
            result.add(mapper.map(line.split(DELIMITER)));
        }
        return result;
    }

    private static List<String> readLinesWithoutHeader(Path filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            reader.readLine(); // 헤더 제거
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    @FunctionalInterface
    public interface RowMapper<T> {
        T map(String[] columns);
    }
}

