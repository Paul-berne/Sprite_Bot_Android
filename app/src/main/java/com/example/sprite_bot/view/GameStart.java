package com.example.sprite_bot.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprite_bot.R;
import com.example.sprite_bot.control.Controller;

import java.sql.SQLException;
import java.text.ParseException;

public class GameStart extends AppCompatActivity {

    private Controller myController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        try {
            myController = new Controller(); // Assurez-vous d'initialiser votre Controller correctement
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnStart = findViewById(R.id.btnLogin);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myController.CreateQuizGameGUI();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                finish(); // Fermer l'activité actuelle
            }
        });
    }
}
