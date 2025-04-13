package com.example.lab2_telequiz_20213745.services;

import com.example.lab2_telequiz_20213745.models.QuizResult;
import java.util.ArrayList;
import java.util.List;

public class QuizHistoryService {
    private static QuizHistoryService instance;
    private List<QuizResult> history;

    private QuizHistoryService(){
        history = new ArrayList<>();
    }

    public static QuizHistoryService getInstance(){
        if(instance == null){
            instance = new QuizHistoryService();
        }
        return instance;
    }

    public void addResult(QuizResult result){
        history.add(result);
    }

    public List<QuizResult> getAllResults(){
        return history;
    }
}
