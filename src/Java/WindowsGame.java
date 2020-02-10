package Java;

import org.newdawn.slick.*;

/**
 * RPG créer avec Slick2d
 * @author J-Charles Luans
 * @version 1.0
 */
public class WindowsGame extends BasicGame {

    /** Conteneur du jeu */
    GameContainer container;

    /** Carte actuelle */
    // Créer la map

    /**
     * constructeur du jeu de base qui est etendu de la classe BasicGame
     * @param title
     */
    public WindowsGame(String title) {
        super(title);
    }

    /**
     * Methode de chargement du jeu
     * @param gameContainer : conteneur du jeu
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        container = gameContainer;
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

    }

    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
    }
}
