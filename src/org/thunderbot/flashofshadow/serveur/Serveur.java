package org.thunderbot.flashofshadow.serveur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Serveur {

    public static final String AUTHENTIFICATION = "auth";
    public static final String UPDATE           = "updt";
    public static final String ERREUR           = "erre";

    public static void main(String[] args) {
        boolean isOn = true;

        // Liste des clients connecter
        ArrayList<InetAddress> clientConnecter = new ArrayList<>();

        // Reception des donnée
        DatagramPacket packetRecu;
        InetAddress adresseExpeditaire;
        String messageRecu;
        String[] donneeATraiter;

        try {
            ServeurJeu serveur = new ServeurJeu();

            while (isOn) {

                // Gestion de la reception
                packetRecu = serveur.receptionDonnee();
                messageRecu = new String(packetRecu.getData());
                adresseExpeditaire = packetRecu.getAddress();
                donneeATraiter = messageRecu.split(":");

                // Traitement de la commande
                switch (donneeATraiter[0]) {
                    case AUTHENTIFICATION:
                        serveur.envoiDonnee(AUTHENTIFICATION + ":ok" , adresseExpeditaire);
                        clientConnecter.add(adresseExpeditaire);
                        System.out.println("LOG : " + AUTHENTIFICATION + " -> " + adresseExpeditaire);
                        break;

                    case UPDATE:
                        // Renvoie des datas au autres client
                        System.out.println("LOG : " + UPDATE + " -> " + donneeATraiter[1]);
                        break;

                }
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }

}
