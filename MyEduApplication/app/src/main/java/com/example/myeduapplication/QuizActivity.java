package com.example.myeduapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, timerText, scoreText;
    private Button optionA, optionB, optionC, optionD;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Handler handler = new Handler();
    private int timeLeft = 30; // 30-second timer
    private String category; // The quiz category

    // SharedPreferences keys
    private static final String PREFS_NAME = "MyProfilePrefs";
    private static final String KEY_SCIENCE_COUNT = "scienceTimes";
    private static final String KEY_MATH_COUNT = "mathTimes";
    private static final String KEY_GEOGRAPHY_COUNT = "geographyTimes";
    private static final String KEY_ENGLISH_COUNT = "englishTimes";

    // Keys for highest and latest scores
    private static final String KEY_SCIENCE_HIGHEST = "scienceHighest";
    private static final String KEY_SCIENCE_LATEST = "scienceLatest";
    private static final String KEY_MATH_HIGHEST = "mathHighest";
    private static final String KEY_MATH_LATEST = "mathLatest";
    private static final String KEY_GEOGRAPHY_HIGHEST = "geographyHighest";
    private static final String KEY_GEOGRAPHY_LATEST = "geographyLatest";
    private static final String KEY_ENGLISH_HIGHEST = "englishHighest";
    private static final String KEY_ENGLISH_LATEST = "englishLatest";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        scoreText = findViewById(R.id.scoreText);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);

        // Retrieve category and increment its quiz count
        category = getIntent().getStringExtra("category");
        incrementCategoryCount(category);

        // Load questions for the selected category
        questions = loadQuestionsForCategory(category);

        // Display the first question
        loadQuestion();

        // Set click listeners for options
        optionA.setOnClickListener(v -> checkAnswer("A"));
        optionB.setOnClickListener(v -> checkAnswer("B"));
        optionC.setOnClickListener(v -> checkAnswer("C"));
        optionD.setOnClickListener(v -> checkAnswer("D"));

        // Start countdown timer
        startTimer();
    }

    // Method to increment category count in SharedPreferences
    private void incrementCategoryCount(String category) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        switch (category) {
            case "Science":
                editor.putInt(KEY_SCIENCE_COUNT, prefs.getInt(KEY_SCIENCE_COUNT, 0) + 1);
                break;
            case "Math":
                editor.putInt(KEY_MATH_COUNT, prefs.getInt(KEY_MATH_COUNT, 0) + 1);
                break;
            case "Geography":
                editor.putInt(KEY_GEOGRAPHY_COUNT, prefs.getInt(KEY_GEOGRAPHY_COUNT, 0) + 1);
                break;
            case "English":
                editor.putInt(KEY_ENGLISH_COUNT, prefs.getInt(KEY_ENGLISH_COUNT, 0) + 1);
                break;
        }
        editor.apply(); // Save the updated count
    }

    private List<Question> loadQuestionsForCategory(String category) {
        List<Question> questionList = new ArrayList<>();

        switch (category) {
            case "Science":
                questionList.add(new Question("What is the chemical symbol for water?", "H₂O", "O₂", "CO₂", "H₂", "A"));
                questionList.add(new Question("Which planet is known as the Red Planet?", "Venus", "Mars", "Jupiter", "Saturn", "B"));
                questionList.add(new Question("What gas do plants absorb from the atmosphere?", "Oxygen", "Nitrogen", "Carbon Dioxide", "Helium", "C"));
                questionList.add(new Question("What force keeps us on the ground?", "Magnetism", "Friction", "Gravity", "Inertia", "C"));
                questionList.add(new Question("Which organ in the human body pumps blood?", "Brain", "Lungs", "Kidney", "Heart", "D"));
                questionList.add(new Question("What is the boiling point of water?", "90°C", "100°C", "120°C", "80°C", "B"));
                questionList.add(new Question("What part of the plant conducts photosynthesis?", "Stem", "Leaf", "Root", "Flower", "B"));
                questionList.add(new Question("Which planet is closest to the sun?", "Earth", "Venus", "Mercury", "Mars", "C"));
                questionList.add(new Question("What is the largest organ in the human body?", "Heart", "Skin", "Liver", "Brain", "B"));
                questionList.add(new Question("What vitamin do we get from sunlight?", "Vitamin A", "Vitamin B", "Vitamin C", "Vitamin D", "D"));
                break;

            case "Geography":
                questionList.add(new Question("Which is the largest continent?", "Africa", "Asia", "Europe", "Antarctica", "B"));
                questionList.add(new Question("Which ocean is the deepest?", "Atlantic", "Indian", "Arctic", "Pacific", "D"));
                questionList.add(new Question("What is the capital of France?", "Rome", "Madrid", "Paris", "Berlin", "C"));
                questionList.add(new Question("Which country has the Great Barrier Reef?", "Brazil", "Australia", "USA", "India", "B"));
                questionList.add(new Question("Which is the longest river in the world?", "Amazon", "Nile", "Mississippi", "Yangtze", "B"));
                questionList.add(new Question("Which desert is the largest in the world?", "Gobi", "Sahara", "Arabian", "Kalahari", "B"));
                questionList.add(new Question("Mount Everest is located in which country?", "China", "Nepal", "India", "Bhutan", "B"));
                questionList.add(new Question("Which country has the most islands?", "Canada", "Australia", "Norway", "Sweden", "D"));
                questionList.add(new Question("What is the smallest country in the world?", "Monaco", "Vatican City", "Malta", "Liechtenstein", "B"));
                questionList.add(new Question("Which city is known as the 'City of Love'?", "London", "Rome", "Paris", "New York", "C"));
                break;

            case "Math":
                questionList.add(new Question("What is 10 + 15?", "20", "25", "30", "35", "B"));
                questionList.add(new Question("What is the square root of 64?", "6", "7", "8", "9", "C"));
                questionList.add(new Question("If you have 20 apples and give away 5, how many are left?", "10", "15", "20", "25", "B"));
                questionList.add(new Question("What is 7 x 6?", "42", "36", "49", "56", "A"));
                questionList.add(new Question("What is the value of π (Pi) up to two decimal places?", "3.12", "3.14", "3.16", "3.18", "B"));
                questionList.add(new Question("What is 100 divided by 5?", "15", "20", "25", "30", "B"));
                questionList.add(new Question("What is 12 squared (12 x 12)?", "124", "144", "156", "164", "B"));
                questionList.add(new Question("What is 45% of 200?", "80", "85", "90", "95", "C"));
                questionList.add(new Question("What is the sum of angles in a triangle?", "90°", "120°", "180°", "360°", "C"));
                questionList.add(new Question("What is 9 cubed?", "81", "729", "243", "512", "B"));
                break;

            case "English":
                questionList.add(new Question("Which fruit is yellow and curved?", "Apple", "Banana", "Cat", "Fish", "B"));
                questionList.add(new Question("Which animal barks?", "Giraffe", "Fish", "Dog", "Elephant", "C"));
                questionList.add(new Question("Which animal has a long neck?", "Elephant", "Fish", "Cat", "Giraffe", "D"));
                questionList.add(new Question("Which animal is known to live in water?", "Dog", "Fish", "Horse", "Giraffe", "B"));
                questionList.add(new Question("Which fruit is red and round?", "Apple", "Banana", "Giraffe", "Elephant", "A"));
                questionList.add(new Question("Which animal is often used for riding?", "Fish", "Horse", "Banana", "Cat", "B"));
                questionList.add(new Question("Which animal is known to be very big and have tusks?", "Dog", "Giraffe", "Elephant", "Fish", "C"));
                questionList.add(new Question("Which fruit is green and sour?", "Grapes", "Banana", "Apple", "Lemon", "D"));
                questionList.add(new Question("Which animal is known as man's best friend?", "Cat", "Elephant", "Dog", "Horse", "C"));
                questionList.add(new Question("Which animal has stripes and lives in the jungle?", "Giraffe", "Tiger", "Elephant", "Cat", "B"));
                break;
        }
        return questionList;
    }

private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionText.setText(question.getText());
            optionA.setText(question.getOptionA());
            optionB.setText(question.getOptionB());
            optionC.setText(question.getOptionC());
            optionD.setText(question.getOptionD());

            timeLeft = 30;
            timerText.setText("Time: " + timeLeft + "s");
        } else {
            endQuiz();
        }
    }

    private void startTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerText.setText("Time: " + timeLeft + "s");
                    handler.postDelayed(this, 1000);
                } else {
                    showCorrectAnswer();
                    handler.postDelayed(QuizActivity.this::advanceToNextQuestion, 2000);
                }
            }
        }, 1000);
    }

    private void checkAnswer(String selectedOption) {
        disableOptionButtons();
        handler.removeCallbacksAndMessages(null); // Stop the timer
        Question question = questions.get(currentQuestionIndex);

        if (question.getCorrectAnswer().equals(selectedOption)) {
            score++;
            showFeedback(true);
            scoreText.setText("Score: " + score);
        } else {
            showFeedback(false);
            showCorrectAnswer(); // Show the correct answer if the selected answer is incorrect
        }

        handler.postDelayed(this::advanceToNextQuestion, 2000);
    }

    private void disableOptionButtons() {
        optionA.setEnabled(false);
        optionB.setEnabled(false);
        optionC.setEnabled(false);
        optionD.setEnabled(false);
    }

    private void enableOptionButtons() {
        optionA.setEnabled(true);
        optionB.setEnabled(true);
        optionC.setEnabled(true);
        optionD.setEnabled(true);
    }

    private void showFeedback(boolean isCorrect) {
        Toast.makeText(this, isCorrect ? "Correct!" : "Wrong!", Toast.LENGTH_SHORT).show();
    }

    private void showCorrectAnswer() {
        Question question = questions.get(currentQuestionIndex);
        resetOptionColors();

        Button correctButton = null;
        if (question.getCorrectAnswer().equals("A")) correctButton = optionA;
        if (question.getCorrectAnswer().equals("B")) correctButton = optionB;
        if (question.getCorrectAnswer().equals("C")) correctButton = optionC;
        if (question.getCorrectAnswer().equals("D")) correctButton = optionD;

        if (correctButton != null) {
            correctButton.setBackgroundColor(getResources().getColor(R.color.teal_200));
            correctButton.setTextSize(30); // Make the correct answer's text bigger
        }
    }

    private void advanceToNextQuestion() {
        currentQuestionIndex++;
        resetOptionColors();
        enableOptionButtons();
        loadQuestion();
        startTimer();
    }

    private void resetOptionColors() {
        optionA.setBackgroundColor(getResources().getColor(R.color.default_button_color));
        optionB.setBackgroundColor(getResources().getColor(R.color.default_button_color));
        optionC.setBackgroundColor(getResources().getColor(R.color.default_button_color));
        optionD.setBackgroundColor(getResources().getColor(R.color.default_button_color));

        optionA.setTextSize(18);
        optionB.setTextSize(18);
        optionC.setTextSize(18);
        optionD.setTextSize(18);
    }

    private void endQuiz() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int highestScore, latestScore;

        switch (category) {
            case "Science":
                highestScore = prefs.getInt(KEY_SCIENCE_HIGHEST, 0);
                latestScore = score;
                editor.putInt(KEY_SCIENCE_LATEST, latestScore);
                if (score > highestScore) {
                    editor.putInt(KEY_SCIENCE_HIGHEST, score);
                }
                break;
            case "Math":
                highestScore = prefs.getInt(KEY_MATH_HIGHEST, 0);
                latestScore = score;
                editor.putInt(KEY_MATH_LATEST, latestScore);
                if (score > highestScore) {
                    editor.putInt(KEY_MATH_HIGHEST, score);
                }
                break;
            case "Geography":
                highestScore = prefs.getInt(KEY_GEOGRAPHY_HIGHEST, 0);
                latestScore = score;
                editor.putInt(KEY_GEOGRAPHY_LATEST, latestScore);
                if (score > highestScore) {
                    editor.putInt(KEY_GEOGRAPHY_HIGHEST, score);
                }
                break;
            case "English":
                highestScore = prefs.getInt(KEY_ENGLISH_HIGHEST, 0);
                latestScore = score;
                editor.putInt(KEY_ENGLISH_LATEST, latestScore);
                if (score > highestScore) {
                    editor.putInt(KEY_ENGLISH_HIGHEST, score);
                }
                break;
        }

        editor.apply();

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questions.size());
        startActivity(intent);
        finish();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QuizActivity.this, WelcomingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
