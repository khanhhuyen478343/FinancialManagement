package com.example.financial1.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TypeOfVariableExpenses {
    @PrimaryKey(autoGenerate = true)
    public int tid;
    @ColumnInfo(name = "name")
    public String name;
}
