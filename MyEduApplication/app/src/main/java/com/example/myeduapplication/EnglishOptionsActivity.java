package com.example.myeduapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EnglishOptionsActivity extends AppCompatActivity {

    private Button quizButton;
    private Button flashcardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_options);

        // Initialize buttons
        quizButton = findViewById(R.id.quizButton);
        flashcardButton = findViewById(R.id.flashcardButton);

        // Set click listener for Quiz button with finish() after starting QuizActivity
        quizButton.setOnClickListener(v -> {
            Intent intent = new Intent(EnglishOptionsActivity.this, QuizActivity.class);
            intent.putExtra("category", "English");
            startActivity(intent);
            finish(); // Optionally finish EnglishOptionsActivity after navigating to QuizActivity
        });

        // Set click listener for Flashcard button
        flashcardButton.setOnClickListener(v -> {
            Intent intent = new Intent(EnglishOptionsActivity.this, FlashcardActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EnglishOptionsActivity.this, WelcomingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
