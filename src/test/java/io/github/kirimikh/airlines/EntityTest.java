package io.github.kirimikh.airlines;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.github.kirimikh.airlines.entity.*;

class EntityTest {
    private Plane passenger(String model, int range, int fuel, int payload, int seats) {
        return new PassengerPlane(model, range, fuel, payload, seats);
    }

    private static class BasicPlane extends Plane {
        BasicPlane() {
            super("Base", 1, 2, 3);
        }
    }

    @Test
    void baseEqualityContract() {
        Plane plane = new BasicPlane();
        assertEquals(plane, plane);
        assertNotEquals(plane, null);
        assertNotEquals(plane, "Base");
        assertEquals(plane, new BasicPlane());
    }

    @Test
    void propertiesAndDescriptions() {
        PassengerPlane plane = new PassengerPlane(" A320 ", 6100, 2500, 20000, 180);
        assertAll(() -> assertEquals("A320", plane.getModel()),
                () -> assertEquals(6100, plane.getFlightRange()),
                () -> assertEquals(2500, plane.getFuelConsumption()),
                () -> assertEquals(20000, plane.getCarryingCapacity()),
                () -> assertEquals(180, plane.getPassengerSeats()));
        assertTrue(plane.toString().contains("passengerSeats=180"));
        assertTrue(plane.toString().contains("model=A320"));
        CargoPlane cargo = new CargoPlane("Cargo", 100, 200, 300, 400);
        assertEquals(400, cargo.getCargoVolume());
        assertTrue(cargo.toString().contains("cargoVolume=400"));
        Airline airline = new Airline("Test");
        assertEquals("Test", airline.getName());
        airline.addPlane(plane);
        assertEquals(List.of(plane), airline.getFleet());
        assertEquals("Airline [name = Test, fleet = 1]", airline.toString());
        assertThrows(UnsupportedOperationException.class, () -> airline.getFleet().clear());
        assertThrows(NullPointerException.class, () -> airline.addPlane(null));
        assertThrows(NullPointerException.class, () -> new Airline(null));
    }

    @Test
    void equalityAndHashCode() {
        Plane first = passenger("A", 1, 2, 3, 4);
        Plane second = passenger("A", 1, 2, 3, 4);
        assertEquals(first, first);
        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, null);
        assertNotEquals(first, "A");
        assertNotEquals(first, passenger("B", 1, 2, 3, 4));
        assertNotEquals(first, passenger("A", 2, 2, 3, 4));
        assertNotEquals(first, passenger("A", 1, 3, 3, 4));
        assertNotEquals(first, passenger("A", 1, 2, 4, 4));
        assertNotEquals(first, passenger("A", 1, 2, 3, 5));
        Plane cargo = new CargoPlane("A", 1, 2, 3, 4);
        Plane equalCargo = new CargoPlane("A", 1, 2, 3, 4);
        assertEquals(cargo, cargo);
        assertEquals(cargo, equalCargo);
        assertEquals(cargo.hashCode(), equalCargo.hashCode());
        assertNotEquals(cargo, null);
        assertNotEquals(cargo, first);
        assertNotEquals(first, cargo);
        assertNotEquals(cargo, new CargoPlane("A", 1, 2, 3, 5));
    }

    static Stream<Arguments> invalidPlanes() {
        return Stream.of(
                Arguments.of(null, 1, 2, 3, 4), Arguments.of(" ", 1, 2, 3, 4),
                Arguments.of("A", 0, 2, 3, 4), Arguments.of("A", 1, -2, 3, 4),
                Arguments.of("A", 1, 2, 0, 4), Arguments.of("A", 1, 2, 3, 0));
    }

    @ParameterizedTest
    @MethodSource("invalidPlanes")
    void rejectsInvalidObjects(String model, int range, int fuel, int payload, int capacity) {
        assertThrows(IllegalArgumentException.class,
                () -> new PassengerPlane(model, range, fuel, payload, capacity));
        assertThrows(IllegalArgumentException.class,
                () -> new CargoPlane(model, range, fuel, payload, capacity));
    }
}
