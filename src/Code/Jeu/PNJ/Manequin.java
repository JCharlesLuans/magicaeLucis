package Code.Jeu.PNJ;

import Code.Jeu.Carte.Map;
import Code.Jeu.Personnage.Stats;
import Code.Jeu.UI.BarresStatsPNJ;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Manequin extends Mob {

    public Manequin(float posX, float posY, int niveau) throws SlickException {
        super(posX, posY, niveau);
        animations = new Animation[1];
        spriteSheet = new SpriteSheet("Ressources/Personnage/Sprites/manequin.png", 64, 64);
        animations[0] = loadAnimation(spriteSheet, 0, 8,0);
    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        if (actif) {
            super.render(graphics);
            graphics.drawAnimation(animations[0], positionX - 32, positionY - 15);
        }
    }
}
