package com.company;

import java.awt.*;

public class Rectangle extends Shape {
    /** Ширина **/
    private int width;
    /** Высота **/
    private int height;

    public Rectangle(int x, int y, Color color, int w, int h) {
        super(x, y, color);
        width = w;
        height = h;
    }

    @Override
    public Shape clone() {
        return new Rectangle(this.x, this.y, this.color, this.width, this.height);
    }

    public boolean isEquals(Rectangle other) {
        return x==other.x && y==other.y && color==other.color && width==other.width && height==other.height;
    }
}
