package Java;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class MagicaeLucis {
    /**
     * Methode de lancement du jeu
     * @param args non utilisé
     */
    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new WindowsGame("Magicae Lucis"), 640, 480, false).start();
    }
}
