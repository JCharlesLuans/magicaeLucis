package Code.Jeu;

import org.newdawn.slick.Graphics;

import java.awt.*;

/**
 * Hit box des objets physique qui apparaissent sur la map
 * Permet les interaction physique
 * Definit par 2 point sur la map qui sont l'angle gauche de la hit box (0,0)
 * et part une longueur et une largeur
 */
public class HitBox {

    private float x, // Position de l'angle haut gauche sur la carte
                  y; // Position de l'angle haut gauche sur la carte

    private float height, // Hauteur
                 width;  // Largeur


    /**
     * Créer une nouvelle HitBox
     * @param newX : x de l'angle supperieur gauche
     * @param newY : y de l'angle supperieur gauche
     * @param newHeight : hauteur de la hit box
     * @param newWidth : largeur de la hit box
     */
    public HitBox(float newX, float newY, float newHeight, float newWidth) {
        x = newX;
        y = newY;
        height = newHeight;
        width = newWidth;
    }

    /**
     * Test si il y a une colision entre 2 hit box
     * @param aTester : deuxieme hit box
     * @return true si colision
     */
    public boolean isColision(HitBox aTester) {

        boolean aTesterDansX = x < aTester.x && aTester.x < x + width;
        boolean xDansATester = aTester.x < x && x < aTester.x + aTester.width;

        boolean aTesterDansY = y < aTester.y && aTester.y < y + height;
        boolean yDansATester = aTester.y < y && y < aTester.y + aTester.height;

        return (aTesterDansX && aTesterDansY) || (xDansATester && yDansATester);

    }

    public void render(Graphics graphics) {
        graphics.drawRect((int)x, (int)y, (int)width, (int)height);
    }

    public void setX(float newX) {x = newX;}
    public void setY(float newY) {y = newY;}

}
