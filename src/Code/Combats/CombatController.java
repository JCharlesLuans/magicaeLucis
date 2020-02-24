/*
 * CombatController.java                         19/02/2020
 * Pas de copyright (pour le moment :)
 */

package Code.Combats;

import Code.Jeu.MapGameState;
import Code.Menu.GameOverState;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

/**
 * Controller des combats
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
        int attaqueJoueur = hero.getStats().getDegaAttaque() + random.nextInt(4);

        // Si inférieur a 10% alors dega double
        if (random.nextDouble() < 0.1 ) {
            attaqueJoueur += attaqueJoueur;
        }

        ennemi.getStats().setPv(ennemi.getStats().getPv() - attaqueJoueur); // Set des dega de l'ennemi

        if (ennemi.getStats().getPv() <= 0) { // Ennemi est mort
            hero.getStats().setXp(hero.getStats().getXp() + ennemi.getStats().getNiveau()*10);
            game.enterState(MapGameState.ID);
        } else {

            // Ennemi fait entre 10 et 18 degats
            int attaqueEnnemi = ennemi.getStats().getDegaAttaque() + random.nextInt(2);
            hero.getStats().setPv(hero.getStats().getPv() - attaqueEnnemi);

            if (hero.getStats().getPv() <= 0) { //Hero a perdu
                game.enterState(GameOverState.ID);
            }

        }
    }

    private void defendre() {
        // l'ennemi inflige entre 5 et 9 dégâts
        int attaqueEnnemi = ennemi.getStats().getDegaAttaque() + random.nextInt(3);

        attaqueEnnemi -= hero.getStats().getBouclier(); // La capacité du bouclier est absorbé
        attaqueEnnemi = attaqueEnnemi >= 0 ? attaqueEnnemi : 0;

        hero.getStats().setPv(hero.getStats().getPv() - attaqueEnnemi);
        if (hero.getStats().getPv() <= 0) { // joueur mort ?
            game.enterState(GameOverState.ID); // retour titre
        } else {

            // le joueur inflige entre 7 et 10 dégats sans critique
            int attaqueJoueur = hero.getStats().getDegaDefense() + random.nextInt(3);

            ennemi.getStats().setPv(ennemi.getStats().getPv() - attaqueJoueur);

            if (ennemi.getStats().getPv() <= 0) { // ennemi mort ?
                hero.getStats().setXp(hero.getStats().getXp() + ennemi.getStats().getNiveau()*10);
                game.enterState(MapGameState.ID); // retour à la carte
            }
        }
    }


    private void fuir() {
        // l'ennemi inflige entre 5 et 9 dégats
        int attaqueEnnemi = 10 + random.nextInt(9);
        hero.getStats().setPv(hero.getStats().getPv() - attaqueEnnemi);
        if (hero.getStats().getPv() <= 0) { // joueur mort ?
            game.enterState(GameOverState.ID); // retour titre
        } else {
            game.enterState(MapGameState.ID); // retour à la carte
        }
    }
}
