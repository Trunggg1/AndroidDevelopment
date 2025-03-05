// Flashcard.java
package com.example.myeduapplication;

public class Flashcard {
    private int imageResId;
    private String word;

    public Flashcard(int imageResId, String word) {
        this.imageResId = imageResId;
        this.word = word;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getWord() {
        return word;
    }
}
