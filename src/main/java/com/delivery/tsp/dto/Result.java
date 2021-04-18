package com.delivery.tsp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result {

    private List<PointToPoint>pointList=new ArrayList<>();
    private int totalMesure;
    private int returnDistance;


    public Result() {
    }

    public Result(List<PointToPoint> pointList, int totalMesure, int returnDistance) {
        this.pointList = pointList;
        this.totalMesure = totalMesure;
        this.returnDistance = returnDistance;
    }

    public List<PointToPoint> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointToPoint> pointList) {
        this.pointList = pointList;
    }

    public int getTotalMesure() {
        return totalMesure;
    }

    public void setTotalMesure(int totalMesure) {
        this.totalMesure = totalMesure;
    }

    public int getReturnDistance() {
        return returnDistance;
    }

    public void setReturnDistance(int returnDistance) {
        this.returnDistance = returnDistance;
    }

    @Override
    public String toString() {
        return "Result{" +
                "pointList=" + pointList +
                ", totalMesure=" + totalMesure +
                ", returnDistance=" + returnDistance +
                '}';
    }
}
