package com.example.straydogsdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    private Button btnReportDog, btnViewDogs, btnViewMap, btnLogout;
    private TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Get the user's name from the intent
        String username = getIntent().getStringExtra("USERNAME");

        // Initialize the UI components
        txtWelcome = findViewById(R.id.txtWelcome);
        btnReportDog = findViewById(R.id.btnReportDog);
        btnViewDogs = findViewById(R.id.btnViewDogs);
        btnViewMap = findViewById(R.id.btnViewMap);
        btnLogout = findViewById(R.id.btnLogout);

        // Set the welcome message with the user's name
        String welcomeMessage = "Welcome, " + username + "!";
        txtWelcome.setText(welcomeMessage);

        // Set the click listeners for the buttons
//        btnReportDog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainMenuActivity.this, ReportDogActivity.class);
//                startActivity(intent);
//            }
//        });

        btnViewDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, StrayDogData.class);
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
