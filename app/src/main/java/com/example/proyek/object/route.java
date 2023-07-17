package com.example.proyek.object;

public class route{
    String id_rute;
    String depature;
    String arrival;
    String time;
    String price;
    String date;
    public route(String id, String  depature, String arrival, String time, String price, String date) {
        this.id_rute = id;
        this.depature = depature;
        this.arrival = arrival;
        this.time = time;
        this.price = price;
        this.date = date;
    }

    public String getId_rute() {
        return id_rute;
    }

    public String getDepature() {
        return depature;
    }

    public String getArrival() {
        return arrival;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

}
