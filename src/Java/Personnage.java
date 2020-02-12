package Java;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Classe du personnage principal du jeu
 * @author J-Charles Luans
 */
public class Personnage {

    /* Indique les position */
    private final int HAUT = 0,
                      GAUCHE = 1,
                      BAS = 2,
                      DROITE = 3;

    /* Vitesse de succéssion d'image dans une animation (en ms) */
    private final int TEMPS_ANIMATION = 100;

    /** Position du personnage */
    private float positionX,
            positionY;

    /** Direction du personnage */
    private int direction;

    /** Indicateur de mouvement */
    private boolean moving;

    /** Animations du personnage */
    private Animation[] listeAnimation = new Animation[8];

    /** Sprite du personnage */
    SpriteSheet sprite;

    /**
     * Créer un peersonnage avce un sprite par default
     * @throws SlickException
     */
    Personnage() throws SlickException {
        positionX = positionY = 400; // Position a la création du personnage
        direction = BAS; // Position par default du personnage
        moving = false; // Le personnage ne bouge pas lors de sa création
        sprite = new SpriteSheet("src/Ressources/Personnage/Sprites/sprite_personnage.png", 64, 64);
        animer(sprite);
    }

    /**
     * Créer un persoannge avec un sprite prédéfini
     * @param newSprite
     * @throws SlickException
     */
    Personnage(SpriteSheet newSprite) throws SlickException {
        positionX = positionY = 0; // Position a la création du personnage
        direction = BAS; // Position par default du personnage
        moving = false; // Le personnage ne bouge pas lors de sa création
        sprite = newSprite;
        animer(sprite);
    }

    /**
     * Charge une animation a partir du SpriteSheet
     * @param spriteSheet   : sprite a charger
     * @param positionDebut : n° de la premiere frame
     * @param positionFin   : n° de la derniere frame
     * @param action        : n° de la ligne de frame
     */
    private Animation loadAnimation(SpriteSheet spriteSheet, int positionDebut, int positionFin, int action) {

        Animation aRetourner = new Animation(); // Animaation a retourner

        for (int i = positionDebut;i < positionFin; i++) {
            aRetourner.addFrame(spriteSheet.getSprite(i,action), TEMPS_ANIMATION);
        }

        return  aRetourner;
    }

    /** Anime le personnage */
    private void animer(SpriteSheet spriteSheet) {

        /* Position a l'arret */
        listeAnimation[0] = loadAnimation(spriteSheet, 0, 1, 0);
        listeAnimation[1] = loadAnimation(spriteSheet, 0, 1, 1);
        listeAnimation[2] = loadAnimation(spriteSheet, 0, 1, 2);
        listeAnimation[3] = loadAnimation(spriteSheet, 0, 1, 3);

        /* Position en mouvement*/
        listeAnimation[4] = loadAnimation(spriteSheet, 1, 9, 0);
        listeAnimation[5] = loadAnimation(spriteSheet, 1, 9, 1);
        listeAnimation[6] = loadAnimation(spriteSheet, 1, 9, 2);
        listeAnimation[7] = loadAnimation(spriteSheet, 1, 9, 3);

    }

    /**
     * @return l'animation du personnage en fonction de sa direction
     * et de si il est en train de bouger ou non
     */
    public Animation animation() {
        return (listeAnimation[direction + (moving ? 4 : 0)]);
    }

    /**
     * Actualise le personnage en le deplacant
     * @param delta vitesse de deplacement
     */
    public void actualisation(int delta, TiledMap map) {

        Image tile;

        float futurX;
        float futurY;

        boolean colision;

        if (moving) {

            futurX = positionX;
            futurY = positionY;

            /* On va chercher la tile qui se trouve au coordonnée future du personnage sur le calque logic */
            tile = map.getTileImage((int) futurX / map.getTileWidth(),
                                    (int) futurY / map.getTileHeight(),
                                        map.getLayerIndex("Logic"));

            // On teste si elle existe
            colision = tile != null;

            if (colision) {

                Color color = tile.getColor((int) futurX % map.getTileWidth(), (int) futurY % map.getTileHeight());
                colision = color.getAlpha() > 0;
            } if (colision) {
                moving = false;
            } else {
                positionX = futurX;
                positionY = futurY;
            }

            switch (direction) {
                case 0:
                    futurY -= .1f * delta;
                    break;
                case 1:
                    futurX -= .1f * delta;
                    break;
                case 2:
                    futurY += .1f * delta;
                    break;
                case 3:
                    futurX += .1f * delta;
                    break;
            }

            positionX = futurX;
            positionY = futurY;

        }
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Animation[] getListeAnimation() {
        return listeAnimation;
    }

    public void setListeAnimation(Animation[] listeAnimation) {
        this.listeAnimation = listeAnimation;
    }
}
