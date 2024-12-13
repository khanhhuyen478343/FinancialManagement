package com.example.financial1.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.financial1.dao.AppDatabase;
import com.example.financial1.dao.VariableExpensesDao;
import com.example.financial1.entity.VariableExpenses;
import com.example.financial1.entity.StatisticTypeOfVariableExpenses;

import java.util.List;

public class VariableExpensesRepository {
    private VariableExpensesDao mVariableExpensesDao;
    private LiveData<List<VariableExpenses>> mAllVariableExpenses;

    public VariableExpensesRepository(Application application) {
        this.mVariableExpensesDao = AppDatabase.getDatabase(application).variableExpensesDao();
        mAllVariableExpenses = mVariableExpensesDao.findAll();
    }

    public LiveData<List<VariableExpenses>> getAllVariableExpenses() {
        return mAllVariableExpenses;
    }

    public LiveData<Float> sumTotalVariableExpenses() {
        return mVariableExpensesDao.sumTotalVariableExpenses();
    }

    public LiveData<List<StatisticTypeOfVariableExpenses>> sumByTypeOfVariableExpenses() {
        return mVariableExpensesDao.sumByTypeOfVariableExpenses();
    }

    public LiveData<List<VariableExpenses>> findByDate(String date) {
        return mVariableExpensesDao.findByDate(date); // Truy vấn theo ngày
    }

    public LiveData<List<VariableExpenses>> findByDateRange(String startDate, String endDate) {
        return mVariableExpensesDao.findByDateRange(startDate, endDate); // Truy vấn trong khoảng ngày
    }

    public void update(VariableExpenses variableExpenses) {
        new UpdateAsyncTask(mVariableExpensesDao).execute(variableExpenses);
    }

    class UpdateAsyncTask extends AsyncTask<VariableExpenses, Void, Void> {
        private VariableExpensesDao mVariableExpensesDao;

        public UpdateAsyncTask(VariableExpensesDao variableExpensesDao) {
            this.mVariableExpensesDao = variableExpensesDao;
        }

        @Override
        protected Void doInBackground(VariableExpenses... variableExpensess) {
            mVariableExpensesDao.update(variableExpensess[0]);
            return null;
        }
    }

    public void insert(VariableExpenses variableExpenses) {
        new InsertAsyncTask(mVariableExpensesDao).execute(variableExpenses);
    }

    class InsertAsyncTask extends AsyncTask<VariableExpenses, Void, Void> {
        private VariableExpensesDao mVariableExpensesDao;

        public InsertAsyncTask(VariableExpensesDao variableExpensesDao) {
            this.mVariableExpensesDao = variableExpensesDao;
        }

        @Override
        protected Void doInBackground(VariableExpenses... variableExpensess) {
            mVariableExpensesDao.insert(variableExpensess[0]);
            return null;
        }
    }

    public void delete(VariableExpenses variableExpenses) {
        new DeleteAsyncTask(mVariableExpensesDao).execute(variableExpenses);
    }

    class DeleteAsyncTask extends AsyncTask<VariableExpenses, Void, Void> {
        private VariableExpensesDao mVariableExpensesDao;

        public DeleteAsyncTask(VariableExpensesDao variableExpensesDao) {
            this.mVariableExpensesDao = variableExpensesDao;
        }

        @Override
        protected Void doInBackground(VariableExpenses... variableExpensess) {
            mVariableExpensesDao.delete(variableExpensess[0]);
            return null;
        }
    }

    public LiveData<String> getTotalVariableExpensesAlert() {
        return Transformations.map(sumByTypeOfVariableExpenses(), statisticList -> {
            if (statisticList == null || statisticList.isEmpty()) {
                return null;
            }

            float totalVariableExpenses = 0;
            for (StatisticTypeOfVariableExpenses statistic : statisticList) {
                totalVariableExpenses += statistic.getTotal();
            }

            if (totalVariableExpenses > 300) {
                return "Warning: Total Variable Expenses exceeds the limit of 300!";
            }
            return null;
        });
    }
}
