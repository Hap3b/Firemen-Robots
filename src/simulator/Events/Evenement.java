package simulator.Events;

import machines.Robots;
import simulator.Simulator;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;

/**
 * Module qui définit une classe abstraite d'évenement.
 * Elle implémente l'interface Comparable pour la gestion d'évènements.
 * Un évènement est inférieur si sa date est inférieure à un autre.
 * 4 attributs
 * date de type long: date de début
 * dateEnd de type long: date de fin
 * sim de type Simulator: Simulateur statique associé aux évenments
 * machine de type Robots: Robot qui réalise l'évenement
 */
public abstract class Evenement implements Comparable<Evenement>{

    protected long date;
    protected long dateEnd;
    protected static Simulator sim;
    protected Robots machine;

    /**
     * Méthode abstraite qui réalise l'action de l'évenement
     * @throws RefillImpossibleException
     * @throws MoveImpossibleException
     * @throws TurnOffImpossibleException
     */
    public abstract void execute() throws RefillImpossibleException, MoveImpossibleException, TurnOffImpossibleException;

    /**
     * 
     * @return Date de début.
     */
    private long getDate() {
        return this.date;
    }

    /**
     * 
     * @return Date de fin.
     */
    public long getDateEnd() {
        return this.dateEnd;
    }

    /**
     * Constructeur protégé d'un évenement car c'est une classe abstraite.
     * @param date Date de début.
    */
    protected Evenement(long date) {
        this.date = date;
    }

    /**
     * Définit le simulateur statique aux évenements
     * @param sim
     */
    public void setSim(Simulator sim) {
        Evenement.sim = sim;
    }

    /**
     * 
     * @return Booléen si le temps passé est suffisant pour réaliser l'action
     */
    protected boolean isDone() {
        return Evenement.sim.getDateSim()>=dateEnd;
    }

    @Override
    public int compareTo(Evenement other) {
        if (this.date > other.getDate()) {
            return 1;
        }
        else if (this.date < other.getDate()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
