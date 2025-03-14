package machines;

import field.Case;
import field.Direction;
import field.NatureTerrain;
import gui.GUISimulator;
import gui.ImageElement;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;

public class Pattes extends Robots {

    /**
     * Constructeur définissant tous les attributs selon 
     * les caractéristiques d'un robot à pattes
     * @param position Position du robot à pattes
     */
     public Pattes(Case position) {
        super(position);
        this.speed = 30;
        this.reserve = 2147483647; // Max value for an int type
        this.water = 2147483647;
        this.timeWater = 1;
        this.quantityWater = 10;
        this.timeRefill = 0;
    }

    /**
     * Vitesse réduit à 10 km/h sur de la roche
     */
    public double getSpeed(NatureTerrain neighbor) {
        if (neighbor == null) {
            return this.speed;
        }
        if (neighbor == NatureTerrain.ROCHE) {
            if (this.position.getBiome() != NatureTerrain.ROCHE) {
                return 20;   
            }
        }
        if (this.position.getBiome() == NatureTerrain.ROCHE) {
            if (neighbor != NatureTerrain.ROCHE) {
                return 20;   
            }
        }
        return this.speed;
    }

    /**
     * Ne peut se déplacer sur l'eau
     */
    @Override
    public void move(Direction dir, Case neighbor) throws MoveImpossibleException {
        if (neighbor == null) {
            super.move(dir, neighbor);
        }
        else if (neighbor != null && neighbor.getBiome() != NatureTerrain.EAU) {
            super.move(dir, neighbor);
        }
        else {
            throw new MoveImpossibleException("Déplacement sur l'eau impossible");
        }
        if (this.position.getBiome() == NatureTerrain.ROCHE) {
            this.speed = 10;
        }
        else {
            this.speed = 30;
        }
    }

    public void draw(GUISimulator gui) {
        int lig = this.position.getLine();
        int col = this.position.getColumn();
        int size = (int) this.position.getMap().getSizeCase()/3;
        size = Math.min(size, 110);

        int smallImage = (int) (0.8*size);
        int center = (int) (0.1*size);

        gui.addGraphicalElement(new ImageElement(50+size*col+center, 50+size*lig+center, "images/pattes.jpeg", smallImage, smallImage, gui));
    }

    @Override
    public Pattes clone() {
        return new Pattes(this.position);
    }

    /**
     * Un robot à pattes ne se remplit jamais, il a un réservoir infini
     */
    @Override
    public void fill() throws RefillImpossibleException {
        throw new RefillImpossibleException("Recharge Impossible sur un robot à pattes");
    }
}
