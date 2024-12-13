package com.example.financial1.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Revenue {
    @PrimaryKey(autoGenerate = true)
    public int rid;

    @ColumnInfo(name = "torid")
    public int torid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "amountofmoney")
    public float amountofmoney;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "date")
    public String date; // Có thể thay bằng Date nếu cần
}

