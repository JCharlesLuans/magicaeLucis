/*
 * MainScreenGameState.java                         15/02/2020
 * Pas de copyright (pour le moment :)
 */

package org.thunderbot.flashofshadow.client.statiqueState;

import org.thunderbot.flashofshadow.client.gameState.MapGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Menu principal du jeu
 * Game state du menu
 * ID = 1
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class MenuGameState extends BasicGameState {

    public static final int ID = 1;
    private Image background;
    private StateBasedGame game;
    private GameContainer gameContainer;
    private Music click;

    @Override
    public int getID() {
        return ID;
    }
    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Music background = new Music("res/son/musique/menuPrincipal/Sahara_Rains.ogg");
        background.loop();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        click = new Music("res/son/bruitage/menus/click.ogg");
        game = stateBasedGame;
        this.gameContainer = gameContainer;
        background = new Image("res/drawable/org.thunderbot.flashofshadow.client.statiqueState/fond/menu_principal.jpg");
    }

    @Override
    public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0, 0, container.getWidth(), container.getHeight());
        graphics.setColor(Color.white);
        graphics.drawString("Nouvelle partie", 300, 200);
        graphics.drawString("Charger une partie", 300, 250);
        graphics.drawString("Quitter", 300, 300);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {

        System.out.println(x +  " " + y);

        if (button == Input.MOUSE_LEFT_BUTTON && isOnSauvegarde(x, y)) {
            click.play();
            ((MapGameState)game.getState(MapGameState.ID)).setCharger(true);
            game.enterState(MapGameState.ID);
        }

        if (button == Input.MOUSE_LEFT_BUTTON && isOnJouer(x, y)) {
            click.play();
            game.enterState(MapGameState.ID);
        }

        if (button == Input.MOUSE_LEFT_BUTTON && isOnQuitter(x,y)) {
            click.play();
            gameContainer.exit();
        }
    }

    private boolean isOnSauvegarde(int x, int y) {
      return x < 500 && x > 300 && y < 265 && y > 250;
    }

    private boolean isOnJouer(int x, int y) {
        return x < 500 && x > 300 && y < 215 && y > 200;
    }

    private boolean isOnQuitter(int x, int y) {
        return x < 500 && x > 300 && y < 315 && y > 300;
    }


}
