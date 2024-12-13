package com.example.financial1.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.financial1.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    // Define an Executor to handle background tasks
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    private static volatile UserDatabase INSTANCE;

    public static UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UserDatabase.class, "expense_management_db")
                            .fallbackToDestructiveMigration()  // Xử lý khi có thay đổi schema
                            .build();

                }
            }
        }
        return INSTANCE;
    }


}
