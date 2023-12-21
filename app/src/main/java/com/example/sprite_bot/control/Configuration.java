package com.example.sprite_bot.control;

import android.content.Context;

import com.example.sprite_bot.R;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Configuration {

    // Propriétés pour stocker les données de configuration
    private Properties properties;

    // Encrypteur pour décrypter les données sensibles
    private StandardPBEStringEncryptor encryptor;

    // Contexte Android pour accéder aux ressources
    private Context context;

    // Constructeur prenant le contexte Android en paramètre
    public Configuration() {
        // Initialise les propriétés et l'encrypteur
        this.properties = new Properties();
        this.encryptor = new StandardPBEStringEncryptor();

        // Définit le mot de passe pour l'encrypteur
        this.encryptor.setPassword("P@ssw0rdsio");

        // Enregistre le contexte Android
        this.context = context;

        // Charge les propriétés de configuration à partir du fichier dans res/raw
        loadPropertiesFromRawResource(R.raw.vtg);
    }

    // Méthode pour lire une propriété et la décrypter
    public String readProperty(String theKey) {
        // Décrypte la valeur de la propriété en utilisant l'encrypteur
        return encryptor.decrypt(properties.getProperty(theKey));
    }

    // Méthode pour charger les propriétés à partir d'une ressource "raw"
    private void loadPropertiesFromRawResource(int resourceId) {
        try {
            // Ouvre le flux d'entrée vers la ressource "raw"
            InputStream inputStream = context.getResources().openRawResource(resourceId);

            // Lit le contenu du fichier et charge les propriétés
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(reader);

            // Ferme le flux d'entrée
            inputStream.close();
        } catch (IOException e) {
            // Gère une éventuelle IOException si le chargement du fichier échoue
            e.printStackTrace();
        }
    }
}
