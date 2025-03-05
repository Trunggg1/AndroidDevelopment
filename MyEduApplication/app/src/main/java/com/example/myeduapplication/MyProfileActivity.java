package com.example.myeduapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Random;

public class MyProfileActivity extends AppCompatActivity {

    private TextView greetingText, motivationalQuote;
    private ImageView avatarImage;
    private TextView scienceCount, mathCount, geographyCount, englishCount;
    private Button viewProgressButton, resetScoresButton;

    private int scienceTimes, mathTimes, geographyTimes, englishTimes;

    private static final String PREFS_NAME = "MyProfilePrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_AVATAR = "avatar";
    private static final String KEY_SCIENCE_COUNT = "scienceTimes";
    private static final String KEY_MATH_COUNT = "mathTimes";
    private static final String KEY_GEOGRAPHY_COUNT = "geographyTimes";
    private static final String KEY_ENGLISH_COUNT = "englishTimes";
    private static final String KEY_SCIENCE_HIGHEST = "scienceHighest";
    private static final String KEY_SCIENCE_LATEST = "scienceLatest";
    private static final String KEY_MATH_HIGHEST = "mathHighest";
    private static final String KEY_MATH_LATEST = "mathLatest";
    private static final String KEY_GEOGRAPHY_HIGHEST = "geographyHighest";
    private static final String KEY_GEOGRAPHY_LATEST = "geographyLatest";
    private static final String KEY_ENGLISH_HIGHEST = "englishHighest";
    private static final String KEY_ENGLISH_LATEST = "englishLatest";

    private static final String[] QUOTES = {
            "Believe you can and you're halfway there.",
            "Happiness is the key to success.",
            "Create your future today.",
            "Learn as if you were to live forever."
    };

    private int[] avatars = {R.drawable.avatar_default1, R.drawable.avatar_default2, R.drawable.avatar_default3};
    private int selectedAvatarIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        greetingText = findViewById(R.id.greetingText);
        motivationalQuote = findViewById(R.id.motivationalQuote);
        avatarImage = findViewById(R.id.avatarImage);
        scienceCount = findViewById(R.id.scienceCount);
        mathCount = findViewById(R.id.mathCount);
        geographyCount = findViewById(R.id.geographyCount);
        englishCount = findViewById(R.id.englishCount);
        viewProgressButton = findViewById(R.id.viewProgressButton);
        resetScoresButton = findViewById(R.id.resetScoresButton);

        // Load profile from SharedPreferences
        loadProfile();

        // Set dynamic greeting based on the time of day
        setDynamicGreeting();

        // Display a random motivational quote
        displayRandomQuote();

        // Handle completed quiz category if passed from ResultActivity
        handleCompletedCategory();

        // Update display of category counts
        updateCategoryCounts();

        // Set up name and avatar change functionality
        greetingText.setOnClickListener(v -> changeName());
        avatarImage.setOnClickListener(v -> changeAvatar());

        // Handle "View Progress" button click
        viewProgressButton.setOnClickListener(v -> {
            Intent intent = new Intent(MyProfileActivity.this, OverallStatsActivity.class);
            startActivity(intent);
        });

        // Handle "Reset Scores" button click
        resetScoresButton.setOnClickListener(v -> showResetConfirmationDialog());
    }

    private void loadProfile() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = prefs.getString(KEY_NAME, "");
        selectedAvatarIndex = prefs.getInt(KEY_AVATAR, 0);
        scienceTimes = prefs.getInt(KEY_SCIENCE_COUNT, 0);
        mathTimes = prefs.getInt(KEY_MATH_COUNT, 0);
        geographyTimes = prefs.getInt(KEY_GEOGRAPHY_COUNT, 0);
        englishTimes = prefs.getInt(KEY_ENGLISH_COUNT, 0);

        if (name.isEmpty()) {
            name = "[Name]";
        }
        greetingText.setTag(name); // Store the name for greeting later
        avatarImage.setImageResource(avatars[selectedAvatarIndex]);
    }

    private void setDynamicGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String greeting;
        String userName = (String) greetingText.getTag(); // Retrieve stored name

        if (hour < 12) {
            greeting = "Good Morning";
        } else if (hour < 18) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }

        greetingText.setText(greeting + ", " + userName);
    }

    private void displayRandomQuote() {
        int randomIndex = new Random().nextInt(QUOTES.length);
        motivationalQuote.setText(QUOTES[randomIndex]);
    }

    private void saveProfile() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_NAME, (String) greetingText.getTag());
        editor.putInt(KEY_AVATAR, selectedAvatarIndex);
        editor.putInt(KEY_SCIENCE_COUNT, scienceTimes);
        editor.putInt(KEY_MATH_COUNT, mathTimes);
        editor.putInt(KEY_GEOGRAPHY_COUNT, geographyTimes);
        editor.putInt(KEY_ENGLISH_COUNT, englishTimes);
        editor.apply();
    }

    private void handleCompletedCategory() {
        Intent intent = getIntent();
        String completedCategory = intent.getStringExtra("completedCategory");
        if (completedCategory != null) {
            switch (completedCategory) {
                case "Science":
                    scienceTimes++;
                    break;
                case "Math":
                    mathTimes++;
                    break;
                case "Geography":
                    geographyTimes++;
                    break;
                case "English":
                    englishTimes++;
                    break;
            }
            saveProfile();
            updateCategoryCounts();
        }
    }

    private void updateCategoryCounts() {
        scienceCount.setText("Science: " + scienceTimes + " " + (scienceTimes <= 1 ? "time" : "times"));
        mathCount.setText("Math: " + mathTimes + " " + (mathTimes <= 1 ? "time" : "times"));
        geographyCount.setText("Geography: " + geographyTimes + " " + (geographyTimes <= 1 ? "time" : "times"));
        englishCount.setText("English: " + englishTimes + " " + (englishTimes <= 1 ? "time" : "times"));
    }



    private void showResetConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Scores");
        builder.setMessage("Are you sure you want to reset all scores and times? This action cannot be undone.");
        builder.setPositiveButton("Yes", (dialog, which) -> resetAllScores());
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void resetAllScores() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Reset all times
        scienceTimes = 0;
        mathTimes = 0;
        geographyTimes = 0;
        englishTimes = 0;

        // Reset all scores
        editor.putInt(KEY_SCIENCE_COUNT, 0);
        editor.putInt(KEY_MATH_COUNT, 0);
        editor.putInt(KEY_GEOGRAPHY_COUNT, 0);
        editor.putInt(KEY_ENGLISH_COUNT, 0);

        // Reset highest and latest scores
        editor.putInt(KEY_SCIENCE_HIGHEST, 0);
        editor.putInt(KEY_SCIENCE_LATEST, 0);
        editor.putInt(KEY_MATH_HIGHEST, 0);
        editor.putInt(KEY_MATH_LATEST, 0);
        editor.putInt(KEY_GEOGRAPHY_HIGHEST, 0);
        editor.putInt(KEY_GEOGRAPHY_LATEST, 0);
        editor.putInt(KEY_ENGLISH_HIGHEST, 0);
        editor.putInt(KEY_ENGLISH_LATEST, 0);

        editor.apply(); // Save changes

        // Update counts on UI
        updateCategoryCounts();

        Toast.makeText(this, "All scores and times have been reset.", Toast.LENGTH_SHORT).show();
    }

    private void changeName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Name");
        final EditText input = new EditText(this);
        input.setHint("Enter new name");
        builder.setView(input);
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = input.getText().toString();
            if (!newName.isEmpty()) {
                greetingText.setTag(newName); // Store name for greeting
                setDynamicGreeting(); // Update greeting dynamically
                saveProfile();
                Toast.makeText(this, "Name updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void changeAvatar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Avatar");
        builder.setItems(new CharSequence[]{"Avatar 1", "Avatar 2", "Avatar 3"}, (dialog, which) -> {
            selectedAvatarIndex = which;
            avatarImage.setImageResource(avatars[which]);
            saveProfile();
            Toast.makeText(this, "Avatar updated", Toast.LENGTH_SHORT).show();
        });
        builder.show();
    }
}
