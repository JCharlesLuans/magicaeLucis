package org.thunderbot.flashofshadow.client.gameState.utils;

import org.xml.sax.HandlerBase;

/**
 * Classe utilisée pour gérer les événements émis par SAX lors du traitement du fichier XML
 */
public class XMLHandler extends HandlerBase {

    private String tagCourant;

    public XMLHandler() {
        super();
    }

    /**
     * Actions à réaliser sur les données
     */
    public void characters(char[] caracteres, int debut, int longueur) {
        String donnees = new String(caracteres, debut, longueur);
    }

    /**
     * Actions à réaliser lors de la fin du document XML.
     */
    public void endDocument() {
    }

    /**
     * Actions à réaliser lors de la détection de la fin d'un élément.
     */
    public void endElement(String name) {
    }

    /**
     * Actions à réaliser au début du document.
     */
    public void startDocument() {
    }

    /**
     * Actions à réaliser lors de la détection d'un nouvel élément.
     */
    public void startElement(String name, org.xml.sax.AttributeList atts) {
        tagCourant = name;
    }
}