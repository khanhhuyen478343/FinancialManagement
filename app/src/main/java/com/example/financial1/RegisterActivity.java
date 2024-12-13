package com.example.financial1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.financial1.entity.User;
import com.example.financial1.ui.User.UserViewModel;
import com.example.financial1.utils.PasswordUtil;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etPhoneNumber, etIdCard;
    private UserViewModel userViewModel;
    private RadioGroup rdSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        rdSex = findViewById(R.id.rdSex);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etIdCard = findViewById(R.id.etIdCard);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        findViewById(R.id.btAccept).setOnClickListener(v -> registerUser());

        TextView tvAlreadyHaveAccount = findViewById(R.id.tvAlreadyHaveAccount);
        tvAlreadyHaveAccount.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        int selectedSexId = rdSex.getCheckedRadioButtonId();
        RadioButton selectedSexButton = findViewById(selectedSexId);
        String sex = selectedSexButton != null ? selectedSexButton.getText().toString() : "";

        String phoneNumber = etPhoneNumber.getText().toString();
        String idCard = etIdCard.getText().toString();

        if (username.isEmpty() || password.isEmpty() || sex.isEmpty() || phoneNumber.isEmpty() || idCard.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!username.matches("^(?=.*\\d)(?=.*[@#$%^&+=!]).+$")) {
            Toast.makeText(this, "Username must include at least 1 special character and 1 digit", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phoneNumber.matches("\\d{10}")) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        byte[] salt = PasswordUtil.getSalt();
        String hashedPassword = PasswordUtil.hashPassword(password, salt);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        user.setSex(sex);
        user.setPhoneNumber(phoneNumber);
        user.setIdCard(idCard);

        userViewModel.insert(user);
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
