package com.example.financial1.ui.Statistic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financial1.entity.StatisticTypeOfRevenue;
import com.example.financial1.repository.RevenueRepository;

import java.util.List;

public class StatisticRevenueViewModel extends AndroidViewModel {
    private RevenueRepository mRevenueRepository;
    private LiveData<Float> mTotalRevenue;
    private LiveData<List<StatisticTypeOfRevenue>> mStatisticTypeOfRevenues;

    public StatisticRevenueViewModel(@NonNull Application application) {
        super(application);

        mRevenueRepository = new RevenueRepository(application);
        mTotalRevenue = mRevenueRepository.sumTotalRevenue();
        mStatisticTypeOfRevenues = mRevenueRepository.sumByTypeOfRevenue();
    }

    public LiveData<Float> getTotalRevenue(){
        return mTotalRevenue;
    }

    public LiveData<List<StatisticTypeOfRevenue>> getStatisticTypeOfRevenues(){
        return mStatisticTypeOfRevenues;
    }
}
