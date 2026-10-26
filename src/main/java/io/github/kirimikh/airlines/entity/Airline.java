package io.github.kirimikh.airlines.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Airline {
    private String name;
    private List<Plane> fleet = new ArrayList<>();

    public Airline(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addPlane(Plane plane) {
        fleet.add(plane);
    }

    public List<Plane> getFleet() {
        return Collections.unmodifiableList(fleet);
    }

    @Override
    public String toString() {
        return "Airline [name = %s, fleet = %d]".formatted(name, fleet.size());
    }
}
