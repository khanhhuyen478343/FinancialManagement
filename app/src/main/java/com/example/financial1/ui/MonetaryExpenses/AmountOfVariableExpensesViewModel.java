package com.example.financial1.ui.MonetaryExpenses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.financial1.entity.VariableExpenses;
import com.example.financial1.entity.TypeOfVariableExpenses;
import com.example.financial1.repository.VariableExpensesRepository;
import com.example.financial1.repository.TypeOfVariableExpensesRepository;

import java.util.List;

public class AmountOfVariableExpensesViewModel extends AndroidViewModel {
    private final VariableExpensesRepository mVariableExpensesRepository;
    private final TypeOfVariableExpensesRepository mTypeOfVariableExpensesRepository;
    private final LiveData<List<VariableExpenses>> mAllVariableExpenses;
    private final LiveData<List<TypeOfVariableExpenses>> mAllTypeOfVariableExpenses;
    private final LiveData<String> mAmountOfVariableExpensesAlert;

    public AmountOfVariableExpensesViewModel(@NonNull Application application) {
        super(application);
        mVariableExpensesRepository = new VariableExpensesRepository(application);
        mTypeOfVariableExpensesRepository = new TypeOfVariableExpensesRepository(application);

        mAllVariableExpenses = mVariableExpensesRepository.getAllVariableExpenses();
        mAllTypeOfVariableExpenses = mTypeOfVariableExpensesRepository.getAllTypeOfVariableExpenses();

        // Thiết lập LiveData để hiển thị thông báo
        mAmountOfVariableExpensesAlert = Transformations.map(
                Transformations.distinctUntilChanged(mVariableExpensesRepository.sumTotalVariableExpenses()),
                total -> {
                    if (total != null && total > 300) {
                        return "Warning: Total Variable Expenses exceeds the limit of 300!";
                    }
                    return null;
                }
        );
    }

    // Thêm các phương thức thao tác với ngày
    public LiveData<List<VariableExpenses>> getVariableExpensesByDate(String date) {
        return mVariableExpensesRepository.findByDate(date);
    }

    public LiveData<List<VariableExpenses>> getVariableExpensesByDateRange(String startDate, String endDate) {
        return mVariableExpensesRepository.findByDateRange(startDate, endDate);
    }

    public LiveData<String> getAmountOfVariableExpensesAlert() {
        return mAmountOfVariableExpensesAlert;
    }

    public LiveData<List<TypeOfVariableExpenses>> getAllTypeOfVariableExpenses() {
        return mAllTypeOfVariableExpenses;
    }

    public LiveData<List<VariableExpenses>> getAllVariableExpenses() {
        return mAllVariableExpenses;
    }

    public void insert(VariableExpenses variableExpenses) {
        mVariableExpensesRepository.insert(variableExpenses);
    }

    public void delete(VariableExpenses variableExpenses) {
        mVariableExpensesRepository.delete(variableExpenses);
    }

    public void update(VariableExpenses variableExpenses) {
        mVariableExpensesRepository.update(variableExpenses);
    }

}
