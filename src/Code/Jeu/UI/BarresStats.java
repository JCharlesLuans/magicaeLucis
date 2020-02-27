/*
 * Hud.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Jeu.UI;

import Code.Jeu.Personnage.Personnage;
import Code.Jeu.Personnage.Stats;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
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

    private final int DEBUT_X = 84 + POS_X;

    private final int DEBUT_Y_VIE = 4 + POS_Y;
    private final int DEBUT_Y_MANA = 24 + POS_Y;
    private final int DEBUT_Y_XP = 44 + POS_Y;

    private final int LONGUEUR = 154; // Longueur maximale de la barre
    private final int HAUTEUR = 15;

    private static final Color COULEUR_VIE= new Color(255, 0, 0);
    private static final Color COULEUR_MANA = new Color(0, 0, 255);
    private static final Color COULEUR_XP = new Color(0, 255, 0);

    private Stats stats;

    private Font font;

    /**
     * Image a afficher
     */
    private Image barreJoueur;

    public BarresStats(Stats stats) throws SlickException {
        font = new UnicodeFont(new java.awt.Font("DejaVu Serif", java.awt.Font.PLAIN, 20));
        ((UnicodeFont) font).addAsciiGlyphs();
        ((UnicodeFont) font).addGlyphs(400,600);
        ((UnicodeFont) font).getEffects().add(new ColorEffect(java.awt.Color.WHITE));//Ca sert a quoi ?
        ((UnicodeFont) font).loadGlyphs();

        barreJoueur = new Image("Ressources/HUD/barre_d_etat.png");
        this.stats = stats;
    }

    public void affichage(Graphics graphics) throws SlickException {

        graphics.resetTransform();  // Permet de ne pas deplacer l'image avec le joueur
        float pourcentagePv  = ((float) stats.getPv() )/ ((float) stats.getTotalPv()) * LONGUEUR;
        float pourcentageMana  = ((float) stats.getMana() )/ ((float) stats.getTotalMana()) * LONGUEUR;
        float pourcentageXp  = ((float) stats.getXp() )/ ((float) stats.getTotalXp()) * LONGUEUR;

        graphics.setColor(COULEUR_VIE);
        graphics.fillRect(DEBUT_X, DEBUT_Y_VIE, pourcentagePv, HAUTEUR);

        graphics.setColor(COULEUR_MANA);
        graphics.fillRect(DEBUT_X, DEBUT_Y_MANA, pourcentageMana, HAUTEUR);

        graphics.setColor(COULEUR_XP);
        graphics.fillRect(DEBUT_X, DEBUT_Y_XP, pourcentageXp, HAUTEUR);

        graphics.drawImage(barreJoueur, POS_X, POS_Y); // Position de l'image

        graphics.setFont(font);
        font.drawString(25 + POS_X, 20 + POS_Y, Integer.toString(stats.getNiveau()), Color.white);
    }

    public Image getBarreJoueur() {
        return barreJoueur;
    }
}
