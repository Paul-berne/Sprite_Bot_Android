package com.example.sprite_bot.control;

import android.content.Context;
import android.content.res.AssetManager;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    // Properties to store configuration data
    private Properties properties;

    // Encryptor to decrypt sensitive data
    private StandardPBEStringEncryptor encryptor;

    // Constructor
    public Configuration(Context context) {
        // Initialize properties and encryptor
        this.properties = new Properties();
        this.encryptor = new StandardPBEStringEncryptor();

        // Set the password for the encryptor
        this.encryptor.setPassword("P@ssw0rdsio");

        try {
            // Load the configuration properties from the assets folder
            loadProperties(context.getAssets(), "vtg.cfg");
        } catch (IOException e) {
            // Handle any IOException if loading the file fails
            e.printStackTrace();
        }
    }

    // Method to read a property and decrypt it
    public String readProperty(String theKey) {
        // Decrypt the value of the property using the encryptor
        return encryptor.decrypt(properties.getProperty(theKey));
    }

    // Load properties from a file in the assets folder
    private void loadProperties(AssetManager assetManager, String fileName) throws IOException {
        try (InputStream input = assetManager.open(fileName)) {
            // Load the configuration properties from the file
            properties.load(input);
        }
    }
}