package com.delivery.tsp.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
public class DeliveryPoint {

    @Id
    private int id;
    private double latitude;
    private double longitude;
    private LocalTime deliveryFrom;
    private LocalTime deliveryTo;

    public DeliveryPoint() {
    }

    public DeliveryPoint(int id
            , double latitude
            , double longitude
            , LocalTime deliveryFrom
            , LocalTime deliveryTo) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deliveryFrom = deliveryFrom;
        this.deliveryTo = deliveryTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalTime getDeliveryFrom() {
        return deliveryFrom;
    }

    public void setDeliveryFrom(LocalTime deliveryFrom) {
        this.deliveryFrom = deliveryFrom;
    }

    public LocalTime getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(LocalTime deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    @Override
    public String toString() {
        return "DeliveryPoint{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", deliveryFrom=" + deliveryFrom +
                ", deliveryTo=" + deliveryTo +
                '}';
    }
}
