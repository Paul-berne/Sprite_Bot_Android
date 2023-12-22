package com.example.sprite_bot.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sprite_bot.control.Controller;

import java.sql.SQLException;
import java.text.ParseException;

public class DAOsqlQuestion extends SQLiteOpenHelper {

    private Controller myController;
    private SQLiteDatabase db;

    // Nom de la base de données
    private static final String DATABASE_NAME = "your_database_name.db";
    private static final int DATABASE_VERSION = 1;

    // Table Question
    private static final String TABLE_QUESTION = "question";
    private static final String COLUMN_ID_QUESTION = "id_question";
    private static final String COLUMN_DESC_QUESTION = "desc_question";

    // Tableau de questions
    private String[] questions = new String[2];

    public DAOsqlQuestion(Context context) throws SQLException, ParseException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myController = new Controller();
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créez votre table Question si elle n'existe pas encore
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + " (" +
                COLUMN_ID_QUESTION + " INTEGER PRIMARY KEY," +
                COLUMN_DESC_QUESTION + " TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mise à jour de la base de données si nécessaire
        // Peut être laissé vide pour le moment
    }

    // Getter pour le tableau de questions
    public String[] getQuestions() {
        return questions;
    }

    // Méthode pour lire la question associée à un numéro de question depuis la base de données
    public void LireQuestionSQL(int numeroQuestion) {
        try {
            // Requête SQL pour récupérer la question associée au numéro spécifié
            String sqlQuery = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_ID_QUESTION + " = " + numeroQuestion + ";";

            Cursor cursor = db.rawQuery(sqlQuery, null);

            // Parcourt les résultats de la requête
            if (cursor.moveToFirst()) {
                int questionId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_QUESTION));
                String questionDesc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC_QUESTION));

                // Remplit le tableau de questions avec les données lues
                questions[0] = String.valueOf(questionId);
                questions[1] = questionDesc;
            }

            cursor.close();
        } catch (Exception e) {
            // Gère les exceptions liées à la lecture de la question depuis la base de données
            e.printStackTrace();
        }
    }
}
