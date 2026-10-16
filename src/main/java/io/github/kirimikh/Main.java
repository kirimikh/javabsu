/* Определить класс Четырехугольник на плоскости, вершины которого имеют тип Точка.
Определить площадь и периметр четырехугольника.
Создать массив/список/множество объектов и подсчитать количество четырехугольников
разного типа (квадрат, прямоугольник, ромб, произвольный).
Определить для каждой группы наибольший и наименьший по площади (периметру) объект. */
package io.github.kirimikh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Tetragon> figures = List.of(
                new Tetragon(0, 0, 2, 0, 2, 2, 0, 2),
                new Tetragon(0, 1, 1, 0, 0, -1, -1, 0),
                new Tetragon(0, 0, 2, 1, 1, 3, -1, 2),
                new Tetragon(0, 0, 4, 0, 4, 3, 0, 3),
                new Tetragon(0, 0, 5, 0, 5, 1, 0, 1),
                new Tetragon(0, 0, 4, 2, 3, 4, -1, 2),
                new Tetragon(0, 4, 3, 0, 0, -4, -3, 0),
                new Tetragon(0, 1, 2, 0, 0, -1, -2, 0),
                new Tetragon(0, 0, 2, 1, 3, 3, 1, 2),
                new Tetragon(0, 0, 5, 0, 4, 3, 1, 3),
                new Tetragon(0, 0, 4, 0, 5, 2, 1, 2),
                new Tetragon(0, 0, 3, 0, 2, 1, 0, 1));

        Map<Tetragon.Type, List<Tetragon>> model = new EnumMap<>(Tetragon.Type.class);

        for (Tetragon.Type type : Tetragon.Type.values()) {
            model.put(type, new ArrayList<>());
        }

        for (Tetragon tetragon : figures) {
            model.get(tetragon.type()).add(tetragon);
        }

        for (Tetragon.Type type : Tetragon.Type.values()) {
            List<Tetragon> group = model.get(type);
            System.out.println(type + ": " + group.size());
            if (!group.isEmpty()) {
                var biggestArea = Collections.max(group, Comparator.comparingDouble(Tetragon::area));
                var smallestArea = Collections.min(group, Comparator.comparingDouble(Tetragon::area));
                var biggestPerimeter = Collections.max(group, Comparator.comparingDouble(Tetragon::perimeter));
                var smallestPerimeter = Collections.min(group, Comparator.comparingDouble(Tetragon::perimeter));

                System.out.printf("%s has the biggest area: %.2f%n", biggestArea, biggestArea.area());
                System.out.printf("%s has the smallest area: %.2f%n", smallestArea, smallestArea.area());
                System.out.printf("%s has the biggest perimeter: %.2f%n", biggestPerimeter,
                        biggestPerimeter.perimeter());
                System.out.printf("%s has the smallest perimeter: %.2f%n", smallestPerimeter,
                        smallestPerimeter.perimeter());
            }
        }

    }
}