package io.github.kirimikh.airlines.entity;

public class PassengerPlane extends Plane {
    private int passengerSeats;

    public PassengerPlane(String model, int flightRange, int fuelConsumption,
            int carryingCapacity, int passengerSeats) {
        super(model, flightRange, fuelConsumption, carryingCapacity);
        this.passengerSeats = passengerSeats;
    }

    public int getPassengerSeats() {
        return passengerSeats;
    }

    @Override
    public String toString() {
        return "PassengerPlane [passengerSeats=" + passengerSeats + ", "
                + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + passengerSeats;
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
        PassengerPlane other = (PassengerPlane) obj;
        if (passengerSeats != other.passengerSeats) {
            return false;
        }
        return true;
    }
}
