package com.company;
/** Класс-декоратор спортивной машины */
public class SportCarDecorator extends CarDecorator {
    public SportCarDecorator(Car car) {
        super(car);
    }

    @Override
    public int getSpeed() {
        return this.car.getSpeed() + 50;
    }
    @Override
    public int getBaggageWeight() {
        int newBaggageWeight = this.car.getBaggageWeight() - 30;
        return newBaggageWeight > 0 ? newBaggageWeight : 0;
    }
}
