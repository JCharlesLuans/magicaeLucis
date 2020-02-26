package Code.Jeu.Sauvegarde;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Code.Jeu.Carte.Map;
import Code.Jeu.Personnage.Personnage;
import Code.Jeu.Personnage.Spell;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Sauvegarde {

    public static void sauvegarde(Personnage aSauvegarder) {



    }

    public static Personnage chargement() throws SlickException {
        Personnage perso = new Personnage(new Map(), new Spell());
        return perso;
    }
}
