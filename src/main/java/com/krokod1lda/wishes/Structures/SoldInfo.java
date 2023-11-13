package com.krokod1lda.wishes.Structures;

public class SoldInfo {
    private int totally;
    private int purchased;
    private int notPurchased;

    public SoldInfo(int totally, int purchased) {
        this.totally = totally;
        this.purchased = purchased;
        this.notPurchased = totally - purchased;
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

    public int getNotPurchased() {
        return notPurchased;
    }

    public void setNotPurchased(int notPurchased) {
        this.notPurchased = notPurchased;
    }

    public void update(int totally, int purchased) {
        this.totally += totally;
        this.purchased += purchased;
        this.notPurchased += totally - purchased;
    }
}