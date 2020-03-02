package java.gameState.phisique;

import org.newdawn.slick.Graphics;

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

        /* Test des angles de a tester */
        boolean aTesterSupGauche = (x < aTester.x && aTester.x < x + width) && (y < aTester.y && aTester.y < y + height);

        boolean aTesterSupDroit = (x < aTester.x + aTester.width && aTester.x + + aTester.width < x + width)
                               && (y < aTester.y && aTester.y < y + height);

        boolean aTesterInfGauche = (x < aTester.x && aTester.x < x + width)
                                && (y < aTester.y + aTester.height && aTester.y + aTester.height < y + height);

        boolean aTesterInfDroit = (x < aTester.x + aTester.width && aTester.x + + aTester.width < x + width)
                                && (y < aTester.y + aTester.height && aTester.y + aTester.height < y + height);

        /* Test des angles de this */
        boolean thisSupGauche = (aTester.x < x && x < aTester.x + aTester.width) && (aTester.y < y && y < aTester.y + aTester.height);

        boolean thisSupDroit = (aTester.x < x+width && x+width < aTester.x + aTester.width)
                             && (aTester.y < y && y < aTester.y + aTester.height);

        boolean thisInfGauche = (aTester.x < x && x < aTester.x + aTester.width)
                             && (aTester.y < y+height && y+height < aTester.y + aTester.height);

        boolean thisInfDroit = (aTester.x < x+width && x+width < aTester.x + aTester.width)
                                && (aTester.y < y+height && y+height < aTester.y + aTester.height);


        /* Return true si un des angles esrt compris dans l'autre hitbox*/
        return (aTesterInfDroit || aTesterInfGauche || aTesterSupDroit || aTesterSupGauche)
                || (thisInfDroit || thisInfGauche || thisSupDroit || thisSupGauche);

    }

    public boolean isInHitBox(float aTesterX, float aTesterY) {

        boolean aTesterDansX = x < aTesterX && aTesterX < x + width;  // X est dans la largeur de la hit box
        boolean aTesterDansY = y < aTesterY && aTesterY < y + height; // Y est dans la hauteur de la hit box

        return aTesterDansX && aTesterDansY;
    }

    public void render(Graphics graphics) {
        graphics.drawRect((int)x, (int)y, (int)width, (int)height);
    }

    public void setX(float newX) {x = newX;}
    public void setY(float newY) {y = newY;}

    public float getHeight() {
        return height;
    }
    public float getWidth() {
        return width;
    }
}
