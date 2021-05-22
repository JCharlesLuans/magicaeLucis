package gameState.multijoueur;

import gameState.entite.Personnage.Personnage;

import java.io.IOException;
import java.net.*;

public class Client {

    private final static int PORT =  6588;
    private final static int TAILLE = 1024;

    private static final String ADRESSE_SERVEUR = "192.168.1.21";

    private InetAddress serveur;
    private DatagramSocket socket;

    public Client() {

        // STUB
        String message = "auth";

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

            Thread t = new Thread() {
                public void run() {
                    try {
                        aRetourner[0] = receptionDonnee();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();


        } catch (IOException err) {
            System.out.println("erreur");
            System.out.println(err.getMessage());
        }
        return aRetourner[0];
    }

    public void envoiDonnee(String typeMessage, String message) throws IOException {
        byte buffer[];

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