/*
 * GameOver.java                         19/02/2020
 * Pas de copyright (pour le moment :)
 */

package statiqueState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Affichage de la page de Game Over. Gestion des menu de la pages
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class GameOverState extends BasicGameState {

    public static int ID = 4;
    private Image background,
                  gameOver,
                  retourMenu;

    private Music musique;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        musique.loop();
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        musique.stop();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        musique = new Music("res/son/musique/gameOver/Secret_Second_Earth.ogg");
        background = new Image("res/drawable/statiqueState/fond/menu_principal.jpg");
        gameOver = new Image("res/drawable/statiqueState/gameOver/textes/game_over.png");
        retourMenu = new Image("res/drawable/statiqueState/gameOver/textes/back_menu.png");

    }

    @Override
    public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        background.draw(0, 0, container.getWidth(), container.getHeight());
        gameOver.draw(container.getWidth() /2f - gameOver.getWidth() /2f, container.getHeight() / 2f - gameOver.getHeight());
        retourMenu.draw(container.getWidth() /2f - retourMenu.getWidth() /2f, (container.getHeight() / 2f - retourMenu.getHeight()) + gameOver.getHeight());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    // TODO Action du retour au menu
}
