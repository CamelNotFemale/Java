package com.company;

import java.util.Scanner;

public class ATMDispenseChain {

    private DispenseChain c1;

    public ATMDispenseChain() {
        // инициализация цепочки
        this.c1 = new CheckRequest();
        DispenseChain c2 = new Dollar50Dispenser();
        DispenseChain c3 = new Dollar20Dispenser();
        DispenseChain c4 = new Dollar10Dispenser();

        // последовательность вызова
        c1.setNextChain(c2);
        c2.setNextChain(c3);
        c3.setNextChain(c4);
    }

    public void request(int amount) {
        this.c1.dispense(new Currency(amount));
    }

    public static void main(String[] args) {
        ATMDispenseChain atmDispenser = new ATMDispenseChain();
        while (true) {
            int amount = 0;
            System.out.println("Enter amount to dispense");
            Scanner input = new Scanner(System.in);
            amount = input.nextInt();
            // запрос выдачи денег
            atmDispenser.request(amount);
        }

    }

}
