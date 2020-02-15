/*
 * MainScreenGameState.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Menu;

import Code.Jeu.Map;
import Code.Jeu.MapGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class MenuGameState extends BasicGameState {

    public static final int ID = 1;
    private Image background;
    private StateBasedGame game;

    @Override
    public int getID() {
        return ID;
    }
    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Music background = new Music("src/Ressources/Musique/Sahara_Rains.ogg");
        background.loop();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        game = stateBasedGame;
        background = new Image("src/Ressources/Menu/font.jpg");
    }

    @Override
    public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, container.getWidth(), container.getHeight());
        graphics.drawString("Jouer", 300, 200);
        graphics.drawString("Charger une sauvegarde", 300, 250);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (button == Input.MOUSE_LEFT_BUTTON && isOnSauvegarde(x, y)) {
            ((MapGameState)game.getState(MapGameState.ID)).setCharger(true);
            game.enterState(MapGameState.ID);
        }
        if (button == Input.MOUSE_LEFT_BUTTON && isOnJouer(x, y)) {
            game.enterState(MapGameState.ID);
        }
    }

    private boolean isOnSauvegarde(int x, int y) {
      return x < 500 && x > 300 && y < 265 && y > 250;
    }

    private boolean isOnJouer(int x, int y) {
        return x < 500 && x > 300 && y < 215 && y > 200;
    }


}
