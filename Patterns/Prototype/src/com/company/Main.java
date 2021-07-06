package com.company;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Shape rec_orig = new Rectangle(5, 10, Color.GREEN, 5, 5);
        Shape rec_copy = rec_orig.clone();
        System.out.println("Rectangle and his copy are " + (rec_orig.isEquals(rec_copy) ? "equals" : "not equals"));

        Shape circle_1 = new Circle(1,1, Color.BLACK, 6),
                circle_2 = new Circle(1,1, Color.RED, 7);
        System.out.println("Circle_1 and Circle_2 are " + (circle_1.isEquals(circle_2) ? "equals" : "not equals"));
    }
}
