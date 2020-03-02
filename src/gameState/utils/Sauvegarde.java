package java.gameState.utils;

import java.gameState.carte.Map;
import java.gameState.entite.Personnage.Personnage;

import org.newdawn.slick.SlickException;

public class Sauvegarde {

    public static void sauvegarde(Personnage aSauvegarder) {



    }

    public static Personnage chargement() throws SlickException {
        Personnage perso = new Personnage(new Map());
        return perso;
    }
}
