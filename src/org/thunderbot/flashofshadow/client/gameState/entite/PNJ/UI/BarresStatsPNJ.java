/*
 * Hud.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package org.thunderbot.flashofshadow.client.gameState.entite.PNJ.UI;

import org.thunderbot.flashofshadow.client.gameState.entite.PNJ.Mob;
import org.thunderbot.flashofshadow.client.gameState.phisique.Stats;
import org.newdawn.slick.*;

/**
 * Affichage de la barre de vie d'un PNJ
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class BarresStatsPNJ {



    private int LONGUEUR = 109;     // Longueur maximale de la barre
    private final int HAUTEUR = 13; // Hauteur de la barre de vie

    /** Couleur de la vie */
    private static final Color COULEUR_VIE= new Color(255, 0, 0);

    float positionX, // Position de la barre de vie
          positionY; // Position de la barre de vie

    private float debutX; // Début de la barre de vie
    private float debutY; // Début de la barre de vie

    /**
     * Stats a afficher
     */
    private Stats stats;

    /**
     * Image a afficher
     */
    private Image barre;

    public BarresStatsPNJ(Mob mob) throws SlickException {
        positionX = mob.getPositionX()-64;
        positionY = mob.getPositionY()-75;

        debutX = 9 + positionX;
        debutY = 3 + positionY;

        stats = mob.getStats();
        barre = new Image("res/drawable/org.thunderbot.flashofshadow.client.gameState/entite/PNJ/UI/barre_pnj.png");
        this.stats = stats;
    }

    public void render(Graphics graphics) throws SlickException {


        float pourcentagePv  = ((float) stats.getPv() )/ ((float) stats.getTotalPv()) * LONGUEUR;

        graphics.setColor(COULEUR_VIE);
        graphics.fillRect(debutX, debutY, pourcentagePv, HAUTEUR);

        graphics.drawImage(barre, positionX, positionY); // Position de l'image
    }

    public Image getBarre() {
        return barre;
    }
}
