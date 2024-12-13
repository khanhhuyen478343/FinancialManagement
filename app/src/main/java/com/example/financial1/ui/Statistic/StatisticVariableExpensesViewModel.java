package com.example.financial1.ui.Statistic;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financial1.entity.StatisticTypeOfVariableExpenses;
import com.example.financial1.entity.RecurringExpense;
import com.example.financial1.repository.VariableExpensesRepository;
import com.example.financial1.repository.RecurringExpenseRepository;

import java.util.List;

public class StatisticVariableExpensesViewModel extends AndroidViewModel {
    private VariableExpensesRepository mVariableExpensesRepository;
    private RecurringExpenseRepository mRecurringExpensesRepository;
    private LiveData<Float> mTotalVariableExpenses;
    private LiveData<List<StatisticTypeOfVariableExpenses>> mStatisticTypeOfVariableExpenses;
    private LiveData<List<RecurringExpense>> mRecurringExpenses;

    public StatisticVariableExpensesViewModel(@NonNull Application application) {
        super(application);

        mVariableExpensesRepository = new VariableExpensesRepository(application);
        mRecurringExpensesRepository = new RecurringExpenseRepository(application);
        mTotalVariableExpenses = mVariableExpensesRepository.sumTotalVariableExpenses();
        mStatisticTypeOfVariableExpenses = mVariableExpensesRepository.sumByTypeOfVariableExpenses();
        mRecurringExpenses = mRecurringExpensesRepository.getAllRecurringExpenses();
    }

    public LiveData<Float> getTotalVariableExpenses() {
        return mTotalVariableExpenses;
    }

    public LiveData<List<StatisticTypeOfVariableExpenses>> getStatisticTypeOfVariableExpenses() {
        return mStatisticTypeOfVariableExpenses;
    }

    public LiveData<List<RecurringExpense>> getRecurringExpenses() {
        return mRecurringExpenses;
    }
}
