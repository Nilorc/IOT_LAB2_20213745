package com.example.lab2_telequiz_20213745.activities;

import com.example.lab2_telequiz_20213745.R;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.lab2_telequiz_20213745.models.Question;
import com.example.lab2_telequiz_20213745.models.QuizResult;
import com.example.lab2_telequiz_20213745.services.QuizHistoryService;
import com.example.lab2_telequiz_20213745.utils.QuestionRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuizTitle, tvQuestion, tvScore;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3;
    private Button btnPrevious, btnNext;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int[] selectedAnswers;
    private String category;
    private long startTime, elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = findViewById(R.id.toolbarQuiz);
        setSupportActionBar(toolbar);

        tvQuizTitle = findViewById(R.id.tvQuizTitle);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        rgOptions = findViewById(R.id.rgOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        btnPrevious = findViewById(R.id.btnAnterior);
        btnNext = findViewById(R.id.btnSiguiente);

        // Recuperar categoría y título del quiz
        category = getIntent().getStringExtra("CATEGORY");
        tvQuizTitle.setText(category);

        // Carga de preguntas según la categoría (se espera 5 preguntas)
        questionList = QuestionRepository.getQuestionsForCategory(category);

        // Inicializamos el arreglo de respuestas con -1 (sin respuesta)
        selectedAnswers = new int[questionList.size()];
        Arrays.fill(selectedAnswers, -1);

        startTime = System.currentTimeMillis();

        // Sólo habilita "Siguiente" al seleccionar opción;
        rgOptions.setOnCheckedChangeListener((group, checkedId) -> {
            btnNext.setEnabled(true);
        });

        btnPrevious.setOnClickListener(view -> {
            saveAnswer();
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion();
            }
        });

        btnNext.setOnClickListener(view -> {
            saveAnswer();
            // Se actualiza el puntaje solo cuando se presiona "Siguiente"
            updateScoreDisplay();
            if (currentQuestionIndex < questionList.size() - 1) {
                currentQuestionIndex++;
                loadQuestion();
            } else {
                int finalScore = calculatePartialScore();
                elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                QuizResult result = new QuizResult(category, finalScore, elapsedTime, false);
                QuizHistoryService.getInstance().addResult(result);
                showFinalScoreDialog(finalScore, elapsedTime);
            }
        });

        loadQuestion();
    }

    // Menú de la Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_telequiz, menu);
        return true;
    }

    // Manejar los clics en los items del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_back_to_home) {
            // Registrar como cancelado y regresar al inicio
            QuizResult canceledResult = new QuizResult(
                    category,
                    0,
                    (System.currentTimeMillis() - startTime)/1000,
                    true
            );
            QuizHistoryService.getInstance().addResult(canceledResult);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_stats) {
            // Ir a ver estadísticas
            Intent intent = new Intent(this, StatsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Carga la pregunta actual y actualiza la interfaz
    private void loadQuestion() {
        rgOptions.clearCheck();
        Question currentQuestion = questionList.get(currentQuestionIndex);
        tvQuestion.setText(currentQuestion.getQuestionText());

        List<String> options = currentQuestion.getOptions();
        rbOption1.setText(options.get(0));
        rbOption2.setText(options.get(1));
        rbOption3.setText(options.get(2));

        // Si la pregunta ya fue contestada, se selecciona la respuesta guardada
        int selected = selectedAnswers[currentQuestionIndex];
        if (selected != -1) {
            ((RadioButton) rgOptions.getChildAt(selected)).setChecked(true);
        }
        btnNext.setEnabled(selected != -1);
        btnPrevious.setEnabled(currentQuestionIndex > 0);
        // Actualizamos el puntaje basado en preguntas ya finalizadas
        updateScoreDisplay();
    }

    // Guardar la respuesta seleccionada en la pregunta actual
    private void saveAnswer() {
        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId != -1) {
            int index = rgOptions.indexOfChild(findViewById(selectedId));
            selectedAnswers[currentQuestionIndex] = index;
        }
    }

    // Calculo de el puntaje parcial (solo para preguntas finalizadas)
    private int calculatePartialScore() {
        int partialScore = 0;
        for (int i = 0; i <= currentQuestionIndex; i++) {
            if (selectedAnswers[i] != -1) {  // Solo se evalúan las preguntas contestadas hasta el momento
                if (selectedAnswers[i] == questionList.get(i).getCorrectAnswerIndex()) {
                    partialScore += 2;
                } else {
                    partialScore -= 2;
                }
            }
        }
        return partialScore;
    }

    // Actualizacón  el TextView del puntaje usando el puntaje parcial calculado
    private void updateScoreDisplay() {
        int partialScore = calculatePartialScore();
        tvScore.setText("Puntaje: " + partialScore);
    }

    // Muestra el diálogo final con el resultado del quiz
    private void showFinalScoreDialog(int finalScore, long elapsedTime) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado Final");
        String message = "Tu puntaje es: " + finalScore + "\nTiempo: " + elapsedTime + " segundos";
        builder.setMessage(message);
        if (finalScore >= 0) {
            tvScore.setTextColor(Color.GREEN);
        } else {
            tvScore.setTextColor(Color.RED);
        }
        builder.setPositiveButton("Aceptar", (dialog, which) -> finish());
        builder.create().show();
    }
}
