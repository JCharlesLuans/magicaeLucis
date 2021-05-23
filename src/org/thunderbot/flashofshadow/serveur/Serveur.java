package org.thunderbot.flashofshadow.serveur;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Serveur {

    public static final String AUTHENTIFICATION = "auth";
    public static final String UPDATE           = "updt";
    public static final String ERREUR           = "erre";

    public static final int PORT = 6588;
    public static final int TAILLE = 1024;

    public static void main(String[] args) throws IOException {

        DatagramSocket socket = new DatagramSocket(PORT);

        String[] donnee;  // Message découper
        String reception; // Message reçu non découper

        String typeMessage;   // Typer de la reception
        String message;       // Message
        String dataAPartager; // Donnée a renvoyer au client

        int taillePacket;
        byte[] buffer = new byte[TAILLE];

        // Liste des client connecter
        ArrayList<InetAddress> clientConncter = new ArrayList();

        // Adresse dont on viens de recevoir une donnée
        InetAddress adresseReception;

        /* Lancement serveur */
        System.out.println("Lancement du serveur");
        while (true) {

            // Reception data
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            adresseReception = packet.getAddress();

            // Reception et traitement data
            taillePacket = packet.getLength();
            reception = new String(packet.getData(), 0, taillePacket);

            donnee = reception.split(":");

            // Protection echec reception lié a UDP
            if (donnee.length <= 1) {
                // Cas ou le message se perd
                typeMessage = ERREUR;
                message = "Pas de message";
            } else {
                typeMessage = donnee[0];
                message = donnee[1];
            }

            // Traitement de la data
            dataAPartager = typeMessage + ":" + message;

            switch (typeMessage) {
                case AUTHENTIFICATION :
                    clientConncter.add(adresseReception);
                    envoiInformation(socket, AUTHENTIFICATION + ":connexion ok", adresseReception);
                    System.out.println("Connexion : " + adresseReception);
                    break;

                case UPDATE:
                    envoiInformation(socket, dataAPartager, adresseReception);
                    System.out.println(adresseReception + " : Log : " + dataAPartager);
                    break;

                case ERREUR:
                    System.out.println(adresseReception + " : Err : " + "Pas de message");
                    break;
            }
        }
    }

    // TODO renvoie de donner
    private static void envoiInformation(DatagramSocket socket, String aEnvoyer, InetAddress address) {

        try {
            DatagramPacket packet = new DatagramPacket(aEnvoyer.getBytes(), aEnvoyer.length(), address, PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
