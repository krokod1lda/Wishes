package com.krokod1lda.wishes;

public class SoldInfo {
    private int totally;
    private int purchased;

    public SoldInfo(int totally, int purchased) {
        this.totally = totally;
        this.purchased = purchased;
    }

    public int getTotally() {
        return totally;
    }

    public void setTotally(int totally) {
        this.totally = totally;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public void update(int totally, int purchased) {
        this.totally += totally;
        this.purchased += purchased;
    }
}