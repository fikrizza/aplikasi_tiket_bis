package com.example.busticketing;

public class objectRute {
    String depature;
    String arrival;
    String time_depature;
    String price;

    public objectRute(String depature, String time_depature, String arrival, String price) {
        this.depature = depature;
        this.arrival = arrival;
        this.time_depature = time_depature;
        this.price = price;
    }

    public String getDepature() {
        return depature;
    }

    public String getArrival() {
        return arrival;
    }

    public String getTime_depature() {
        return time_depature;
    }

    public String getPrice() {
        return price;
    }
}

