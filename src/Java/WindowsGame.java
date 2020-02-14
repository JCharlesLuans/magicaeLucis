package Java;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.io.IOException;

/**
 * RPG créer avec Slick2d
 * @author J-Charles Luans
 * @version 1.0
 */
public class WindowsGame extends BasicGame {

    /* --------------------------------- CONSTANTE ------------------------------------*/

    /* Indique les position */
    private final int HAUT = 0,
                      GAUCHE = 1,
                      BAS = 2,
                      DROITE = 3;

    /* Vitesse de succéssion d'image dans une animation (en ms) */
    private final int TEMPS_ANIMATION = 100;

    /* -------------------------------- Variable du jeu -----------------------------------*/

    /** Conteneur du jeu */
    private GameContainer container;

    /** Carte actuelle */
    private static TiledMap map;

    /** Personnage principal */
    private Personnage hero;

    /** Camera principale */
    private Camera cam;

    /* --------------------------------------- METHODE PERSO ----------------------------------------------- */


    /* -------------------------------- Méthode d'héritage -------------------------------------------------- */

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
    public void init(GameContainer gameContainer) throws SlickException  {

        // Création du conteneur du jeu
        container = gameContainer;

        // Création de la map
        Map.initialiseMap("src/Ressources/Map/campagne_ThunderSun.tmx");
        map = new TiledMap("src/Ressources/Map/campagne_ThunderSun.tmx");

        // Création du personnage principal
        hero = new Personnage();

        // Création de la camera
        cam = new Camera(hero);

        Music background = new Music("src/Ressources/Musique/TownTheme.ogg");
        background.loop();
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {

        hero.actualisation(delta, map);
        cam.actualisation(map, gameContainer);

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

        // Affiche la camera sur la moitier de l'ecran
        graphics.translate(cam.centreX(container), cam.centreY(container));

        // Rendu de la carte
        map.render(0 ,0, 0); // eau
        map.render(0 ,0, 1); // sol
        map.render(0 ,0, 2); // background
        map.render(0 ,0, 3); // background 2

        graphics.setColor(new Color(49,36,33, 153)); // Couleur de l'ombre

        // Taille + position de l'ombre
        graphics.fillOval(hero.getPositionX() - 16, hero.getPositionY() - 8, 32,16);

        // -32 et -60 permetent de calculer l'affichage par rapport au pied du personnage (millieu bas) car
        // sinon affichage calculer par rapport au coin gauche du personnage
        graphics.drawAnimation(hero.animation(), hero.getPositionX()-32, hero.getPositionY()-60);


        map.render(0, 0, 4);
    }

    public void keyReleased(int key, char c) {
        hero.arretPersonnage(key);
    }

    public void keyPressed(int key, char c) {

        switch (key) {
            case Input.KEY_Z: hero.setDirection(HAUT); hero.setMoving(true); break;
            case Input.KEY_Q: hero.setDirection(GAUCHE); hero.setMoving(true); break;
            case Input.KEY_S: hero.setDirection(BAS); hero.setMoving(true); break;
            case Input.KEY_D: hero.setDirection(DROITE); hero.setMoving(true); break;
        }
    }

    static void changementMap(TiledMap newMap) {
        map = newMap;
    }

}
