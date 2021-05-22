package gameState.carte;

import gameState.phisique.HitBox;
import gameState.entite.PNJ.Manequin;
import gameState.entite.PNJ.Mob;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Map {

    /** Taille d'une tuille */
    private final int TAILLE_TUILLE = 32;

    private TiledMap map;

    private String nomMap;

    private int niveau;

    private int nbMob;   // Nombre de mobs
    private Mob[] mobs;  // Les mobs

    private Random rnd = new Random(); // Générateur de aléa

    /**
     * Initialise la map
     * @throws SlickException
     */
    public Map() throws SlickException {
        initialiseMap("res/carte/campagne_ThunderSun.tmx");
        map = new TiledMap("res/carte/campagne_ThunderSun.tmx");
        nomMap = "campagne_ThunderSun.tmx";

        niveau = Integer.parseInt(map.getMapProperty("niveau", "undefine"));
        nbMob = Integer.parseInt(map.getMapProperty("nbMob", "undefine"));
        mobs = new Mob[nbMob];

        generateurMobs();

        mobs[0] = new Manequin(400,400, 1);
    }

    public Map(String nom) throws SlickException {
        initialiseMap("src/Ressources/Map/" + nom);
        map = new TiledMap("Ressources/Map/" + nom);
        nomMap = nom;
    }

    /**
     * Genere un nombre de mob avec des niveau entre le niveau min de la map et sont niveau max (+2)
     * @throws SlickException
     */
    private void generateurMobs() throws SlickException {

        boolean nok; // Indique si un mob n'est pas placé

        // Esseye de placer tout les mobs
        for (int i = 1; i < nbMob; i++) {
            nok = true;
            do {
                /* Génération d'une position aléatoire */
                float posX = (float) rnd.nextInt(map.getWidth() * TAILLE_TUILLE);
                float posY = (float) rnd.nextInt(map.getHeight() * TAILLE_TUILLE);

                /* Génération d'un niveau aléatoire entre le niveau de la map
                    Et un palier de 2 niveau */
                int niveau = this.niveau + rnd.nextInt(2);

                // Esseye de placer le mobs si il n'y a pas de colision
                if (!isCollision(posX, posY)) {
                    mobs[i] = new Manequin(posX, posY, niveau);

                    // Esseye de placer le mob si il n'y pas de mob
                    if (!isMob(mobs[i].getHitBox()))
                        nok = false;
                }
            } while (nok);
        }
    }

    /**
     * Fait le rendu du foreground de la map
     */
    public void renderBackground() {
        map.render(0 ,0, 0); // eau
        map.render(0 ,0, 1); // sol
        map.render(0 ,0, 2); // background
        map.render(0 ,0, 3); // background 2
    }

    /**
     * Affiche les mobs qui sont sur la map
     * @param graphics graphics sur lequel on affiche les mobs
     * @throws SlickException
     */
    public void renderMob(Graphics graphics) throws SlickException {
        for (int i = 0; i < nbMob; i++) {
            if (mobs[i] != null) {
                mobs[i].render(graphics);
            }
        }
    }

    /**
     * Fait le rendu du foreground de la map
     */
    public void renderForeground() {
        map.render(0,0,4); // overground
        map.render(0,0,5); // overground 2
    }

    /**
     * Réecrit le fichier de map pour qu'il sois lisible par Slick
     * (ajoute a l'attribut objectgroup une auteur et une largeur)
     * @param cheminMap : chemin du fichier map a réécrire
     */
    static void initialiseMap(String cheminMap) {

        ArrayList<String> newMap = new ArrayList<>();

        BufferedReader lecteurAvecBuffer = null;
        PrintWriter ecrivain;

        String ligne;

        try
        {
            lecteurAvecBuffer = new BufferedReader(new FileReader(cheminMap));
            while ((ligne = lecteurAvecBuffer.readLine()) != null) {

                if (ligne.contains("<objectgroup") && !ligne.contains("width")) {
                    System.out.println(ligne);
                    ligne = ligne.replace('>', ' ');
                    if (ligne.contains("/")) {
                        ligne = ligne.replace('/', ' ');
                        ligne = ligne.concat("width=\"1\" height=\"1\" />");
                    } else {
                        ligne = ligne.concat("width=\"1\" height=\"1\" >");
                    }

                }
                newMap.add(ligne);
            }
            lecteurAvecBuffer.close();
        }
        catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ecrivain =  new PrintWriter(new BufferedWriter
                    (new FileWriter(cheminMap)));

            for (int i = 0; i < newMap.size(); i++) {
                ecrivain.println(newMap.get(i));
            }
            ecrivain.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Verifie si il y a une colision
     * @param x axe x sur lequelle on verifie si il y a une colision
     * @param y axe x sur lequelle on verifie si il y a une colision
     * @return true si il y a une colision
     *         false si il n'y a pas de colision
     */
    public boolean isCollision(float x, float y) {

        Image tile;
        Color color;

        /* On va chercher la tile qui se trouve au coordonnée future du personnage sur le calque logic */
        tile = map.getTileImage((int) x / map.getTileWidth(),
                (int) y / map.getTileHeight(),
                map.getLayerIndex("logique"));

        if (tile != null) {
            //Recupere la couleur
            color = tile.getColor((int) x % map.getTileHeight(), (int) y % map.getTileHeight());
            return color.getAlpha() > 0;
        } else {
            return false;
        }
    }

    /**
     * Verifie si il y a un mob au contact de la HitBox a tester
     * @param aTester : hitBox de l'objet qui rentre en colision avec le mob
     * @return true si il y a un mob
     */
    public boolean isMob(HitBox aTester) {

        // Test tout les mobs de la map
        for (int i = 0; i < nbMob; i++) {

            boolean isMob = false; // Indique si il y a un mob

            if (mobs[i] != null) {

                /* Vérifie si x et y sont dans la hit box d'un mob */
                isMob = mobs[i].getHitBox().isColision(aTester);

                // Si colision, on verifie que le mob est actif
                if (isMob) {
                    return mobs[i].isActif();
                }
            }
        }
        return false;
    }

    /**
     * @param hitBox en contacte avec un mob
     * @return : null si il n'y a pas de mob
     *           le mob qui contient la hitbox qui comprend x et y
     */
    public Mob getMobAt(HitBox hitBox) {

        // Parcour tout les monstre de la map
        for (int i = 0; i < nbMob; i++) {

            HitBox aTester = mobs[i].getHitBox(); // Récupere al Hit Box du mob

            // Test si la hitbox du monstre est en contact avec la hit box que l'on veut tester
            if (aTester.isColision(hitBox)) {
                return mobs[i]; // Retourne le mob si c'est le cas
            }
        }
        return null;
    }

    /**
     * Recupere le mob au coordonnée donnée
     * @param x : position X a tester
     * @param y : position Y a tester
     * @return : null si il n'y a pas de mob
     *           le mob qui contient la hitbox qui comprend x et y
     */
    public Mob getMobAt(float x, float y) {

        for (int i = 0; i < nbMob; i++) {

            HitBox aTester = mobs[i].getHitBox();
            if (aTester.isInHitBox(x, y)) {
                    return mobs[i];
            }

        }
        return null;
    }

    /**
     * Change de map
     * @param nom du fichier de la nouvelle carte
     * @throws SlickException
     */
    public void changeMap(String nom) throws SlickException {
        String chemin = "res/carte/" + nom;
        initialiseMap(chemin);
        map = new TiledMap(chemin);
        generateurMobs();
        nomMap = nom;

        // TODO resoudre probleme apparition sur nouvelle map
    }


    /** Getter et setters **/

    public int getObjectCount() {
        return map.getObjectCount(0);
    }

    public String getObjectType(int objectID) {
        return map.getObjectType(0, objectID);
    }

    public float getObjectX(int objectID) {
        return map.getObjectX(0, objectID);
    }

    public float getObjectY(int objectID) {
        return map.getObjectY(0, objectID);
    }

    public float getObjectWidth(int objectID) {
        return map.getObjectWidth(0, objectID);
    }

    public float getObjectHeight(int objectID) {
        return map.getObjectHeight(0, objectID);
    }

    public String getObjectProperty(int objectID, String propertyName, String def) {
        return map.getObjectProperty(0, objectID, propertyName, def);
    }

    public int getWidth() {
        return map.getWidth();
    }

    public int getHeight() {
        return map.getHeight();
    }

    public String getNomMap() {
        return nomMap;
    }

    public int getNiveau() {return niveau;}
}
