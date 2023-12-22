package com.example.sprite_bot.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import com.example.sprite_bot.control.Controller;
import com.example.sprite_bot.model.Answer;

public class DAOsqlAnswer extends SQLiteOpenHelper {

    private Controller myController;
    private SQLiteDatabase db;

    // Nom de la base de données
    private static final String DATABASE_NAME = "your_database_name.db";
    private static final int DATABASE_VERSION = 1;

    // Table Answer
    private static final String TABLE_ANSWER = "answer";
    private static final String COLUMN_ID_ANSWER = "id_answer";
    private static final String COLUMN_DESC_ANSWER = "desc_answer";
    private static final String COLUMN_IS_CORRECT = "is_correct";
    private static final String COLUMN_ID_QUESTION = "id_question";

    // Liste des réponses
    private ArrayList<Answer> lesAnswer;

    public DAOsqlAnswer(Context context) throws SQLException, ParseException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myController = new Controller();
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créez votre table Answer si elle n'existe pas encore
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_ANSWER + " (" +
                COLUMN_ID_ANSWER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DESC_ANSWER + " TEXT," +
                COLUMN_IS_CORRECT + " INTEGER," +
                COLUMN_ID_QUESTION + " INTEGER," +
                "FOREIGN KEY(" + COLUMN_ID_QUESTION + ") REFERENCES question(id_question));";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mise à jour de la base de données si nécessaire
        // Peut être laissé vide pour le moment
    }

    // Méthode pour lire les réponses associées à une question depuis la base de données
    public void LireAnswerSQL(int numeroQuestion) {
        try {
            // Initialise la liste des réponses
            lesAnswer = new ArrayList<>();

            // Requête SQL pour récupérer les réponses associées à la question spécifiée
            String sqlQuery = "SELECT * FROM " + TABLE_ANSWER +
                    " INNER JOIN question ON " + TABLE_ANSWER + "." + COLUMN_ID_QUESTION + " = question.id_question " +
                    " WHERE question.id_question = " + numeroQuestion + ";";

            Cursor cursor = db.rawQuery(sqlQuery, null);

            // Parcourt les résultats de la requête
            if (cursor.moveToFirst()) {
                do {
                    String code_Answer = cursor.getString(cursor.getColumnIndex(COLUMN_ID_ANSWER));
                    boolean is_correct = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_CORRECT)) == 1;
                    String reponseDesc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC_ANSWER));

                    // Crée un objet Answer et l'ajoute à la liste
                    Answer answer = new Answer(reponseDesc, is_correct);
                    lesAnswer.add(answer);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            // Gère les exceptions liées à la lecture des réponses depuis la base de données
            e.printStackTrace();
        }
    }

    // Getter pour la liste des réponses
    public ArrayList<Answer> getLesAnswer() {
        return lesAnswer;
    }
}
