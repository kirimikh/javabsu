package io.github.kirimikh.airlines.validation;

public final class PlaneValidator {
    private PlaneValidator() {
    }

    public static String requireModel(String model) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model must not be blank");
        }
        return model.strip();
    }

    public static int requirePositive(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Plane parameters must be positive: " + value);
        }
        return value;
    }
}
