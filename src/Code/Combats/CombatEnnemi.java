/*
 * CombatEnnemi.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Combats;

import Code.Jeu.Personnage.Stats;
import org.newdawn.slick.*;

/**
 * Ennemi lors des combats
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class CombatEnnemi {

    private Image ennemi;

    private Stats stats;

    public void init(int niveau) throws SlickException {
        ennemi = new Image("src/Ressources/Combat/orc.png").getScaledCopy(0.25f);
        stats = new Stats(niveau);
    }

    public void render(GameContainer gameContainer, Graphics graphics) {

        ennemi.drawCentered(gameContainer.getWidth() * 3/4f, gameContainer.getHeight() /5f);

        Font font = graphics.getFont();
        String texte = Integer.toString(stats.getPv());

        font.drawString(gameContainer.getWidth() * 3/4f,
                       gameContainer.getHeight() /5f - ennemi.getHeight() /1.5f, texte, Color.black);
    }

    public Stats getStats() {
        return stats;
    }
}
