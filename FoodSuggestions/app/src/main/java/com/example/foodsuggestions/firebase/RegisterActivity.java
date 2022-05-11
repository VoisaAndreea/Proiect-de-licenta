package com.example.foodsuggestions.firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodsuggestions.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, emailR, password, confPassword;
    Button registerButton;
    TextView loginLink;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://registerandloginproject-default-rtdb.europe-west1.firebasedatabase.app");
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.idRName);
        emailR = findViewById(R.id.idREmail);
        password = findViewById(R.id.idRPassword);
        confPassword = findViewById(R.id.idRConfPassword);
        loginLink = findViewById(R.id.idLoginBtn);
        registerButton = findViewById(R.id.idRegisterButton);

        loginLink.setOnClickListener(this);
        registerButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.idLoginBtn):
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;

            case (R.id.idRegisterButton):
                String fullName = name.getText().toString();
                String emailRegister = emailR.getText().toString();
                String passwordRegister = password.getText().toString();
                String confirmPassword = confPassword.getText().toString();
                Log.d(RegisterActivity.class.getSimpleName(), "VALOAREEE: " + fullName);

                if(fullName.isEmpty() || emailRegister.isEmpty() || passwordRegister.isEmpty()
                        || confirmPassword.isEmpty()){

                    Toast.makeText(RegisterActivity.this, "Please fill all fields",
                                    Toast.LENGTH_SHORT).show();
                }
                else if(!passwordRegister.equals(confirmPassword)){
                    Toast.makeText(RegisterActivity.this, "Passwords are not matching",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Se va verifica daca emailul a mai fost inregistrat

                            if(snapshot.hasChild(emailRegister)){
                                Toast.makeText(RegisterActivity.this,
                                                "Email is already registered",
                                                Toast.LENGTH_SHORT).show();
                            }else{
                                //Se vor trimite datele in firebase Realtime Database
                                //Se va folosii emailul ca identificare unica pentru fiecare user
                                databaseReference.child("users").child(emailRegister)
                                        .child("fullname").setValue(fullName);
                                databaseReference.child("users").child(emailRegister)
                                                 .child("password").setValue(passwordRegister);

                                Toast.makeText(RegisterActivity.this, "User registered successfully",
                                        Toast.LENGTH_SHORT).show();
                                Log.d("TAG","AAAAAAAAAAAA: " + passwordRegister);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(RegisterActivity.class.getSimpleName(), "Failed to read value. ", error.toException());
                        }
                    });

                }

        }
    }
}