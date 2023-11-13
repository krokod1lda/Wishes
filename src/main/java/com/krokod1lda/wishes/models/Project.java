package com.krokod1lda.wishes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    int totally;
    int purchased;
    boolean isArchived;

    public Project(String name, String phone) {
        this.name = name;
        this.totally = 0;
        this.purchased = 0;
        this.isArchived = false;
    }

    public Project() {}

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
}
