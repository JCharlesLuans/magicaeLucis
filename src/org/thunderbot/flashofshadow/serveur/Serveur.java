package org.thunderbot.flashofshadow.serveur;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Serveur {

    private static final int PORT = 6588;
    private static final int TAILLE = 1024;

    private static DatagramSocket socket;

    public static void main(String[] args) throws IOException {


        String[] donnee;  // Message découper
        String reception; // Message reçu non découper

        int taillePacket;
        byte[] buffer = new byte[TAILLE];

        // Liste des client connecter
        ArrayList<InetAddress> clientConncter = new ArrayList();

        // Adresse dont on viens de recevoir une donnée
        InetAddress adresseReception;

        socket = new DatagramSocket(PORT);

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

            // TODO gestion crash sur data[1]
            donnee = reception.split(":");
            
            if (donnee[0].equals("auth")) {
                clientConncter.add(adresseReception);
                System.out.println("Connexion : " + adresseReception);
            } else {
                //Renvoie nouvelle position du joueur si ce n'est pas la meme adresse
                System.out.println(adresseReception + " : Log : " + "stub");
                renvoiInformation(donnee[1], adresseReception);
            }
        }
    }

    // TODO renvoie de donner
    private static void renvoiInformation(String hero, InetAddress address) {
        DatagramPacket envoi;

        String message;

        // Envoie au autres ces données la
        message = hero;
        //System.out.println("Donnees envoyees = "+message);
        envoi = new DatagramPacket(message.getBytes(),
                message.length(), address, PORT);
        try {
            socket.send(envoi);
        } catch (IOException err) {
            System.out.println("Erreur sur " + address);
            System.out.println(err.getMessage());
        }
    }

}
