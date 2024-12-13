package com.example.financial1.ui.MonetaryVariableExpenses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financial1.entity.TypeOfVariableExpenses;
import com.example.financial1.repository.TypeOfVariableExpensesRepository;

import java.util.List;

public class TypeOfVariableExpensesViewModel extends AndroidViewModel {
    private TypeOfVariableExpensesRepository mTypeOfVariableExpensesRepository;
    private LiveData<List<TypeOfVariableExpenses>> mAllTypeOfVariableExpenses;

    public TypeOfVariableExpensesViewModel(@NonNull Application application) {
        super(application);
        mTypeOfVariableExpensesRepository = new TypeOfVariableExpensesRepository(application);
        mAllTypeOfVariableExpenses = mTypeOfVariableExpensesRepository.getAllTypeOfVariableExpenses();
    }

    public LiveData<List<TypeOfVariableExpenses>> getAllTypeOfVariableExpenses() {
        return mAllTypeOfVariableExpenses;
    }
    public void insert(TypeOfVariableExpenses typeOfVariableExpenses){
        mTypeOfVariableExpensesRepository.insert(typeOfVariableExpenses);
    }
    public void delete(TypeOfVariableExpenses typeOfVariableExpenses){
        mTypeOfVariableExpensesRepository.delete(typeOfVariableExpenses);
    }


    public void update(TypeOfVariableExpenses typeOfVariableExpenses){
        mTypeOfVariableExpensesRepository.update(typeOfVariableExpenses);
    }
}