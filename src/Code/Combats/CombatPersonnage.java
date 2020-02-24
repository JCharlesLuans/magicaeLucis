/*
 * CombatPersonnage.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Combats;

import Code.Jeu.MapGameState;
import Code.Jeu.Personnage.Personnage;
import Code.Jeu.Personnage.Stats;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class CombatPersonnage {

    private Stats stats;

    private Image hero; // Image du héro

    public void init(MapGameState mapGameState) throws SlickException {

        stats = mapGameState.getHero().getStats();
        hero = new Image("src/Ressources/Combat/hero.png").getScaledCopy(0.25f);
    }

    public void render(GameContainer gameContainer, Graphics graphics) {
        hero.drawCentered(gameContainer.getWidth() /4f, gameContainer.getHeight() * (1.5f/4f));

        Font font = graphics.getFont();
        String texte = Integer.toString(stats.getPv());

        font.drawString(gameContainer.getWidth() /4f,
                gameContainer.getHeight() * (1.5f/4f) - hero.getHeight() /1.5f, texte, Color.black);
    }

    public Stats getStats() {
        return stats;
    }


}
