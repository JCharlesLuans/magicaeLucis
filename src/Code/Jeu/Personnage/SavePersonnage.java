/*
 * SavePersonnage.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Jeu.Personnage;

import java.beans.XMLEncoder;
import java.io.*;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class SavePersonnage {

    private int mana;
    private int xp;
    private int niveau;
    private float camPosY;
    private float camPosX;
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
    private String map;

    private int pv;

    private int totalPv;

    public SavePersonnage() {
        positionX = positionY = direction = 0;
        map = "";
    }

    public SavePersonnage(Personnage personnage, Camera cam) {
        positionX = personnage.getPositionX();
        positionY = personnage.getPositionY();
        direction = personnage.getDirection();
        map = personnage.getMap().getNomMap();

        pv = personnage.getPv();
        mana = personnage.getMana();
        xp = personnage.getXp();

        niveau = personnage.getNiveau();


        camPosX = cam.getPositionX();
        camPosY = cam.getPositionY();

    }

    public void chargement() {
        try {
            File save = new File("src/Ressources/Sauvegardes/save.ser");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(save));
            SavePersonnage tmp = (SavePersonnage) ois.readObject();
            positionX = tmp.positionX;
            positionY = tmp.positionY;
            direction = tmp.direction;
            map = tmp.map;
            pv = tmp.pv;
            mana = tmp.mana;
            xp = tmp.xp;
            niveau = tmp.niveau;

            camPosX = tmp.camPosX;
            camPosY = tmp.camPosY;

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

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public float getCamPosY() {
        return camPosY;
    }

    public void setCamPosY(float camPosY) {
        this.camPosY = camPosY;
    }

    public float getCamPosX() {
        return camPosX;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public void setCamPosX(float camPosX) {
        this.camPosX = camPosX;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }
}
