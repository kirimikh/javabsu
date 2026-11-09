package io.github.kirimikh.airlines.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Airline {
    private final String name;
    private final List<Plane> fleet = new ArrayList<>();

    public Airline(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return name;
    }

    public void addPlane(Plane plane) {
        fleet.add(Objects.requireNonNull(plane));
    }

    public List<Plane> getFleet() {
        return Collections.unmodifiableList(fleet);
    }

    @Override
    public String toString() {
        return "Airline [name = %s, fleet = %d]".formatted(name, fleet.size());
    }
}
