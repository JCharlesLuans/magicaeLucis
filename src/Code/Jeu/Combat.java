package Code.Jeu;

import Code.Jeu.PNJ.Mob;
import Code.Jeu.Personnage.Personnage;
import Code.Jeu.Personnage.Stats;

/**
 * Classe permettant les actions de combats
 */
public class Combat {

    /** Personnage faisant les action de combats */
    Personnage personnage;

    /** Stats du personnage qui combat */
    Stats statsPerso;

    /** Stats du mob qui combat */
    Stats statsMob;


    /**
     * Classe de combat lié au joueur
     * @param personnage : personnage impacter par les combats
     */
    public Combat(Personnage personnage) {
        this.personnage = personnage;
        this.statsPerso = personnage.getStats();
    }

    public void spell() {
        personnage.getSpell().tirer();
        statsPerso.setMana(personnage.getStats().getMana() - 25);
    }

    public void coup() {
        // TODO donné des coup d'épée
    }

    public void spellTouch(Mob mob) {

        statsMob = mob.getStats();

        mob.applyDamage(statsPerso.getDegaDefense());

        // Augmente l'XP du joueur si le mob est mort
        if (statsMob.getPv() <= 0) {
            statsPerso.setXp(statsPerso.getXp() + statsMob.getNiveau() * 15);
        }
    }

    public void coupTouch(Mob mob) {

    }
}
