package machines;

import field.Case;
import field.Direction;
import field.NatureTerrain;
import gui.GUISimulator;
import gui.ImageElement;
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

    /**
     * Vitesse divisée par deux en forêt
     */
    public double getSpeed(NatureTerrain neighbor) {
        if (neighbor == NatureTerrain.FORET) {
            return 0.75*this.speed;
        }
        return this.speed;
    }

    /**
     * Ne peut se déplacer sur l'eau ou de la roche
     */
    @Override
    public void move(Direction dir, NatureTerrain neighbor) throws MoveImpossibleException {
        if (neighbor != NatureTerrain.EAU && neighbor != NatureTerrain.ROCHE) {
            super.move(dir, neighbor);
            if (neighbor == NatureTerrain.FORET) {
                this.speed /= 2;
            }
            else if (this.position.getBiome() == NatureTerrain.FORET) {
                this.speed *= 2;
            }
        }
        else {
            throw new MoveImpossibleException("Déplacement Impossible sur " + neighbor.name());
        }
    }

    public void draw(GUISimulator gui) {
        int lig = this.position.getLine();
        int col = this.position.getColumn();
        int size = (int) this.position.getMap().getSizeCase()/3;
        size = Math.min(size, 110);

        int smallImage = (int) (0.8*size);
        int center = (int) (0.1*size);

        gui.addGraphicalElement(new ImageElement(50+size*col+center, 50+size*lig+center, "images/chenille.jpeg", smallImage, smallImage, gui));
    }

    @Override
    public Chenilles clone() {
        return new Chenilles(this.position);
    }
}
