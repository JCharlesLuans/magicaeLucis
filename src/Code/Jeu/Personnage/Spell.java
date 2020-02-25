package Code.Jeu.Personnage;

import org.newdawn.slick.Animation;
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
    SpriteSheet spriteSheet;

    /** Animations */
    Animation[] animations = new Animation[4];

    /** Position de X et Y */
    private float positionX,
                  positionY;

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
        spriteSheet = new SpriteSheet("Ressources/Personnage/Sprites/fireball.png", 64, 64);
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
        animations[BAS] = loadAnimation(spriteSheet, 0, 8, 4);
        animations[DROITE] = loadAnimation(spriteSheet, 0, 8, 6);

    }
}
