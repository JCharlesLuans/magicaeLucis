/*
 * Hud.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Jeu.UI;

import Code.Jeu.Personnage.Personnage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

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

    private final int LONGUEUR = 154; // Longueur maximale de la barre
    private final int HAUTEUR = 15;

    private static final Color COULEUR_VIE= new Color(255, 0, 0);
    private static final Color COULEUR_MANA = new Color(0, 0, 255);
    private static final Color COULEUR_XP = new Color(0, 255, 0);

    private Personnage hero;

    /**
     * Image a afficher
     */
    private Image barreJoueur;

    public BarresStats(Personnage hero) throws SlickException {
        barreJoueur = new Image("Ressources/HUD/barre_d_etat.png");
        this.hero = hero;
    }

    public void affichage(Graphics graphics) {

        graphics.resetTransform();  // Permet de ne pas deplacer l'image avec le joueur
        float pourcentagePv;
        pourcentagePv =((float) hero.getPv() )/ ((float) hero.getTotalPv()) * LONGUEUR;

        graphics.setColor(COULEUR_VIE);
        graphics.fillRect(DEBUT_X, DEBUT_Y_VIE, pourcentagePv, HAUTEUR);

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
