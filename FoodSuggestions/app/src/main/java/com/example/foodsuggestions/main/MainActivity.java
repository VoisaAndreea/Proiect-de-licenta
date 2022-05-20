package com.example.foodsuggestions.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodsuggestions.R;
import com.example.foodsuggestions.firebase.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.bShow);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent =new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent =new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}