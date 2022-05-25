package com.example.foodsuggestions.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.databinding.ActivityRegisterBinding;
import com.example.foodsuggestions.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.idLoginBtn.setOnClickListener(this);
        binding.idRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.idLoginBtn):
                finish();
                break;
            case (R.id.idRegisterButton):
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String fullName = binding.idRName.getText().toString().trim();
        String emailRegister = binding.idREmail.getText().toString().trim();
        String passwordRegister = binding.idRPassword.getText().toString().trim();
        String confirmPassword = binding.idRConfPassword.getText().toString().trim();

        if(fullName.isEmpty()){
            binding.idRName.setError("Full name is required!");
            binding.idRName.requestFocus();
            return;
        }
        if(emailRegister.isEmpty()){
            binding.idREmail.setError("Email is required!");
            binding.idREmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailRegister).matches()){
            binding.idREmail.setError("Please provide valid email");
            binding.idREmail.requestFocus();
            return;
        }
        if(passwordRegister.isEmpty()){
            binding.idRPassword.setError("Password is required!");
            binding.idRPassword.requestFocus();
            return;
        }
        if(passwordRegister.length() < 6){
            binding.idRPassword.setError("Min password length should be 6 characters!");
            binding.idRPassword.requestFocus();
            return;
        }
        if(!passwordRegister.equals(confirmPassword)) {
            binding.idRConfPassword.setError("Passwords are not matching");
            binding.idRConfPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailRegister, passwordRegister)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName, emailRegister);
                            FirebaseDatabase
                                    .getInstance("https://registerandloginproject-default-rtdb.europe-west1.firebasedatabase.app/")
                                    .getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        binding.idRName.getText().clear();
                                        binding.idREmail.getText().clear();
                                        binding.idRPassword.getText().clear();
                                        binding.idRConfPassword.getText().clear();

                                        Toast.makeText(
                                                RegisterActivity.this,
                                                "User has been registered successfully!",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    }else{
                                        Toast.makeText(
                                                RegisterActivity.this,
                                                "Failed to register! Try again!",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(
                                    RegisterActivity.this,
                                    "Failed to register",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }
}