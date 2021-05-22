package org.thunderbot.flashofshadow.client.gameState.entite;

import org.thunderbot.flashofshadow.client.gameState.carte.Map;
import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.Personnage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Camera du jeu
 * @author J-Charles Luans
 */
public class Camera {

    /* Indique les position */
    private final int HAUT = 0,
                      GAUCHE = 1,
                      BAS = 2,
                      DROITE = 3;

    /** Position de la camera */
    private float positionX,
                  positionY;

    /** Personnage que dois suivre la camera */
    Personnage personnage;

    GameContainer container;

    Map map;

    /**
     * Créer une nouvelle camera
     * @param personnage
     */
    public Camera(Personnage personnage, GameContainer container, Map map) {
        this.positionX = personnage.getPositionX();
        this.positionY = personnage.getPositionY();
        this.personnage = personnage;
        this.container = container;
        this.map = map;
    }

    public void render(Graphics graphics) {
        graphics.translate(centreX(), centreY());
    }

    /**
     * Actualise et deplace la camera en fonction du joueur
     */
    public void actualisation() {

        int largeurCarte = map.getWidth() * 32; // Récupere la demi largeur de l'ecran
        int hauteurCarte = map.getHeight()  * 32; // Récupere la demi hauteur de l'ecran
        int demiEcranLarg = container.getWidth() / 2;
        int demiEcranHaut = container.getHeight() / 2;


        if (positionX < largeurCarte - demiEcranLarg) {

            if (positionX > demiEcranLarg) {
                positionX = personnage.getPositionX();
            } else if (personnage.getDirection() == DROITE && personnage.getPositionX() > demiEcranLarg) {
                positionX = personnage.getPositionX();
            }
        } else if (personnage.getDirection() == GAUCHE && personnage.getPositionX() < largeurCarte - demiEcranLarg) {
            positionX = personnage.getPositionX();
        }

        if (positionY < hauteurCarte - demiEcranHaut) {

            if (positionY > demiEcranHaut) {
                positionY = personnage.getPositionY();
            } else if (personnage.getDirection() == BAS && personnage.getPositionY() > demiEcranHaut) {
                positionY = personnage.getPositionY();
            }
        } else if (personnage.getDirection() == HAUT && personnage.getPositionY() < hauteurCarte - demiEcranHaut) {
            positionY = personnage.getPositionY();
        }

    }



    /**
     * @param container : container de la game
     * @return les coordonnée du centre de la camera par rapport au container
     */
    public float centreX() {
        return container.getWidth() / 2f - positionX;
    }

    /**
     * @param container : container de la game
     * @return les coordonnée du centre de la camera par rapport au container
     */
    public float centreY() {
        return container.getHeight() / 2f - positionY;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
}
