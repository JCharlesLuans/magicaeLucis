package org.thunderbot.flashofshadow.client.gameState.entite;

import org.thunderbot.flashofshadow.client.gameState.carte.Map;
import org.thunderbot.flashofshadow.client.gameState.phisique.Combat;
import org.thunderbot.flashofshadow.client.gameState.phisique.HitBox;
import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.Personnage;

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

    /**
     * Indique le mouvement de l'animations
     */
    private int mouvement;

    private final int EXPLOSION = 4;

    /** Vitesse de succéssion d'image dans une animation (en ms) */
    private final int TEMPS_ANIMATION = 100;

    /** Indique la direction de du sort */
    private int direction;

    /** Sprite du sort */
    private SpriteSheet spriteFireBall;
    private SpriteSheet spriteExplosion;

    /** Animations */
    private Animation[] animations = new Animation[5];

    /** Visible */
    private boolean visible;
    private boolean explose;
    private boolean actif;

    /** Position du centre de l'image en X et Y */
    private float positionX,
                  positionY;

    /** Position reele de X et Y (pour les tests) */
    private float reelX,
                  reelY;

    /** Map sur laquelle evolu le sort */
    private Map map;

    /** Hit box du sort */
    HitBox hitBox;

    /** Personnage qui lance le sort */
    Personnage personnage;

    /** Action de combat du sort */
    Combat combat;

    /**
     * Créer un nouveau sort avec comme positionpar default (0,0)
     * qui n'est pas visible
     */
    public Spell(Map map, Personnage personnage) throws SlickException {

        /* Environement du sort */
        this.map = map;

        /* Personnage qui lance le sort */
        this.personnage = personnage;

        this.combat = personnage.getCombat();

        /* Initialise le spell */
        initialiseSpell();

        /* HitBox Du sort */
        hitBox = new HitBox(positionX, positionY, 16, 16);

        /* Affichage du sort */
        visible = false;
        spriteFireBall = new SpriteSheet("res/drawable/org.thunderbot.flashofshadow.client.gameState/entite/spell/sprite/fireball.png", 64, 64);
        spriteExplosion = new SpriteSheet("res/drawable/org.thunderbot.flashofshadow.client.gameState/entite/spell/sprite/explosion.png", 96, 96);

        /* Animation du sort */
        animer(spriteFireBall);
        animations[EXPLOSION] = loadAnimation(spriteExplosion,0,12,0);

    }

    /**
     * Affiche le sort
     * @param graphics : graphique sur le quelle on affiche le sort
     */
    public void render(Graphics graphics) {
        //Calcul de pos X et pos Y pour que l'affichage soit en (0,0)
        if (visible || explose) graphics.drawAnimation(animations[mouvement], positionX-32, positionY-32);

    }

    /**
     * Rafraichie le sort
     * @param delta : vitesse du jeu
     */
    public void update(int delta) {

        correctionPosition();

        boolean colision = map.isCollision(reelX, reelY); // Vérifie si il y a une colision avec un decor
        boolean mob = map.isMob(hitBox);                 // Vérifie si il y a une colision avec un mob

        explose = colision || mob; // Si il y a colision avec decor ou mob : explose = true

        mouvement = direction;

        if (explose) {
            mouvement = EXPLOSION;
            visible = false;

            /* Inflige les dégas au mobs touché */
            if (mob && actif) {
                combat.spellTouch(map.getMobAt(hitBox));
                initialiseSpell();
            }
        }

        if (animations[EXPLOSION].getFrame() == 11 && mouvement == EXPLOSION) {
            mob = false;
            explose = false;
            colision = false;
        }

        if (visible) {
            positionX = getFuturX(delta + 12);
            positionY = getFuturY(delta + 12);
        }

        updateHitBox();
    }

    /**
     * Tir le sort
     */
    public void tirer() {

        visible = true;
        actif = true;
        direction = personnage.getDirection();
        positionX = personnage.getPositionX();

        /* Corige la hauteur du sort par rapport au personnage */
        if (direction == HAUT) {
            positionY = personnage.getPositionY() - 30;
        } else if (direction == DROITE ||direction == GAUCHE){
            positionY = personnage.getPositionY() - 16;
        } else {
            positionY = personnage.getPositionY();
        }

    }

    /**
     * Corrige les direction pour tester les colisions
     */
    private void correctionPosition() {
        switch (direction) {

            case HAUT:
                reelX = positionX;
                reelY = positionY-32;
                break;
            case BAS:
                reelX = positionX;
                reelY = positionY+32;
                break;
            case GAUCHE:
                reelX = positionX-32;
                reelY = positionY;
                break;
            case DROITE:
                reelX = positionX+32;
                reelY = positionY;
                break;
        }
    }

    /**
     * Met ajour les position de la Hit Box
     */
    private void updateHitBox() {
        switch (direction) {

            case GAUCHE:
                hitBox.setX(positionX-32f);
                hitBox.setY(positionY-8f);
                break;

            case DROITE:
                hitBox.setX(positionX+16f);
                hitBox.setY(positionY-8f);
                break;

            case HAUT:
                hitBox.setX(positionX-8f);
                hitBox.setY(positionY-16f);
                break;

            case BAS:
                hitBox.setX(positionX-8f);
                hitBox.setY(positionY);
                break;
        }
    }

    /**
     * Reinisialise le sort
     */
    private void initialiseSpell() {

        /* N'est pas actif */
        actif = false;

        /* N'est pas visible */
        visible = false;

        /* Mouvement du sort */
        direction = personnage.getDirection();
        positionX = personnage.getPositionX();
        positionY = personnage.getPositionY();

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
     * @param spriteSheet   : sprite a charger
     * @param positionDebut : n° de la premiere frame
     * @param positionFin   : n° de la derniere frame
     * @param action        : n° de la ligne de frame
     */
    private Animation loadAnimation(SpriteSheet spriteSheet, int positionDebut, int positionFin, int action) {

        /* Animation a retourner */
        Animation aRetourner = new Animation();
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
