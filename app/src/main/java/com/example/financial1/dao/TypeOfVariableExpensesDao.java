package com.example.financial1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financial1.entity.TypeOfVariableExpenses;

import java.util.List;

@Dao
public interface TypeOfVariableExpensesDao {
    @Query("SELECT * FROM typeofvariableExpenses")
    LiveData<List<TypeOfVariableExpenses>> findAll();

    @Insert
    void insert(TypeOfVariableExpenses typeOfVariableExpenses);

    @Update
    void update(TypeOfVariableExpenses typeOfVariableExpenses);

    @Delete
    void delete(TypeOfVariableExpenses typeOfVariableExpenses);
}
