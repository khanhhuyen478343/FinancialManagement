package com.example.financial1.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.financial1.dao.UserDao;
import com.example.financial1.dao.UserDatabase;
import com.example.financial1.entity.User;

public class UserRepository {
    private UserDao userDao;
    private MutableLiveData<String> errorLiveData;  // Dùng để thông báo lỗi

    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        userDao = db.userDao();
        errorLiveData = new MutableLiveData<>();
    }

    // Thêm phương thức để lấy User theo username
    public LiveData<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    // Phương thức login, không kiểm tra trực tiếp giá trị của LiveData
    public LiveData<User> login(String username, String password) {
        // Trả về LiveData từ dao, lỗi sẽ được thông báo khi observe
        return userDao.login(username, password);
    }

    // Phương thức kiểm tra nếu người dùng tồn tại
    public LiveData<User> checkIfUserExists(String username) {
        return userDao.checkIfUserExists(username);
    }

    // Phương thức thêm người dùng, xử lý lỗi nếu không thành công
    public void insert(User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> {
            try {
                userDao.insert(user);
            } catch (Exception e) {
                errorLiveData.postValue("Lỗi khi thêm người dùng: " + e.getMessage());
            }
        });
    }

    // Phương thức trả về LiveData lỗi để theo dõi
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}
