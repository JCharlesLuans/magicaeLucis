/*
 * Inventaire.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Jeu.UI;

import org.newdawn.slick.*;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class Inventaire {

    private final int centreX;
    private final int centreY;
    private final int x;
    private final int y;
    /**
     * Indique si l'inventaire est visible ou pas
     */
    private boolean showInventaire;

    Image inventaire;

    public Inventaire( GameContainer gameContainer) throws SlickException {
        inventaire = new Image("Ressources/HUD/Affichage_Dialogue.png");
        showInventaire = false;
        centreX = gameContainer.getWidth() / 2 - inventaire.getWidth() / 2;
        centreY = gameContainer.getHeight() / 2 - inventaire.getHeight() / 2;
        x = gameContainer.getWidth() / 2 + inventaire.getWidth() / 2;
        y = gameContainer.getWidth() / 2 + inventaire.getHeight() / 2;
        System.out.println("X : " + x + "\nY : " + y);
    }

    public void affichage(Graphics graphics) {

        if (showInventaire) {

            graphics.resetTransform();  // Permet de ne pas deplacer l'image avec le joueur
            graphics.drawImage(inventaire, centreX, centreY); // Position de l'image
        }
    }

    public void setShowInventaire(boolean showInventaire) {
        this.showInventaire = showInventaire;
    }

    public boolean isSauvegarde(int x, int y) {
        System.out.println("---------------------------------------------------------------------");
        System.out.println("This X : "+ this.x + "\nThis Y : "+ this.y);
        System.out.println("X : "+ x + "\nY : "+y);
        System.out.println("---------------------------------------------------------------------");
        return y > 80 + this.y && y < 105 + this.y && x > 45+this.y && x < 145+this.y && showInventaire;
    }
}
