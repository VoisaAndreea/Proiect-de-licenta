package com.example.foodsuggestions.firebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.databinding.ActivityLoginBinding;
import com.example.foodsuggestions.main.HomeActivity;
import com.example.foodsuggestions.viewmodels.LoginViewModel;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);
        loginViewModel.getNavigateToRegisterEvent().observe(
                this,
                unit -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
        loginViewModel.getNavigateToHomeEvent().observe(this, unit -> {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        });
    }
}