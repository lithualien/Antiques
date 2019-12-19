package com.github.lithualien.model.object;

public class Antique {
    private int id;
    private String name, condition, location, date;
    private double price;

    public Antique() { }

    public Antique(int id, String name, String condition, double price, String location, String date) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.price = price;
        this.location = location;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public double getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}
