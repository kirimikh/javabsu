package io.github.kirimikh.airlines.creator;

import java.io.IOException;
import java.nio.file.Path;
import io.github.kirimikh.airlines.entity.Airline;
import io.github.kirimikh.airlines.parser.PlaneParser;
import io.github.kirimikh.airlines.reader.PlaneDataReader;

/** GRASP Creator: assembles the airline that contains the created planes. */
public class AirlineCreator {
    public Airline create(String name, Path path) throws IOException {
        Airline airline = new Airline(name);
        PlaneParser parser = new PlaneParser();
        for (String line : new PlaneDataReader().readLines(path)) {
            parser.parse(line).ifPresent(airline::addPlane);
        }
        return airline;
    }
}
