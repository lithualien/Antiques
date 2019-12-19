package com.github.lithualien.model.object;

public class Location {
    private int id;
    private String location;

    public Location() { }

    public Location(String location) {
        this.location = location;
    }

    public Location(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ". " + location + "\n";
    }
}
