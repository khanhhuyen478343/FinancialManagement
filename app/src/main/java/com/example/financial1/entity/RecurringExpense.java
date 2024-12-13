package com.example.financial1.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "recurring_spendings")
public class RecurringExpense {

    @PrimaryKey(autoGenerate = true)
    public int id; // Mã chi tiêu định kỳ

    @ColumnInfo(name = "name")
    public String name; // Tên chi tiêu (VD: Rent, Subscription, etc.)

    @ColumnInfo(name = "amount")
    public double amount;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "start_date")
    public Date startDate;

    @ColumnInfo(name = "end_date")
    public Date endDate;

    @ColumnInfo(name = "recurrence_type")
    public int recurrenceType;
    // Getter và Setter
    public int getId() {
        return id;
    }
    public RecurringExpense(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }
    public RecurringExpense() {
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(int recurrenceType) {
        this.recurrenceType = recurrenceType;
    }
}
