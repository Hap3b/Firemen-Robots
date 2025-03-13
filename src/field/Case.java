package field;

/**
 * Module qui définit une case
 * 5 attributs: lignes, colonnes, biome
 * qui renvoie la nature du terrain
 * map est un attribut static définissant la carte 
 * commune à toutes les cases.
 * fire est l'incendie si il est présent sur la case
 */
public class Case {
    private int line;
    private int column;
    private NatureTerrain biome;
    private static Carte map;
    private Incendie fire;

    /**
     * Constructeur d'une case
     * @param line Ligne de la case
     * @param col Colonne de la case
     */
    public Case(int line, int col) {
        this.line = line;
        this.column = col;
    }

    /**
     * Constructeur en prenant en compte le biome
     * et qui définit l'attribut static
     * @param line Ligne de la case
     * @param col Colonne de la case
     * @param biome Nature du terrain à définir
     * @param map Carte statique
     */
    public Case(int line, int col, NatureTerrain biome, Carte map) {
        this.line = line;
        this.column = col;
        this.biome = biome;
        Case.map = map;
    }

    /**
     * 
     * @return La ligne de la case
     */
    public int getLine() {
        return this.line;
    }

    /**
     * 
     * @return La colonne de la case
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * 
     * @return La nature du terrain
     */
    public NatureTerrain getBiome() {
        return this.biome;
    }

    /**
     * 
     * @return La carte statique
     */
    public Carte getMap() {
        return Case.map;
    }

    /**
     * 
     * @return L'incendie de la case
     */
    public Incendie getFire() {
        return this.fire;
    }

    /**
     * Définit un incendie sur la case
     * @param fire Incendie à définir
     */
    public void setFire(Incendie fire) {
        this.fire = fire;
    }

    @Override
    public Case clone() {
        return new Case(this.line, this.column);
    }
}
