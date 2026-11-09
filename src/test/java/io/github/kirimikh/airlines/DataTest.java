package io.github.kirimikh.airlines;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import io.github.kirimikh.airlines.creator.AirlineCreator;
import io.github.kirimikh.airlines.entity.*;
import io.github.kirimikh.airlines.factory.*;
import io.github.kirimikh.airlines.parser.PlaneParser;
import io.github.kirimikh.airlines.reader.PlaneDataReader;
import io.github.kirimikh.airlines.service.AirlineService;

class DataTest {
    @TempDir
    Path directory;

    @Test
    void factoriesCreateConcreteProducts() {
        PlaneFactory passengers = new PassengerPlaneFactory();
        PlaneFactory cargo = new CargoPlaneFactory();
        assertEquals(new PassengerPlane("A", 1, 2, 3, 4),
                passengers.createPlane("A", 1, 2, 3, 4));
        assertEquals(new CargoPlane("B", 5, 6, 7, 8),
                cargo.createPlane("B", 5, 6, 7, 8));
    }

    @Test
    void parsesBothTypesAndWhitespace() {
        PlaneParser parser = new PlaneParser();
        assertEquals(new PassengerPlane("Ту-204", 1, 2, 3, 4),
                parser.parse(" PASSENGER ; Ту-204 ; 1 ; 2 ; 3 ; 4 ").orElseThrow());
        assertEquals(new CargoPlane("Cargo", 1, 2, 3, 4),
                parser.parse("CARGO;Cargo;1;2;3;4").orElseThrow());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "PASSENGER;A;1;2;3", "CARGO;A;1;2;3;4;extra",
            "HELICOPTER;A;1;2;3;4", "PASSENGER;;1;2;3;4", "CARGO; ;1;2;3;4",
            "CARGO;A;abc;2;3;4", "CARGO;A;-1;2;3;4", "PASSENGER;A;1;0;3;4",
            "CARGO;A;1;2;-3;4", "PASSENGER;A;1;2;3;0", "CARGO;A;1;2;3;-1",
            "CARGO;A;2147483648;2;3;4", "CARGO;A;1;2;3;"})
    void skipsInvalidLines(String line) {
        assertTrue(new PlaneParser().parse(line).isEmpty());
    }

    @Test
    void readsUtf8AndCreatesAirlineIgnoringInvalidRows() throws IOException {
        Path input = directory.resolve("planes.txt");
        List<String> lines = List.of("PASSENGER;Ту-204;100;200;300;4", "bad", "",
                "CARGO;Ил-76;500;600;700;8");
        Files.write(input, lines);
        assertEquals(lines, new PlaneDataReader().readLines(input));
        Airline airline = new AirlineCreator().create("Test", input);
        assertEquals("Test", airline.getName());
        assertEquals(List.of(new PassengerPlane("Ту-204", 100, 200, 300, 4),
                new CargoPlane("Ил-76", 500, 600, 700, 8)), airline.getFleet());
        Files.writeString(input, "");
        assertTrue(new AirlineCreator().create("Empty", input).getFleet().isEmpty());
    }

    @Test
    void propagatesReadFailure() {
        Path missing = directory.resolve("missing.txt");
        assertThrows(IOException.class, () -> new PlaneDataReader().readLines(missing));
        assertThrows(IOException.class, () -> new AirlineCreator().create("Test", missing));
    }

    @Test
    void suppliedDatasetHasExpectedTotals() throws IOException {
        Airline airline = new AirlineCreator().create("Test", Path.of("data/planes.txt"));
        AirlineService service = new AirlineService();
        assertEquals(7, airline.getFleet().size());
        assertEquals(785, service.totalPassengerCapacity(airline));
        assertEquals(408600, service.totalCarryingCapacity(airline));
        assertEquals(List.of("Airbus A320", "Boeing 737-800"),
                service.findByFuelConsumption(airline, 2000, 3000).stream()
                        .map(Plane::getModel).toList());
    }
}
