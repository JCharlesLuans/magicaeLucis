package Code.Jeu.Personnage;

import Code.Jeu.Map;
import Code.Jeu.XMLTools;
import org.newdawn.slick.*;

import java.io.*;

/**
 * Classe du personnage principal du jeu
 * @author J-Charles Luans
 */
public class Personnage implements Serializable, MouseListener {

    /* Indique les position */
    private final int HAUT = 0,
                      GAUCHE = 1,
                      BAS = 2,
                      DROITE = 3;

    /* Vitesse de succéssion d'image dans une animation (en ms) */
    private final int TEMPS_ANIMATION = 100;

    /* Pourcentage de bonus lors de prise de niveau */
    private final int BONUS_NIVEAU = 125;

    /*----------------------------------------- Argument -------------------------------------- */

    /**
     * Map sur laquelle évolue le joueur
     */
    private Map map;

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
    private Animation[] animationsMarche;

    /**
     * Animations du personnage lorsqu'il jete un sort
     */
    private Animation[] animationsSort;

    /**
     * Animations du personnage lorsqu'il donne un coup
     */
    private Animation[] animationsCoup;


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

        map = newMap;

        positionX = 650;
        positionY = 400; // Position a la création du personnage
        direction = BAS; // Position par default du personnage
        moving = false; // Le personnage ne bouge pas lors de sa création
        spriteMarche = new SpriteSheet("Ressources/Personnage/Sprites/sprite_personnage.png", 64, 64);
        spriteSort = new SpriteSheet("Ressources/Personnage/Sprites/sprite_personnage_sort.png", 64, 64);
        spriteCoup = new SpriteSheet("Ressources/Personnage/Sprites/sprite_personnage_coup.png", 64, 64);

        animerMarche(spriteMarche);
        animerSort(spriteSort);
        animerCoup(spriteCoup);

        stats = new Stats(true);
    }

    /**
     * Fait le rendu du personnage dans le graphics
     * @param graphics : graphics sur lequelle effectuer le rendu du personnage
     * @throws SlickException
     */
    public void render(Graphics graphics) throws SlickException {

        // Couleur de l'ombre
        graphics.setColor(new Color(49,36,33, 153));

        // Taille + position de l'ombre
        graphics.fillOval(positionX - 16, positionY - 8, 32,16);

        // -32 et -60 permetent de calculer l'affichage par rapport au pied du personnage (millieu bas) car
        // sinon affichage calculer par rapport au coin gauche du personnage

        if (coup) {
            graphics.drawAnimation(animationsCoup[direction], positionX-32, positionY-60);
        } else if (sort) {
            graphics.drawAnimation(animationsSort[direction], positionX-32, positionY-60);
        } else {
            graphics.drawAnimation(animationsMarche[direction + (moving ? 4 : 0)], positionX-32, positionY-60);
        }



    }

    /**
     * Actualise le personnage en le deplacant
     *
     * @param delta vitesse de deplacement
     */
    public void update(int delta) {

        stats.updateNiveau();

        //TODO DEBUGUER ANIMATIONS

        for (Animation animation : animationsCoup) {
            if (animation.isStopped() && coup) {
                coup = false;
                break;
            }
        }

        for (Animation animation : animationsSort) {
            if (animation.isStopped() && sort) {
                sort = false;
                break;
            }
        }


        updateTrigger();
        if (moving) {
            float futurX = getFuturX(delta);
            float futurY = getFuturY(delta);
            boolean collision = map.isCollision(futurX, futurY);

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

        Animation[] listeAnimation = new Animation[8];

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

        animationsMarche = listeAnimation;
    }

    /**
     * Anime le personnage
     */
    private void animerCoup(SpriteSheet spriteSheet) {

        Animation[] listeAnimation = new Animation[4];

        /* Position a l'arret */
        listeAnimation[0] = loadAnimation(spriteSheet, 0, 6, 0);
        listeAnimation[1] = loadAnimation(spriteSheet, 0, 6, 1);
        listeAnimation[2] = loadAnimation(spriteSheet, 0, 6, 2);
        listeAnimation[3] = loadAnimation(spriteSheet, 0, 6, 3);

        for (int i = 0; i < listeAnimation.length; i++) {
            listeAnimation[i].setLooping(false);
        }

        animationsCoup = listeAnimation;
    }

    /**
     * Anime le personnage
     */
    private void animerSort(SpriteSheet spriteSheet) {

        Animation[] listeAnimation = new Animation[4];

        /* Position a l'arret */
        listeAnimation[0] = loadAnimation(spriteSheet, 0, 7, 0);
        listeAnimation[1] = loadAnimation(spriteSheet, 0, 7, 1);
        listeAnimation[2] = loadAnimation(spriteSheet, 0, 7, 2);
        listeAnimation[3] = loadAnimation(spriteSheet, 0, 7, 3);

        for (int i = 0; i < listeAnimation.length; i++) {
            listeAnimation[i].setLooping(false);
        }

        animationsSort = listeAnimation;
    }

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

    public void chargement(Camera cam) throws SlickException {
        SavePersonnage savePero = (SavePersonnage) XMLTools.decodeFromFile("src/Ressources/Sauvegardes/save.xml");
        positionX = savePero.getPositionX();
        positionY = savePero.getPositionY();
        direction = savePero.getDirection();
        map.changeMap(savePero.getMap());

        cam.setPositionX(savePero.getCamPosX());
        cam.setPositionY(savePero.getCamPosY());
    }

    public void sauvegarde(Camera cam) throws IOException {
        SavePersonnage aSave = new  SavePersonnage(this, cam);
        XMLTools.encodeToFile(aSave, "src/Ressources/Sauvegardes/save.xml");
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
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mousePressed(int i, int i1, int i2) {
        switch (i) {
            case Input.MOUSE_RIGHT_BUTTON:
                sort = true;
                animationsSort[direction].start();
                break;

            case Input.MOUSE_LEFT_BUTTON:
                coup = true;
                animationsCoup[direction].start();
                break;
        }
    }

    @Override
    public void mouseReleased(int i, int i1, int i2) {

    }

    @Override
    public void mouseMoved(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return false;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
