package com.example.sprite_bot.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprite_bot.R;
import com.example.sprite_bot.control.Controller;
import com.example.sprite_bot.model.Answer;
import com.example.sprite_bot.model.Question;
import com.example.sprite_bot.model.QuizGame;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class QuizGameActivity extends AppCompatActivity {

    private Controller theController;
    private QuizGame game;
    private int currentQuestionIndex = 0;

    private TextView questionLabel;
    private RadioButton[] answerRadioButtons;
    private Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        try {
            theController = new Controller(); // Assurez-vous d'initialiser votre Controller correctement
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        questionLabel = findViewById(R.id.questionLabel);
        answerRadioButtons = new RadioButton[4];
        @SuppressLint("WrongViewCast") RadioGroup answerRadioGroup = findViewById(R.id.answer3RadioButton);
        submitButton = findViewById(R.id.submitButton);

        // Initialise les boutons radio pour les réponses
        for (int i = 0; i < 4; i++) {
            int buttonId = getResources().getIdentifier("answerRadioButton" + (i + 1), "id", getPackageName());
            answerRadioButtons[i] = findViewById(buttonId);
        }

        // Récupère le jeu de quiz de l'intent
        game = (QuizGame) getIntent().getSerializableExtra("QUIZ_GAME");

        // Affiche la première question
        displayQuestion();

        // Ajoute un écouteur d'événements pour le bouton "Submit"
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                nextQuestion();
            }
        });
    }

    // Affiche la question actuelle et les réponses possibles
    private void displayQuestion() {
        Question currentQuestion = game.getQuestions().get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getDescriptionQuestion());

        ArrayList<Answer> answers = currentQuestion.getAnswers();
        for (int i = 0; i < 4; i++) {
            answerRadioButtons[i].setText(answers.get(i).getDescriptionAnswer());
            answerRadioButtons[i].setChecked(false);
        }
    }

    // Vérifie la réponse et met à jour le score du joueur
    private void checkAnswer() {
        Question currentQuestion = game.getQuestions().get(currentQuestionIndex);
        int selectedAnswerIndex = -1;

        for (int i = 0; i < 4; i++) {
            if (answerRadioButtons[i].isChecked()) {
                selectedAnswerIndex = i;
                break;
            }
        }

        if (selectedAnswerIndex != -1 && currentQuestion.getAnswers().get(selectedAnswerIndex).getIsCorrect()) {
            game.addPlayerScore(10);
            showMessage("Bravo ! Vous avez obtenu la bonne réponse.", "Réponse correcte");
        } else {
            showMessage("Dommage, la réponse était incorrecte.", "Réponse incorrecte");
        }
    }

    // Passe à la question suivante ou termine le jeu
    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < game.getQuestions().size()) {
            displayQuestion();
        } else {
            endGame();
        }
    }

    // Affiche un message de fin de jeu avec le score final du joueur
    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fin du jeu")
                .setMessage("Score final : " + game.getPlayerScore())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        theController.CreateRecapGame(game.getPlayerScore(), game.getPlayerScore() >= 30);
                        finish(); // Fermer l'activité actuelle
                    }
                })
                .setCancelable(false)
                .show();
    }

    // Affiche un message d'alerte
    private void showMessage(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
