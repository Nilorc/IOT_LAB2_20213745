package com.example.lab2_telequiz_20213745.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab2_telequiz_20213745.R;

public class MainActivity extends AppCompatActivity {

    private Button btnRedes, btnCiber, btnMicroondas, btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRedes = findViewById(R.id.btnRedes);
        btnCiber = findViewById(R.id.btnCiber);
        btnMicroondas = findViewById(R.id.btnMicroondas);
        btnStats = findViewById(R.id.btnStats); // botón para ver estadísticas desde el menú de inicio

        btnRedes.setOnClickListener(view -> startQuiz("Redes"));
        btnCiber.setOnClickListener(view -> startQuiz("Ciberseguridad"));
        btnMicroondas.setOnClickListener(view -> startQuiz("Microondas"));
        btnStats.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StatsActivity.class);
            startActivity(intent);
        });
    }

    private void startQuiz(String category) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}