package com.example.carmanager.model;

public class Car {
    private int mId;
    private String mName;
    private float mPrice;
    private int mYear;

    public Car(int mId, String mName, float mPrice, int mYear) {
        this.mId = mId;
        this.mName = mName;
        this.mPrice = mPrice;
        this.mYear = mYear;
    }
    public Car(){

    }
    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public float getmPrice() {
        return mPrice;
    }

    public void setmPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }
}
