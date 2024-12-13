package com.example.financial1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financial1.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    LiveData<User> login(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    LiveData<User> checkIfUserExists(String username);

    // Phương thức kiểm tra xem username có tồn tại hay không
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE username = :username LIMIT 1)")
    LiveData<Boolean> checkUsernameExists(String username);


    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    LiveData<User> getUserByUsername(String username);
}
