package com.example.myeduapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OverallStatsActivity extends AppCompatActivity {


    private TextView scienceStats, mathStats, geographyStats, englishStats;

    private static final String PREFS_NAME = "MyProfilePrefs";
    private static final String KEY_SCIENCE_HIGHEST = "scienceHighest";
    private static final String KEY_MATH_HIGHEST = "mathHighest";
    private static final String KEY_GEOGRAPHY_HIGHEST = "geographyHighest";
    private static final String KEY_ENGLISH_HIGHEST = "englishHighest";
    private static final String KEY_SCIENCE_LATEST = "scienceLatest";
    private static final String KEY_MATH_LATEST = "mathLatest";
    private static final String KEY_GEOGRAPHY_LATEST = "geographyLatest";
    private static final String KEY_ENGLISH_LATEST = "englishLatest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_stats);

        // Initialize TextViews
        scienceStats = findViewById(R.id.scienceStats);
        mathStats = findViewById(R.id.mathStats);
        geographyStats = findViewById(R.id.geographyStats);
        englishStats = findViewById(R.id.englishStats);

        // Load stats from SharedPreferences
        loadStats();
    }

    private void loadStats() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int scienceHighest = prefs.getInt(KEY_SCIENCE_HIGHEST, 0);
        int scienceLatest = prefs.getInt(KEY_SCIENCE_LATEST, 0);

        int mathHighest = prefs.getInt(KEY_MATH_HIGHEST, 0);
        int mathLatest = prefs.getInt(KEY_MATH_LATEST, 0);

        int geographyHighest = prefs.getInt(KEY_GEOGRAPHY_HIGHEST, 0);
        int geographyLatest = prefs.getInt(KEY_GEOGRAPHY_LATEST, 0);

        int englishHighest = prefs.getInt(KEY_ENGLISH_HIGHEST, 0);
        int englishLatest = prefs.getInt(KEY_ENGLISH_LATEST, 0);

        // Update TextViews
        scienceStats.setText("Science - Highest: " + scienceHighest + " | Latest: " + scienceLatest);
        mathStats.setText("Math - Highest: " + mathHighest + " | Latest: " + mathLatest);
        geographyStats.setText("Geography - Highest: " + geographyHighest + " | Latest: " + geographyLatest);
        englishStats.setText("English - Highest: " + englishHighest + " | Latest: " + englishLatest);
    }
}
