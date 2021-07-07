package com.company;

/** Абстрактный класс декоратора машины */
public abstract class CarDecorator implements Car {
    protected Car car;
    public CarDecorator(Car car) {
        this.car = car;
    }
    public abstract int getSpeed();
    public abstract int getBaggageWeight();
}
