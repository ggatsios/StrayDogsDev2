package com.example.straydogsdev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View infoWindowView;
    private final Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        infoWindowView = inflater.inflate(R.layout.info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        StrayDogData dog = (StrayDogData) marker.getTag();
        if (dog == null) {
            return null;
        }


        ImageView dogImageView = infoWindowView.findViewById(R.id.dog_image);
        TextView dogNameTextView = infoWindowView.findViewById(R.id.dog_name);
        TextView dogGenderTextView = infoWindowView.findViewById(R.id.dog_gender);
        TextView dogDescriptionTextView = infoWindowView.findViewById(R.id.dog_description);

        Picasso.get().load(dog.getPhotoUrl()).into(dogImageView);
        dogNameTextView.setText(dog.getName());
        dogGenderTextView.setText(dog.getGender());
        dogDescriptionTextView.setText(dog.getDescription());

        return infoWindowView;
    }
}

