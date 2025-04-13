package com.example.lab2_telequiz_20213745.models;

public class QuizResult {
    private String category;
    private int score;
    private long timeInSeconds;
    private boolean canceled;

    public QuizResult(String category, int score, long timeInSeconds, boolean canceled) {
        this.category = category;
        this.score = score;
        this.timeInSeconds = timeInSeconds;
        this.canceled = canceled;
    }

    public String getCategory() {
        return category;
    }

    public int getScore() {
        return score;
    }

    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
