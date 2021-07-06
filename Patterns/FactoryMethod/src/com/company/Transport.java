package com.company;

public interface Transport {
    /** Добавление посылки к перевозке **/
    boolean addPackage(Package pack);
    /** Доставка всего содержимого **/
    void deliver();
}
