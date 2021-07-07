package com.company;
/**
 * Класс стандартной машины (базовой)
 * Конкретный компонент
 */
public class SimpleCar implements Car {
    private int speed = 60;
    private int baggageWeight = 100;

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public int getBaggageWeight() {
        return this.baggageWeight;
    }
}