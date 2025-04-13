package com.example.lab2_telequiz_20213745.models;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;
    private String category;

    public Question(String questionText, List<String> options, int correctAnswerIndex, String category) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.category = category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getCategory() {
        return category;
    }
}
