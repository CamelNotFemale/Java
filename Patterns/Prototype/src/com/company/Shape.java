package com.company;

import java.awt.*;

public abstract class Shape {
    /** Координаты центра фигуры **/
    protected int x, y;
    /** Цвет фигуры **/
    protected Color color;

    public Shape(int _x, int _y, Color _color) {
        x = _x;
        y = _y;
        color = _color;
    }

    public abstract Shape clone();
    public boolean isEquals(Shape other) {
        return x==other.x && y==other.y && color==other.color;
    }
}
