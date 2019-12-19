package com.github.lithualien.model.object;

public class Condition {
    private int id;
    private String condition;

    public Condition() { }

    public Condition(int id, String condition) {
        this.id = id;
        this.condition = condition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return id + ". " + condition + "\n";
    }
}
