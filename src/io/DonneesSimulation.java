package io;
import field.Carte;
import field.Incendie;
import machines.Robots;

/**
 * Module regroupant toutes les données de la simulation
 * 3 attributs map, firemen, et fire
 * Map de type Carte, la carte de la simulation
 * Firemen un tableau des robots de la simulation
 * Fire un tableau des incendies de la simulation
 */
public class DonneesSimulation {
    private Carte map;
    private Robots[] firemen;
    private Incendie[] fire;

    /**
     * Constructeur par défaut où tout est null
     */
    public DonneesSimulation() {
        this.map = null;
        this.firemen = null;
        this.fire = null;
    }

    /**
     * Constructeur des données de la simulation
     * @param map Carte de la simulation
     * @param fireRobots Tableau des Robots de la simulation
     * @param fire Tableau des incendies de la simulation
     */
    public DonneesSimulation(Carte map, Robots[] fireRobots, Incendie[] fire) {
        this.map = map;
        this.firemen = fireRobots;
        this.fire = fire;
    }

    /**
     * 
     * @return Carte de la simulation
     */
    public Carte getMap() {
        return this.map;
    }

    /**
     * 
     * @return Tableau de robots de la simulation
     */
    public Robots[] getRobots() {
        return this.firemen;
    }

    /**
     * 
     * @return Tableau d'incendies de la simulation
     */
    public Incendie[] getFire() {
        return this.fire;
    }

    @Override
    public DonneesSimulation clone() {
        Carte map = this.map.clone();
        Robots[] firemen = this.firemen.clone();
        Incendie[] fire = this.fire.clone();
        return new DonneesSimulation(map, firemen, fire);
    }
}
