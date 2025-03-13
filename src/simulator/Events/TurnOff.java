package simulator.Events;

import machines.Robots;
import simulator.Events.Exceptions.TurnOffImpossibleException;

/**
 * Module qui définit Éteindre un incendie.
 * Hérité d'évenement.
 */
public class TurnOff extends Evenement {

    /**
     * Constructeur d'un évenement éteindre
     * @param date Date de début.
     * @param firemen Robot qui éteint.
     */
    public TurnOff(long date, Robots firemen) {
        super(date);
        this.machine = firemen;
        this.dateEnd = date + this.machine.getTimeWater();
    }

    /**
     * Execute l'éteinte d'un incendie
     */
    public void execute() throws TurnOffImpossibleException{
        if (this.isDone()) {
            this.machine.turnOff(this.machine.getQuantityWater());
        }
    }
}
