package com.mindbriks.sparkle.utils;

import com.mindbriks.sparkle.model.Location;

public class DistanceHelper {
    public static double getDistance(Location loc1, Location loc2) {
        if(loc1 !=null && loc2!= null) {
            double lat1 = loc1.getLatitude();
            double lon1 = loc1.getLongitude();
            double lat2 = loc2.getLatitude();
            double lon2 = loc2.getLongitude();
            double R = 6371; // Earth's radius in kilometers
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c; // Distance in kilometers
            return distance;
        }
        return 0;
    }
}
