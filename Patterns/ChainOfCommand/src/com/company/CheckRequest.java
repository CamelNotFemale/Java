package com.company;

public class CheckRequest implements DispenseChain {

    private DispenseChain chain;

    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.chain=nextChain;
    }

    @Override
    public void dispense(Currency cur) {
        if (cur.getAmount() % 10 != 0) {
            System.out.println("Error! Amount should be in multiple of 10s.");
        }
        else {
            this.chain.dispense(cur);
        }
    }
}
