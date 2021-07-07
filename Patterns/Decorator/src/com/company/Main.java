package com.company;

public class Main {

    public static void main(String[] args) {
        Car simpleCar = new SimpleCar();
        Car sportCar = new SportCarDecorator(simpleCar);
        Car sportTruck = new TruckDecorator(sportCar);
        System.out.println("simpleCar: " + simpleCar.getSpeed() + " speed and " + simpleCar.getBaggageWeight() + " weight (default)");
        System.out.println("sportCar: " + sportCar.getSpeed() + " speed and " + sportCar.getBaggageWeight() + " weight (+50 speed, -30 weight)");
        System.out.println("sportTruck: " + sportTruck.getSpeed() + " speed and " + sportTruck.getBaggageWeight() + " weight (+1000 weight)");
    }
}
