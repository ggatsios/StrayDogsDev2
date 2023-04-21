package com.example.straydogsdev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ReportDogActivity extends AppCompatActivity {

    private EditText editTextDogName;
    private EditText editTextDogLocation;
    private EditText editTextDogColor;
    private EditText editTextDogBreed;
    private String selectedGender;
    private EditText editTextDogDescription;
    private Button buttonReportDog;
    private Button buttonUploadPhoto;

    private Uri selectedImageUri;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private LatLng locationLatLng;
    private ActivityResultLauncher<Intent> placeResultLauncher;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dog);

        editTextDogName = findViewById(R.id.editTextDogName);
        editTextDogLocation = findViewById(R.id.editTextDogLocation);
        editTextDogColor = findViewById(R.id.editTextDogColor);
        editTextDogBreed = findViewById(R.id.editTextDogBreed);
        RadioGroup genderRadioGroup = findViewById(R.id.gender_radio_group);
        editTextDogDescription = findViewById(R.id.editTextDogDescription);
        buttonReportDog = findViewById(R.id.buttonReportDog);
        buttonUploadPhoto = findViewById(R.id.btnPhoto);

        editTextDogLocation.setInputType(InputType.TYPE_NULL);

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male_radio_button) {
                    selectedGender = "Male";
                } else if (checkedId == R.id.female_radio_button) {
                    selectedGender = "Female";
                } else {
                    selectedGender = "";
                }
                // Use the selectedGender variable for your app's logic
            }
        });


        placeResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(result.getData());
                editTextDogLocation.setText(place.getAddress());
                locationLatLng = place.getLatLng();
                if (locationLatLng != null) {
                    latitude = locationLatLng.latitude;
                    longitude = locationLatLng.longitude;
                }
            } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(result.getData());
                Toast.makeText(ReportDogActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        editTextDogLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutocompleteActivity();
            }
        });

        buttonReportDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextDogName.getText().toString().trim();
                String location = editTextDogLocation.getText().toString().trim();
                String color = editTextDogColor.getText().toString().trim();
                String breed = editTextDogBreed.getText().toString().trim();
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
                            if (id != null) {
                                StrayDogData dog = new StrayDogData(id, name, location, color, breed, selectedGender, description, downloadUri.toString(), latitude, longitude);
                                databaseDogs.child(id).setValue(dog);
                                Toast.makeText(ReportDogActivity.this, "Dog reported successfully", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                // Handle failures
                                Toast.makeText(ReportDogActivity.this, "Error generating dog ID, please try again", Toast.LENGTH_LONG).show();
                            }
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

    private void openAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(ReportDogActivity.this);
        placeResultLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
        }

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = (Place) Autocomplete.getPlaceFromIntent(data);
                editTextDogLocation.setText(place.getAddress());
                locationLatLng = place.getLatLng();
                if (locationLatLng != null) {
                    latitude = locationLatLng.latitude;
                    longitude = locationLatLng.longitude;
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(ReportDogActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}


