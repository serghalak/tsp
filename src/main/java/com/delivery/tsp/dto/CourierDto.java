package com.delivery.tsp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalTime;
import java.util.Arrays;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

public class CourierDto {

//    @JsonDeserialize(using = LocalTimeDeserializer.class/* LocalDateDeserializer.class*/)
//    @JsonSerialize(using = LocalTimeSerializer.class /*LocalDateSerializer.class*/)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
//    @DateTimeFormat(pattern = "HH:mm:ss", iso = DateTimeFormat.ISO.TIME)
    private LocalTime beginWorkDay;

//    @JsonDeserialize(using = LocalTimeDeserializer.class/* LocalDateDeserializer.class*/)
//    @JsonSerialize(using = LocalTimeSerializer.class /*LocalDateSerializer.class*/)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endWorkDay;
    private int[] spendTime;
    private int speed;
    private double lotitude;
    private double longitude;

    public CourierDto() {
    }

    public CourierDto(LocalTime beginWorkDay
            , LocalTime endworkDay
            , int[] spendTime
            , int speed
            , double lotitude
            , double longitude) {

        this.beginWorkDay = beginWorkDay;
        this.endWorkDay = endworkDay;
        this.spendTime = spendTime;
        this.speed = speed;
        this.lotitude = lotitude;
        this.longitude = longitude;
    }

    public LocalTime getBeginWorkDay() {
        return beginWorkDay;
    }

    public void setBeginWorkDay(LocalTime beginWorkDay) {
        this.beginWorkDay = beginWorkDay;
    }

    public LocalTime getEndWorkDay() {
        return endWorkDay;
    }

    public void setEndWorkDay(LocalTime endWorkDay) {
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
        return "CourierDto{" +
                "beginWorkDay=" + beginWorkDay +
                ", endWorkDay=" + endWorkDay +
                ", spendTime=" + Arrays.toString(spendTime) +
                ", speed=" + speed +
                ", lotitude=" + lotitude +
                ", longitude=" + longitude +
                '}';
    }
}
