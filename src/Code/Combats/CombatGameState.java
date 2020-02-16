/*
 * CombatGameState.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Combats;

import Code.Jeu.MapGameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class CombatGameState extends BasicGameState  {

    public static final int ID = 3;

    private StateBasedGame game;
    private Image background;
    private CombatEnnemi ennemi;
    private CombatEnnemi hero;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.game = stateBasedGame;
        background = new Image("src/Ressources/Combat/font.png");
        ennemi.init();
        hero.init();
        }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0,0, gameContainer.getWidth(), gameContainer.getHeight());
        ennemi.render(gameContainer);
        hero.render(gameContainer);
        }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void keyPressed(int key, char c) {
        game.enterState(MapGameState.ID);
    }
}
