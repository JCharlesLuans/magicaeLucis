package gameState.multijoueur;

import gameState.entite.Personnage.Personnage;

import java.io.IOException;
import java.net.*;

public class Client {

    private final static int PORT =  6588;
    private final static int TAILLE = 1024;

    private static final String ADRESSE_SERVEUR = "localhost";

    private InetAddress serveur;
    private DatagramSocket socket;

    public Client() {

        // STUB
        String message = "auth";

        try {
            serveur = InetAddress.getByName(ADRESSE_SERVEUR);
            socket = new DatagramSocket();
            envoiDonnee(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Personnage hero) {
        try {
            envoiDonnee(hero.toString());
        } catch (IOException err) {
            System.out.println("erreur");
            System.out.println(err.getMessage());
        }
    }

    public void envoiDonnee(String aEnvoyer) throws IOException {
        byte buffer[];

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