package com.example.straydogsdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;

public class MainMenuActivity extends AppCompatActivity {

    private Button btnReportDog, btnFindDogs, btnLogout;
    private TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String apiKey = "AIzaSyCWMYdlULQUXLukj5ubeIBFXurTzQtY_Oo";
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), apiKey);
        }
        // Get the user's name from the intent
        String username = getIntent().getStringExtra("USERNAME");

        // Initialize the UI components
        txtWelcome = findViewById(R.id.txtWelcome);
        btnReportDog = findViewById(R.id.btnReportDog);
        btnFindDogs = findViewById(R.id.btnFindDogs);
        btnLogout = findViewById(R.id.btnLogout);

//         Set the welcome message with the user's name
        String welcomeMessage = "Welcome to StrayDog !";
        txtWelcome.setText(welcomeMessage);

        // Set the click listeners for the buttons
        btnReportDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, ReportDogActivity.class);
                startActivity(intent);
            }
        });

        btnFindDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SearchDogActivity.class);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform logout functionality
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
