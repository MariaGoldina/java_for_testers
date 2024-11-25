package ru.jft.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {
    @Test
    void canCalculatePerimetr() {
        double result1 = new Triangle(1.0, 4.0, 4.0).getPerimetr();
        Assertions.assertEquals(9.0, result1);

        double result2 = new Triangle(5.0, 7.0, 8.0).getPerimetr();
        Assertions.assertEquals(20.0, result2);

        double result3 = new Triangle(11.0,15.0,18.0).getPerimetr();
        Assertions.assertEquals(44.0, result3);
    }

    @Test
    void canCalculateArea() {
        double result1 = new Triangle(1.0, 4.0, 4.0).getArea();
        Assertions.assertEquals(1.984313483298443, result1);

        double result2 = new Triangle(5.0, 7.0, 8.0).getArea();
        Assertions.assertEquals(17.320508075688775, result2);

        double result3 = new Triangle(11.0, 15.0, 18.0).getArea();
        Assertions.assertEquals(82.3164625090267, result3);
    }

    @Test
    void cannotCreateTriangleWithNegativeSide1 () {
        try {
            new Triangle(-1, 2, 3);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            //Test passed
        }
    }

    @Test
    void cannotCreateTriangleWithNegativeSide2 () {
        try {
            new Triangle(1, -2, 3);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            //Test passed
        }
    }

    @Test
    void cannotCreateTriangleWithNegativeSide3 () {
        try {
            new Triangle(1, 2, -3);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            //Test passed
        }
    }

    @Test
    void cannotCreateTriangleWithoutTriangleInequality () {
        try {
            new Triangle(1, 2, 3);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            //Test passed
        }
    }
}
