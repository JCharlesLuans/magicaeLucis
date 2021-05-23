package org.thunderbot.flashofshadow.client.gameState.multijoueur;

import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.Personnage;
import org.thunderbot.flashofshadow.serveur.ServeurJeu;

import java.io.IOException;
import java.net.*;

public class Client {

    public static final int PORT_ENVOI = 6589;
    public static final int PORT_RECEPTION = 6588;

    private final static int TAILLE = 1024;

    private static final String ADRESSE_SERVEUR = "localhost";

    private InetAddress serveur;
    private DatagramSocket socket;

    public Client() {

        // Connexion au serveur
        try {
            serveur = InetAddress.getByName(ADRESSE_SERVEUR);
            socket = new DatagramSocket(PORT_RECEPTION);
            envoiDonnee("auth", " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String update(Personnage hero) {

        String aRetourner = "";
        try {
            envoiDonnee("updt", hero.toString());
            aRetourner = receptionDonnee();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return aRetourner;
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
}