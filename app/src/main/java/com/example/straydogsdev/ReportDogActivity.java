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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.google.android.libraries.places.api.model.Place.Field;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        editTextDogGender = findViewById(R.id.editTextDogGender);
        editTextDogDescription = findViewById(R.id.editTextDogDescription);
        buttonReportDog = findViewById(R.id.buttonReportDog);
        buttonUploadPhoto = findViewById(R.id.btnPhoto);

        editTextDogLocation.setInputType(InputType.TYPE_NULL);

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

        private void openAutocompleteActivity() {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(ReportDogActivity.this);
            placeResultLauncher.launch(intent);
        }


        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
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
                    Toast.makeText(this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


