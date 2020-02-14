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

    /**
     * Position du personnage
     */
    private float positionX,
            positionY;

    /**
     * Direction du personnage
     */
    private int direction;

    /**
     * Indicateur de mouvement
     */
    private boolean moving;

    /**
     * Inidique si le personnage est sur un escalier ou pas
     */
    private boolean escalierDroite,
                    escalierGauche;

    /**
     * Animations du personnage
     */
    private Animation[] listeAnimation = new Animation[8];

    /**
     * Sprite du personnage
     */
    SpriteSheet sprite;

    /**
     * Créer un peersonnage avce un sprite par default
     *
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
     *
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
     *
     * @param spriteSheet   : sprite a charger
     * @param positionDebut : n° de la premiere frame
     * @param positionFin   : n° de la derniere frame
     * @param action        : n° de la ligne de frame
     */
    private Animation loadAnimation(SpriteSheet spriteSheet, int positionDebut, int positionFin, int action) {

        Animation aRetourner = new Animation(); // Animaation a retourner

        for (int i = positionDebut; i < positionFin; i++) {
            aRetourner.addFrame(spriteSheet.getSprite(i, action), TEMPS_ANIMATION);
        }

        return aRetourner;
    }

    /**
     * Anime le personnage
     */
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
     *
     * @param delta vitesse de deplacement
     */
    public void actualisation(int delta, TiledMap map) {

        updateTrigger(map); // Update les triggers

        float futurX = positionX;
        float futurY = positionY;

        if (moving) {

            switch (direction) {
                case HAUT:
                    futurY -= .1f * delta;
                    break;
                case GAUCHE:
                    futurX -= .1f * delta;
                    if (escalierDroite) futurY = futurY + .1f * delta;
                    if (escalierGauche) futurY = futurY - .1f * delta;
                    break;
                case BAS:
                    futurY += .1f * delta;
                    break;
                case DROITE:
                    futurX += .1f * delta;
                    if (escalierGauche) futurY = futurY + .1f * delta;
                    if (escalierDroite) futurY = futurY - .1f * delta;
                    break;
            }

        }
        if (isColision(map, futurX, futurY)) {
            moving = false;
        } else {
            positionX = futurX;
            positionY = futurY;
        }

    }

    /**
     * Verifie si il y a une colision
     * @param map map actuelle
     * @param x axe x sur lequelle on verifie si il y a une colision
     * @param y axe x sur lequelle on verifie si il y a une colision
     * @return true si il y a une colision
     *         false si il n'y a pas de colision
     */
    private boolean isColision(TiledMap map, float x, float y) {
        Image tile;
        Color color;

        /* On va chercher la tile qui se trouve au coordonnée future du personnage sur le calque logic */
        tile = map.getTileImage((int) x / map.getTileWidth(),
                (int) y / map.getTileHeight(),
                map.getLayerIndex("logique"));

        if (tile != null) {
            //Recupere la couleur
            color = tile.getColor((int) x % map.getTileHeight(), (int) y % map.getTileHeight());
            return color.getAlpha() > 0;
        } else {
            return false;
        }
    }

    private void updateTrigger(TiledMap map)  {

        escalierDroite = escalierGauche = false;

        for (int objectID = 0; objectID < map.getObjectCount(0); objectID++) {

            if (isInTrigger(map,objectID)) {
                escalierGauche = "escalierGauche".equals(map.getObjectType(0,objectID));
                escalierDroite = "escalierDroite".equals(map.getObjectType(0,objectID));

                if ("changementMap".equals(map.getObjectType(0, objectID))) {
                    String newMap = map.getObjectProperty(0,objectID, "destiMap","undefine");
                    try {
                        WindowsGame.changementMap(new TiledMap("src/Ressources/Map/"+ newMap));
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    private boolean isInTrigger(TiledMap map, int id) {

        return positionX > map.getObjectX(0, id)
                && positionX < map.getObjectX(0, id) + map.getObjectWidth(0, id)
                && positionY > map.getObjectY(0, id)
                && positionY < map.getObjectY(0, id) + map.getObjectHeight(0, id);
    }

    /**
     *  Arret du personnage
     * @param key touche relâcher
     */
    public void arretPersonnage(int key) {

        if (Input.KEY_Z == key && direction == HAUT) {
            moving = false;
        }
        if (Input.KEY_Q == key && direction == GAUCHE) {
            moving = false;
        }
        if (Input.KEY_S == key && direction == BAS) {
            moving = false;
        }
        if (Input.KEY_D == key && direction == DROITE) {
            moving = false;
        }
    }

    /**
     * @return la position en X du personnage
     */
    public float getPositionX() {
        return positionX;
    }

    /**
     * @param positionX la nouvelle position en X du personnage
     */
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    /**
     * @return la position en Y du personnage
     */
    public float getPositionY() {
        return positionY;
    }

    /**
     * @param positionY la nouvelle position en Y du personnage
     */
    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    /**
     * @return la direction du personnage
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction la nouvelle direction du personnage
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return si le personnage est en mouvement ou pas
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * @param moving le nouvel indicateur de mouvement
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
