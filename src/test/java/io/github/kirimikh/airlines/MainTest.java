package io.github.kirimikh.airlines;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MainTest {
    @TempDir
    Path directory;

    @Test
    void runsDefaultAndCustomScenarios() throws IOException {
        Main.main(new String[0]);
        Path data = directory.resolve("planes.txt");
        Files.writeString(data, "PASSENGER;A;1;2000;3;4\n");
        Main.main(new String[] {data.toString()});
        Main.main(new String[] {data.toString(), "2000", "2000"});
        Files.writeString(data, "bad\n");
        Main.main(new String[] {data.toString(), "0", "1"});
    }

    @Test
    void rejectsInvalidArgumentsAndMissingFile() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[] {"a", "b"}));
        assertThrows(IllegalArgumentException.class,
                () -> Main.main(new String[] {"a", "b", "c", "d"}));
        assertThrows(NumberFormatException.class,
                () -> Main.main(new String[] {"a", "bad", "3"}));
        assertThrows(IOException.class,
                () -> Main.main(new String[] {directory.resolve("missing.txt").toString()}));
    }
}
