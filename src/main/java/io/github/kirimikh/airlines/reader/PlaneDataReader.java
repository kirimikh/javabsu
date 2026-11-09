package io.github.kirimikh.airlines.reader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlaneDataReader {
    private static final Logger LOGGER = LogManager.getLogger(PlaneDataReader.class);

    public List<String> readLines(Path path) throws IOException {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            LOGGER.debug("Read {} lines from {}", lines.size(), path);
            return lines;
        } catch (IOException exception) {
            LOGGER.error("Cannot read plane data from {}", path, exception);
            throw exception;
        }
    }
}
