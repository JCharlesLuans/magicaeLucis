/*
 * Hud.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Jeu.UI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Affichage de la barre de vie, de mana et d'XP
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class BarresStats {

    private final int POS_X= 10,
                      POS_Y = 10;

    private final int DEBUT_X = 9 + POS_X;

    private final int DEBUT_Y_VIE = 3 + POS_Y;
    private final int DEBUT_Y_MANA = 24 + POS_Y;
    private final int DEBUT_Y_XP = 45 + POS_Y;

    private final int LONGUEUR = 154;
    private final int HAUTEUR = 15;

    private static final Color COULEUR_VIE= new Color(255, 0, 0);
    private static final Color COULEUR_MANA = new Color(0, 0, 255);
    private static final Color COULEUR_XP = new Color(0, 255, 0);

    /**
     * Image a afficher
     */
    private Image barreJoueur;

    public BarresStats() throws SlickException {
        barreJoueur = new Image("Ressources/HUD/barre_d_etat.png");
    }

    public void affichage(Graphics graphics) {

        graphics.resetTransform();  // Permet de ne pas deplacer l'image avec le joueur


        graphics.setColor(COULEUR_VIE);
        graphics.fillRect(DEBUT_X, DEBUT_Y_VIE, LONGUEUR, HAUTEUR);

        graphics.setColor(COULEUR_MANA);
        graphics.fillRect(DEBUT_X, DEBUT_Y_MANA, LONGUEUR, HAUTEUR);

        graphics.setColor(COULEUR_XP);
        graphics.fillRect(DEBUT_X, DEBUT_Y_XP, LONGUEUR, HAUTEUR);

        graphics.drawImage(barreJoueur, 10, 10); // Position de l'image

    }

    public Image getBarreJoueur() {
        return barreJoueur;
    }
}
