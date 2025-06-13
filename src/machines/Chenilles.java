package machines;

import java.util.Deque;
import java.util.LinkedList;

import field.Case;
import field.Direction;
import field.NatureTerrain;
import gui.GUISimulator;
import gui.ImageElement;
import paths.GPS;
import simulator.Events.Exceptions.MoveImpossibleException;

/**
 * Module hérité de Robots
 * Définit le type de robots Chenilles
 */
public class Chenilles extends Robots{

    /**
     * Constructeur définissant tous les attributs selon 
     * les caractéristiques d'une chenille
     * @param position Position de la chenille
     */
    public Chenilles(Case position) {
        super(position);
        this.speed = 60;
        this.reserve = 2000;
        this.water = 2000;
        this.quantityWater = 100;
        this.timeWater = 8;
        this.timeRefill = 5;
        this.buildGraph();
    }

    /**
     * Constructeur définissant tous les attributs selon 
     * les caractéristiques d'une chenille en prenant une
     * vitesse personnalisée.
     * @param position Position de la chenille
     * @param speed Vitesse personalisée
     */
    public Chenilles(Case position, int speed) {
        super(position);
        this.reserve = 2000;
        this.water = 2000;
        this.quantityWater = 100;
        this.timeWater = 8;
        this.timeRefill = 5;
        if (speed >= 0 && speed <= 80) {
            this.speed = speed;
        }
        else {
            this.speed = 60;
        }
    }
    
    public Chenilles(Chenilles chenilles) {
        super(chenilles);
    }
    
    /**
     * Vitesse divisée par deux en forêt
     */
    public double getSpeed(NatureTerrain neighbor) {
        if (neighbor == null) {
            return this.speed;
        }
        if (neighbor == NatureTerrain.FORET) {
            if (this.position.getBiome()!=NatureTerrain.FORET) {
                return 0.75*this.speed;
            }
        }
        else if (this.position.getBiome()==NatureTerrain.FORET) {
            return 1.5*this.speed;
        }
        return this.speed;
    }

    /**
     * Méthode pour calculer la vitesse entre une case et son voisin dans la direction indiquée
     * Utiliser dans la calcul de plus courts chemins
     * @param pos La case depuis laquelle calculer
     * @param dir La direction dans laquelle regarder
     * @return La vitesse du robot pour se déplacer de la case dans la direction
     * @throws MoveImpossibleException Si le déplacement est impossible
     */
    public double getSpeed(Case pos, Direction dir) throws MoveImpossibleException {
        Case neighbor = position.getMap().getNeighbor(pos, dir);
        if (neighbor == null || neighbor.getBiome() == NatureTerrain.EAU || neighbor.getBiome() == NatureTerrain.ROCHE) {
            throw new MoveImpossibleException("Déplacement impossible");
        }
        if (pos.getBiome() != NatureTerrain.FORET) {
            if (neighbor.getBiome()==NatureTerrain.FORET) {
                return 0.75*this.speed;
            }
        }
        else {
            if (neighbor.getBiome() != NatureTerrain.FORET) {
                return 1.5*this.speed;
            }
        }
        return this.speed;
    }
    
    /**
     * Ne peut se déplacer sur l'eau ou de la roche
     */
    @Override
    public void move(Direction dir) throws MoveImpossibleException {
        Case neighbor = position.getMap().getNeighbor(position, dir);
        if (neighbor == null) {
            super.move(dir);
        }
        else if (neighbor.getBiome() != NatureTerrain.EAU && neighbor.getBiome() != NatureTerrain.ROCHE) {
            super.move(dir);
            if (neighbor.getBiome() == NatureTerrain.FORET) {
                this.speed /= 2;
            }
            else if (this.position.getBiome() == NatureTerrain.FORET) {
                this.speed *= 2;
            }
        }
        else {
            throw new MoveImpossibleException("Déplacement Impossible sur " + neighbor.getBiome().name());
        }
    }
    
    /**
     * Dessine une chenille.
     */
    public void draw(GUISimulator gui) {
        int lig = this.position.getLine();
        int col = this.position.getColumn();
        int size = (int) this.position.getMap().getSizeCase()/3;
        size = Math.min(size, 110);
    
        int smallImage = (int) (0.8*size);
        int center = (int) (0.1*size);
    
        gui.addGraphicalElement(new ImageElement(50+size*col+center, 50+size*lig+center, "images/chenille.jpeg", smallImage, smallImage, gui));
    }

    public Case findNearestWaterCase(Case start) {
        int nbLines = start.getMap().getNbLine();
        int nbCols = start.getMap().getNbCol();

        Case closestWater = null;
        long minCost = Long.MAX_VALUE;
        Deque<Direction> bestPath = null;

        for (int i = 0; i < nbLines; i++) {
            for (int j = 0; j < nbCols; j++) {
                Case current = start.getMap().getCase(i, j);
                if (current.getBiome() == NatureTerrain.EAU) {
                    for (Direction dir : Direction.values()) {
                        Case neighbor = current.getMap().getNeighbor(current, dir);
                        if (neighbor != null) {
                            long cost = GPS.costPaths(start, neighbor, this);

                            if (cost < minCost) {
                                minCost = cost;
                                closestWater = neighbor;
                                bestPath = new LinkedList<>(this.getPath());
                            }
                        }
                    }
                }
            }
        }

        if (bestPath != null) {
            this.setPath(bestPath);
        }

        return closestWater;
    }
    
    @Override
    public Chenilles clone() {
        return new Chenilles(this);
    }
}
