package org.thunderbot.flashofshadow.client.gameState.multijoueur;

import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.Personnage;

import java.io.IOException;
import java.net.*;

public class Client {

    private final static int PORT =  6588;
    private final static int TAILLE = 1024;

    private static final String ADRESSE_SERVEUR = "192.168.1.21";

    private InetAddress serveur;
    private DatagramSocket socket;

    public Client() {

        // Connexion au serveur
        try {
            serveur = InetAddress.getByName(ADRESSE_SERVEUR);
            socket = new DatagramSocket();
            envoiDonnee("auth", " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String update(Personnage hero) {

        final String[] aRetourner = {""};

        try {
            envoiDonnee("updt", hero.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // reception // TODO reception et exploitation des données
        Thread t = new Thread() {
            public void run() {
                try {
                    System.out.println(receptionDonnee());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();


        return aRetourner[0];
    }

    public void envoiDonnee(String typeMessage, String message) throws IOException {
        byte buffer[];

        if (message.equals("")) {
            message = " ";
        }

        String aEnvoyer = typeMessage + ':' + message;

        int length = aEnvoyer.length();
        buffer = aEnvoyer.getBytes();
        DatagramPacket donneesEmises = new DatagramPacket(buffer, length, serveur, PORT);
        socket.send(donneesEmises);
    }

    public String receptionDonnee() throws IOException {
        DatagramPacket donneesRecues = new DatagramPacket(new byte[TAILLE], TAILLE);
        socket.receive(donneesRecues);

        return new String(donneesRecues.getData());
    }
}