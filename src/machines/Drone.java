package machines;

import field.NatureTerrain;
import field.Case;
import gui.GUISimulator;
import gui.ImageElement;
import simulator.Events.Exceptions.RefillImpossibleException;

public class Drone extends Robots{

    /**
     * Constructeur définissant tous les attributs selon 
     * les caractéristiques d'un drone
     * @param position Position du drone
     */
    public Drone(Case position) {
        super(position);
        this.speed = 100;
        this.reserve = 10000;
        this.water = 10000;
        this.quantityWater = 10000;
        this.timeWater = 30;
        this.timeRefill = 30;
    }

    /**
     * Constructeur définissant tous les attributs selon 
     * les caractéristiques d'un drone en prenant une
     * vitesse personnalisée.
     * @param position Position du drone
     * @param speed Vitesse personnalisée
     */
    public Drone(Case position, int speed) {
        super(position);
        this.reserve = 10000;
        this.water = 10000;
        this.quantityWater = 10000;
        this.timeWater = 30;
        this.timeRefill = 30;
        if (speed <= 150 && speed >= 0) {
            this.speed = speed;
        }
        else {
            this.speed = 100;
        }
    }

    /**
     * Jamais ralentit 
     * Accès à toutes les cases
     */
    public double getSpeed(NatureTerrain neighbor) {
        return this.speed;
    }

    /**
     * Refill sur une case eau, pas adjacente
     */
    public void fill() throws RefillImpossibleException {
        if (this.position.getBiome() == NatureTerrain.EAU) {
            this.water = this.reserve-this.water;
        }
        else {
            throw new RefillImpossibleException("Recharge possible que sur une case EAU");
        }
    }

    public void draw(GUISimulator gui) {
        int lig = this.position.getLine();
        int col = this.position.getColumn();
        int size = (int) this.position.getMap().getSizeCase()/3;
        size = Math.min(size, 110);

        int smallImage = (int) (0.8*size);
        int center = (int) (0.1*size);

        gui.addGraphicalElement(new ImageElement(50+size*col+center, 50+size*lig+center, "images/drone.png", smallImage, smallImage, gui));
    }

    @Override
    public Drone clone() {
        return new Drone(this.position);
    }
}
