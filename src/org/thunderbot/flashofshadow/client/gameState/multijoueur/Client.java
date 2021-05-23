package org.thunderbot.flashofshadow.client.gameState.multijoueur;

import org.newdawn.slick.SlickException;
import org.thunderbot.flashofshadow.client.gameState.MapGameState;
import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.Personnage;
import org.thunderbot.flashofshadow.client.gameState.utils.XMLHandler;
import org.thunderbot.flashofshadow.serveur.Serveur;

import java.io.IOException;
import java.net.*;

import org.thunderbot.flashofshadow.client.gameState.utils.XMLTools;

public class Client {

    public static final int PORT_ENVOI = 6589;
    public static final int PORT_RECEPTION = 6588;

    private final static int TAILLE = 1024;

    private static final String ADRESSE_SERVEUR = "localhost";

    private InetAddress serveur;
    private DatagramSocket socket;

    public Client() {

        // Utilisation de la config des option
        String adresseName = XMLTools.getValueIn("res/option/config_net.xml", "adresse_serveur");

        // Connexion au serveur
        try {
            serveur = InetAddress.getByName(adresseName);
            socket = new DatagramSocket(PORT_RECEPTION);
            envoiDonnee(Serveur.AUTHENTIFICATION, " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(MapGameState mapGameState) throws SlickException {

        Personnage personnageJoueur = mapGameState.getHero();

        String aRetourner = "";
        try {
            envoiDonnee("updt", personnageJoueur.toString());
            aRetourner = receptionDonnee();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] data = aRetourner.split(":");
        switch (data[0]) {
            case Serveur.AUTHENTIFICATION:
                System.out.println(data[1]);
                personnageJoueur.setID(data[1]);
                break;

            case Serveur.NOUVEAU_JOUEUR:

                //Render new joueur
                mapGameState.addNewPlayer();
                System.out.println("newJoueur");

            case Serveur.UPDATE:
                mapGameState.updatePlayer(aRetourner.split(":")[1], aRetourner.split(":")[2]);

        }
    }

    public void envoiDonnee(String typeMessage, String message) throws IOException {
        byte buffer[];

        if (message.equals("")) {
            message = " ";
        }

        String aEnvoyer = typeMessage + ':' + message;

        int length = aEnvoyer.length();
        buffer = aEnvoyer.getBytes();
        DatagramPacket donneesEmises = new DatagramPacket(buffer, length, serveur, PORT_ENVOI);
        socket.send(donneesEmises);
    }

    public String receptionDonnee() throws IOException {
        DatagramPacket donneesRecues = new DatagramPacket(new byte[TAILLE], TAILLE);
        socket.receive(donneesRecues);

        return new String(donneesRecues.getData());
    }

    public void deconnection() {
        try {
            envoiDonnee(Serveur.STOP, "arret du client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}