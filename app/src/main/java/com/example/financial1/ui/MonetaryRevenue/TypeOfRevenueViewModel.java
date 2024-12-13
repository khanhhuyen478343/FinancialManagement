package com.example.financial1.ui.MonetaryRevenue;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financial1.entity.TypeOfRevenue;
import com.example.financial1.repository.TypeOfRevenueRepository;

import java.util.List;

public class TypeOfRevenueViewModel extends AndroidViewModel {
    private TypeOfRevenueRepository mTypeOfRevenueRepository;
    private LiveData<List<TypeOfRevenue>> mAllTypeOfRevenue;

    public TypeOfRevenueViewModel(@NonNull Application application) {
        super(application);
        mTypeOfRevenueRepository = new TypeOfRevenueRepository(application);
        mAllTypeOfRevenue = mTypeOfRevenueRepository.getAllTypeOfRevenue();
    }

    public LiveData<List<TypeOfRevenue>> getAllTypeOfRevenue() {
        return mAllTypeOfRevenue;
    }
    public void insert(TypeOfRevenue typeOfRevenue){
        mTypeOfRevenueRepository.insert(typeOfRevenue);
    }
    public void delete(TypeOfRevenue typeOfRevenue){
        mTypeOfRevenueRepository.delete(typeOfRevenue);
    }

    public void update(TypeOfRevenue typeOfRevenue){
        mTypeOfRevenueRepository.update(typeOfRevenue);
    }
}