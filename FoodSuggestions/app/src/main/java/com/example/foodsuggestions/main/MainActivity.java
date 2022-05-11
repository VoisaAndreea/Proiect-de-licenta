package com.example.foodsuggestions.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foodsuggestions.firebase.LoginActivity;
import com.example.foodsuggestions.R;

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
        Intent intent =new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}