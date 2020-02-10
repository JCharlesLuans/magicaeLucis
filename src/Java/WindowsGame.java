package Java;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

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
    private TiledMap map;

    /** Position du personnage */
    private float positionX,
                  positionY;

    /** Direction du personnage */
    private int direction;

    /** Indicateur de mouvement */
    private boolean moving;

    /** Animations du personnage */
    private Animation[] listeAnimation = new Animation[8];

    /* --------------------------------------- METHODE PERSO ----------------------------------------------- */

    /**
     * Initialise le personnage
     */
    private void initPersonnage() {
        positionX = positionY = 300;
        direction = BAS;
        moving = false;
    }

    /**
     * Charge une animation a partir du SpriteSheet
     * @param spriteSheet   : sprite a charger
     * @param positionDebut : n° de la premiere frame
     * @param positionFin   : n° de la derniere frame
     * @param action        : n° de la ligne de frame
     */
    Animation loadAnimation(SpriteSheet spriteSheet, int positionDebut, int positionFin, int action) {

        Animation aRetourner = new Animation(); // Animaation a retourner

        for (int i = positionDebut;i < positionFin; i++) {
            aRetourner.addFrame(spriteSheet.getSprite(i,action), TEMPS_ANIMATION);
        }

        return  aRetourner;

    }


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
    public void init(GameContainer gameContainer) throws SlickException {
        container = gameContainer;
        map = new TiledMap("src/Ressources/Map/ville.tmx");

        initPersonnage();

        SpriteSheet spriteSheet = new SpriteSheet("src/Ressources/Personnage/Sprite/sprite_personnage.png", 64, 64);

        this.listeAnimation[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.listeAnimation[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.listeAnimation[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.listeAnimation[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.listeAnimation[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.listeAnimation[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.listeAnimation[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.listeAnimation[7] = loadAnimation(spriteSheet, 1, 9, 3);

        // TODO refactor le code pour avoir plus de métode et moins de ligne qui se repete

    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {

        if (this.moving) {
            switch (this.direction) {
                case 0: this.positionY -= .1f * delta; break;
                case 1: this.positionX -= .1f * delta; break;
                case 2: this.positionY += .1f * delta; break;
                case 3: this.positionX += .1f * delta; break;
            }
        }

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        map.render(0,0);

        graphics.setColor(new Color(0,0,0, 5f)); // Couleur de l'ombre
        graphics.fillOval(positionX - 16, positionY - 8, 32,16); // Taille + position de l'ombre

        // -32 et -60 permetent de calculer l'affichage par rapport au pied du personnage (millieu bas) car
        // sinon affichage calculer par rapport au coin gauche du personnage
        graphics.drawAnimation(listeAnimation[direction + (moving ? 4 : 0)], positionX-32, positionY-60);
    }

    public void keyReleased(int key, char c) {
        moving = false;
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
    }

    public void keyPressed(int key, char c) {

        switch (key) {
            case Input.KEY_Z: direction = HAUT;   moving = true; break;
            case Input.KEY_Q: direction = GAUCHE; moving = true; break;
            case Input.KEY_S: direction = BAS;    moving = true; break;
            case Input.KEY_D: direction = DROITE; moving = true; break;
        }
    }

}
