package com.example.financial1.ui.MonetaryRevenue;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financial1.entity.Revenue;
import com.example.financial1.entity.TypeOfRevenue;
import com.example.financial1.repository.RevenueRepository;
import com.example.financial1.repository.TypeOfRevenueRepository;

import java.util.List;

public class AmountOfRevenueViewModel extends AndroidViewModel {
    private RevenueRepository mRevenueRepository;
    private TypeOfRevenueRepository mTypeOfRevenueRepository;
    private LiveData<List<Revenue>> mAllRevenue;
    private LiveData<List<TypeOfRevenue>> mAllTypeOfRevenue;


    public AmountOfRevenueViewModel(@NonNull Application application) {
        super(application);
        mRevenueRepository = new RevenueRepository(application);
        mAllRevenue = mRevenueRepository.getAllRevenue();
        mTypeOfRevenueRepository = new TypeOfRevenueRepository(application);
        mAllTypeOfRevenue = mTypeOfRevenueRepository.getAllTypeOfRevenue();


    }
    // Thêm các phương thức thao tác với ngày
    public LiveData<List<Revenue>> getVariableExpensesByDate(String date) {
        return mRevenueRepository.findByDate(date);
    }

    public LiveData<List<Revenue>> getVariableExpensesByDateRange(String startDate, String endDate) {
        return mRevenueRepository.findByDateRange(startDate, endDate);
    }


    public LiveData<List<TypeOfRevenue>> getAllTypeOfRevenue() {
        return mAllTypeOfRevenue;
    }

    public LiveData<List<Revenue>> getAllRevenue() {
        return mAllRevenue;
    }
    public void insert(Revenue revenue){
        mRevenueRepository.insert(revenue);
    }
    public void delete(Revenue revenue){
        mRevenueRepository.delete(revenue);
    }

    public void update(Revenue revenue){
        mRevenueRepository.update(revenue);
    }

}