package com.example.sprite_bot.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprite_bot.DAO.DataFileUser;
import com.example.sprite_bot.R;
import com.example.sprite_bot.control.Controller;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import java.sql.SQLException;
import java.text.ParseException;

public class ChangePassword extends AppCompatActivity {

    private Controller myController;
    private EditText txtPwd;
    private EditText txtConfirmPwd;
    private TextView lblPasswordCheck;
    private DataFileUser leDAOUser;
    private String login;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        try {
            myController = new Controller(); // Assurez-vous d'initialiser votre Controller correctement
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        leDAOUser = new DataFileUser(myController);

        // Récupérer le login passé depuis l'intent
        login = getIntent().getStringExtra("login");

        txtPwd = findViewById(R.id.txtPwd);
        txtConfirmPwd = findViewById(R.id.txtConfirmPwd);
        lblPasswordCheck = findViewById(R.id.passwordCheckLabel);

        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String motDePasse = txtPwd.getText().toString();
                String confirmationMotDePasse = txtConfirmPwd.getText().toString();

                // Vérification des règles de mot de passe avec Passay
                PasswordValidator validator = new PasswordValidator(
                        new LengthRule(8, 30),
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1),
                        new WhitespaceRule()
                );

                RuleResult result = validator.validate(new PasswordData(motDePasse));

                if (motDePasse.isEmpty()) {
                    lblPasswordCheck.setText("Password cannot be empty.");
                } else if (!motDePasse.equals(confirmationMotDePasse)) {
                    lblPasswordCheck.setText("Passwords do not match.");
                } else if (!result.isValid()) {
                    lblPasswordCheck.setText("Password does not meet security rules.");
                } else {
                    lblPasswordCheck.setText("");
                    leDAOUser.ChangePasswordUser(login, motDePasse);
                    myController.CreateQuizGameGUI();
                    finish();
                }
            }
        });
    }
}
