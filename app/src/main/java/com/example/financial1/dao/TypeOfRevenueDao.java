package com.example.financial1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Dao;

import com.example.financial1.entity.TypeOfRevenue;

import java.util.List;

@Dao
public interface TypeOfRevenueDao {
    @Query("SELECT * FROM typeofrevenue")
    LiveData<List<TypeOfRevenue>> findAll();

    @Insert
    void insert(TypeOfRevenue typeOfRevenue);

    @Update
    void update(TypeOfRevenue typeOfRevenue);

    @Delete
    void delete(TypeOfRevenue typeOfRevenue);
}
