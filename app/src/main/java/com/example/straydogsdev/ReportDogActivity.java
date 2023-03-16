package com.example.straydogsdev;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportDogActivity extends AppCompatActivity {

    private EditText editTextDogName;
    private EditText editTextDogLocation;
    private EditText editTextDogColor;
    private EditText editTextDogBreed;
    private EditText editTextDogGender;

    private EditText editTextDogDescription;
    private Button buttonReportDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dog);

        editTextDogName = findViewById(R.id.editTextDogName);
        editTextDogLocation = findViewById(R.id.editTextDogLocation); // Fix error, probably needs to be called differently
        editTextDogColor = findViewById(R.id.editTextDogColor);
        editTextDogBreed = findViewById(R.id.editTextDogBreed);
        editTextDogGender = findViewById(R.id.editTextDogGender);
        editTextDogDescription = findViewById(R.id.editTextDogDescription);
        buttonReportDog = findViewById(R.id.buttonReportDog);

        buttonReportDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextDogName.getText().toString().trim();
                String color = editTextDogColor.getText().toString().trim();
                String breed = editTextDogBreed.getText().toString().trim();
                String gender = editTextDogGender.getText().toString().trim();
                String description = editTextDogDescription.getText().toString().trim();
                String location = editTextDogLocation.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    editTextDogName.setError("Please enter the dog's name");
                    editTextDogName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(breed)) {
                    editTextDogBreed.setError("Please enter the dog's breed");
                    editTextDogBreed.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(color)) {
                    editTextDogColor.setError("Please enter the dog's color");
                    editTextDogColor.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(location)) {
                    editTextDogLocation.setError("Please enter the location");
                    editTextDogLocation.requestFocus();
                    return;
                }

                DatabaseReference databaseDogs = FirebaseDatabase.getInstance().getReference("dogs");
                String id = databaseDogs.push().getKey();
                StrayDogData dog = new StrayDogData(id, location, name, color, breed, gender, description);
                databaseDogs.child(id).setValue(dog);

                Toast.makeText(ReportDogActivity.this, "Dog reported successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}

