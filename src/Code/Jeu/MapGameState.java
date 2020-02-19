package Code.Jeu;

import Code.Combats.CombatGameState;
import Code.Jeu.Personnage.Camera;
import Code.Jeu.Personnage.Personnage;
import Code.Jeu.UI.BarresStats;
import Code.Jeu.UI.MenuEnJeu;
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

    /* Indique les position */
    private final int HAUT = 0,
                      GAUCHE = 1,
                      BAS = 2,
                      DROITE = 3;

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

    /** Camera principale */
    private Camera cam;

    /** Barre de vie du personnage principal */
    private BarresStats barres;

    /** Indique si le menu doit etre montré ou pas */
    private MenuEnJeu menuEnJeu;

    /** Indique si la map doit etre charger ou pas */
    private boolean charger = false;

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

        Music background = new Music("src/Ressources/Musique/TownTheme.ogg");
        background.loop();

        if (charger) {
            hero.chargement(cam);
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

        // Création du conteneur du jeu
        container = gameContainer;

        // Création de la map
        map = new Map();

        // Création du personnage principal
        hero = new Personnage(map);

        // Création de la camera
        cam = new Camera(hero, container, map);

        // Création du HUD
        barres = new BarresStats();

        menuEnJeu = new MenuEnJeu(container, game);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame game, int delta) throws SlickException {

        hero.actualisation(delta);
        cam.actualisation();

        if (Math.random() < 0.001 && hero.isMoving()) {
            game.enterState(CombatGameState.ID);
            hero.setMoving(false);
        }

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame game, Graphics graphics) throws SlickException {

        gameContainer.setShowFPS(false); // Affichage des fps


        cam.render(graphics);            // Affiche la camera sur la moitier de l'ecran
        map.renderBackground();          // Rendu du background de la carte
        hero.render(graphics);           // Rendu du personnnage
        map.renderForeground();          // Rendu du foreground de la carte
        barres.affichage(graphics);      // Rendu des barres de vie de magie et d'xp
        menuEnJeu.render(graphics);      // Rendu du menu + de l'inventaire


    }

    public void keyReleased(int key, char c) {
        hero.arretPersonnage(key);
    }

    public void keyPressed(int key, char c) {

        switch (key) {
            case Input.KEY_Z: hero.setDirection(HAUT); hero.setMoving(true); break;
            case Input.KEY_Q: hero.setDirection(GAUCHE); hero.setMoving(true); break;
            case Input.KEY_S: hero.setDirection(BAS); hero.setMoving(true); break;
            case Input.KEY_D: hero.setDirection(DROITE); hero.setMoving(true); break;
            case Input.KEY_SPACE: menuEnJeu.setShowInventaire(true); break;
            case Input.KEY_ESCAPE: menuEnJeu.setShowInventaire(false); break;
        }
    }

    public void mouseClicked(int button, int x, int y, int clickCount) {
        // System.out.println("X: " + x + " Y: " + y);
        menuEnJeu.action(x, y, hero, cam);
    }

    public void setCharger(boolean charger) {
        this.charger = charger;
    }


    @Override
    public int getID() {
        return ID;
    }
}
