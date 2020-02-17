/*
 * CombatPersonnage.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Combats;

import org.newdawn.slick.*;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class CombatPersonnage {

    private int pv = 100;

    private Image hero; // Image du héro

    public void init() throws SlickException {
        hero = new Image("src/Ressources/Combat/hero.png").getScaledCopy(0.25f);
    }

    public void render(GameContainer gameContainer, Graphics graphics) {
        hero.drawCentered(gameContainer.getWidth() /4f, gameContainer.getHeight() * (1.5f/4f));

        Font font = graphics.getFont();
        String texte = Integer.toString(pv);

        font.drawString(gameContainer.getWidth() /4f,
                gameContainer.getHeight() * (1.5f/4f) - hero.getHeight() /1.5f, texte, Color.black);
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

}
