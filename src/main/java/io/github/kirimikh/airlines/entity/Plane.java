package io.github.kirimikh.airlines.entity;

import io.github.kirimikh.airlines.validation.PlaneValidator;

public abstract class Plane {
    private final String model;
    private final int flightRange;
    private final int fuelConsumption;
    private final int carryingCapacity;

    public Plane(String model, int flightRange, int fuelConsumption,
            int carryingCapacity) {
        this.model = PlaneValidator.requireModel(model);
        this.flightRange = PlaneValidator.requirePositive(flightRange);
        this.fuelConsumption = PlaneValidator.requirePositive(fuelConsumption);
        this.carryingCapacity = PlaneValidator.requirePositive(carryingCapacity);
    }

    public String getModel() {
        return model;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + model.hashCode();
        result = prime * result + flightRange;
        result = prime * result + fuelConsumption;
        result = prime * result + carryingCapacity;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Plane other = (Plane) obj;
        if (!model.equals(other.model)) {
            return false;
        }
        if (flightRange != other.flightRange) {
            return false;
        }
        if (fuelConsumption != other.fuelConsumption) {
            return false;
        }
        if (carryingCapacity != other.carryingCapacity) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Plane [model=" + model + ", flightRange=" + flightRange
                + ", fuelConsumption=" + fuelConsumption + ", carryingCapacity="
                + carryingCapacity + "]";
    }

    public int getFlightRange() {
        return flightRange;
    }

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public int getCarryingCapacity() {
        return carryingCapacity;
    }
}
