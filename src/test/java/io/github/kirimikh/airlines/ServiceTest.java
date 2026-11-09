package io.github.kirimikh.airlines;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import io.github.kirimikh.airlines.comparator.FlightRangeComparator;
import io.github.kirimikh.airlines.entity.*;
import io.github.kirimikh.airlines.service.AirlineService;

class ServiceTest {
    private final AirlineService service = new AirlineService();

    @Test
    void emptyFleet() {
        Airline airline = new Airline("Empty");
        assertEquals(0, service.totalPassengerCapacity(airline));
        assertEquals(0, service.totalCarryingCapacity(airline));
        assertTrue(service.sortByFlightRange(airline).isEmpty());
        assertTrue(service.findByFuelConsumption(airline, 0, 0).isEmpty());
    }

    @Test
    void totalsUseLongToAvoidIntegerOverflow() {
        Airline airline = new Airline("Large");
        airline.addPlane(new PassengerPlane("A", 1, 1, Integer.MAX_VALUE, Integer.MAX_VALUE));
        airline.addPlane(new PassengerPlane("B", 1, 1, Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(4294967294L, service.totalPassengerCapacity(airline));
        assertEquals(4294967294L, service.totalCarryingCapacity(airline));
        Airline cargo = new Airline("Cargo");
        cargo.addPlane(new CargoPlane("C", 1, 1, 100, 200));
        assertEquals(0, service.totalPassengerCapacity(cargo));
        assertEquals(100, service.totalCarryingCapacity(cargo));
    }

    @Test
    void sortsStablyWithoutChangingFleet() {
        Plane a = new CargoPlane("A", 300, 1, 1, 1);
        Plane b = new CargoPlane("B", 100, 1, 1, 1);
        Plane c = new PassengerPlane("C", 300, 1, 1, 1);
        Airline airline = new Airline("Test");
        List.of(a, b, c).forEach(airline::addPlane);
        assertEquals(List.of(b, a, c), service.sortByFlightRange(airline));
        assertEquals(List.of(a, b, c), airline.getFleet());
        FlightRangeComparator comparator = new FlightRangeComparator();
        assertTrue(comparator.compare(a, b) > 0);
        assertTrue(comparator.compare(b, a) < 0);
        assertEquals(0, comparator.compare(a, c));
    }

    @Test
    void findsAllMatchesIncludingBothBounds() {
        Airline airline = new Airline("Test");
        for (int fuel : List.of(99, 100, 150, 200, 201)) {
            airline.addPlane(new CargoPlane("C" + fuel, 1, fuel, 1, 1));
        }
        assertEquals(List.of(100, 150, 200), service.findByFuelConsumption(airline, 100, 200)
                .stream().map(Plane::getFuelConsumption).toList());
        assertEquals(1, service.findByFuelConsumption(airline, 150, 150).size());
        assertTrue(service.findByFuelConsumption(airline, 300, 400).isEmpty());
        assertThrows(IllegalArgumentException.class,
                () -> service.findByFuelConsumption(airline, -1, 200));
        assertThrows(IllegalArgumentException.class,
                () -> service.findByFuelConsumption(airline, 200, 100));
    }
}
