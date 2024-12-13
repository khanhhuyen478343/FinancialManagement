package com.example.financial1.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.financial1.entity.RecurringExpense;
import com.example.financial1.entity.Revenue;
import com.example.financial1.entity.TypeOfRevenue;

import com.example.financial1.entity.VariableExpenses;
import com.example.financial1.entity.TypeOfVariableExpenses;
import com.example.financial1.utils.Converters;

import java.util.Date;

@Database(entities = {TypeOfRevenue.class, Revenue.class, VariableExpenses.class, TypeOfVariableExpenses.class, RecurringExpense.class}, version = 6)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TypeOfRevenueDao typeOfRevenueDao();
    public abstract RevenueDao revenueDao();
    public abstract VariableExpensesDao variableExpensesDao();
    public abstract TypeOfVariableExpensesDao typeOfVariableExpensesDao();
    public abstract RecurringExpenseDao recurringExpenseDao();

    public static AppDatabase INSTANCE;

    // Phương thức để lấy instance của AppDatabase (singleton)
    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "expense_management_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateData(INSTANCE).execute();
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "expense_management_db")
                        .fallbackToDestructiveMigration()
                        .addCallback(callback)
                        .build();
            }
        }
        return INSTANCE;
    }

    public static class PopulateData extends AsyncTask<Void, Void, Void> {
        private TypeOfRevenueDao typeOfRevenueDao;
        private RevenueDao revenueDao;
        private VariableExpensesDao variableExpensesDao;
        private TypeOfVariableExpensesDao typeOfVariableExpensesDao;
        private RecurringExpenseDao recurringExpenseDao;


        public PopulateData(AppDatabase db) {
            typeOfRevenueDao = db.typeOfRevenueDao();
            revenueDao = db.revenueDao();
            variableExpensesDao = db.variableExpensesDao();
            typeOfVariableExpensesDao = db.typeOfVariableExpensesDao();
            recurringExpenseDao = db.recurringExpenseDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            // Thêm dữ liệu cho TypeOfRevenue
            String[] typeofrevenues = new String[]{"Salary", "Bonus", "Share"};
            for (String it : typeofrevenues) {
                TypeOfRevenue tor = new TypeOfRevenue();
                tor.name = it;
                typeOfRevenueDao.insert(tor);
            }

            // Thêm dữ liệu cho Revenue
            // Thêm dữ liệu cho Revenue với date
            Revenue revenue = new Revenue();
            revenue.name = "January 1 salary";
            revenue.amountofmoney = 20;
            revenue.rid = 2;
            revenue.note = "Received on time";
            revenue.date = "01/01/2024"; // Thêm ngày
            revenueDao.insert(revenue);

            // Thêm dữ liệu cho VariableExpenses với date
            VariableExpenses spending = new VariableExpenses();
            spending.name = "Rent";
            spending.amountofmoney = 500;
            spending.toeid = 1;
            spending.note = "Monthly rent";
            spending.date = "01/12/2024"; // Ví dụ ngày
            variableExpensesDao.insert(spending);

            // Thêm dữ liệu cho TypeOfVariableExpenses
            String[] typeofvariableExpenses = new String[]{"Housing", "Food", "Transportation"};
            for (String it : typeofvariableExpenses) {
                TypeOfVariableExpenses toe = new TypeOfVariableExpenses();
                toe.name = it;
                typeOfVariableExpensesDao.insert(toe);
            }
            RecurringExpense recurringExpense = new RecurringExpense();
            recurringExpense.name = "Monthly Rent";
            recurringExpense.amount = 500;
            recurringExpense.startDate = new Date(2024, 0, 1);  // Sử dụng Date constructor để tạo ngày
            recurringExpense.endDate = new Date(2025, 0, 1);    // Cũng sử dụng Date constructor cho ngày kết thúc
            recurringExpense.recurrenceType = 1;  // Ví dụ: 1 - Lặp lại theo tháng (có thể thêm logic chi tiết)
            recurringExpenseDao.insert(recurringExpense);


            return null;
        }
    }
}
