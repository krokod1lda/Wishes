package com.krokod1lda.wishes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private String name;
    private String surname;
    private boolean isArchived;
    private int totally;
    private int purchased;

    public Person(String type, String name, String surname) {
        this.type = type;
        this.name = name;
        this.surname = surname;
        this.isArchived = false;
        this.totally = 0;
        this.purchased = 0;
    }

    public Person() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
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

    public void increase(boolean isPurchased) {
        ++this.totally;

        if (isPurchased)
            ++this.purchased;
    }

    public void increasePurchased() {
        ++this.purchased;
    }

    public void decrease(boolean isPurchased) {
        --this.totally;

        if (isPurchased)
            --this.purchased;
    }

    public void decreasePurchased() {
        --this.purchased;
    }
}