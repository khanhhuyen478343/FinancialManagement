package com.example.financial1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.financial1.entity.RecurringExpense;

import java.util.Date;
import java.util.List;

@Dao
public interface RecurringExpenseDao {

    @Insert
    void insert(RecurringExpense recurringExpense);

    @Update
    void update(RecurringExpense recurringExpense);

    @Delete
    void delete(RecurringExpense recurringExpense);

    // Lấy tất cả chi tiêu định kỳ
    @Query("SELECT * FROM recurring_spendings")
    LiveData<List<RecurringExpense>> getAllRecurringExpenses();

    // Lấy chi tiêu định kỳ đang hoạt động (startDate <= currentDate <= endDate)
    @Query("SELECT * FROM recurring_spendings WHERE start_date <= :currentDate AND (end_date >= :currentDate OR end_date IS NULL)")
    LiveData<List<RecurringExpense>> getActiveRecurringExpenses(Date currentDate);


    @Query("SELECT SUM(amount) FROM recurring_spendings")
    LiveData<Float> sumTotalRecurringExpenses();


}
