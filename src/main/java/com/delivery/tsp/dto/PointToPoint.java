package com.delivery.tsp.dto;

public class PointToPoint {
    private String startPoint;
    private String endPoint;
    private int mesure;


    public PointToPoint() {
    }

    public PointToPoint(String startPoint, String endPoint, int mesure) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.mesure = mesure;

    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public int getMesure() {
        return mesure;
    }

    public void setMesure(int mesure) {
        this.mesure = mesure;
    }
}
