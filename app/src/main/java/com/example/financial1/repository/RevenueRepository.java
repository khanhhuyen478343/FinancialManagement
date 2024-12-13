package com.example.financial1.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.financial1.dao.AppDatabase;
import com.example.financial1.dao.RevenueDao;
import com.example.financial1.entity.Revenue;
import com.example.financial1.entity.StatisticTypeOfRevenue;

import java.util.List;

public class RevenueRepository {
    private RevenueDao mRevenueDao;
    private LiveData<List<Revenue>> mAllRevenue;

    public RevenueRepository(Application application) {
        this.mRevenueDao = AppDatabase.getDatabase(application).revenueDao();
        mAllRevenue = mRevenueDao.findAll();

    }

    public LiveData<List<Revenue>> getAllRevenue() {
        return mAllRevenue;
    }
    public LiveData<Float> sumTotalRevenue() {
        return mRevenueDao.sumTotalRevenue();
    }

    public LiveData<List<StatisticTypeOfRevenue>> sumByTypeOfRevenue() {
        return mRevenueDao.sumByTypeOfRevenue();
    }

    public LiveData<List<Revenue>> findByDate(String date) {
        return mRevenueDao.findByDate(date); // Truy vấn theo ngày
    }

    public LiveData<List<Revenue>> findByDateRange(String startDate, String endDate) {
        return mRevenueDao.findByDateRange(startDate, endDate); // Truy vấn trong khoảng ngày
    }

    public void update(Revenue revenue){
        new UpdateAsyncTask(mRevenueDao).execute(revenue);
    }

    class UpdateAsyncTask extends AsyncTask<Revenue, Void, Void>{
        private RevenueDao mRevenueDao;
        public UpdateAsyncTask(RevenueDao revenueDao){
            this.mRevenueDao = revenueDao;
        }

        @Override
        protected Void doInBackground(Revenue... revenues) {
            mRevenueDao.update(revenues[0]);
            return null;
        }
    }

    public void insert(Revenue revenue){
        new InsertAsyncTask(mRevenueDao).execute(revenue);
    }
    class InsertAsyncTask extends AsyncTask<Revenue, Void, Void>{
        private RevenueDao mRevenueDao;
        public InsertAsyncTask(RevenueDao revenueDao){
            this.mRevenueDao = revenueDao;
        }
        @Override
        protected Void doInBackground(Revenue... revenues) {
            mRevenueDao.insert(revenues[0]);
            return null;
        }
    }
    public void delete(Revenue revenue){
        new DeleteAsyncTask(mRevenueDao).execute(revenue);
    }
    class DeleteAsyncTask extends AsyncTask<Revenue, Void, Void>{
        private RevenueDao mRevenueDao;
        public DeleteAsyncTask(RevenueDao revenueDao){
            this.mRevenueDao = revenueDao;
        }

        @Override
        protected Void doInBackground(Revenue... revenues) {
            mRevenueDao.delete(revenues[0]);
            return null;
        }
    }



}
