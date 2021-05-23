/*
 * Inventaire.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package org.thunderbot.flashofshadow.client.gameState.entite.Personnage.UI;

import org.newdawn.slick.*;

/**
 * Inventaire de joueur, et conteneur des objets
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class Inventaire {

    private final int centreX;
    private final int centreY;

    /**
     * Indique si l'inventaire est visible ou pas
     */
    private boolean showInventaire;

    Image inventaire;

    public Inventaire( GameContainer gameContainer) throws SlickException {
        inventaire = new Image("res/drawable/gameState/entite/personnage/UI/inventaire.png");
        showInventaire = false;
        centreX = gameContainer.getWidth() / 2 - inventaire.getWidth() / 2;
        centreY = gameContainer.getHeight() / 2 - inventaire.getHeight() / 2;
    }

    public void render(Graphics graphics) {

        if (showInventaire) {
            graphics.resetTransform();  // Permet de ne pas deplacer l'image avec le joueur
            graphics.drawImage(inventaire, centreX, centreY); // Position de l'image
        }
    }

    public void setShow(boolean showInventaire) {
        this.showInventaire = showInventaire;
    }
    public boolean isShow() {
        return this.showInventaire;
    }

    public boolean isSauvegarde(int x, int y) {
        return y > 80 + this.centreY && y < 105 + this.centreY && x > 45+this.centreX && x < 145+this.centreX && showInventaire;
    }

    public boolean isCharger(int x, int y) {
        return y > 110 + this.centreY && y < 135 + this.centreY && x > 45+this.centreX && x < 145+this.centreX && showInventaire;
    }

    public boolean isRetour(int x, int y) {
        return y > 140 + this.centreY && y < 165 + this.centreY && x > 45+this.centreX && x < 145+this.centreX && showInventaire;
    }

    public boolean isQuitter(int x, int y) {
        return y > 170 + this.centreY && y < 195 + this.centreY && x > 45+this.centreX && x < 145+this.centreX && showInventaire;
    }
}
