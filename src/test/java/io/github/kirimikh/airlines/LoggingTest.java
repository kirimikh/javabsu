package io.github.kirimikh.airlines;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.SimpleMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LoggingTest {
    @TempDir
    Path directory;

    @Test
    void routesLevelsAndCompressesArchives() throws IOException {
        String previous = System.getProperty("airlines.logDir");
        LoggerContext context = new LoggerContext("logging-test");
        try {
            System.setProperty("airlines.logDir", directory.toString());
            context.setConfigLocation(getClass().getResource("/log4j2.xml").toURI());
            var logger = context.getLogger("test");
            logger.debug("debug-marker");
            logger.info("info-marker");
            logger.warn("warn-marker");
            logger.error("error-marker");
            assertTrue(Files.readString(directory.resolve("debug.log")).contains("debug-marker"));
            assertFalse(Files.readString(directory.resolve("debug.log")).contains("info-marker"));
            assertTrue(Files.readString(directory.resolve("info.log")).contains("info-marker"));
            assertFalse(Files.readString(directory.resolve("info.log")).contains("warn-marker"));
            String errors = Files.readString(directory.resolve("error.log"));
            assertTrue(errors.contains("warn-marker"));
            assertTrue(errors.contains("error-marker"));
            assertFalse(errors.contains("info-marker"));

            for (String name : List.of("debug", "info", "error")) {
                RollingFileAppender appender = context.getConfiguration().getAppender(name);
                appender.getManager().rollover();
            }
            logger.info("after-rollover");
            ConsoleAppender console = context.getConfiguration().getAppender("console");
            String info = console.getLayout().toSerializable(Log4jLogEvent.newBuilder()
                    .setLoggerName("test").setLevel(Level.INFO).setMessage(new SimpleMessage("color")).build()).toString();
            String warn = console.getLayout().toSerializable(Log4jLogEvent.newBuilder()
                    .setLoggerName("test").setLevel(Level.WARN).setMessage(new SimpleMessage("color")).build()).toString();
            assertTrue(info.contains("\u001b["));
            assertTrue(warn.contains("\u001b["));
            assertNotEquals(info, warn);
        } catch (java.net.URISyntaxException exception) {
            throw new AssertionError(exception);
        } finally {
            context.stop();
            if (previous == null) {
                System.clearProperty("airlines.logDir");
            } else {
                System.setProperty("airlines.logDir", previous);
            }
        }
        try (var files = Files.list(directory.resolve("archive"))) {
            List<Path> archives = files.toList();
            assertEquals(3, archives.size());
            for (Path archive : archives) {
                assertTrue(archive.toString().endsWith(".log.gz"));
                try (var input = new GZIPInputStream(Files.newInputStream(archive))) {
                    assertTrue(new String(input.readAllBytes(), StandardCharsets.UTF_8).contains("-marker"));
                }
            }
        }
        assertTrue(Files.readString(directory.resolve("info.log")).contains("after-rollover"));
    }
}
