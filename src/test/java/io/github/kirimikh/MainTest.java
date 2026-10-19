package io.github.kirimikh;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class MainTest {

    private final Map<Tetragon.Type, List<Tetragon>> groups = Main.groupByType(Main.createFigures());

    @Test
    void countTest() {
        assertEquals(3, groups.get(Tetragon.Type.SQUARE).size());
        assertEquals(3, groups.get(Tetragon.Type.RECTANGLE).size());
        assertEquals(3, groups.get(Tetragon.Type.RHOMBUS).size());
        assertEquals(3, groups.get(Tetragon.Type.ARBITRARY).size());
    }

    @Test
    void biggestAreaTest() {
        assertEquals(5.0, Main.biggestArea(groups.get(Tetragon.Type.SQUARE)).area(), 0.01);
        assertEquals(12.0, Main.biggestArea(groups.get(Tetragon.Type.RECTANGLE)).area(), 0.01);
        assertEquals(24.0, Main.biggestArea(groups.get(Tetragon.Type.RHOMBUS)).area(), 0.01);
        assertEquals(12.0, Main.biggestArea(groups.get(Tetragon.Type.ARBITRARY)).area(), 0.01);
    }

    @Test
    void smallestAreaTest() {
        assertEquals(2.0, Main.smallestArea(groups.get(Tetragon.Type.SQUARE)).area(), 0.01);
        assertEquals(5.0, Main.smallestArea(groups.get(Tetragon.Type.RECTANGLE)).area(), 0.01);
        assertEquals(3.0, Main.smallestArea(groups.get(Tetragon.Type.RHOMBUS)).area(), 0.01);
        assertEquals(2.5, Main.smallestArea(groups.get(Tetragon.Type.ARBITRARY)).area(), 0.01);
    }

    @Test
    void biggestPerimeterTest() {
        assertEquals(8.94, Main.biggestPerimeter(groups.get(Tetragon.Type.SQUARE)).perimeter(), 0.01);
        assertEquals(14.0, Main.biggestPerimeter(groups.get(Tetragon.Type.RECTANGLE)).perimeter(), 0.01);
        assertEquals(20.0, Main.biggestPerimeter(groups.get(Tetragon.Type.RHOMBUS)).perimeter(), 0.01);
        assertEquals(14.32, Main.biggestPerimeter(groups.get(Tetragon.Type.ARBITRARY)).perimeter(), 0.01);
    }

    @Test
    void smallestPerimeterTest() {
        assertEquals(5.66, Main.smallestPerimeter(groups.get(Tetragon.Type.SQUARE)).perimeter(), 0.01);
        assertEquals(12.0, Main.smallestPerimeter(groups.get(Tetragon.Type.RECTANGLE)).perimeter(), 0.01);
        assertEquals(8.94, Main.smallestPerimeter(groups.get(Tetragon.Type.RHOMBUS)).perimeter(), 0.01);
        assertEquals(7.41, Main.smallestPerimeter(groups.get(Tetragon.Type.ARBITRARY)).perimeter(), 0.01);
    }
}
