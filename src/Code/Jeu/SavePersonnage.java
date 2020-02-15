/*
 * SavePersonnage.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Jeu;

import java.io.*;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class SavePersonnage implements Serializable {

    /**
     * Position du personnage
     */
    private float positionX,
                  positionY;
    /**
     * Direction du personnage
     */
    private int direction;

    /**
     * Nom de la carte sur laquelle se trouve le joueur
     */
    String map;

    public SavePersonnage() {
        positionX = positionY = direction = 0;
        map = "";
    }

    public SavePersonnage(Personnage personnage) {
        positionX = personnage.getPositionX();
        positionY = personnage.getPositionY();
        direction = personnage.getDirection();
        map = personnage.getMap().getNomMap();
        sauvegarde();
    }

    private void sauvegarde() {
        try {
            File save = new File("src/Ressources/Sauvegardes/save.ser");
            ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(save)) ;
            oos.writeObject(this);
            System.out.print("Sauvegarde");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargement() {
        try {
            File save = new File("src/Ressources/Sauvegardes/save.ser");
            ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(save));
            SavePersonnage tmp = (SavePersonnage) ois.readObject();
            positionX = tmp.positionX;
            positionY = tmp.positionY;
            direction = tmp.direction;
            map = tmp.map;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public int getDirection() {
        return direction;
    }

    public String getMap() {
        return map;
    }
}
