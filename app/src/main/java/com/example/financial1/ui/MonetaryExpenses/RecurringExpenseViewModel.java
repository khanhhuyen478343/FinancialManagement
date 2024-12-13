package com.example.financial1.ui.MonetaryExpenses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financial1.entity.RecurringExpense;
import com.example.financial1.repository.RecurringExpenseRepository;

import java.util.Date;
import java.util.List;

public class RecurringExpenseViewModel extends AndroidViewModel {
    private RecurringExpenseRepository repository;
    private LiveData<List<RecurringExpense>> allRecurringExpenses;

    public RecurringExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new RecurringExpenseRepository(application);
        allRecurringExpenses = repository.getAllRecurringExpenses();
    }

    public void insert(RecurringExpense recurringExpense) {
        repository.insert(recurringExpense);
    }

    public void update(RecurringExpense recurringExpense) {
        repository.update(recurringExpense);
    }

    public void delete(RecurringExpense recurringExpense) {
        repository.delete(recurringExpense);
    }

    public LiveData<List<RecurringExpense>> getAllRecurringExpenses() {
        return allRecurringExpenses;
    }

    public LiveData<List<RecurringExpense>> getActiveRecurringExpenses(Date currentDate) {
        return repository.getActiveRecurringExpenses(currentDate);
    }
}

