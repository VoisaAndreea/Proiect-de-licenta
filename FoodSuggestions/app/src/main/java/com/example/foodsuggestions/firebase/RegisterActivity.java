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
import com.example.foodsuggestions.databinding.ActivityRegisterBinding;
import com.example.foodsuggestions.viewmodels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setLifecycleOwner(this);

        RegisterViewModel registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setRegisterViewModel(registerViewModel);

        registerViewModel.getNavigateToLoginEvent().observe(this,
                unit -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class))
        );

        registerViewModel.getFullNameError().observe(this, error -> {
            if (error) {
                binding.idRName.setError(getString(R.string.fullNameErrorMessage));
            } else {
                binding.idRName.setError(null);
            }
        });

        registerViewModel.getMailError().observe(this, error -> {
            if (error) {
                binding.idREmail.setError(getString(R.string.mailErrorMassage));
            } else {
                binding.idREmail.setError(null);
            }
        });

        registerViewModel.getPasswordError().observe(this, error -> {
            if (error) {
                binding.idRPassword.setError(getString(R.string.passwordErrorMassage));
            } else {
                binding.idRPassword.setError(null);
            }
        });

        registerViewModel.getConfirmPasswordError().observe(this, error -> {
            if (error) {
                binding.idRConfPassword.setError(getString(R.string.confirmPasswordErrorMessage));
            } else {
                binding.idRConfPassword.setError(null);
            }
        });

        binding.idRName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.getFullNameError().postValue(false);
            }
        });

        binding.idREmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.getMailError().postValue(false);
            }
        });

        binding.idRPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.getPasswordError().postValue(false);
            }
        });

        binding.idRConfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.getConfirmPasswordError().postValue(false);
            }
        });

        registerViewModel.getStatus().observe(this, status -> {
            if ( status != null) {
                registerViewModel.getStatus().postValue(null);
                Toast.makeText(this, getString(R.string.registerMessage), Toast.LENGTH_SHORT).show();
            }
        });
    }
}