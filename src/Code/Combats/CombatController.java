/*
 * CombatController.java                         19/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Combats;

import Code.Jeu.MapGameState;
import Code.Menu.MenuGameState;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

/**
 * //TODO ecrire Java Doc
 *
 * @author Jean-Charles Luans
 * @verison 1.0
 */
public class CombatController implements InputProviderListener {

    private CombatEnnemi ennemi;
    private CombatPersonnage hero;
    private StateBasedGame game;

    private Random random = new Random();

    public CombatController (CombatPersonnage newHero, CombatEnnemi newEnnemi, StateBasedGame newGame) {
        hero = newHero;
        ennemi = newEnnemi;
        game = newGame;
    }

    @Override
    public void controlPressed(Command command) {

        switch ((CombatCommande) command) {
            case ATTAQUER: attaquer(); break;
            case DEFENDRE: defendre(); break;
            case FUIR: fuir(); break;
            default: break;
        }

    }

    @Override
    public void controlReleased(Command command) {}

    private void attaquer() {

        // Le joueur inflige entre 7 et 10 dégats
        int attaqueJoueur = 7 + random.nextInt(4);

        // Si inférieur a 10%
        if (random.nextDouble() < 0.1 ) {
            attaqueJoueur += attaqueJoueur;
        }

        ennemi.setPv(ennemi.getPv() - attaqueJoueur);

        if (ennemi.getPv() <= 0) {
            game.enterState(MapGameState.ID);
        } else {

            // Ennemi fait entre 10 et 18 degats
            int attaqueEnnemi = 10 + random.nextInt(9);
            hero.setPv(hero.getPv() - attaqueEnnemi);

            if (hero.getPv() <= 0) {
                game.enterState(MenuGameState.ID);
            }

        }
    }

    private void defendre() {}

    private void fuir() {}
}
