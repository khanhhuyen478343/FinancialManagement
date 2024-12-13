package com.example.financial1.repository;

public class ExpenseItem {
    private String name;
    private float amount;
    private boolean isRecurring;

    public ExpenseItem(String name, float amount, boolean isRecurring) {
        this.name = name;
        this.amount = amount;
        this.isRecurring = isRecurring;
    }

    // Add a getTotal method to return the amount
    public float getTotal() {
        return this.amount;
    }

    public String getName() {
        return name;
    }

    public boolean isRecurring() {
        return isRecurring;
    }
}
