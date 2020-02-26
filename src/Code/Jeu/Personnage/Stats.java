package Code.Jeu.Personnage;

/**
 * Encapsule les stats d'un personnage ou d'un ennemi
 */
public class Stats {

    private final int NIVEAU_DEFAUT = 1;

    private final int PV_DEFAUT = 25;
    private final int PV_PNJ_DEFAUT = 10;

    private final int MANA_DEFAUT = 75;
    private final int XP_TOTAL_DEFAUT = 100;

    private final int DEGA_ATTAQUE_JOUEUR_DEFAUT = 5;
    private final int DEGA_ATTAQUE_PNJ_DEFAUT = 1;

    private final int DEGA_DEFENSE_JOUEUR_DEFAUT = 7;
    private final int DEGA_DEFENSE_PNJ_DEFAUT = 2;

    private final float BONUS_NIVEAU = 1.25f;
    private final float BONUS_DEFENSE = 1.33f;

    private int niveau; /** Niveau de l'entité */

    private int pv,   /** Pv actuelle du joueur */
                mana, /** Mana actuelle du joueur */
                xp;   /** Xp actuelle du joueur */

    private int totalPv,   /** Total des PV */
                totalMana, /** Total de la mana */
                totalXp;   /** Total de l'XP */

    private int degaAttaque, /** Dega infliger lors d'une attaque */
                degaDefense; /** Dega infliger lors d'une défense */

    private int bouclier; /** Déga absorber lors d'une defense */

    /**
     * Stats d'une entités
     * @param estJoueur : indique si l'entité est un joueur ou pas
     */
    public Stats(boolean estJoueur) {
        niveau = NIVEAU_DEFAUT;

        if (estJoueur) {
            pv = totalPv = PV_DEFAUT;
            mana = totalMana = MANA_DEFAUT;
            xp = 0;
            totalXp = XP_TOTAL_DEFAUT;

            degaAttaque = DEGA_ATTAQUE_JOUEUR_DEFAUT;
            degaDefense = DEGA_DEFENSE_JOUEUR_DEFAUT;

        } else {

            pv = totalPv = (int) (PV_DEFAUT / BONUS_NIVEAU);
            mana = totalMana = (int) ( MANA_DEFAUT / BONUS_NIVEAU);
            xp = 0;
            totalXp = XP_TOTAL_DEFAUT;

            degaAttaque = DEGA_ATTAQUE_PNJ_DEFAUT;
            degaDefense = DEGA_DEFENSE_PNJ_DEFAUT;
        }

        bouclier = (int) (degaAttaque / BONUS_DEFENSE);
    }

    public Stats(int niveau) {

        this.niveau = niveau;


            pv = totalPv = (int) (PV_PNJ_DEFAUT * (niveau * BONUS_NIVEAU) / BONUS_NIVEAU);
            mana = totalMana = (int) ( MANA_DEFAUT * (niveau * BONUS_NIVEAU) / BONUS_NIVEAU);
            xp = 0;
            totalXp = XP_TOTAL_DEFAUT;

            degaAttaque = (int) (DEGA_ATTAQUE_PNJ_DEFAUT * (niveau * BONUS_NIVEAU) / BONUS_NIVEAU);
            degaDefense = (int) (DEGA_DEFENSE_PNJ_DEFAUT * (niveau * BONUS_NIVEAU) / BONUS_NIVEAU);

        bouclier = (int) (degaAttaque / BONUS_DEFENSE);
    }

    public void updateNiveau() {

        if (xp >= totalXp) {

            niveau++;

            totalPv *= BONUS_NIVEAU;
            pv = totalPv;

            totalMana *= BONUS_NIVEAU;
            mana = totalMana;
            xp = 0;

            totalXp *= BONUS_NIVEAU;

            degaAttaque *= BONUS_NIVEAU;
            degaDefense *= BONUS_NIVEAU;

            bouclier = (int) (degaAttaque / BONUS_DEFENSE);
        }
    }

    public int getNiveau() {
        return niveau;
    }

    public int getPv() {
        return pv;
    }

    public int getMana() {
        return mana;
    }

    public int getXp() {
        return xp;
    }

    public int getTotalPv() {
        return totalPv;
    }

    public int getTotalMana() {
        return totalMana;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public int getDegaAttaque() {
        return degaAttaque;
    }

    public int getDegaDefense() {
        return degaDefense;
    }

    public int getBouclier() {
        return bouclier;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "niveau=" + niveau +
                ", pv=" + pv +
                ", mana=" + mana +
                ", xp=" + xp +
                ", totalPv=" + totalPv +
                ", totalMana=" + totalMana +
                ", totalXp=" + totalXp +
                ", degaAttaque=" + degaAttaque +
                ", degaDefense=" + degaDefense +
                ", bouclier=" + bouclier +
                '}';
    }
}
