package com.example.straydogsdev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etRePassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    //TODO: Implement registration logic here
                } else {
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
