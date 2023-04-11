package com.example.takeme;

public class PaymentHelperClass {

    String name, details, km, date, total;

    public PaymentHelperClass() {

    }

    public PaymentHelperClass(String name, String details, String km, String date, String total) {
        this.name = name;
        this.details = details;
        this.km = km;
        this.date = date;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
