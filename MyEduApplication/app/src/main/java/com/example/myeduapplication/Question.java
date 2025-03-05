package com.example.myeduapplication;

public class Question {
    private String text;          // Question text
    private String optionA;       // Option A text
    private String optionB;       // Option B text
    private String optionC;       // Option C text
    private String optionD;       // Option D text
    private String correctAnswer; // The correct answer ("A", "B", "C", or "D")

    // Constructor to initialize the question, options, and correct answer
    public Question(String text, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.text = text;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    // Getters
    public String getText() {
        return text;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
