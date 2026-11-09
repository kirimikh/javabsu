package io.github.kirimikh.airlines.factory;

import io.github.kirimikh.airlines.entity.CargoPlane;

public final class CargoPlaneFactory extends PlaneFactory {
    @Override
    public CargoPlane createPlane(String model, int flightRange,
            int fuelConsumption, int carryingCapacity, int cargoVolume) {
        return new CargoPlane(model, flightRange, fuelConsumption,
                carryingCapacity, cargoVolume);
    }
}
