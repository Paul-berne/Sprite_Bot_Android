package com.example.sprite_bot.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprite_bot.R;
import com.example.sprite_bot.control.Controller;

import java.sql.SQLException;
import java.text.ParseException;

public class LoginActivity extends AppCompatActivity {

    private Controller myController;
    private EditText txtLogin;
    private EditText txtPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            myController = new Controller(); // Assurez-vous d'initialiser votre Controller correctement
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtLogin = findViewById(R.id.txtLogin);
        txtPwd = findViewById(R.id.txtPwd);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtLogin.getText().toString();
                String password = txtPwd.getText().toString();

                if (myController.verifyUserLogin(username, password)) {
                    myController.CreateGameStart();
                    finish(); // Fermer l'activité actuelle
                } else {
                    Toast.makeText(LoginActivity.this, "Nom d'utilisateur ou mot de passe incorrect.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtLogin.getText().toString();
                String password = txtPwd.getText().toString();

                if (myController.verifyUserLogin(username, password)) {
                    myController.CreateFrameChangePassword(username);
                    finish(); // Fermer l'activité actuelle
                } else {
                    Toast.makeText(LoginActivity.this, "Nom d'utilisateur ou mot de passe incorrect.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
