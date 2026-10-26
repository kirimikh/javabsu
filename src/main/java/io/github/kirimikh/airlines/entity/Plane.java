package io.github.kirimikh.airlines.entity;

public abstract class Plane {
    private String model;
    private int flightRange;
    private int fuelConsumption;
    private int carryingCapacity;

    public Plane(String model, int flightRange, int fuelConsumption,
            int carryingCapacity) {
        this.model = model;
        this.flightRange = flightRange;
        this.fuelConsumption = fuelConsumption;
        this.carryingCapacity = carryingCapacity;
    }

    public String getModel() {
        return model;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((model == null) ? 0 : model.hashCode());
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
        if (model == null) {
            if (other.model != null) {
                return false;
            }
        } else if (!model.equals(other.model)) {
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
