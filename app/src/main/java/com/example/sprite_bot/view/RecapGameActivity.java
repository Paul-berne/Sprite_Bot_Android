package com.example.sprite_bot.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprite_bot.R;
import com.example.sprite_bot.control.Controller;

import java.sql.SQLException;
import java.text.ParseException;

public class RecapGameActivity extends AppCompatActivity {

    private Controller controller;
    private int finalScore;
    private boolean hasWon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_game);

        try {
            controller = new Controller(); // Assurez-vous d'initialiser votre Controller correctement
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Récupère les données de l'intent
        Intent intent = getIntent();
        finalScore = intent.getIntExtra("FINAL_SCORE", 0);
        hasWon = intent.getBooleanExtra("HAS_WON", false);

        // Affiche le score final
        TextView scoreLabel = findViewById(R.id.scoreLabel);
        scoreLabel.setText("Votre score final est de " + finalScore + " points.");

        // Affiche le résultat du jeu (gagné ou perdu)
        TextView resultLabel = findViewById(R.id.resultLabel);
        resultLabel.setText(hasWon ? "Félicitations ! Vous avez gagné !" : "Dommage, vous avez perdu.");

        // Bouton "Rejouer"
        Button replayButton = findViewById(R.id.replayButton);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Génère de nouvelles questions, ferme l'activité actuelle et lance une nouvelle partie
                controller.generateNewQuestions();
                startActivity(new Intent(RecapGameActivity.this, QuizGameActivity.class));
                finish();
            }
        });

        // Bouton "Quitter"
        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ferme l'application
                finishAffinity();
            }
        });
    }
}
