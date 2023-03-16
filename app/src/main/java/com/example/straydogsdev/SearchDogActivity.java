package com.example.straydogsdev;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SearchDogActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dog);

        // get references to UI elements
        searchEditText = findViewById(R.id.search_edit_text);
        searchButton = findViewById(R.id.search_button);

        // set onClick listener for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // perform search using the searchEditText value
                String searchText = searchEditText.getText().toString().trim();
                performSearch(searchText);
            }
        });
    }

    private void performSearch(String searchText) {
        // perform the search operation
        // e.g. query the database for dogs matching the search text
        // and display the results in a list view or a map view
    }
}

