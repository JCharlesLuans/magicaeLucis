package Code.Jeu.PNJ;

import Code.Jeu.Carte.Map;
import Code.Jeu.Personnage.Stats;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Manequin {

    /* Indique les position */
    private final int HAUT = 0,
            GAUCHE = 1,
            BAS = 2,
            DROITE = 3;

    /* Vitesse de succéssion d'image dans une animation (en ms) */
    private final int TEMPS_ANIMATION = 100;

    /**
     * Position de mob
     */
    private float positionX,
                  positionY;

    /**
     * Direction du mob
     */
    private int direction;

    /**
     * Sprite du mob
     */
    private SpriteSheet spriteSheet;

    /**
     * Animations du personnages
     */
    private Animation animations;

    /** Stats du personnage */
    private Stats stats;

    /** Map sur laquelle se trouve un mob */
    private Map map;

    public Manequin(Map map) throws SlickException {
        spriteSheet = new SpriteSheet("src/Ressources/Personnage/Sprites/manequin.png", 64, 64);
        animations = loadAnimation(spriteSheet, 0, 8, 0);
        positionX = 650;
        positionY = 400; // Position a la création du personnage
        this.map = map;
        stats = new Stats(map.getNiveau());
    }

    /**
     * Affichage du manequin
     * @param graphics sur le quel on affiche l'objet
     */
    public void render(Graphics graphics) {
        // TODO affichage pv + barre pv
        graphics.drawAnimation(animations, positionX-32, positionY-60);
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
}
