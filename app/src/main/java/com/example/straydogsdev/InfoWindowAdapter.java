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

        View view = LayoutInflater.from(context).inflate(R.layout.info_window, null);

        ImageView dogImageView = view.findViewById(R.id.dogImageView);
        TextView dogNameTextView = view.findViewById(R.id.dog_name);
        TextView dogGenderTextView = view.findViewById(R.id.dog_gender);
        TextView dogDescriptionTextView = view.findViewById(R.id.dog_description);

        if (dog.getPhotoUrl() != null && !dog.getPhotoUrl().isEmpty()) {
            MarkerImageTarget target = new MarkerImageTarget(marker, dogImageView);
            Picasso.get()
                    .load(dog.getPhotoUrl())
                    .resize(100, 100)
                    .centerCrop()
                    .into(target);
        } else {
            dogImageView.setImageResource(R.drawable.default_dog_image);
        }

        dogNameTextView.setText(context.getString(R.string.dog_name, dog.getName()));
        dogGenderTextView.setText(context.getString(R.string.dog_gender, dog.getGender()));
        dogDescriptionTextView.setText(context.getString(R.string.dog_comments, dog.getComments()));

        return view;
    }

}

