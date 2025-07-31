package com.railway.booking;

public class FareService {

    public double calculateDiscountedFare(double baseFare, String passengerType) {
        if (passengerType == null) {
            return baseFare;
        }
        
        double discount = 0.0;
        switch (passengerType.toLowerCase()) {
            case "child":
                discount = 0.25;
                break;
            case "senior":
                discount = 0.35;
                break;
            case "disabled":
                discount = 0.50;
                break;
            default:
                discount = 0.0;
                break;
        }
        
        return baseFare * (1.0 - discount);
    }
}