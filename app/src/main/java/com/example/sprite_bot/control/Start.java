package com.example.sprite_bot.control;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private Controller leController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            leController = new Controller();
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Vous pouvez ajouter des méthodes pour gérer les interactions avec l'interface utilisateur si nécessaire
    // par exemple, un bouton qui démarre le quiz lorsque l'utilisateur appuie dessus
    public void onStartQuizClick(View view) {
        leController.CreateQuizGameGUI();
    }
}
