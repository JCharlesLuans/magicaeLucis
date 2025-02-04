package gameState.phisique;

import gameState.entite.PNJ.Mob;
import gameState.entite.Personnage.Personnage;

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
        if (statsPerso.getMana() >= 25) {
            personnage.getSpell().tirer();
            statsPerso.setMana(personnage.getStats().getMana() - 25);
        }
    }

    public void coup() {

        boolean mobExiste; // Indique si il y a un mobExiste ou pas

        // Récupere la Hit Box Du personnage
        HitBox tmp = personnage.getHitBoxArme();

        // Verifie si il y a un mobExiste a porté
        mobExiste = personnage.getMap().isMob(tmp);

        // Retire des PV au mobs
        if (mobExiste) {
            Mob mob = personnage.getMap().getMobAt(personnage.getHitBoxArme());
            statsMob = mob.getStats();
            mob.applyDamage(statsPerso.getDegaAttaque());

            // Verifie si le mobExiste est mort
            if (statsMob.getPv() <= 0) {
                statsPerso.setXp(statsPerso.getXp() + statsMob.getNiveau() * 15);
            }
        }
    }

    public void spellTouch(Mob mob) {

        statsMob = mob.getStats();

        mob.applyDamage(statsPerso.getDegaDefense());

        // Augmente l'XP du joueur si le mob est mort
        if (statsMob.getPv() <= 0) {
            statsPerso.setXp(statsPerso.getXp() + statsMob.getNiveau() * 15);
        }
    }

}
