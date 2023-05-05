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

    private final View mWindow;
    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.info_window, null);
    }

    private void renderWindowText(Marker marker, View view) {
        StrayDogData dog = (StrayDogData) marker.getTag();

        ImageView dogImageView = view.findViewById(R.id.dogImageView);
        TextView dogNameTextView = view.findViewById(R.id.dog_name);
        TextView dogGenderTextView = view.findViewById(R.id.dog_gender);
        TextView dogDescriptionTextView = view.findViewById(R.id.dog_description);
        TextView dogDateAddedTextView = view.findViewById(R.id.dog_date_added); // Add this line

        if (dog != null) {
            if (dog.getPhotoUrl() != null && !dog.getPhotoUrl().isEmpty()) {
                Picasso.get()
                        .load(dog.getPhotoUrl())
                        .resize(100, 100)
                        .centerCrop()
                        .into(dogImageView);
            } else {
                dogImageView.setImageResource(R.drawable.default_dog_image);
            }

            dogNameTextView.setText(context.getString(R.string.dog_name, dog.getName()));
            dogGenderTextView.setText(context.getString(R.string.dog_gender, dog.getGender()));
            dogDescriptionTextView.setText(context.getString(R.string.dog_description, dog.getDescription()));
            dogDateAddedTextView.setText(context.getString(R.string.dog_date_added, dog.getDateAdded())); // Add this line
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
