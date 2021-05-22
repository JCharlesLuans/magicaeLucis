/*
 * MenuEnJeu.java                         16/02/2020
 * Pas de copyright (pour le moment :)
 */

package org.thunderbot.flashofshadow.client.gameState.UI;

import org.thunderbot.flashofshadow.client.gameState.MapGameState;
import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.Personnage;
import org.thunderbot.flashofshadow.client.gameState.utils.Sauvegarde;
import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.UI.Inventaire;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Menu de pause en jeu
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
        click = new Music("res/son/bruitage/menus/click.ogg");

        // Inventaire
        inventaire = new Inventaire(container);

        // Message
        message = new Message(container);
    }

    public void render(Graphics graphics) {
        inventaire.render(graphics);
        message.render(graphics);
    }

    public void action(int x, int y, Personnage hero)  {

        if(inventaire.isSauvegarde(x, y)) {
            click.play();
            inSauvegarde = true;
            message.affichage("La partie a été sauvegardée");
            Sauvegarde.sauvegarde(hero);

        }else if (inventaire.isCharger(x,y)) {
            inChargement = true;
            message.affichage("La partie va etre chargée");

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
                container.exit();
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

    public void setShowInventaire(boolean showInventaire) {
        inventaire.setShow(showInventaire);
    }

    public boolean isShowInventaire() {
        return inventaire.isShow();
    }

}
