package com.company;
/** Класс-декоратор грузовой машины */
public class TruckDecorator extends CarDecorator {
    public TruckDecorator(Car car) {
        super(car);
    }

    @Override
    public int getSpeed() {
        return this.car.getSpeed();
    }

    @Override
    public int getBaggageWeight() {
        return this.car.getBaggageWeight() + 1000;
    }
}
