package org.thunderbot.flashofshadow.serveur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Serveur {

    public static final String AUTHENTIFICATION = "auth";
    public static final String UPDATE           = "updt";
    public static final String STOP             = "stop";
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
                        for (int i = 0; i < clientConnecter.size(); i++) {
                            serveur.envoiDonnee(UPDATE + ":" + donneeATraiter[1], clientConnecter.get(i));
                        }
                        System.out.println("LOG : " + UPDATE + " -> " + donneeATraiter[1]);
                        break;

                    case STOP:
                        // Enleve le client qui viens de se deconnecter de la liste des lients
                        System.out.println("LOG : " + STOP + " -> " + adresseExpeditaire);
                        clientConnecter.remove(adresseExpeditaire);
                        break;
                }
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }

}
