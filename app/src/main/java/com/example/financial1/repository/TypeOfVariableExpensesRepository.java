package com.example.financial1.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.financial1.dao.AppDatabase;
import com.example.financial1.dao.TypeOfVariableExpensesDao;
import com.example.financial1.entity.TypeOfVariableExpenses;

import java.util.List;

public class TypeOfVariableExpensesRepository {
    private TypeOfVariableExpensesDao mTypeOfVariableExpensesDao;
    private LiveData<List<TypeOfVariableExpenses>> mAllTypeOfVariableExpenses;

    public TypeOfVariableExpensesRepository(Application application) {
        this.mTypeOfVariableExpensesDao = AppDatabase.getDatabase(application).typeOfVariableExpensesDao();
        mAllTypeOfVariableExpenses = mTypeOfVariableExpensesDao.findAll();
    }

    public LiveData<List<TypeOfVariableExpenses>> getAllTypeOfVariableExpenses() {
        return mAllTypeOfVariableExpenses;
    }
///////////////////////////////////////////////////////////////////////////////////////////
    public void update(TypeOfVariableExpenses typeOfVariableExpenses){
        new UpdateAsyncTask(mTypeOfVariableExpensesDao).execute(typeOfVariableExpenses);
    }

    class UpdateAsyncTask extends AsyncTask<TypeOfVariableExpenses, Void, Void>{
        private TypeOfVariableExpensesDao mTypeOfVariableExpensesDao;
        public UpdateAsyncTask(TypeOfVariableExpensesDao typeOfVariableExpensesDao){
            this.mTypeOfVariableExpensesDao = typeOfVariableExpensesDao;
        }

        @Override
        protected Void doInBackground(TypeOfVariableExpenses... typeOfVariableExpensess) {
            mTypeOfVariableExpensesDao.update(typeOfVariableExpensess[0]);
            return null;
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    public void insert(TypeOfVariableExpenses typeOfVariableExpenses){
        new InsertAsyncTask(mTypeOfVariableExpensesDao).execute(typeOfVariableExpenses);
    }
    class InsertAsyncTask extends AsyncTask<TypeOfVariableExpenses, Void, Void>{
        private TypeOfVariableExpensesDao mTypeOfVariableExpensesDao;
        public InsertAsyncTask(TypeOfVariableExpensesDao typeOfVariableExpensesDao){
            this.mTypeOfVariableExpensesDao = typeOfVariableExpensesDao;
        }
        @Override
        protected Void doInBackground(TypeOfVariableExpenses... typeOfVariableExpensess) {
            mTypeOfVariableExpensesDao.insert(typeOfVariableExpensess[0]);
            return null;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    public void delete(TypeOfVariableExpenses typeOfVariableExpenses){
        new DeleteAsyncTask(mTypeOfVariableExpensesDao).execute(typeOfVariableExpenses);
    }
    class DeleteAsyncTask extends AsyncTask<TypeOfVariableExpenses, Void, Void>{
        private TypeOfVariableExpensesDao mTypeOfVariableExpensesDao;
        public DeleteAsyncTask(TypeOfVariableExpensesDao typeOfVariableExpensesDao){
            this.mTypeOfVariableExpensesDao = typeOfVariableExpensesDao;
        }

        @Override
        protected Void doInBackground(TypeOfVariableExpenses... typeOfVariableExpensess) {
            mTypeOfVariableExpensesDao.delete(typeOfVariableExpensess[0]);
            return null;
        }
    }
}
