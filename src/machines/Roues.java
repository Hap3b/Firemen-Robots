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

public class Roues extends Robots{

    /**
     * Constructeur définissant tous les attributs selon 
     * les caractéristiques d'une chenille en prenant une
     * vitesse personnalisée.
     * @param position Position du robot à roues
     */
    public Roues(Case position) {
        super(position);
        this.speed = 80;
        this.reserve = 5000;
        this.water = 5000;
        this.quantityWater = 100;
        this.timeWater = 5;
        this.timeRefill = 10;
    }

    /**
     * Constructeur définissant tous les attributs selon 
     * les caractéristiques d'un robot à roues en prenant
     * une vitesse personnalisée.
     * @param position Position du robot à roues
     * @param speed Vitesse personnalisée
     */
    public Roues(Case position, int speed) {
        super(position);
        this.reserve = 5000;
        this.water = 5000;
        this.quantityWater = 100;
        this.timeWater = 5;
        this.timeRefill = 10;
        if (speed >= 0) {
            this.speed = speed;
        }
        this.buildGraph();
    }

    public Roues(Roues other) {
        super(other);
    }

    /**
     * Jamais ralentit
     */
    public double getSpeed(NatureTerrain neighbor) {
        return this.speed;
    }

    /**
     * Méthode pour calculer la vitesse
     * @param pos
     * @param dir
     * @return
     * @throws MoveImpossibleException
    */
    public double getSpeed(Case pos, Direction dir) throws MoveImpossibleException {
        Case neighbor = position.getMap().getNeighbor(pos, dir);
        if (neighbor == null || (neighbor.getBiome() != NatureTerrain.TERRAIN_LIBRE && neighbor.getBiome() != NatureTerrain.HABITAT)) {
            throw new MoveImpossibleException("Déplacement impossible");
        }
        return this.getSpeed(neighbor.getBiome());
    }

    /**
     * Ne se déplace que sur du terrain libre ou des habitats
     */
    @Override
    public void move(Direction dir) throws MoveImpossibleException {
        Case neighbor = position.getMap().getNeighbor(position, dir);
        if (neighbor == null) {
            super.move(dir);
        }
        else if (neighbor.getBiome() == NatureTerrain.TERRAIN_LIBRE || neighbor.getBiome() == NatureTerrain.HABITAT) {
            super.move(dir);
        }
        else {
            throw new MoveImpossibleException("Déplacement Impossible sur " + neighbor.getBiome().name());
        }
    }

    public void draw(GUISimulator gui) {
        int lig = this.position.getLine();
        int col = this.position.getColumn();
        int size = (int) this.position.getMap().getSizeCase()/3;
        size = Math.min(size, 110);

        int smallImage = (int) (0.8*size);
        int center = (int) (0.1*size);

        gui.addGraphicalElement(new ImageElement(50+size*col+center, 50+size*lig+center, "images/roue.jpeg", smallImage, smallImage, gui));
    }

     public Case findNearestWaterCase(Case start, Robots robot) {
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
                            long cost = GPS.costPaths(start, neighbor, robot);

                            if (cost < minCost) {
                                minCost = cost;
                                closestWater = neighbor;
                                bestPath = new LinkedList<>(robot.getPath());
                            }
                        }
                    }
                }
            }
        }

        if (bestPath != null) {
            robot.setPath(bestPath);
        }

        return closestWater;
    }

    @Override
    public Roues clone() {
        return new Roues(this);
    }
}
