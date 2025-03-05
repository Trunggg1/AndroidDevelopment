package com.example.myeduapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FlashcardActivity extends AppCompatActivity {

    private GridLayout flashcardGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        flashcardGrid = findViewById(R.id.flashcardGrid);

        // Sample data for flashcards (image resource IDs and text)
        int[] imageResIds = { R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
                R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8 };
        String[] words = { "Apple", "Banana", "Cat", "Dog", "Elephant", "Fish", "Giraffe", "Horse" };

        // Add flashcards to the grid
        for (int i = 0; i < imageResIds.length; i++) {
            addFlashcard(imageResIds[i], words[i]);
        }
    }

    private void addFlashcard(int imageResId, String word) {
        View flashcardView = LayoutInflater.from(this).inflate(R.layout.item_flashcard, flashcardGrid, false);

        ImageView flashcardImage = flashcardView.findViewById(R.id.flashcardImage);
        TextView flashcardText = flashcardView.findViewById(R.id.flashcardText);

        flashcardImage.setImageResource(imageResId);
        flashcardText.setText(word);

        flashcardView.setOnClickListener(v -> {
            // Toggle visibility for flip effect
            if (flashcardImage.getVisibility() == View.VISIBLE) {
                flashcardImage.setVisibility(View.GONE);
                flashcardText.setVisibility(View.VISIBLE);
            } else {
                flashcardImage.setVisibility(View.VISIBLE);
                flashcardText.setVisibility(View.GONE);
            }
        });

        flashcardGrid.addView(flashcardView);
    }
}
