package com.example.proyek.object;

public class reserved {
    String id_reservation;
    String dateReserv;
    String bookCode;
    String id_account;
    String id_route;
    String name;
    String telp;
    String paymentMethod;
    String paymentStatus;
    String depature;
    String arrival;
    String timeDepature;
    String dateDepature;
    String price;

    public reserved(String id_reservation, String dateReserv, String bookCode, String id_account,
                    String id_route, String name, String telp, String paymentMethod, String paymentStatus,
                    String depature, String arrival, String timeDepature, String dateDepature, String price) {
        this.id_reservation = id_reservation;
        this.dateReserv = dateReserv;
        this.bookCode = bookCode;
        this.id_account = id_account;
        this.id_route = id_route;
        this.name = name;
        this.telp = telp;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.depature = depature;
        this.arrival = arrival;
        this.timeDepature = timeDepature;
        this.dateDepature = dateDepature;
        this.price = price;
    }

    public String getId_reservation() {
        return id_reservation;
    }

    public String getDateReserv() {
        return dateReserv;
    }

    public String getBookCode() {
        return bookCode;
    }

    public String getId_account() {
        return id_account;
    }

    public String getId_route() {
        return id_route;
    }

    public String getName() {
        return name;
    }

    public String getTelp() {
        return telp;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getDepature() {
        return depature;
    }

    public String getArrival() {
        return arrival;
    }

    public String getTimeDepature() {
        return timeDepature;
    }

    public String getDateDepature() {
        return dateDepature;
    }

    public String getPrice() {
        return price;
    }
}
