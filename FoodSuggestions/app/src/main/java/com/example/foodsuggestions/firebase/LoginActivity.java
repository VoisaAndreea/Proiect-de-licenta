package com.example.foodsuggestions.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

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
        binding.setLifecycleOwner(this);

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

        loginViewModel.getMailError().observe(this, error -> {
            if (error) {
                binding.idLEmail.setError(getString(R.string.mailErrorMassage));
            } else {
                binding.idLEmail.setError(null);
            }
        });

        loginViewModel.getPasswordError().observe(this, error -> {
            if (error) {
                binding.idLPassword.setError(getString(R.string.passwordErrorMassage));
            } else {
                binding.idLPassword.setError(null);
            }
        });

        binding.idLEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.getMailError().postValue(false);
            }
        });

        binding.idLPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.getPasswordError().postValue(false);
            }
        });

        loginViewModel.getStatus().observe(this, status -> {
            if ( status != null) {
                loginViewModel.getStatus().postValue(null);
                Toast.makeText(this, getString(R.string.loginMessage), Toast.LENGTH_SHORT).show();
            }
        });
    }
}