package com.example.sprite_bot.control;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private Controller Lecontroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise le contrôleur
        try {
            Lecontroller = new Controller();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Bouton pour créer l'interface graphique du jeu de quiz
        Button startQuizButton = findViewById(R.id.startQuizButton);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Lecontroller.CreateQuizGameGUI();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Autres fonctionnalités peuvent être ajoutées ici
    }
}
