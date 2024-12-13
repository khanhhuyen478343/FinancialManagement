package com.example.financial1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financial1.entity.VariableExpenses;
import com.example.financial1.entity.StatisticTypeOfVariableExpenses;

import java.util.List;

@Dao
public interface VariableExpensesDao {
    @Query("SELECT * FROM variableExpenses")
    LiveData<List<VariableExpenses>> findAll();

    @Query("SELECT * FROM variableExpenses WHERE date = :date")
    LiveData<List<VariableExpenses>> findByDate(String date); // Truy vấn theo ngày

    @Query("SELECT sum(amountofmoney) FROM variableExpenses")
    LiveData<Float> sumTotalVariableExpenses();

    @Query("SELECT b.tid, b.name, sum(amountofmoney) as total FROM variableExpenses a INNER JOIN typeofvariableExpenses b on a.toeid = b.tid " +
            "GROUP BY b.tid, b.name")
    LiveData<List<StatisticTypeOfVariableExpenses>> sumByTypeOfVariableExpenses();

    @Query("SELECT * FROM variableExpenses WHERE date BETWEEN :startDate AND :endDate")
    LiveData<List<VariableExpenses>> findByDateRange(String startDate, String endDate); // Truy vấn trong khoảng ngày

    @Insert
    void insert(VariableExpenses variableExpenses);

    @Update
    void update(VariableExpenses variableExpenses);

    @Delete
    void delete(VariableExpenses variableExpenses);
}

