package com.company;

public class Main {

    public static void main(String[] args) {
        // грузы, требующие доставки
        Package coal = new Package("Coal", 1030),
                logs = new Package("Logs", 800),
                metals = new Package("Metals", 9999);
        // сначала доставим все возможное на Грузовике:
        Transport transport = TransportFactory.getTransport(TransportFactory.TransportType.TRUCK);
        transport.addPackage(coal);
        transport.addPackage(logs);
        transport.addPackage(metals);
        transport.deliver();

        // заметим, что металлы слишком тяжелы и они не были загружены в Грузовик. Придется доставлять их на Барже:
        transport = TransportFactory.getTransport(TransportFactory.TransportType.BARGE);
        transport.addPackage(metals);
        transport.deliver();
    }
}
