package com.example.foodsuggestions.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.databinding.ActivityLoginBinding;
import com.example.foodsuggestions.main.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.idLoginButton.setOnClickListener(this);
        binding.idRegisterBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.idRegisterBtn):
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case(R.id.idLoginButton):
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String emailLogin = binding.idLEmail.getText().toString().trim();
        String passwordLogin = binding.idLPassword.getText().toString().trim();

        if(emailLogin.isEmpty()){
            binding.idLEmail.setError("Email is required!");
            binding.idLEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailLogin).matches()){
            binding.idLEmail.setError("Please provide valid email");
            binding.idLEmail.requestFocus();
            return;
        }
        if(passwordLogin.isEmpty()){
            binding.idLPassword.setError("Email is required!");
            binding.idLPassword.requestFocus();
            return;
        }
        if(passwordLogin.length() < 6){
            binding.idLPassword.setError("Min password length should be 6 characters!");
            binding.idLPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    binding.idLEmail.getText().clear();
                    binding.idLPassword.getText().clear();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }else{
                    Toast.makeText(
                            LoginActivity.this,
                            "Filed to login! Please check your credentials or register.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}