package ru.jft.geometry.figures;

public class Figures {
    public static void main(String[] args) {
        Triangle triangle1 = new Triangle(1.0,4.0,4.0);
        triangle1.showInfo();
        triangle1.showPerimetr();
        triangle1.showArea();

        Triangle triangle2 = new Triangle(5.0,7.0,8.0);
        triangle2.showInfo();
        triangle2.showPerimetr();
        triangle2.showArea();

        Triangle triangle3 = new Triangle(11.0,15.0,18.0);
        triangle3.showInfo();
        triangle3.showPerimetr();
        triangle3.showArea();
    }
}
