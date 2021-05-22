/*
 * PlayerController.java             22/05/2021
 * Copyright et copyleft TNLag Corp.
 */

package gameState.phisique;

import gameState.UI.MenuEnJeu;
import gameState.entite.Camera;
import gameState.entite.Personnage.Personnage;
import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

import java.awt.*;


/**
 * Controller du joueur, pour lui faire effectuer des mouvements
 * Clavier et manette
 *
 * @author J-Charles Luans
 * @version 1.0
 */
public class PlayerController implements KeyListener, ControllerListener, MouseListener {

    /* Indique les position */
    private static final int HAUT = 0,
                             GAUCHE = 1,
                             BAS = 2,
                             DROITE = 3;

    Personnage personnage; // Personnage a faire bouger
    MenuEnJeu menuEnJeu;   // Menu a ouvrir en jeu
    Camera camera;         // Camera du joueur

    public PlayerController(Personnage personnage, Camera camera, MenuEnJeu menuEnJeu) {
        this.personnage = personnage;
        this.menuEnJeu = menuEnJeu;
        this.camera = camera;
    }


    @Override
    public void keyPressed(int key, char c) {
        System.out.println("Salut");
        switch (key) {
            case Input.KEY_Z:
                personnage.setDirection(HAUT);
                personnage.setMoving(true);
                break;
            case Input.KEY_Q:
                personnage.setDirection(GAUCHE);
                personnage.setMoving(true);
                break;
            case Input.KEY_S:
                personnage.setDirection(BAS);
                personnage.setMoving(true);
                break;
            case Input.KEY_D:
                personnage.setDirection(DROITE);
                personnage.setMoving(true);
                break;


            case Input.KEY_SPACE: menuEnJeu.setShowInventaire(true); break;
            case Input.KEY_ESCAPE: menuEnJeu.setShowInventaire(false); break;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        personnage.arretPersonnage(key);

        switch (key) {
            case Input.MOUSE_LEFT_BUTTON:
                personnage.setCoup(false);
                break;
            case Input.MOUSE_RIGHT_BUTTON:
                personnage.setSort(false);
                break;
        }

    }

    public void controllerLeftPressed(int controller) {
        personnage.setDirection(GAUCHE);
        personnage.setMoving(true);
    }
    public void controllerLeftReleased(int controller) {
        personnage.setMoving(false);
    }

    public void controllerRightPressed(int controller) {
        personnage.setDirection(DROITE);
        personnage.setMoving(true);
    }
    public void controllerRightReleased(int controller) {
        personnage.setMoving(false);
    }

    public void controllerUpPressed(int controller) {
        personnage.setDirection(HAUT);
        personnage.setMoving(true);
    }
    public void controllerUpReleased(int controller) {
        personnage.setMoving(false);
    }

    public void controllerDownPressed(int controller) {
        personnage.setDirection(BAS);
        personnage.setMoving(true);
    }
    public void controllerDownReleased(int controller) {
        personnage.setMoving(false);
    }

    @Override
    public void controllerButtonPressed(int i, int bouton) {
        switch (bouton) {
            case 2:
                personnage.setCoup(true);
                break;
            case 4:
                personnage.setSort(true);
                break;
            case 8:
                if (menuEnJeu.isShowInventaire())
                    menuEnJeu.setShowInventaire(false);
                else
                    menuEnJeu.setShowInventaire(true);
                break;
        }
    }

    @Override
    public void controllerButtonReleased(int i, int bouton) {
        switch (bouton) {
            case 2:
                personnage.setCoup(true);
                break;
            case 4:
                personnage.setSort(true);
                break;
        }
    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }

    @Override
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {

    }

    public void mousePressed(int button, int x, int y) {
        menuEnJeu.action(x, y, personnage);

        switch (button) {
            case Input.MOUSE_LEFT_BUTTON: personnage.setCoup(true); break;
            case Input.MOUSE_RIGHT_BUTTON: personnage.setSort(true); break;
        }
    }

    public void mouseReleased(int button, int x, int y) {
        menuEnJeu.action(x, y, personnage);

    }

    @Override
    public void mouseMoved(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }
}
