package org.thunderbot.flashofshadow.serveur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Serveur {

    public static final String AUTHENTIFICATION = "auth";
    public static final String UPDATE           = "updt";
    public static final String STOP             = "stop";
    public static final String ERREUR           = "erre";

    private static ServeurJeu serveur;
    private static ArrayList<InetAddress> listClientConnecter;

    public static void main(String[] args) {
        boolean isOn = true;

        // Liste des clients connecter
        listClientConnecter = new ArrayList<>();

        // Reception des donnée
        DatagramPacket packetRecu;
        InetAddress adresseExpeditaire;
        String messageRecu;
        String[] donneeATraiter;

        try {
            serveur = new ServeurJeu();

            while (isOn) {

                // Gestion de la reception
                packetRecu = serveur.receptionDonnee();
                messageRecu = new String(packetRecu.getData());
                adresseExpeditaire = packetRecu.getAddress();
                donneeATraiter = messageRecu.split(":");

                // Traitement de la commande
                switch (donneeATraiter[0]) {
                    case AUTHENTIFICATION:
                        authentification(adresseExpeditaire);
                        break;

                    case UPDATE:
                        update(donneeATraiter);
                        break;

                    case STOP:
                        stop(adresseExpeditaire);
                        break;
                }
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    /**
     * Authentifie un nouveau client aupres du serveur
     * @param adresseExpeditaire adresse du nouveau client
     * @throws IOException
     */
    private static void authentification(InetAddress adresseExpeditaire) throws IOException {
        serveur.envoiDonnee(AUTHENTIFICATION + ":ok" , adresseExpeditaire);
        listClientConnecter.add(adresseExpeditaire);
        System.out.println("LOG : " + AUTHENTIFICATION + " -> " + adresseExpeditaire);

        // Envoie a tout les autres clients la connextion d'un nouveau client
        // TODO gestion des clients
    }

    /**
     * Renvoi les information actualiser au client
     * @param donneeATraiter donner actualiser
     * @throws IOException
     */
    private static void update(String[] donneeATraiter) throws IOException {
        for (int i = 0; i < listClientConnecter.size(); i++) {
            serveur.envoiDonnee(UPDATE + ":" + donneeATraiter[1], listClientConnecter.get(i));
        }
        System.out.println("LOG : " + UPDATE + " -> " + donneeATraiter[1]);
    }

    /**
     * Deconnecte un client du serveur
     * @param adresseExpeditaire
     */
    private static void stop(InetAddress adresseExpeditaire) {
        System.out.println("LOG : " + STOP + " -> " + adresseExpeditaire);
        listClientConnecter.remove(adresseExpeditaire);
    }

}
