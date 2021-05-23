package org.thunderbot.flashofshadow.client.gameState.utils;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class XMLParser {

    public XMLParser() {
        super();
    }

    public void parse(String filename) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            parser.parse(new File(filename), new XMLHandler());

        } catch (Exception e) {
            System.out.println("Exception capturée : ");
            e.printStackTrace(System.out);
        }
    }
}