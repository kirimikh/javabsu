package io.github.kirimikh.airlines.service;

import java.util.List;
import io.github.kirimikh.airlines.comparator.FlightRangeComparator;
import io.github.kirimikh.airlines.entity.Airline;
import io.github.kirimikh.airlines.entity.PassengerPlane;
import io.github.kirimikh.airlines.entity.Plane;

public class AirlineService {
    public long totalPassengerCapacity(Airline airline) {
        return airline.getFleet().stream()
                .filter(PassengerPlane.class::isInstance)
                .map(PassengerPlane.class::cast)
                .mapToLong(PassengerPlane::getPassengerSeats).sum();
    }

    public long totalCarryingCapacity(Airline airline) {
        return airline.getFleet().stream().mapToLong(Plane::getCarryingCapacity).sum();
    }

    public List<Plane> sortByFlightRange(Airline airline) {
        return airline.getFleet().stream().sorted(new FlightRangeComparator()).toList();
    }

    /** Both bounds are inclusive; consumption is measured in kg/h. */
    public List<Plane> findByFuelConsumption(Airline airline, int minimum, int maximum) {
        if (minimum < 0 || maximum < minimum) {
            throw new IllegalArgumentException("Expected 0 <= minimum <= maximum");
        }
        return airline.getFleet().stream()
                .filter(plane -> plane.getFuelConsumption() >= minimum
                        && plane.getFuelConsumption() <= maximum)
                .toList();
    }
}
