package com.example.straydogsdev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ReportDogActivity extends AppCompatActivity {

    private EditText editTextDogName;
    private EditText editTextDogLocation;
    private EditText editTextDogColor;
    private EditText editTextDogBreed;
    private EditText editTextDogGender;
    private EditText editTextDogDescription;
    private Button buttonReportDog;
    private Button buttonUploadPhoto;

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dog);

        editTextDogName = findViewById(R.id.editTextDogName);
        editTextDogLocation = findViewById(R.id.editTextDogLocation);
        editTextDogColor = findViewById(R.id.editTextDogColor);
        editTextDogBreed = findViewById(R.id.editTextDogBreed);
        editTextDogGender = findViewById(R.id.editTextDogGender);
        editTextDogDescription = findViewById(R.id.editTextDogDescription);
        buttonReportDog = findViewById(R.id.buttonReportDog);
        buttonUploadPhoto = findViewById(R.id.btnPhoto);

        buttonReportDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextDogName.getText().toString().trim();
                String location = editTextDogLocation.getText().toString().trim();
                String color = editTextDogColor.getText().toString().trim();
                String breed = editTextDogBreed.getText().toString().trim();
                String gender = editTextDogGender.getText().toString().trim();
                String description = editTextDogDescription.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    editTextDogName.setError("Please enter the dog's name");
                    editTextDogName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(location)) {
                    editTextDogLocation.setError("Please enter the location");
                    editTextDogLocation.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(color)) {
                    editTextDogColor.setError("Please enter the dog's color");
                    editTextDogColor.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(breed)) {
                    editTextDogBreed.setError("Please enter the dog's breed");
                    editTextDogBreed.requestFocus();
                    return;
                }

                if (selectedImageUri == null) {
                    Toast.makeText(ReportDogActivity.this, "Please select a photo", Toast.LENGTH_SHORT).show();
                    return;
                }

                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("dog_photos/" + UUID.randomUUID().toString());
                UploadTask uploadTask = storageRef.putFile(selectedImageUri);

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return storageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            // Save the photo's download URL to the database
                            DatabaseReference databaseDogs = FirebaseDatabase.getInstance().getReference("dogs");
                            String id = databaseDogs.push().getKey();
                            StrayDogData dog = new StrayDogData(id, name, location, color, breed, gender, description, downloadUri.toString());
                            databaseDogs.child(id).setValue(dog);

                            Toast.makeText(ReportDogActivity.this, "Dog reported successfully", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            // Handle failures
                        }
                    }
                });
            }
        });

        buttonUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
        }
    }
}

