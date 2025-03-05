package com.example.myeduapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView scoreText;
    private Button homeButton;
    private Handler handler = new Handler();
    private int[] fireworkIds = {R.id.firework1, R.id.firework2, R.id.firework3, R.id.firework4, R.id.firework5, R.id.firework6};
    private int[] gradientResources = {
            R.drawable.gradient_color_1,
            R.drawable.gradient_color_2,
            R.drawable.gradient_color_3,
            R.drawable.gradient_color_4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize Views
        scoreText = findViewById(R.id.scoreText);
        homeButton = findViewById(R.id.homeButton);

        // Get score and total questions from the intent
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 7);
        scoreText.setText(score + " / " + total);

        // Start fireworks animations
        startFireworkAnimations();

        // Home button click listener to go back to the Welcoming page
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, WelcomingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Start background color animation
        startBackgroundAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop animations and remove callbacks on pause to save resources
        handler.removeCallbacksAndMessages(null);
        stopFireworkAnimations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart animations if needed
        startFireworkAnimations();
        startBackgroundAnimation();
    }

    private void startFireworkAnimations() {
        Animation fireworkAnimation = AnimationUtils.loadAnimation(this, R.anim.firework_animation);
        for (int id : fireworkIds) {
            ImageView firework = findViewById(id);
            firework.startAnimation(fireworkAnimation);
        }
    }

    private void stopFireworkAnimations() {
        for (int id : fireworkIds) {
            ImageView firework = findViewById(id);
            firework.clearAnimation();
        }
    }

    private void startBackgroundAnimation() {
        Runnable runnable = new Runnable() {
            int index = 0;

            @Override
            public void run() {
                findViewById(R.id.animatedBackground).setBackgroundResource(gradientResources[index]);
                index = (index + 1) % gradientResources.length; // Cycle through the gradients
                handler.postDelayed(this, 2000); // Change color every 2 seconds
            }
        };
        handler.post(runnable);
    }
}
