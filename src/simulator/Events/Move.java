package simulator.Events;

import machines.Robots;
import simulator.Events.Exceptions.MoveImpossibleException;
import field.Case;
import field.Direction;

/**
 * Module hérité d'évenement.
 * Définit les évenements de déplacement.
 * 1 attribut supplémentaire dir de type Direction/
 * La direction dans laquelle se déplacer.
 */
public class Move extends Evenement {
    private Direction dir;

    /**
     * Constructeur d'un évenement déplacement.
     * @param date Date de début.
     * @param machine Robot qui se déplace.
     * @param dir Direction dans laquelle il se déplace.
     */
    public Move(long date, Robots machine, Direction dir) {
        super(date);
        this.machine = machine;
        this.dir = dir;
        double speed = machine.getSpeed(machine.getPosition().getMap().getNeighbor(machine.getPosition(), dir).getBiome());
        this.dateEnd = (long) ((long) 3.6*machine.getPosition().getMap().getSizeCase()/speed) + date;
    }

    /**
     * Déplace le robot et dessine en fonction
     */
    public void execute() throws MoveImpossibleException{
        if (this.isDone()) {
            sim.drawCase(machine.getPosition());
            if (machine.getPosition().getFire()!=null && machine.getPosition().getFire().getLife()>0) {
                sim.drawFire(machine.getPosition());
            }
            Case neighbor = machine.getPosition().getMap().getNeighbor(machine.getPosition(), this.dir);
            machine.move(this.dir, neighbor);

            machine.draw(sim.getGUI());
        }
    }
}
