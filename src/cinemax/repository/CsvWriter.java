package repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import exception.PersistenceException;
import model.PersistenceError;

/**
 * Writer class for .csv files.
 * Supports append, update and delete operations.
 * All operations are atomic at the file level — the file is either
 * fully written or left unchanged on failure.
 */
public enum CsvWriter {
    INSTANCE;

    CsvWriter() {
    }

    /**
     * Appends a new line at the end of the file.
     * The line must already be formatted as a valid CSV row.
     *
     * @param path file path, not null
     * @param line CSV-formatted line to append, not null
     * @throws PersistenceException if the file cannot be written
     */
    public void append(String path, String line) throws PersistenceException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(line);

        try {
            Files.writeString(
                    Paths.get(path),
                    line + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceError.WRITE_FAILED);
        }
    }

    /**
     * Replaces the first line matching the processor with a new line.
     * If no line matches, the file is left unchanged.
     *
     * @param path    file path, not null
     * @param matcher processor identifying the line to replace, not null
     * @param newLine replacement CSV-formatted line, not null
     * @return true if a line was replaced, false if no match was found
     * @throws PersistenceException if the file cannot be read or written
     */
    public boolean update(String path, CsvProcessor matcher, String newLine)
            throws PersistenceException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(matcher);
        Objects.requireNonNull(newLine);

        try {
            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            boolean[] replaced = { false }; // array trick per usarlo in lambda

            List<String> updated = lines.stream()
                    .map(line -> {
                        if (!replaced[0] && matcher.process(line)) {
                            replaced[0] = true;
                            return newLine;
                        }
                        return line;
                    })
                    .collect(Collectors.toList());

            if (!replaced[0])
                return false;

            Files.write(
                    Paths.get(path),
                    updated,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            return true;

        } catch (IOException e) {
            throw new PersistenceException(PersistenceError.WRITE_FAILED);
        }
    }

    /**
     * Removes the first line matching the processor.
     * If no line matches, the file is left unchanged.
     *
     * @param path    file path, not null
     * @param matcher processor identifying the line to remove, not null
     * @return true if a line was removed, false if no match was found
     * @throws PersistenceException if the file cannot be read or written
     */
    public boolean delete(String path, CsvProcessor matcher)
            throws PersistenceException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(matcher);

        try {
            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            boolean[] removed = { false };

            List<String> filtered = lines.stream()
                    .filter(line -> {
                        if (!removed[0] && matcher.process(line)) {
                            removed[0] = true;
                            return false; // rimuovi
                        }
                        return true; // mantieni
                    })
                    .collect(Collectors.toList());

            if (!removed[0])
                return false;

            Files.write(
                    Paths.get(path),
                    filtered,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            return true;

        } catch (IOException e) {
            throw new PersistenceException(PersistenceError.WRITE_FAILED);
        }
    }
}