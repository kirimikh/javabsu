package io.github.kirimikh.airlines.parser;

import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.kirimikh.airlines.entity.Plane;
import io.github.kirimikh.airlines.factory.CargoPlaneFactory;
import io.github.kirimikh.airlines.factory.PassengerPlaneFactory;
import io.github.kirimikh.airlines.factory.PlaneFactory;

public class PlaneParser {
    private static final Logger LOGGER = LogManager.getLogger(PlaneParser.class);
    private final Map<String, PlaneFactory> factories = Map.of(
            "PASSENGER", new PassengerPlaneFactory(), "CARGO", new CargoPlaneFactory());

    public Optional<Plane> parse(String line) {
        if (line.isBlank()) {
            return Optional.empty();
        }
        try {
            String[] fields = line.split(";", -1);
            if (fields.length != 6) {
                throw new IllegalArgumentException("Expected exactly 6 fields");
            }
            PlaneFactory factory = factories.get(fields[0].strip());
            if (factory == null) {
                throw new IllegalArgumentException("Unknown plane type: " + fields[0]);
            }
            Plane plane = factory.createPlane(fields[1].strip(),
                    Integer.parseInt(fields[2].strip()), Integer.parseInt(fields[3].strip()),
                    Integer.parseInt(fields[4].strip()), Integer.parseInt(fields[5].strip()));
            LOGGER.debug("Created {}", plane);
            return Optional.of(plane);
        } catch (IllegalArgumentException exception) {
            LOGGER.warn("Skipping invalid plane data [{}]: {}", line, exception.getMessage());
            return Optional.empty();
        }
    }
}
