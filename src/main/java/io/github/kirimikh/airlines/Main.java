/*Авиакомпания. Определить иерархию самолетов. Создать авиакомпанию.
Посчитать общую вместимость и грузоподъемность. Провести сортировку
самолетов компании по дальности полета. Найти самолет в компании,
соответствующий заданному диапазону параметров потребления горючего. */
package io.github.kirimikh.airlines;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.kirimikh.airlines.creator.AirlineCreator;
import io.github.kirimikh.airlines.entity.Airline;
import io.github.kirimikh.airlines.entity.Plane;
import io.github.kirimikh.airlines.service.AirlineService;

public final class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 0 && args.length != 1 && args.length != 3) {
            throw new IllegalArgumentException("Usage: Main [data.txt [minimum maximum]]");
        }
        Path path = Path.of(args.length == 0 ? "data/planes.txt" : args[0]);
        int minimum = args.length == 3 ? Integer.parseInt(args[1]) : 2000;
        int maximum = args.length == 3 ? Integer.parseInt(args[2]) : 3000;
        Airline airline = new AirlineCreator().create("Training Airlines", path);
        AirlineService service = new AirlineService();
        List<Plane> matches = service.findByFuelConsumption(airline, minimum, maximum);
        LOGGER.info("{}", airline);
        LOGGER.info("Общая вместимость: {} пассажиров", service.totalPassengerCapacity(airline));
        LOGGER.info("Общая грузоподъёмность: {} кг", service.totalCarryingCapacity(airline));
        LOGGER.info("Самолёты по возрастанию дальности полёта (км):");
        service.sortByFlightRange(airline).forEach(plane -> LOGGER.info("{}", plane));
        LOGGER.info("Расход топлива от {} до {} кг/ч: {}", minimum, maximum, matches);
    }
}
