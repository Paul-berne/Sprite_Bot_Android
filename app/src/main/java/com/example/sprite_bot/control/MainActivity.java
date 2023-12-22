package com.example.sprite_bot.control;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprite_bot.R;

import java.sql.SQLException;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    //private Controller leController;

    private EditText txtLogin;
    private EditText txtPwd;
    private Button btnLogin;
    private Button btnChangePassword;
    private TextView pwdLabel;
    private TextView loginLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        txtLogin = findViewById(R.id.txtLogin);
        txtPwd = findViewById(R.id.txtPwd);

        pwdLabel = findViewById(R.id.pwdLabel);
        loginLabel = findViewById(R.id.loginLabel);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Vrai", Toast.LENGTH_SHORT).show();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Faux", Toast.LENGTH_SHORT).show();
            }
        });
/**
        try {
            leController = new Controller();
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
**/
    }
}
