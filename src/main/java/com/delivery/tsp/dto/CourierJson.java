package com.delivery.tsp.dto;

import java.util.Arrays;

public class CourierJson {
    private String beginWorkDay;
    private String endWorkDay;
    private int[] spendTime;
    private int speed;
    private double lotitude;
    private double longitude;

    public CourierJson() {
    }

    public CourierJson(String beginWorkDay
            , String endWorkDay
            , int[] spendTime
            , int speed
            , double lotitude
            , double longitude) {

        this.beginWorkDay = beginWorkDay;
        this.endWorkDay = endWorkDay;
        this.spendTime = spendTime;
        this.speed = speed;
        this.lotitude = lotitude;
        this.longitude = longitude;
    }

    public String getBeginWorkDay() {
        return beginWorkDay;
    }

    public void setBeginWorkDay(String beginWorkDay) {
        this.beginWorkDay = beginWorkDay;
    }

    public String getEndWorkDay() {
        return endWorkDay;
    }

    public void setEndWorkDay(String endWorkDay) {
        this.endWorkDay = endWorkDay;
    }

    public int[] getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(int[] spendTime) {
        this.spendTime = spendTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getLotitude() {
        return lotitude;
    }

    public void setLotitude(double lotitude) {
        this.lotitude = lotitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CourierJson{" +
                "beginWorkDay='" + beginWorkDay + '\'' +
                ", endWorkDay='" + endWorkDay + '\'' +
                ", spendTime=" + Arrays.toString(spendTime) +
                ", speed=" + speed +
                ", lotitude=" + lotitude +
                ", longitude=" + longitude +
                '}';
    }
}
