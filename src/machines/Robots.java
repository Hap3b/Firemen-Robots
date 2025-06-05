package machines;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import field.Case;
import field.Direction;
import field.NatureTerrain;
import gui.GUISimulator;
import paths.Node;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;;

/**
 * Module qui définit une classe abstraite de robots
 * 7 attributs
 * position de type Case: la position du robot
 * reserve de type int: la capacité maximale d'eau du robot
 * water de type int: la quantité d'eau dans le robot
 * speed de type double: la vitesse du robot
 * timeWater de type int: secondes pour lacher quantityWater
 * quantityWater de type int: volume d'eau laché au bout de timeWater
 * timeRefill de type int: minutes nécessaire pour remplir le réservoir à fond
 */
public abstract class Robots {
    protected Case position;
    protected int reserve;
    protected int water;
    protected double speed;
    protected int timeWater;
    protected int quantityWater;
    protected int timeRefill;
    protected Map<Direction, Double>[] graph;

    /**
     * Constructeur protégé car classe abstraite
     * Eau du robot nulle par défaut
     * @param position Case où se trouve le robot
     */
    protected Robots(Case position) {
        this.position = position;
        this.water = 0;
    }

    /**
     * Copie profonde d'un robot
     * @param other Copie
     */
    public Robots(Robots other) {
        this.position = new Case(other.position); // Copie de la position
        this.reserve = other.reserve;
        this.water = other.water;
        this.speed = other.speed;
        this.timeWater = other.timeWater;
        this.quantityWater = other.quantityWater;
        this.timeRefill = other.timeRefill;
    }
    

    /**
     * Méthode abstraite qui renvoie la vitesse du robot en fonction
     * du voisin
     * @param neighbor
     * @return Vitesse du robot
     */
    public abstract double getSpeed(NatureTerrain neighbor);

    /**
     * Méthode abstraite pour renvoyer la vitesse du robot pour se rendre
     * dans une direction à partir d'une Case
     * @param pos Case départ
     * @param dir Direction dans laquelle regarder
     * @return Vitesse pour se déplacer dans un direction depuis la case
     * @throws MoveImpossibleException Si le déplacement est impossible
     */
    public abstract double getSpeed(Case pos, Direction dir) throws MoveImpossibleException;

    /**
     * Methode pour construire le graphe propre à chaque Robot
     */
    protected void buildGraph() {
        int nbLine = position.getMap().getNbLine();
        int nbCol = position.getMap().getNbCol();
        this.graph = new HashMap[nbLine*nbCol];

        for (int i = 0; i < graph.length; i++) {
            graph[i] = new HashMap<>();
            int line = i / nbCol;
            int col = i % nbLine;
            Case pos = new Case(line, col);
            for (Direction dir : Direction.values()) {
                try {
                    graph[i].put(dir, this.getSpeed(pos, dir));
                } catch (MoveImpossibleException e) {
                    graph[i].put(dir, Double.MAX_VALUE);
                }
            }
        }
    }

    public Map<Direction, Double>[] getGraph() {
        return this.graph;
    }

    /**
     * Méthode abstraite permettant de dessiner un robot
     * @param gui Interface graphique sur laquelle dessiner
     */
    public abstract void draw(GUISimulator gui);

    /**
     * 
     * @return Volume d'eau laché au bout de timeWater
     */
    public int getQuantityWater() {
        return this.quantityWater;
    }

    /**
     * 
     * @return Secondes pour lacher quantityWater
     */
    public int getTimeWater() {
        return this.timeWater;
    }

    /**
     * 
     * @return Minutes pour se recharger en eau complètement
     */
    public int getTimeRefill() {
        return this.timeRefill;
    }

    /**
     * 
     * @return Position du robot
     */
    public Case getPosition() {
        return this.position;
    }

    /**
     * 
     * @return Capacité maximale d'eau du robot
     */
    public int getReserve() {
        return this.reserve;
    }

    /**
     * Méthode qui bouge un robot dans la direction
     * Méthode à override selon les caractéristiques des robots
     * @param dir Direction dans laquelle bouger
     * @param biome Paramètre non nécessaire ici mais necessaire pour l'override
     * @throws MoveImpossibleException Si déplacement hors des limites
     */
    public void move(Direction dir) throws MoveImpossibleException{
        if (this.position.getMap().getNeighbor(this.position, dir)!=null) {
            this.position = this.position.getMap().getNeighbor(position, dir);
        }
        else {
            throw new MoveImpossibleException("Déplacements Hors Limites");
        }
    }

    /**
     * Méthode qui éteint un incendie
     * Méthode à override selon les caractéristiques des robots
     * @param vol Volume d'eau à verser sur le feu
     * @throws TurnOffImpossibleException Si il n'y a pas de feu sur la case
     */
    public void turnOff(int vol) throws TurnOffImpossibleException{
        if (this.position.getFire() != null) {
            int maxVol = vol;
            if (vol>this.water) {
                maxVol = this.quantityWater;
            }
            else if (this.position.getFire().getLife()< vol) {
                maxVol = this.position.getFire().getLife();
            }
            this.position.getFire().setLife(maxVol);
            this.water -=  maxVol;
        }
        else {
            throw new TurnOffImpossibleException("Pas d'incendie en (" + this.position.getColumn() + ", " + this.position.getLine() + ")");
        }
    }

    /**
     * Méthode qui recharge en eau un robot
     * Méthode à override selon les caractéristiques des robots
     * @throws RefillImpossibleException Si il n'y a pas d'eau disponible
     */
    public void fill() throws RefillImpossibleException {
        boolean waterAvailable = false;

        for (Direction dir : Direction.values()) {
            if (this.position.getMap().getNeighbor(this.position, dir).getBiome() == NatureTerrain.EAU) {
                waterAvailable = true;
            }
        }

        if (waterAvailable) {
            this.water = this.reserve-this.water;
        }
        else {
            throw new RefillImpossibleException("Recharge Impossible en (" + this.position.getColumn() + ", " + this.position.getLine() + ")");
        }
    }

    @Override
    public abstract Robots clone();

}
