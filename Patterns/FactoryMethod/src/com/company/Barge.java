package com.company;

import java.util.ArrayList;
import java.util.List;

public class Barge implements Transport {
    /** перевозимый груз (посылки) **/
    private List<Package> pack;
    /** грузоподъемность в тоннах **/
    private int capacity;
    /** текущая загруженность **/
    private int load;

    public Barge() {
        pack = new ArrayList<>();
        capacity = 10000;
        load = 0;
    }

    @Override
    public boolean addPackage(Package pkg) {
        if (load + pkg.getWeight() > capacity) {
            return false;
        }
        else {
            pack.add(pkg);
            load += pkg.getWeight();
            return true;
        }
    }

    @Override
    public void deliver() {
        System.out.println("Barge successfully delivered:");
        for (Package p: pack) {
            System.out.println("  "+p.getDescription());
        }
    }
}
