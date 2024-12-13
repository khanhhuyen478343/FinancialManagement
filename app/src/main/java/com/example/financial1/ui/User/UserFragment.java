package com.example.financial1.ui.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financial1.R;
import com.example.financial1.entity.User;
import com.example.financial1.ui.User.UserViewModel;
import com.example.financial1.MainActivity;

public class UserFragment extends Fragment {
    private UserViewModel userViewModel;
    private EditText etUsername, etPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.layout_login, container, false);

        etUsername = rootView.findViewById(R.id.etUserName);
        etPassword = rootView.findViewById(R.id.etPassword);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        rootView.findViewById(R.id.btLogin).setOnClickListener(v -> loginUser());

        return rootView;
    }

    private void loginUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        userViewModel.login(username, password).observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // Thành công, chuyển sang màn hình chính
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
