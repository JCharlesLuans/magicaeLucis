/*
 * CombatGameState.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Combats;

import Code.Jeu.MapGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;
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
    private Music musique;

    private CombatEnnemi ennemi = new CombatEnnemi();
    private CombatPersonnage hero = new CombatPersonnage();

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        musique.loop();
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        musique.stop();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.game = stateBasedGame;
        musique = new Music("src/Ressources/Musique/The_Last_Encounter.ogg");
        background = new Image("src/Ressources/Combat/font.png");
        ennemi.init();
        hero.init();


        InputProvider provider = new InputProvider(gameContainer.getInput());
        provider.bindCommand(new KeyControl(Input.KEY_A), CombatCommande.ATTAQUER);
        provider.bindCommand(new KeyControl(Input.KEY_Z), CombatCommande.DEFENDRE);
        provider.bindCommand(new KeyControl(Input.KEY_E), CombatCommande.FUIR);
        provider.addListener(new CombatController(hero, ennemi, game));
        }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        background.draw(0,0, gameContainer.getWidth(), gameContainer.getHeight());
        ennemi.render(gameContainer, graphics);
        hero.render(gameContainer, graphics);
        }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void keyPressed(int key, char c) {
    }
}
