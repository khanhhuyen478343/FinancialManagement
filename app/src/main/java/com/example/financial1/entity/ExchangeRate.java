package com.example.financial1.entity;

import java.io.Serializable;

public class ExchangeRate implements Serializable {
    private int ID_Img;
    private String icon;
    private Long buyCash;
    private Long sellCash;
    private Long buyTransfer;
    private Long sellTransfer;

    public ExchangeRate(){

    }

    public ExchangeRate(int ID_Img, String icon, Long buyCash, Long sellCash, Long buyTransfer, Long sellTransfer) {
        this.ID_Img = ID_Img;
        this.icon = icon;
        this.buyCash = buyCash;
        this.sellCash = sellCash;
        this.buyTransfer = buyTransfer;
        this.sellTransfer = sellTransfer;
    }

    public int getID_Img() {
        return ID_Img;
    }

    public void setID_Img(int ID_Img) {
        this.ID_Img = ID_Img;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getBuyCash() {
        return buyCash;
    }

    public void setBuyCash(Long buyCash) {
        this.buyCash = buyCash;
    }

    public Long getSellCash() {
        return sellCash;
    }

    public void setSellCash(Long sellCash) {
        this.sellCash = sellCash;
    }

    public Long getBuyTransfer() {
        return buyTransfer;
    }

    public void setBuyTransfer(Long buyTransfer) {
        this.buyTransfer = buyTransfer;
    }

    public Long getSellTransfer() {
        return sellTransfer;
    }

    public void setSellTransfer(Long sellTransfer) {
        this.sellTransfer = sellTransfer;
    }
}
