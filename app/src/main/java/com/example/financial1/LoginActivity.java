package com.example.financial1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.financial1.entity.User;
import com.example.financial1.ui.User.UserViewModel;
import com.example.financial1.utils.PasswordUtil;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        findViewById(R.id.btLogin).setOnClickListener(view -> {
            String userName = ((EditText) findViewById(R.id.etUserName)).getText().toString().trim();
            String password = ((EditText) findViewById(R.id.etPassword)).getText().toString().trim();

            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            userViewModel.getUserByUsername(userName).observe(this, user -> {
                if (user != null) {
                    Log.d("Login", "User found: " + user.getUsername());

                    byte[] salt = user.getSalt();
                    String hashedInputPassword = PasswordUtil.hashPassword(password, salt);

                    if (hashedInputPassword.equals(user.getPassword())) {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                        Log.d("Login", "Login status saved: " + isLoggedIn);

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d("Login", "Invalid password");
                        Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Login", "User not found");
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            });
        });

        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
