package Code.Jeu.Personnage;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Spell {

    /* Indique les position */
    private final int HAUT = 0,
            GAUCHE = 1,
            BAS = 2,
            DROITE = 3;

    /* Vitesse de succéssion d'image dans une animation (en ms) */
    private final int TEMPS_ANIMATION = 100;

    /** Indique la direction de du sort */
    private int direction;

    /** Sprite du sort */
    private SpriteSheet spriteSheet;

    /** Animations */
    private Animation[] animations = new Animation[4];

    /** Visible */
    private boolean visible;

    /** Position de X et Y */
    private float positionX,
                  positionY;

    /**
     * Créer un nouveau sort avec comme positionpar default (0,0)
     * qui n'est pas visible
     */
    public Spell() throws SlickException {
        direction = HAUT;
        positionX = positionY = 0;
        visible = true;
        spriteSheet = new SpriteSheet("Ressources/Personnage/Sprites/fireball.png", 64, 64);
        animer(spriteSheet);
    }

    /**
     * Créer un nouveau sort
     * @param newDirection direction du sort
     * @param departX position de depart en X
     * @param departY position de depart en Y
     */
    public Spell(int newDirection, float departX, float departY) throws SlickException {
        direction = newDirection;
        positionX = departX;
        positionY = departY;
        visible = true;
        spriteSheet = new SpriteSheet("Ressources/Personnage/Sprites/fireball.png", 64, 64);
        animer(spriteSheet);
    }

    /**
     * Affiche le sort
     * @param graphics : graphique sur le quelle on affiche le sort
     */
    public void render(Graphics graphics) {
        if (visible) graphics.drawAnimation(animations[direction], positionX-32, positionY-15);
    }

    /**
     * Rafraichie le sort
     * @param delta : vitesse du jeu
     */
    public void update(int delta) {
        if (visible) {
            positionX = getFuturX(delta);
            positionY = getFuturY(delta);
        }
    }

    /**
     * Tir le sort
     */
    public void tirer(float newPositionX, float newPositionY, int newDirection) {
        visible = true;
        positionX = newPositionX;
        positionY = newPositionY;
        direction = newDirection;
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
        }

        return futurY;
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
        animations[HAUT] = loadAnimation(spriteSheet, 0, 8, 2);
        animations[GAUCHE] = loadAnimation(spriteSheet, 0, 8, 0);
        animations[BAS] = loadAnimation(spriteSheet, 0, 8, 6);
        animations[DROITE] = loadAnimation(spriteSheet, 0, 8, 4);

    }
}
