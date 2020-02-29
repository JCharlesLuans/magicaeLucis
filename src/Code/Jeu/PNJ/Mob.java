package Code.Jeu.PNJ;

import Code.Jeu.Carte.Map;
import Code.Jeu.HitBox;
import Code.Jeu.Personnage.Stats;
import Code.Jeu.UI.BarresStatsPNJ;
import org.newdawn.slick.*;

public class Mob {
    /* Indique les position */
    private final int HAUT = 0;
    private final int GAUCHE = 1;
    private final int BAS = 2;
    private final int DROITE = 3;

    /* Vitesse de succéssion d'image dans une animation (en ms) */
    private final int TEMPS_ANIMATION = 100;

    /** Indique si le mob est actifs ou pas */
    protected boolean actif;

    /** Position du centre de l'image du mob */
    protected float positionX;
    protected float positionY;

    /** Sprite du mob */
    protected SpriteSheet spriteSheet;

    /**  Animations du personnages */
    protected Animation[] animations;

    /** Stats du mob */
    protected Stats stats;

    /** Map sur laquelle se trouve un mob */
    protected Map map;

    /** Barre de vie du PNJ */
    protected BarresStatsPNJ barre;

    /** Hit Box du personnage */
    HitBox hitBox;

    /**
     * Direction du mob
     */
    private int direction;

    public Mob(float newPositionX, float newPositionY, int newNiveau) throws SlickException {

        // Position a la création du personnage
        positionX = newPositionX;
        positionY = newPositionY;

        actif = true;

        stats = new Stats(newNiveau);
        barre = new BarresStatsPNJ(this);
    }

    /**
     * Affichage du manequin
     * @param graphics sur le quel on affiche l'objet
     */
    public void render(Graphics graphics) throws SlickException {
        // Affiche la barre de vie si le mob est actif
        if (actif) barre.render(graphics);
        hitBox.render(graphics);
    }

    /**
     * Charge une animation a partir du SpriteSheet
     *
     * @param spriteSheet   : sprite a charger
     * @param positionDebut : n° de la premiere frame
     * @param positionFin   : n° de la derniere frame
     * @param action        : n° de la ligne de frame
     */
    protected Animation loadAnimation(SpriteSheet spriteSheet, int positionDebut, int positionFin, int action) {

        Animation aRetourner = new Animation(); // Animaation a retourner

        for (int i = positionDebut; i < positionFin; i++) {
            aRetourner.addFrame(spriteSheet.getSprite(i, action), TEMPS_ANIMATION);
        }

        return aRetourner;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public Stats getStats() {
        return stats;
    }

    public HitBox getHitBox() {return hitBox;}

    public boolean isActif() {return actif;}

    public void applyDamage(int damage) {
        if (stats.getPv() - damage > 0 ) {
            stats.setPv(stats.getPv()-damage);
        } else {
            stats.setPv(0);
            actif = false;
        }
    }
}
