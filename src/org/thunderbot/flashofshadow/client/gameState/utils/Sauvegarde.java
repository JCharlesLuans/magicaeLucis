package org.thunderbot.flashofshadow.client.gameState.utils;

import org.thunderbot.flashofshadow.client.gameState.carte.Map;
import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.Personnage;

import org.newdawn.slick.SlickException;

public class Sauvegarde {

    public static void sauvegarde(Personnage aSauvegarder) {



    }

    public static Personnage chargement() throws SlickException {
        Personnage perso = new Personnage(new Map());
        return perso;
    }
}
