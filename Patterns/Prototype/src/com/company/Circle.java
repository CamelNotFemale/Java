package com.company;

import java.awt.*;

public class Circle extends Shape {
    /** Падиус **/
    private int radius;

    public Circle(int x, int y, Color color, int r) {
        super(x, y, color);
        radius = r;
    }

    @Override
    public Shape clone() {
        return new Circle(this.x, this.y, this.color, this.radius);
    }

    public boolean isEquals(Circle other) {
        return x==other.x && y==other.y && color==other.color && radius==other.radius;
    }
}
