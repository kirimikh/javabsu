package io.github.kirimikh.airlines.comparator;

import java.util.Comparator;
import io.github.kirimikh.airlines.entity.Plane;

public class FlightRangeComparator implements Comparator<Plane> {
    @Override
    public int compare(Plane first, Plane second) {
        return Integer.compare(first.getFlightRange(), second.getFlightRange());
    }
}
