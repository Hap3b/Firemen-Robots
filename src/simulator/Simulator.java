package simulator;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Simulable;
import field.Carte;
import field.Case;
import field.Incendie;
import field.NatureTerrain;
import io.DonneesSimulation;
import machines.Robots;
import simulator.Events.Evenement;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;

/**
 * Module qui réalise l'interface simulable
 * Gère la gestion de la simulation
 * 5 attributs gui, data, initialization, dateSimulatio, eventsPriorityQueue
 * gui de type GUISimulator: interface graphique de la simulation
 * data de type DonneesSimulation: données de la simulation
 * initialData de type DonneesSimulation: attribut servant à la réinitialisation
 * eventsPriorityQueue une file de priorité d'Evenement
 * dateSimulation de type long: nombre de secondes depuis le début de la simulation
 */
public class Simulator implements Simulable {

    private GUISimulator gui;
    private DonneesSimulation data;
    /* private DonneesSimulation initialData; */

    protected long dateSimulation;

    /**
     * 
     * @return Inteface graphique de la simulation
     */
    public GUISimulator getGUI() {
        return this.gui;
    }

    /**
     * 
     * @return Nombre de secondes depuis le début de la simulation
     */
    public long getDateSim() {
        return this.dateSimulation;
    }

    public DonneesSimulation getData() {
        return this.data;
    }

    /**
     * Augmente la date de la simulation de 1 seconde
     */
    private void dateIncr() {
        this.dateSimulation += 1;
    }

    /**
     * Constructeur d'un simulateur
     * @param gui Interface graphique du simulateur
     * @param data Données de la simulation
     */
    public Simulator(GUISimulator gui, DonneesSimulation data) {
        this.gui = gui;
        this.data = data;
        Robots[] initialRobots = new Robots[data.getRobots().length];
        for (int i = 0; i < initialRobots.length; i++) {
            initialRobots[i] = (data.getRobots()[i] != null) ? data.getRobots()[i].clone() : null;
        }
        Incendie[] initialFire = new Incendie[data.getFire().length];
        for (int i = 0; i < initialFire.length; i++) {
            initialFire[i] = (data.getFire()[i] != null) ? data.getFire()[i].clone() : null;
        }
        // this.initialData = new DonneesSimulation(data.getMap(), initialRobots, initialFire);
        gui.setSimulable(this);
        this.dateSimulation = 0;

        drawMap();
    }

    @Override
    public void next() {
        try {
            this.execute();
        } catch (MoveImpossibleException | RefillImpossibleException | TurnOffImpossibleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restart() {
        /* Robots[] initialRobots = new Robots[initialData.getRobots().length];
        for (int i = 0; i < initialRobots.length; i++) {
            initialRobots[i] = (initialData.getRobots()[i] != null) ? initialData.getRobots()[i].clone() : null;
        }
        Incendie[] initialFire = new Incendie[initialData.getFire().length];
        for (int i = 0; i < initialFire.length; i++) {
            initialFire[i] = (initialData.getFire()[i] != null) ? initialData.getFire()[i].clone() : null;
        }
        this.data = new DonneesSimulation(initialData.getMap(), initialRobots, initialFire);
        this.dateSimulation = 0;
        this.eventsPriorityQueue.clear();
        drawMap(); */
        
    }

    /**
     * Méthode qui dessine la carte
     */
    private void drawMap() {
        Carte map = data.getMap();

        int nbLine = map.getNbLine();
        int nbCol = map.getNbCol();

        for (int i = 0; i < nbLine; i++) {
            for (int j = 0; j < nbCol; j++) {
                drawCase(map.getCase(i, j));
            }
        }

        for (Incendie fire : data.getFire()) {
            drawFire(fire.getCase());
        }

        for (Robots machines : data.getRobots()) {
            machines.draw(gui);
        }
    }

    /**
     * Méthode qui dessine la case
     * @param pos Position de la case
     */
    public void drawCase(Case pos) {
        int lig = pos.getLine();
        int col = pos.getColumn();

        int size = (int) this.data.getMap().getSizeCase()/3;
        size = Math.min(size, 110);

        NatureTerrain biome = pos.getBiome();

        switch (biome) {
            case EAU:
                gui.addGraphicalElement(new ImageElement(50+size*col, 50+size*lig, "images/water.png", size, size, gui));
                break;
            case FORET:
                gui.addGraphicalElement(new ImageElement(50+size*col, 50+size*lig, "images/forest.jpeg", size, size, gui));
                break;
            case ROCHE:
                gui.addGraphicalElement(new ImageElement(50+size*col, 50+size*lig, "images/rock.jpeg", size, size, gui));
                break;
            case TERRAIN_LIBRE:
                gui.addGraphicalElement(new ImageElement(50+size*col, 50+size*lig, "images/road.jpeg", size, size, gui));
                break;
            case HABITAT:
                gui.addGraphicalElement(new ImageElement(50+size*col, 50+size*lig, "images/building.png", size, size, gui));
                break;
            default:
                break;
        }
    }

    /**
     * Méthode qui dessine un incendie
     * @param pos Position de l'incendie
     */
    public void drawFire(Case pos) {
        int lig = pos.getLine();
        int col = pos.getColumn();
        int size = (int) this.data.getMap().getSizeCase()/3;
        size = Math.min(size, 110);

        int smallImage = (int) (0.8*size);
        int center = (int) (0.1*size);

        gui.addGraphicalElement(new ImageElement(50+size*col+center, 50+size*lig+center, "images/incendie.png", smallImage, smallImage, gui));
    }

    /**
     * Méthode qui execute les evenements dans l'ordre de passage
     * @throws MoveImpossibleException
     * @throws RefillImpossibleException
     * @throws TurnOffImpossibleException
     */
    public void execute() 
    throws MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException
    {
        dateIncr();
        for (Robots machine : data.getRobots()) {
            if (!machine.endedSim()) {
                Evenement e = machine.eventsPriorityQueue.poll();
                e.execute();
                if (e.getDateEnd()>this.dateSimulation) {
                    machine.addEvents(e);
                }
            }
        }

    }
}
