package com.example.financial1.dao;

import android.graphics.Bitmap;

public class ExchangeRate_json {
    private String type;
    private String imageurl;
    private Bitmap bitmap;
    private String buycash;
    private String buytransfer;
    private String sellcash;
    private String selltransfer;

    public ExchangeRate_json() {
    }

    public ExchangeRate_json(String type, String imageurl, Bitmap bitmap, String buycash, String buytransfer, String sellcash, String selltransfer) {
        this.type = type;
        this.imageurl = imageurl;
        this.bitmap = bitmap;
        this.buycash = buycash;
        this.buytransfer = buytransfer;
        this.sellcash = sellcash;
        this.selltransfer = selltransfer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getBuycash() {
        return buycash;
    }

    public void setBuycash(String buycash) {
        this.buycash = buycash;
    }

    public String getBuytransfer() {
        return buytransfer;
    }

    public void setBuytransfer(String buytransfer) {
        this.buytransfer = buytransfer;
    }

    public String getSellcash() {
        return sellcash;
    }

    public void setSellcash(String sellcash) {
        this.sellcash = sellcash;
    }

    public String getSelltransfer() {
        return selltransfer;
    }

    public void setSelltransfer(String selltransfer) {
        this.selltransfer = selltransfer;
    }

    @Override
    public String toString() {
        return "ExchangeRate_json{" +
                "type='" + type + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", bitmap=" + bitmap +
                ", buycash='" + buycash + '\'' +
                ", buytransfer='" + buytransfer + '\'' +
                ", sellcash='" + sellcash + '\'' +
                ", selltransfer='" + selltransfer + '\'' +
                '}';
    }
}
