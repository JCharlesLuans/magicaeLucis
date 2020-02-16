/*
 * Message.java                         16/02/2020
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
public class Message {

    private final int centreX;
    private final int centreY;

    /**
     * Indique si l'inventaire est visible ou pas
     */
    private boolean show;

    /**
     * Image de bases des messages
     */
    Image imgMessage;

    /**
     * Message que affiche le texte
     */
    String message;

    public Message(GameContainer gameContainer) throws SlickException {
        imgMessage = new Image("src/Ressources/HUD/message.png");
        centreX = gameContainer.getWidth() / 2 - imgMessage.getWidth() / 2;
        centreY = gameContainer.getHeight() / 2 - imgMessage.getHeight() / 2;
    }

    public void render(Graphics graphics) {

        if (show) {
            graphics.resetTransform();  // Permet de ne pas deplacer l'image avec le joueur
            graphics.drawImage(imgMessage, centreX, centreY); // Position de l'image
            graphics.setColor(new Color(Color.black));
            graphics.drawString(message, centreX+25, centreY+55);
        }
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void affichage(String message) {
        show = true;
        this.message = message;
    }

    public boolean isOk(int x, int y) {
        return x > 370 && x < 400 && y > 340 && y < 360;
    }
    public boolean isNo(int x, int y) {
        return x > 540 && x < 560 && y > 280 && y < 315;
    }

}
