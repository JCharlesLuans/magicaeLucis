/*
 * CombatPersonnage.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Combats;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class CombatPersonnage {

    private Image hero; // Image du héro

    public void init() throws SlickException {
        hero = new Image("src/Ressources/Combat/hero.png").getScaledCopy(0.25f);
    }

    public void render(GameContainer gameContainer) {
        hero.drawCentered(gameContainer.getWidth() /4f, gameContainer.getHeight() * (1.5f/4f));
    }

}
