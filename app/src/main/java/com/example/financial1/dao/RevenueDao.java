package com.example.financial1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financial1.entity.Revenue;
import com.example.financial1.entity.StatisticTypeOfRevenue;

import java.util.List;

@Dao
public interface RevenueDao {
    @Query("SELECT * FROM revenue")
    LiveData<List<Revenue>> findAll();
    @Query("SELECT * FROM revenue WHERE date = :date")
    LiveData<List<Revenue>> findByDate(String date);

     @Query("SELECT sum(amountofmoney) FROM revenue")
    LiveData<Float> sumTotalRevenue();

    @Query("SELECT b.tid, b.name, sum(amountofmoney) as total FROM revenue a INNER JOIN typeofrevenue b on a.torid = b.tid " +
            "GROUP BY b.tid, b.name")
    LiveData<List<StatisticTypeOfRevenue>> sumByTypeOfRevenue();

    @Query("SELECT * FROM revenue WHERE date BETWEEN :startDate AND :endDate")
    LiveData<List<Revenue>> findByDateRange(String startDate, String endDate);

    @Insert
    void insert(Revenue revenue);

    @Update
    void update(Revenue revenue);

    @Delete
    void delete(Revenue revenue);
}
