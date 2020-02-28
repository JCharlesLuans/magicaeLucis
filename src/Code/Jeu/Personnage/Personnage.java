package Code.Jeu.Personnage;

import Code.Jeu.Carte.Map;
import Code.Jeu.Sauvegarde.Sauvegarde;
import Code.Jeu.XMLTools;
import org.newdawn.slick.*;

import java.io.*;
import java.util.Arrays;

/**
 * Classe du personnage principal du jeu
 * @author J-Charles Luans
 */
public class Personnage implements Serializable {

    /* Indique les position */
    private final int HAUT = 0,
                      GAUCHE = 1,
                      BAS = 2,
                      DROITE = 3;

    /* Indique les mouvement */
    private final int MOUV_SORT = 12,
                      MOUV_COUP = 8,
                      MOUV_MOUVEMENT = 4,
                      MOUV_STATIQUE = 0;

    /* Vitesse de succéssion d'image dans une animation (en ms) */
    private final int TEMPS_ANIMATION = 100;

    /*----------------------------------------- Argument -------------------------------------- */

    /**
     * Map sur laquelle évolue le joueur
     */
    private Map map;

    /**
     * Spell que peux lancer le personnage
     */
    private Spell spell;

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
     *  Indicateur d'attaque
     */
    private boolean coup;

    /**
     *  Indicateur de sort
     */
    private boolean sort;

    /**
     * Inidique si le personnage est sur un escalier ou pas
     */
    private boolean escalierDroite,
                    escalierGauche;

    /**
     * Animations du personnage lorsqu'il marche
     */
    private Animation[] animations = new  Animation[16];

    /**
     * Indique le mouvement de l'animations
     */
    private int mouvement;

    /**
     * Sprite du personnage lorqi'il marche
     */
    private SpriteSheet spriteMarche;

    /**
     * Sprite du personnage lorsqu'il jete un coup
     */
    private SpriteSheet spriteSort;

    /**
     * Sprite du personnage lorsqu'il donne un coup
     */
    private SpriteSheet spriteCoup;

    /** Stats du personnage */
    Stats stats;


    /* --------------------------------------------- Méthode ------------------------------ */

    /**
     * Créer un personnage en précisent ou se situe le personnage
     * @param newMap
     */
    public Personnage(Map newMap) throws SlickException {

        map = newMap; // Initialise la map sur laquelle evolue le personnage

        stats = new Stats(true); // Stats du personnage

        spell = new Spell(newMap, stats.getDegaDefense()); // Sort du personnage

        /* Déplacement du personnage */
        positionX = 650;
        positionY = 400; // Position a la création du personnage
        direction = BAS; // Position par default du personnage
        moving = false; // Le personnage ne bouge pas lors de sa création

        /* Affichage du personnage */
        spriteMarche = new SpriteSheet("Ressources/Personnage/Sprites/sprite_personnage.png", 64, 64);
        spriteSort = new SpriteSheet("Ressources/Personnage/Sprites/sprite_personnage_sort.png", 64, 64);
        spriteCoup = new SpriteSheet("Ressources/Personnage/Sprites/sprite_personnage_coup.png", 64, 64);

        /* Animation du personngae */
        animerMarche(spriteMarche);
        animerSort(spriteSort);
        animerCoup(spriteCoup);
    }

    /**
     * Fait le rendu du personnage dans le graphics
     * @param graphics : graphics sur lequelle effectuer le rendu du personnage
     * @throws SlickException
     */
    public void render(Graphics graphics) throws SlickException {

        // Rendu du sort du personnage
        spell.render(graphics);

        // Couleur de l'ombre
        graphics.setColor(new Color(49,36,33, 153));

        // Taille + position de l'ombre
        graphics.fillOval(positionX - 16, positionY - 8, 32,16);

        // -32 et -60 permetent de calculer l'affichage par rapport au pied du personnage (millieu bas) car
        // sinon affichage calculer par rapport au coin gauche du personnage

        if (moving) {
            mouvement = MOUV_MOUVEMENT;
            sort = false;
            coup = false;
        } else if (sort && stats.getMana() > 0) {
            mouvement = MOUV_SORT;
        } else if (coup) {
            mouvement = MOUV_COUP;
        }else {
            mouvement = MOUV_STATIQUE;
            sort = false;
            coup = false;
        }

        graphics.drawAnimation(animations[direction + mouvement], positionX-32, positionY-60);

    }

    /**
     * Actualise le personnage en le deplacant
     *
     * @param delta vitesse de deplacement
     */
    public void update(int delta) {

        stats.updateNiveau();

        spell.update(delta);

        if (animations[direction + mouvement].getFrame() == 6 &&  mouvement == MOUV_SORT) {
            mouvement = MOUV_STATIQUE;
            spell.tirer(positionX, positionY, direction);
            /*stats.setMana(stats.getMana() - 25);*/
            sort = false;
        }

        if (animations[direction + mouvement].getFrame() == 5 && mouvement == MOUV_COUP) {
            System.out.println(animations[direction + mouvement].getFrame());
            mouvement = MOUV_STATIQUE;
            coup = false;
        }

        updateTrigger();

        if (moving) {
            float futurX = getFuturX(delta);
            float futurY = getFuturY(delta);
            boolean collision = map.isCollision(futurX, futurY) || map.isMob(futurX, futurY);

            if (collision) {
                moving = false;
            } else {
                positionX = futurX;
                positionY = futurY;
            }

        }

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
    private void animerMarche(SpriteSheet spriteSheet) {

        /* Position a l'arret */
        animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        animations[3] = loadAnimation(spriteSheet, 0, 1, 3);

        /* Position en mouvement*/
        animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        animations[7] = loadAnimation(spriteSheet, 1, 9, 3);

    }

    /**
     * Anime le personnage
     */
    private void animerCoup(SpriteSheet spriteSheet) {

        /* Position a l'arret */
        animations[8] = loadAnimation(spriteSheet, 0, 6, 0);
        animations[9] = loadAnimation(spriteSheet, 0, 6, 1);
        animations[10] = loadAnimation(spriteSheet, 0, 6, 2);
        animations[11] = loadAnimation(spriteSheet, 0, 6, 3);
    }

    /**
     * Anime le personnage
     */
    private void animerSort(SpriteSheet spriteSheet) {

        /* Position a l'arret */
        animations[12] = loadAnimation(spriteSheet, 0, 7, 0);
        animations[13] = loadAnimation(spriteSheet, 0, 7, 1);
        animations[14] = loadAnimation(spriteSheet, 0, 7, 2);
        animations[15] = loadAnimation(spriteSheet, 0, 7, 3);

    }

    /**
     * Gere les update de triggerr
     */
    private void updateTrigger()  {

        escalierDroite = escalierGauche = false;

        for (int objectID = 0; objectID < map.getObjectCount(); objectID++) {

            if (isInTrigger(objectID)) {
                escalierGauche = "escalierGauche".equals(map.getObjectType(objectID));
                escalierDroite = "escalierDroite".equals(map.getObjectType(objectID));

                if ("changementMap".equals(map.getObjectType(objectID))) {
                    String newMap = map.getObjectProperty(objectID, "destiMap","undefine");
                    positionX = Integer.parseInt(map.getObjectProperty(objectID, "destiX","undefinine"));
                    positionY = Integer.parseInt(map.getObjectProperty(objectID, "destiY","undefinine"));

                    try {
                        map.changeMap(newMap);
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    /**
     * Verifie si il y a un trigger
     * @param id id de l'objet
     * @return true si il y a un trigger
     */
    private boolean isInTrigger(int id) {

        return positionX > map.getObjectX(id)
                && positionX < map.getObjectX(id) + map.getObjectWidth(id)
                && positionY > map.getObjectY(id)
                && positionY < map.getObjectY(id) + map.getObjectHeight(id);
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
     * Calcul la future position en X du personnage
     * @param delta de vitesse
     */
    private float getFuturX(int delta) {

        float futurX = positionX;

        switch (direction) {

            case GAUCHE:
                futurX -= .1f * delta;
                break;

            case DROITE:
                futurX += .1f * delta;
                break;
        }
        return futurX;
    }

    /**
     * Calcul la future position en Y du personnage
     * @param delta de vitesse
     */
    private float getFuturY(int delta) {

        float futurY = positionY;

        switch (this.direction) {

            case HAUT:
                futurY = positionY - .1f * delta;
                break;

            case BAS:
                futurY = positionY + .1f * delta;
                break;

            case GAUCHE:
                if (escalierDroite) {
                    futurY = positionY + .1f * delta;
                }
                if (escalierGauche) {
                    futurY = positionY - .1f * delta;
                }
                break;

            case 3:
                if (escalierGauche) {
                    futurY = positionY + .1f * delta;
                }
                if (escalierDroite) {
                    futurY = positionY - .1f * delta;
                }
                break;
        }

        return futurY;
    }

    /**
     * @return la position en X du personnage
     */
    public float getPositionX() {
        return positionX;
    }

    /**
     * @return la position en Y du personnage
     */
    public float getPositionY() {
        return positionY;
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

    public Map getMap() {
        return map;
    }

    public Stats getStats() {
        return stats;
    }

    public boolean isCoup() {
        return coup;
    }

    public void setCoup(boolean coup) {
        this.coup = coup;
    }

    public boolean isSort() {
        return sort;
    }

    public void setSort(boolean sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Personnage{" +
                "map=" + map.getNomMap() +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", direction=" + direction +
                '}';
    }
}
