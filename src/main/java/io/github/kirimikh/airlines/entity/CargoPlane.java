package io.github.kirimikh.airlines.entity;

public class CargoPlane extends Plane {
    private int cargoVolume;

    public CargoPlane(String model, int flightRange, int fuelConsumption,
            int carryingCapacity, int cargoVolume) {
        super(model, flightRange, fuelConsumption, carryingCapacity);
        this.cargoVolume = cargoVolume;
    }

    public int getCargoVolume() {
        return cargoVolume;
    }

    @Override
    public String toString() {
        return "CargoPlane [cargoVolume=" + cargoVolume + ", "
                + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + cargoVolume;
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
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CargoPlane other = (CargoPlane) obj;
        if (cargoVolume != other.cargoVolume) {
            return false;
        }
        return true;
    }
}
