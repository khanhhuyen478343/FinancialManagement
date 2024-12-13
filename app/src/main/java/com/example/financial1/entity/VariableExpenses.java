package com.example.financial1.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VariableExpenses {
    @PrimaryKey(autoGenerate = true)
    public int rid;

    @ColumnInfo(name = "toeid")
    public int toeid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "amountofmoney")
    public float amountofmoney;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "date")
    public String date;
}
