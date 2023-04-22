package com.example.straydogsdev;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SearchDogActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseDogs;
    private Map<String, Marker> dogMarkers;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dog);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchEditText = findViewById(R.id.searchEditText);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().toLowerCase().trim();
                for (Marker marker : dogMarkers.values()) {
                    StrayDogData dog = (StrayDogData) marker.getTag();
                    if (dog != null) {
                        boolean isVisible = dog.getName().toLowerCase().contains(searchText);
                        marker.setVisible(isVisible);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dogMarkers = new HashMap<>();
        databaseDogs = FirebaseDatabase.getInstance().getReference("dogs");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new InfoWindowAdapter(SearchDogActivity.this));

        databaseDogs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dogSnapshot : dataSnapshot.getChildren()) {
                    StrayDogData dog = dogSnapshot.getValue(StrayDogData.class);
                    if (dog != null) {
                        LatLng dogLocation = new LatLng(dog.getLatitude(), dog.getLongitude());
                        Marker marker = mMap.addMarker(new MarkerOptions().position(dogLocation).title(dog.getName()));
                        marker.setTag(dog);
                        dogMarkers.put(dog.getId(), marker);
                    }
                }

                // Set the initial map camera position
                if (!dogMarkers.isEmpty()) {
                    LatLng firstDogPosition = dogMarkers.values().iterator().next().getPosition();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstDogPosition, 12));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
            }
        });
    }

}
