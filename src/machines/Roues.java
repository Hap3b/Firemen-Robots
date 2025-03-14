package machines;

import field.Case;
import field.Direction;
import field.NatureTerrain;
import gui.GUISimulator;
import gui.ImageElement;
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
    }

    /**
     * Jamais ralentit
     */
    public double getSpeed(NatureTerrain neighbor) {
        return this.speed;
    }

    /**
     * Ne se déplace que sur du terrain libre ou des habitats
     */
    @Override
    public void move(Direction dir, Case neighbor) throws MoveImpossibleException {
        if (neighbor == null) {
            super.move(dir, neighbor);
        }
        else if (neighbor.getBiome() == NatureTerrain.TERRAIN_LIBRE || neighbor.getBiome() == NatureTerrain.HABITAT) {
            super.move(dir, neighbor);
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

    @Override
    public Roues clone() {
        return new Roues(this.position);
    }
}
