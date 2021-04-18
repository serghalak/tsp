package com.delivery.tsp.utils;

import java.util.concurrent.TimeUnit;

public class Distance {

    public static int distFrom(double lat1
            , double lng1
            , double lat2
            , double lng2) {

        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        int dist = (int) Math.round (earthRadius * c/1000);

        return dist;
    }

    public static String secondsToHours(int seconds){

        long hours = TimeUnit.SECONDS.toHours(seconds) ;
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
        if(second>=30){
            minute++;
            if (minute==60){
                hours++;
            }
        }
        return String.valueOf(hours+"h "+minute +" m");
        /*
        int p1 = seconds % 60;
        int p2 = seconds / 60;
        int p3 = p2 % 60;
        p2 = p2 / 60;*/
    }
}
