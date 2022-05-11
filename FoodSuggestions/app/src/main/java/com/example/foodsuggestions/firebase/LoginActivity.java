package com.example.foodsuggestions.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.main.RecipesActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView registerLink;
    EditText email, password;
    Button loginButton;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://registerandloginproject-default-rtdb.europe-west1.firebasedatabase.app")
                                                          .getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.idLEmail);
        password = findViewById(R.id.idLPassword);
        registerLink = findViewById(R.id.idRegisterBtn);
        loginButton = findViewById(R.id.idLoginButton);

        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case(R.id.idRegisterBtn):
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case(R.id.idLoginButton):
                String emailLogin = email.getText().toString();
                String passwordLogin = password.getText().toString();

                if(emailLogin.isEmpty() || passwordLogin.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter your email or password",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //Se va verifica daca email-ul exista in baza de date sau nu
                            if(snapshot.hasChild(emailLogin)){
                                final String getPassword = snapshot.child(emailLogin)
                                                    .child("password").getValue(String.class);

                                if(getPassword.equals(passwordLogin)){
                                    Toast.makeText(LoginActivity.this,
                                            "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, RecipesActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this,
                                            "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this,
                                        "E-mail is not registered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                break;

        }

    }
}