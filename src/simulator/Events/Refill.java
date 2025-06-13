package simulator.Events;

import machines.Robots;
import simulator.Events.Exceptions.RefillImpossibleException;

/**
 * Module hérité d'évenement.
 * Définit la recharge d'eau.
 */
public class Refill extends Evenement {
    
    /**
     * Constructeur d'un évenement recharge.
     * @param date Date de début.
     * @param machine Robot qui se recharge.
     */
    public Refill(long date, Robots machine) {
        super(date);
        this.machine = machine;
        this.dateEnd = date + (machine.getTimeRefill()*60);
        if (dateEnd>machine.busy) {
            machine.busy = dateEnd;
        }
    }

    /**
     * Recharge
     */
    public void execute() throws RefillImpossibleException{
        if (this.isDone()) {
            this.machine.fill();
        }        
    }
}
