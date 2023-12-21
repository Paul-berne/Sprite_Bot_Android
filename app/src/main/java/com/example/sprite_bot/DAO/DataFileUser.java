package com.example.sprite_bot.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;

import com.example.sprite_bot.control.Controller;
import com.example.sprite_bot.model.Player;
import tools.BCrypt;

public class DataFileUser extends SQLiteOpenHelper {

    private Controller myController;
    private SQLiteDatabase db;

    // Nom de la base de données
    private static final String DATABASE_NAME = "your_database_name.db";
    private static final int DATABASE_VERSION = 1;

    // Table Player
    private static final String TABLE_PLAYER = "player";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PLAYER_NAME = "player_name";

    private Player lePlayer;

    public DataFileUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        try {
            this.myController = new Controller();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créez votre table Player si elle n'existe pas encore
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYER + " (" +
                COLUMN_LOGIN + " TEXT PRIMARY KEY," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_PLAYER_NAME + " TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mise à jour de la base de données si nécessaire
        // Peut être laissé vide pour le moment
    }

    // Méthode pour vérifier les informations de connexion de l'utilisateur
    public boolean lireUserSQL(String theLogin, String thePassword) throws ParseException {
        boolean verif = false;

        try {
            // Requête SQL pour récupérer les informations de tous les joueurs
            String sqlQuery = "SELECT * FROM " + TABLE_PLAYER;
            Cursor cursor = db.rawQuery(sqlQuery, null);

            // Parcourt les résultats de la requête
            while (cursor.moveToNext()) {
                String login = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_NAME));

                // Vérifie si les informations de connexion correspondent
                if (login.equals(theLogin) && BCrypt.checkpw(thePassword, password)) {
                    verif = true;
                    this.lePlayer = new Player(login, password, name);
                    break;
                }
            }

            cursor.close();
        } catch (SQLException e) {
            // Gère les exceptions liées à la lecture des informations de l'utilisateur
            e.printStackTrace();
        }

        return verif;
    }

    // Méthode pour changer le mot de passe de l'utilisateur
    public void ChangePasswordUser(String theLogin, String thePassword) {
        // Hash le nouveau mot de passe avec un sel
        String passwordToHash = thePassword;
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(passwordToHash, salt);

        // Requête SQL pour mettre à jour le mot de passe de l'utilisateur
        String sqlQuery = "UPDATE " + TABLE_PLAYER + " SET " + COLUMN_PASSWORD + " = '" + hashedPassword + "' WHERE " + COLUMN_LOGIN + " = '" + theLogin + "'";
        try {
            // Exécute la requête de mise à jour
            db.execSQL(sqlQuery);
        } catch (SQLException e) {
            // Gère les exceptions liées à la mise à jour du mot de passe
            e.printStackTrace();
        }
    }

    // Getter pour l'objet Player associé à l'utilisateur
    public Player getLePlayer() {
        return lePlayer;
    }
}
