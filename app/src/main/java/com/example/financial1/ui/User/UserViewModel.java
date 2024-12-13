package com.example.financial1.ui.User;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financial1.entity.User;
import com.example.financial1.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<User> login(String username, String password) {
        return userRepository.login(username, password);
    }

    public LiveData<User> checkIfUserExists(String username) {
        return userRepository.checkIfUserExists(username);
    }

    // Trong UserViewModel:
    public LiveData<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }


    public void insert(User user) {
        userRepository.insert(user);
    }


}
