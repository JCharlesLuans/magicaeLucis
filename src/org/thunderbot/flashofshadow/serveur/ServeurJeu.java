package org.thunderbot.flashofshadow.serveur;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ServeurJeu {

    public static final int PORT_ENVOI = 6588;
    public static final int PORT_RECEPTION = 6589;

    /** Socket du serveur */
    DatagramSocket socket;

    public ServeurJeu() throws SocketException {
        socket = new DatagramSocket(PORT_RECEPTION);
        System.out.println("Démarrage du serveur");
    }

    public void envoiDonnee(String aEnvoyer, InetAddress adresse) throws IOException {
        DatagramPacket dataEnvoyee = new DatagramPacket(aEnvoyer.getBytes(), aEnvoyer.length(), adresse, PORT_ENVOI);
        socket.send(dataEnvoyee);
    }

    public DatagramPacket receptionDonnee() throws IOException{
        byte[] buffer = new byte[1024];
        DatagramPacket reception = new DatagramPacket(buffer, 1024);
        socket.receive(reception);
        return reception;
    }
}