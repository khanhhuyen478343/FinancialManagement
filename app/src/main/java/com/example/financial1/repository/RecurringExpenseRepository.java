package com.example.financial1.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.financial1.dao.AppDatabase;
import com.example.financial1.dao.AppExecutors;
import com.example.financial1.dao.RecurringExpenseDao;
import com.example.financial1.entity.RecurringExpense;

import java.util.Date;
import java.util.List;

public class RecurringExpenseRepository {
    private RecurringExpenseDao mRecurringExpenseDao;
    private LiveData<List<RecurringExpense>> mAllRecurringExpenses;

    public RecurringExpenseRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        mRecurringExpenseDao = database.recurringExpenseDao();
        mAllRecurringExpenses = mRecurringExpenseDao.getAllRecurringExpenses();
    }

    public void insert(RecurringExpense recurringExpense) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            mRecurringExpenseDao.insert(recurringExpense);
        });
    }

    public void update(RecurringExpense recurringExpense) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            mRecurringExpenseDao.update(recurringExpense);
        });
    }

    public void delete(RecurringExpense recurringExpense) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            mRecurringExpenseDao.delete(recurringExpense);
        });
    }

    public LiveData<List<RecurringExpense>> getAllRecurringExpenses() {
        return mRecurringExpenseDao.getAllRecurringExpenses();
    }

    // Lấy danh sách các chi tiêu định kỳ đang hoạt động dựa trên ngày hiện tại
    public LiveData<List<RecurringExpense>> getActiveRecurringExpenses(Date currentDate) {
        return mRecurringExpenseDao.getActiveRecurringExpenses(currentDate);
    }
}
