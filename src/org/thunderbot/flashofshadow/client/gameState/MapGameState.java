package org.thunderbot.flashofshadow.client.gameState;

import org.thunderbot.flashofshadow.client.gameState.carte.Map;
import org.thunderbot.flashofshadow.client.gameState.entite.PNJ.Manequin;
import org.thunderbot.flashofshadow.client.gameState.entite.Camera;
import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.Personnage;
import org.thunderbot.flashofshadow.client.gameState.entite.Spell;
import org.thunderbot.flashofshadow.client.gameState.multijoueur.Client;
import org.thunderbot.flashofshadow.client.gameState.phisique.PlayerController;
import org.thunderbot.flashofshadow.client.gameState.utils.Sauvegarde;
import org.thunderbot.flashofshadow.client.gameState.entite.Personnage.UI.BarresStats;
import org.thunderbot.flashofshadow.client.gameState.UI.MenuEnJeu;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * RPG créer avec Slick2d
 * @author J-Charles Luans
 * @version 1.0
 */
public class MapGameState extends BasicGameState {

    /* --------------------------------- CONSTANTE ------------------------------------*/

    /* Vitesse de succéssion d'image dans une animation (en ms) */
    private final int TEMPS_ANIMATION = 100;

    private final int POS_X= 10,
            POS_Y = 10;

    private static final Color COULEUR_VIE= new Color(255, 0, 0);
    private static final Color COULEUR_MANA = new Color(0, 0, 255);
    private static final Color COULEUR_XP = new Color(0, 255, 0);

    /* -------------------------------- Attribut du jeu -----------------------------------*/

    public static final int ID = 2;

    /** Conteneur du jeu */
    private GameContainer container;

    /** Carte actuelle */
    private static Map map;

    /** Personnage principal */
    private Personnage hero;

    /** Controller du personnage */
    private PlayerController playerController;

    /** Sort du hero */
    private Spell spell;

    /** Camera principale */
    private Camera cam;

    /** Ennemi */
    private Manequin mob;

    /** Barre de vie du personnage principal */
    private BarresStats barres;

    /** Indique si le menu doit etre montré ou pas */
    private MenuEnJeu menuEnJeu;

    /** Indique si la map doit etre charger ou pas */
    private boolean charger = false;

    /** Client multijour pour communication avec le serveur */
    private Client client;

    /* -------------------------------- Méthode d'héritage -------------------------------------------------- */

    /**
     * constructeur du jeu de base qui est etendu de la classe BasicGame
     */
    public MapGameState() {
        super();
    }

    public MapGameState(boolean isCharged) {
        super();
        charger = isCharged;
    }

    public void enter(GameContainer gameContainer, StateBasedGame game) throws SlickException {

        Music background = new Music("res/son/musique/carte/TownTheme.ogg");
        background.loop();

        if (charger) {
            hero = Sauvegarde.chargement();
            charger = false;
        }
    }

    /**
     * Methode de chargement du jeu
     * @param gameContainer : conteneur du jeu
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gameContainer, StateBasedGame game) throws SlickException  {

        // Init du client multijoueur
        client = new Client();

        // Création du conteneur du jeu
        container = gameContainer;

        // Création de la map
        map = new Map();

        // Création du personnage principal
        hero = new Personnage(map);

        // Création de la camera
        cam = new Camera(hero, container, map);

        // Création du HUD
        barres = new BarresStats(hero.getStats());

        menuEnJeu = new MenuEnJeu(container, game);

        playerController = new PlayerController(hero, cam, menuEnJeu);
        container.getInput().addKeyListener(playerController);
        container.getInput().addControllerListener(playerController);
        container.getInput().addMouseListener(playerController);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame game, int delta) throws SlickException {

        hero.update(delta);
        System.out.println(client.update(hero)); // Envoie des données au serveur pour mettre a jour
        cam.actualisation();
        if (game.closeRequested() ) {
            System.out.println("STOP");
            System.out.println("DECONNECTION");
        }

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame game, Graphics graphics) throws SlickException {

        gameContainer.setShowFPS(false); // Affichage des fps

        cam.render(graphics);            // Affiche la camera sur la moitier de l'ecran

        map.renderBackground();          // Rendu du background de la carte


        map.renderMob(graphics);            // Rendu du mob
        hero.render(graphics);           // Rendu du personnnage

        map.renderForeground();          // Rendu du foreground de la carte

        barres.affichage(graphics);      // Rendu des barres de vie de magie et d'xp
        menuEnJeu.render(graphics);      // Rendu du menu + de l'inventaire



    }



    public void setCharger(boolean charger) {
        this.charger = charger;
    }

    public Personnage getHero() {
        return hero;
    }
    public int getNiveauMap() {
        return map.getNiveau();
    }

    @Override
    public int getID() {
        return ID;
    }
}
