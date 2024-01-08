package com.example.sprite_bot.control;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sprite_bot.DAO.*;
import com.example.sprite_bot.model.*;
import com.example.sprite_bot.view.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

public class Controller extends Context {

    private QuizGame theGame;
    private Player lePlayer;
    private DAOsqlQuestion leStubQuestion;
    private DAOsqlAnswer leStubAnswer;
    private ArrayList<Question> lesQuestions;
    private ArrayList<Answer> lesReponses;
    private LoginActivity myLogin;
    private Configuration myConfiguration;

    public Controller() throws ParseException, SQLException {
        this.myConfiguration = new Configuration(this);
        initializeQuestions();
    }

    private void initializeQuestions() throws SQLException, ParseException {
        lesQuestions = new ArrayList<>();
        Random random = new Random();
        ArrayList<Integer> numerosUtilises = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int randomNumber;

            do {
                randomNumber = random.nextInt(20) + 1;
            } while (numerosUtilises.contains(randomNumber));

            numerosUtilises.add(randomNumber);

            this.leStubQuestion = new DAOsqlQuestion(this);
            this.leStubQuestion.LireQuestionSQL(randomNumber);
            String[] question = this.leStubQuestion.getQuestions();

            Question laQuestion = new Question(Integer.valueOf(question[0]), question[1], new ArrayList<>());
            lesQuestions.add(laQuestion);

            this.leStubAnswer = new DAOsqlAnswer(this);
            this.leStubAnswer.LireAnswerSQL(randomNumber);
            this.lesReponses = leStubAnswer.getLesAnswer();
            laQuestion.setAnswers(lesReponses);
        }
    }

    public boolean verifyUserLogin(String login, String password) {
        DataFileUser dataFileUser = new DataFileUser(this);
        try {
            if (dataFileUser.lireUserSQL(login, password)) {
                this.lePlayer = dataFileUser.getLePlayer();
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void CreateFrameChangePassword(String thelogin) {

    }

    public void CreateQuizGameGUI() throws SQLException, ParseException {
        try {
            initializeQuestions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        this.theGame = new QuizGame(lePlayer, 0, lesQuestions);
    }

    public void CreateGameStart() {

    }

    public void CreateRecapGame(int playerscore, Boolean hasWon) {

    }

    public void generateNewQuestions() throws SQLException, ParseException {
        try {
            initializeQuestions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public QuizGame getTheGame() {
        return theGame;
    }

    public void setTheGame(QuizGame theGame) {
        this.theGame = theGame;
    }

    public Player getLePlayer() {
        return lePlayer;
    }

    public void setLePlayer(Player lePlayer) {
        this.lePlayer = lePlayer;
    }

    public ArrayList<Question> getLesQuestions() {
        return lesQuestions;
    }

    public void setLesQuestions(ArrayList<Question> lesQuestions) {
        this.lesQuestions = lesQuestions;
    }

    public ArrayList<Answer> getLesReponses() {
        return lesReponses;
    }

    public void setLesReponses(ArrayList<Answer> lesReponses) {
        this.lesReponses = lesReponses;
    }

    public LoginActivity getMyLogin() {
        return myLogin;
    }

    public void setMyLogin(LoginActivity myLogin) {
        this.myLogin = myLogin;
    }

    public Configuration getMyConfiguration() {
        return myConfiguration;
    }
}

