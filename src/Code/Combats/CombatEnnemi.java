/*
 * CombatEnnemi.java                         16/02/2020
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
public class CombatEnnemi {

    private Image ennemi;

    public void init() throws SlickException {
        ennemi = new Image("src/Ressources/Combat/orc.png").getScaledCopy(0.25f);
    }

    public void render(GameContainer gameContainer) {
        ennemi.drawCentered(gameContainer.getWidth() * 3/4f, gameContainer.getHeight() /5f);
    }
}
