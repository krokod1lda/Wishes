package com.krokod1lda.wishes.models;

import jakarta.persistence.*;

@Entity
public class Wanty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String wantyName;
    private String date;
    private String size;
    private long sellerId;
    private long buyerId;
    private long clientId;
    private boolean isPurchased;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] wantyPhoto;

    public Wanty(String wantyName, String date, String size, long sellerId, long buyerId,
                 long clientId, boolean isPurchased, String description, byte[] wantyPhoto) {

        this.wantyName = wantyName;
        this.date = date;
        this.size = size;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.clientId = clientId;
        this.isPurchased = isPurchased;
        this.description = description;
        this.wantyPhoto = wantyPhoto;
    }

    public Wanty() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWantyName() {
        return wantyName;
    }

    public void setWantyName(String wantyName) {
        this.wantyName = wantyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getWantyPhoto() {
        return wantyPhoto;
    }

    public void setWantyPhoto(byte[] wantyPhoto) {
        this.wantyPhoto = wantyPhoto;
    }

    public void update (String wantyName, String date, String size, long sellerId, long buyerId,
                        long clientId, boolean isPurchased, String description, byte[] wantyPhoto) {
        this.wantyName = wantyName;
        this.date = date;
        this.size = size;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.clientId = clientId;
        this.isPurchased = isPurchased;
        this.description = description;
        this.wantyPhoto = wantyPhoto;
    }
}
