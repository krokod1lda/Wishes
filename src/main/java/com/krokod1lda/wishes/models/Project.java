package com.krokod1lda.wishes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    int totally;
    int purchased;
    boolean isArchived;

    public Project(String name) {
        this.name = name;
        this.totally = 0;
        this.purchased = 0;
        this.isArchived = false;
    }

    public Project() {}

    public long getId() {return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public void increase(boolean isPurchased) {
        ++this.totally;

        if (isPurchased) {
            ++this.purchased;
        }
    }
    public void decrease(boolean isPurchased) {
        --this.totally;

        if (isPurchased)
            --this.purchased;
    }
    public void increasePurchased() {
        ++this.purchased;
    }
    public void decreasePurchased() {
        --this.purchased;
    }
}
