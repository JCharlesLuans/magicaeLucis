package gameState.entite.PNJ;

import gameState.phisique.HitBox;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Mob de type manequin : ne bouge pas n'attaque pas
 *
 * @author Jean-Charles Luans
 */
public class Manequin extends Mob {

    public Manequin(float posX, float posY, int niveau) throws SlickException {

        /* Construit un mob */
        super(posX, posY, niveau);

        /* Sprite du manequin */
        spriteSheet = new SpriteSheet("res/drawable/gameState/entite/PNJ/mob/sprite/manequin.png", 64, 64);

        /* Animation du mob */
        animations = new Animation[1];
        animations[0] = loadAnimation(spriteSheet, 0, 8,0);

        /* Hit box du mob */
        hitBox = new HitBox(positionX-16, positionY-32, 64, 32);
    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        if (actif) {
            super.render(graphics);
            graphics.drawAnimation(animations[0], positionX - 32, positionY - 32);
        }
    }
}
