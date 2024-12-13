package com.example.financial1.entity;

public class StatisticTypeOfVariableExpenses {
    public int tid;
    public String name;
    public Float total;

    public StatisticTypeOfVariableExpenses(String name, float total) {
        this.name = name;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public float getTotal() {
        return total;
    }
}
