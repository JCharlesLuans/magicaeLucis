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

    private static ArrayList<InetAddress> clientConncter = new ArrayList();

    private static DatagramSocket socket;

    public static void main(String[] args) throws IOException {

        byte buffer[] = new byte[TAILLE];

        String donnee[];
        String reception;

        InetAddress adresseReception; // Adresse dont on viens de recevoir une donnée

        int taille;

        socket = new DatagramSocket(PORT);

        System.out.println("Lencement du serveur");

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);

            adresseReception = packet.getAddress();

            // Reception et traitement data
            taille = packet.getLength();
            reception = new String(packet.getData(), 0, taille);

            // TODO gestion crash sur data[1]
            donnee = reception.split(":");
            
            if (donnee[0].equals("auth")) {
                clientConncter.add(adresseReception);
                System.out.println("Connexion : " + adresseReception);
            } else {
                //Renvoie nouvelle position du joueur si ce n'est pas la meme adresse
                System.out.println(adresseReception + " : Log : " + "stub");
                for (int i = 0; i < clientConncter.size(); i++) {
                    renvoiInformation("stub", clientConncter.get(i));

                }
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
