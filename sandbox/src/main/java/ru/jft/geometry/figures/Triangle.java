package ru.jft.geometry.figures;

import java.util.Arrays;

public class Triangle {
    private double side1;
    private double side2;
    private double side3;

    public Triangle(double side1, double side2, double side3) {
        if (side1 < 0 || side2 < 0 || side3 < 0) {
            throw new IllegalArgumentException("Triangle side should not be negative");
        }

        boolean condition1 = (side1 + side2)<side3 || (side1 + side3)<side2 || (side2 + side3)<side1;
        boolean condition2 = (side1 + side2)==side3 || (side1 + side3)==side2 || (side2 + side3)==side1;
        if (condition1 || condition2) {
            throw new IllegalArgumentException(
                    "The sum of any two sides of triangle should be less than the third side");
        }

        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    public String getSides() {
        double[] sides = new double[]{side1, side2, side3};
        return Arrays.toString(sides);
    }

    public double getPerimetr() {
        return (side1 + side2 + side3);
    }

    public double getArea() {
        double p = getPerimetr() / 2;
        double area = Math.sqrt(p * (p - side1) * (p - side2) * (p - side3));
        return area;
    }

    public void showInfo() {
        System.out.println(String.format("Это треугольник со сторонами: %s", getSides()));
    }

    public void showPerimetr() {
        System.out.println(String.format(
                "Периметр треугольника со сторонами: %s равен %.2f.", getSides(), getPerimetr()));
    }

    public void showArea() {
        System.out.println(String.format(
                "Площадь треугольника со сторонами: %s равна %f.", getSides(), getArea()));
    }

}
