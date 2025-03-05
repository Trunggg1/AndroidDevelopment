package com.example.myeduapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomingActivity extends AppCompatActivity {

    private FrameLayout selectedFrameLayout; // To keep track of the selected frame
    private String selectedCategory; // To store the selected category name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcoming);

        // Initialize FrameLayouts for each category
        FrameLayout scienceFrame = findViewById(R.id.scienceFrame);
        FrameLayout geographyFrame = findViewById(R.id.geographyFrame);
        FrameLayout mathFrame = findViewById(R.id.mathFrame);
        FrameLayout englishFrame = findViewById(R.id.englishFrame);

        // Set up selection functionality for each frame
        setupFrameSelection(scienceFrame, "Science");
        setupFrameSelection(geographyFrame, "Geography");
        setupFrameSelection(mathFrame, "Math");
        setupFrameSelection(englishFrame, "English");

        // Setup JOIN button
        Button joinButton = findViewById(R.id.playButton);
        joinButton.setOnClickListener(v -> {
            if (selectedCategory != null) {
                if (selectedCategory.equals("English")) {
                    // Navigate to EnglishOptionsActivity if English category is selected
                    Intent intent = new Intent(WelcomingActivity.this, EnglishOptionsActivity.class);
                    startActivity(intent);
                } else {
                    // Proceed to QuizActivity with the selected category for other categories
                    Intent intent = new Intent(WelcomingActivity.this, QuizActivity.class);
                    intent.putExtra("category", selectedCategory);
                    startActivity(intent);
                }
                finish(); // Finish this activity to avoid memory overload
            } else {
                Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            }
        });

        // Setup My Profile button
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomingActivity.this, MyProfileActivity.class);
            startActivity(intent);
        });
    }

    // Method to set up click listener for frame selection
    private void setupFrameSelection(final FrameLayout frameLayout, final String category) {
        frameLayout.setOnClickListener(v -> {
            // Clear previous selection
            if (selectedFrameLayout != null) {
                selectedFrameLayout.setBackground(null); // Remove border from previously selected frame
            }

            // Set selected background to current frame and update selected category
            selectedFrameLayout = frameLayout;
            selectedFrameLayout.setBackgroundResource(R.drawable.selected_border);
            selectedCategory = category;
        });
    }
}
