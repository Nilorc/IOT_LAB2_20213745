package com.example.lab2_telequiz_20213745.activities;

import com.example.lab2_telequiz_20213745.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.lab2_telequiz_20213745.models.QuizResult;
import com.example.lab2_telequiz_20213745.services.QuizHistoryService;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private LinearLayout layoutStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Toolbar toolbar = findViewById(R.id.toolbarStats);
        setSupportActionBar(toolbar);

        layoutStats = findViewById(R.id.layoutStats);
        List<QuizResult> results = QuizHistoryService.getInstance().getAllResults();

        for (QuizResult result : results) {
            TextView tv = new TextView(this);
            String text;
            if (result.isCanceled()) {
                text = "Tema: " + result.getCategory() + " - Canceló";
            } else {
                text = "Tema: " + result.getCategory() + " | Puntaje: " + result.getScore() +
                        " | Tiempo: " + result.getTimeInSeconds() + " s";
            }
            tv.setText(text);
            if (!result.isCanceled()) {
                if (result.getScore() >= 0) {
                    tv.setTextColor(Color.GREEN);
                } else {
                    tv.setTextColor(Color.RED);
                }
            }
            layoutStats.addView(tv);
        }
    }

    //Para el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_telequiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_back_to_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_stats) {
            // Estás en StatsActivity; podrías recargar o hacer nada
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



