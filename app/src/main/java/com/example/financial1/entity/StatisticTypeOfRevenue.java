package com.example.financial1.entity;

public class StatisticTypeOfRevenue {
    public int tid;
    public String name;
    public Float total;
    // Constructor
    public StatisticTypeOfRevenue(String name, float total) {
        this.name = name;
        this.total = total;
    }

    // Getter cho name
    public String getName() {
        return name;
    }

    // Getter cho total
    public float getTotal() {
        return total;
    }
}
