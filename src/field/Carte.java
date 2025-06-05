package field;
/**
 * Module qui définit la carte.
 * Il y a deux attributs: map et sizeCase.
 * Map est un double tableaux de Case représentant la carte,
 * tandis que sizeCase représente la longueur d'une case
 */
public class Carte {

    private Case[][] map;
    private int sizeCase;

    /**
     * Constructeur avec une taille de case par défaut de 100
     * @param nbLine Nombre de lignes dans le tableau
     * @param nbColumns Nombre de colonnes dans le tableau
     */
    public Carte(int nbLine, int nbColumns) {
        this.map = new Case[nbLine][nbColumns];
        this.sizeCase = 100;
    }

    /**
     * Constructeur en spécifaint la taille de la case
     * @param nbLine Nombre de lignes dans le tableau
     * @param nbColumns Nombre de colonnes dans le tableau
     * @param size Taille d'une case 
     */
    public Carte(int nbLine, int nbColumns, int  size) {
        this.map = new Case[nbLine][nbColumns];
        this.sizeCase = size;
    }

    /**
     * Définit une case à la position (i, j) du double tableau
     * @param pos La case de la position
     * @param i Ligne de la carte
     * @param j Colonne de la carte
     */
    public void setCase(Case pos, int i, int j) {
        this.map[i][j] = pos;
    }

    /**
     * 
     * @return Le nombre de lignes du tableau
    */
    public int getNbLine() {
        return this.map.length;
    }

    /**
     * 
     * @return Le nombre de colonnes du tableau
     */
    public int getNbCol() {
        return this.map[0].length;
    }

    /**
     * 
     * @return La taille d'une case
     */
    public int getSizeCase() {
        return this.sizeCase;
    }

    /**
     * Renvoie une ArrayIndexOutOfBoundsException si i ou j
     * n'est pas dans le tableau
     * 
     * @param i Index ligne
     * @param j Index colonnes
     * @return La case du tableau carte[i][j]
     */
    public Case getCase(int i, int j) {
        try {
            return this.map[i][j];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
            return null;
        }
        
    }

    /**
     * Vérifie qu'un voisin existe dans la direction indiquée
     * @param position Position depuis laquelle on vérifie si il y a un voisin
     * @param dir Direction dans laquelle regarder
     * @return Vrai ou faux selon si le voisin existe ou non
     */
    private boolean neighborExists(Case position, Direction dir) {
        int i = position.getLine();
        int j = position.getColumn();
        int height = this.getNbLine();
        int len = this.getNbCol();
        switch (dir) {
            case NORD:
                return i-1>=0;
            case SUD:
                return i+1<height;
            case EST:
                return j+1<len;
            case OUEST:
                return j-1>=0;
            default:
                return false;
        }
    }

    /**
     * Renvoie le voisin de la position dans la direction indiquée
     * Si il n'y a pas de voisin renvoie null
     * 
     * @param position Position depuis laquelle prendre le voisin
     * @param dir Direction dans laquelle regarder pour le voisin
     * @return Voisin dans la direction indiquée
     */
    public Case getNeighbor(Case position, Direction dir) {
        if (neighborExists(position, dir)) {
            int i = position.getLine();
            int j = position.getColumn();
            switch (dir) {
                case NORD:
                    return this.map[i-1][j];
                case SUD:
                    return this.map[i+1][j];
                case EST:
                    return this.map[i][j+1];
                case OUEST:
                    return this.map[i][j-1];
                default:
                    return null;
                }
        }
        return null;
    }

    @Override
    public Carte clone() {
        int line = this.getNbLine();
        int col = this.getNbCol();
        int size = this.sizeCase;
        return new Carte(line, col, size);
    }
}
