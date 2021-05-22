package org.thunderbot.flashofshadow.client;

import org.thunderbot.flashofshadow.client.gameState.MapGameState;
import org.thunderbot.flashofshadow.client.statiqueState.GameOverState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.thunderbot.flashofshadow.client.statiqueState.MenuGameState;

public class MagicaeLucis extends StateBasedGame {
    /**
     * Methode de lancement du jeu
     * @param args non utilisé
     */
    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new MagicaeLucis(), 1240, 720, false).start();
    }

    public MagicaeLucis() {
        super("Magicae Lucis");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new MenuGameState());
        addState(new MapGameState());
        addState(new GameOverState());
    }
}
