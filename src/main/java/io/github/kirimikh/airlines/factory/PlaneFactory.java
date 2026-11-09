package io.github.kirimikh.airlines.factory;

import io.github.kirimikh.airlines.entity.Plane;

/** Creator in the Factory Method pattern; subclasses choose the concrete product. */
public abstract class PlaneFactory {
    public abstract Plane createPlane(String model, int flightRange,
            int fuelConsumption, int carryingCapacity, int specificCapacity);
}
