/*
 * XMLencoder.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package gameState.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class XMLTools {

    /**
     * Serialisation d'un objet dans un fichier
     * @param object objet a serialiser
     * @param filename chemin du fichier
     */
    public static void encodeToFile(Object object, String fileName) throws FileNotFoundException, IOException {
        // ouverture de l'encodeur vers le fichier
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(fileName));
        try {
            // serialisation de l'objet
            encoder.writeObject(object);
            encoder.flush();
        } finally {
            // fermeture de l'encodeur
            encoder.close();
        }
    }

    /**
     * Deserialisation d'un objet depuis un fichier
     * @param filename chemin du fichier
     */
    public static Object decodeFromFile(String fileName) {
        Object object = null;
        // ouverture de decodeur
        try {
            XMLDecoder decoder = new XMLDecoder(new FileInputStream(fileName));
            try {
                // deserialisation de l'objet
                object = decoder.readObject();
            } finally {
                // fermeture du decodeur
                decoder.close();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return object;
    }

}
