/*
 * CombatEnnemi.java                         16/02/2020
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
public class CombatEnnemi {

    private Image ennemi;

    private int pv = 25;

    public void init() throws SlickException {
        ennemi = new Image("src/Ressources/Combat/orc.png").getScaledCopy(0.25f);
    }

    public void render(GameContainer gameContainer, Graphics graphics) {

        ennemi.drawCentered(gameContainer.getWidth() * 3/4f, gameContainer.getHeight() /5f);

        Font font = graphics.getFont();
        String texte = Integer.toString(pv);

        font.drawString(gameContainer.getWidth() * 3/4f,
                       gameContainer.getHeight() /5f - ennemi.getHeight() /1.5f, texte, Color.black);
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }
}
