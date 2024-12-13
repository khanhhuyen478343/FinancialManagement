package com.example.financial1.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.financial1.dao.AppDatabase;
import com.example.financial1.dao.TypeOfRevenueDao;
import com.example.financial1.entity.TypeOfRevenue;

import java.util.List;

public class TypeOfRevenueRepository {
    private TypeOfRevenueDao mTypeOfRevenueDao;
    private LiveData<List<TypeOfRevenue>> mAllTypeOfRevenue;

    public TypeOfRevenueRepository(Application application) {
        this.mTypeOfRevenueDao = AppDatabase.getDatabase(application).typeOfRevenueDao();
        mAllTypeOfRevenue = mTypeOfRevenueDao.findAll();
    }

    public LiveData<List<TypeOfRevenue>> getAllTypeOfRevenue() {
        return mAllTypeOfRevenue;
    }

    public void update(TypeOfRevenue typeOfRevenue){
        new UpdateAsyncTask(mTypeOfRevenueDao).execute(typeOfRevenue);
    }

    class UpdateAsyncTask extends AsyncTask<TypeOfRevenue, Void, Void>{
        private TypeOfRevenueDao mTypeOfRevenueDao;
        public UpdateAsyncTask(TypeOfRevenueDao typeOfRevenueDao){
            this.mTypeOfRevenueDao = typeOfRevenueDao;
        }

        @Override
        protected Void doInBackground(TypeOfRevenue... typeOfRevenues) {
            mTypeOfRevenueDao.update(typeOfRevenues[0]);
            return null;
        }
    }

    public void insert(TypeOfRevenue typeOfRevenue){
        new InsertAsyncTask(mTypeOfRevenueDao).execute(typeOfRevenue);
    }
    class InsertAsyncTask extends AsyncTask<TypeOfRevenue, Void, Void>{
        private TypeOfRevenueDao mTypeOfRevenueDao;
        public InsertAsyncTask(TypeOfRevenueDao typeOfRevenueDao){
            this.mTypeOfRevenueDao = typeOfRevenueDao;
        }
        @Override
        protected Void doInBackground(TypeOfRevenue... typeOfRevenues) {
            mTypeOfRevenueDao.insert(typeOfRevenues[0]);
            return null;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    public void delete(TypeOfRevenue typeOfRevenue){
        new DeleteAsyncTask(mTypeOfRevenueDao).execute(typeOfRevenue);
    }
    class DeleteAsyncTask extends AsyncTask<TypeOfRevenue, Void, Void>{
        private TypeOfRevenueDao mTypeOfRevenueDao;
        public DeleteAsyncTask(TypeOfRevenueDao typeOfRevenueDao){
            this.mTypeOfRevenueDao = typeOfRevenueDao;
        }

        @Override
        protected Void doInBackground(TypeOfRevenue... typeOfRevenues) {
            mTypeOfRevenueDao.delete(typeOfRevenues[0]);
            return null;
        }
    }
}
