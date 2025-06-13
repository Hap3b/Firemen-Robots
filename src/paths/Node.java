package paths;

/**
 * Classe définissant les noeuds sur un graphe.
 * 2 Attributs
 * vertex de type int: Identifiant du sommet.
 * cost de type long: Nombre de secondes pour aller à ce sommet.
 */
public class Node implements Comparable<Node>{

    private int vertex;
    private long cost;

    /**
     * Constructeur du noeud.
     * @param v Identifiant du sommet.
     * @param cost Cout pour aller à ce sommet.
     */
    public Node(int v, long cost) {
        this.vertex = v;
        this.cost = cost;
    }

    /**
     * 
     * @return L'identifiant du sommet.
     */
    public int getVertex() {
        return this.vertex;
    }

    /**
     * 
     * @return Le cout pour aller à ce sommet.
     */
    public long getCost() {
        return this.cost;
    }

    @Override
    public int compareTo(Node u) {
        return Long.compare(cost, u.getCost());
    }

}
