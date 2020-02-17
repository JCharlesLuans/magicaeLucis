/*
 * MenuEnJeu.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Jeu.UI;

import Code.Jeu.MapGameState;
import Code.Jeu.Personnage.Camera;
import Code.Jeu.Personnage.Personnage;
import Code.Jeu.Personnage.SavePersonnage;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class MenuEnJeu {

    private StateBasedGame game;
    Inventaire inventaire;
    Message message;
    GameContainer container;
    Music click;

    private boolean inSauvegarde = false;
    private boolean inQuitter = false;
    private boolean inChargement = false;

    public MenuEnJeu(GameContainer container, StateBasedGame game) throws SlickException {

        this.game = game;
        this.container = container;
        click = new Music("src/Ressources/HUD/Son/click.ogg");

        // Inventaire
        inventaire = new Inventaire(container);

        // Message
        message = new Message(container);
    }

    public void render(Graphics graphics) {
        inventaire.render(graphics);
        message.render(graphics);
    }

    public void setShowInventaire(boolean showInventaire) {
        inventaire.setShow(showInventaire);
    }

    public void action(int x, int y, Personnage hero, Camera cam)  {

        if(inventaire.isSauvegarde(x, y)) {
            click.play();
            inSauvegarde = true;
            message.affichage("La partie a été sauvegardée");
            try {
                hero.sauvegarde(cam);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (inventaire.isCharger(x,y)) {
            inChargement = true;
            message.affichage("La partie va etre charger");

        } else if (inventaire.isRetour(x,y)) {
            click.play();
            inventaire.setShow(false);

        } else if (inventaire.isQuitter(x, y)) {
            click.play();
            inQuitter = true;
        }

        if (inQuitter) {
            message.affichage("Voulez vous quitter le jeu ?");

            if (message.isOk(x, y)) {
                click.play();
                game.enterState(1);
                message.setShow(false);
                inventaire.setShow(false);
                inQuitter = false;
            } else if (message.isNo(x, y)){
                click.play();
                message.setShow(false);
                inQuitter = false;
            }
        }

        if (inSauvegarde) {

            if (message.isOk(x, y)) {
                click.play();
                message.setShow(false);

            } else if (message.isNo(x, y)){
                click.play();
                message.setShow(false);
            }
        }

        if (inChargement) {
            if (message.isOk(x,y)) {
                click.play();
                ((MapGameState)game.getState(MapGameState.ID)).setCharger(true);
                game.enterState(MapGameState.ID);
                message.setShow(false);
                inventaire.setShow(false);
            } else if (message.isNo(x, y)) {
                click.play();
                message.setShow(false);
            }
        }

    }

}
