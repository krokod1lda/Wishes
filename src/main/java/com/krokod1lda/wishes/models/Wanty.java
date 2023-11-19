package com.krokod1lda.wishes.models;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Base64;

@Entity
public class Wanty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String wantyName;
    @Column(columnDefinition = "DATE")
    private Date date;
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

    public Wanty(String wantyName, Date date, String size, long sellerId, long buyerId,
                 long clientId, boolean isPurchased, String description, MultipartFile wantyPhoto) {

        this.wantyName = wantyName;
        this.date = date;
        this.size = size;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.clientId = clientId;
        this.isPurchased = isPurchased;
        this.description = description;
        this.wantyPhoto = photoMultipartToByte(wantyPhoto);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public void update(String wantyName, Date date, String size, long sellerId, long buyerId,
                        long clientId, boolean isPurchased, String description, MultipartFile wantyPhoto) {

        this.wantyName = wantyName;
        this.date = date;
        this.size = size;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.clientId = clientId;
        this.isPurchased = isPurchased;
        this.description = description;

        if(wantyPhoto != null)
            this.wantyPhoto = photoMultipartToByte(wantyPhoto);
    }

    public byte[] photoMultipartToByte(MultipartFile nonBinPhoto) {
        byte[] wantyPhoto = null;

        if (nonBinPhoto != null) {
            try {
                wantyPhoto = new byte[nonBinPhoto.getBytes().length];
                int i = 0;

                for (byte b : nonBinPhoto.getBytes())
                    wantyPhoto[i++] = b;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return wantyPhoto;
    }
}