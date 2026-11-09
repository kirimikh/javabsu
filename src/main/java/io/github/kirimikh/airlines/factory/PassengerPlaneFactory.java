package io.github.kirimikh.airlines.factory;

import io.github.kirimikh.airlines.entity.PassengerPlane;

public final class PassengerPlaneFactory extends PlaneFactory {
    @Override
    public PassengerPlane createPlane(String model, int flightRange,
            int fuelConsumption, int carryingCapacity, int passengerSeats) {
        return new PassengerPlane(model, flightRange, fuelConsumption,
                carryingCapacity, passengerSeats);
    }
}
