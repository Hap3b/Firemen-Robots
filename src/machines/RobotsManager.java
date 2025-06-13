package machines;

import field.Case;
import field.Incendie;
import simulator.Simulator;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;

/**
 * Module qui gère les actions des différents robots
 * 6 attributs
 * fires de type Incendie[]: tableaux de feux à éteindre
 * team de type Robots[]: tableaux des robots pompiers
 * sim de type Simulator: attribut statique qui gère la simulation
 * damage[] de type long[]: tableaux des dégats infligés aux incendies
 * dateRobots[] de type long[]: tableaux des dates à partir desquelles les robots sont libres
 * posRobots[] de type Case[]: tableaux recensant les dernières positions des robots
 */
public class RobotsManager {

    protected Incendie[] fires;
    protected Robots[] team;
    private static Simulator sim;
    private long[] damage;
    private long[] dateRobots;
    private Case[] posRobots;

    /**
     * Constructeur du manager, et initialisation des tableaux.
     * @param fires Feu à eteindre.
     * @param firemen Pompiers de l'équipe.
     * @param sim Simulateur.
     */
    public RobotsManager(Incendie[] fires, Robots[] firemen, Simulator sim) {
        this.fires = fires;
        this.team = firemen;
        RobotsManager.sim = sim;
        this.damage = new long[fires.length];
        this.dateRobots = new long[team.length];
        this.posRobots = new Case[team.length];
        for (int i = 0; i < firemen.length; i++) {
            posRobots[i]=firemen[i].getPosition();
        }
    }

    /**
     * Stratégie élémentaire.
     * @throws MoveImpossibleException
     * @throws TurnOffImpossibleException
     */
    public void strategyElem() throws MoveImpossibleException, TurnOffImpossibleException {
        long dateSim = 0;
        while (!isOff()) {
            int idxFire = 0;
            for (Incendie fire : fires) {
                int idxRobot = 0;
                if (fire.getLife()-damage[idxFire]>0) {
                    for (Robots firemen : team) {
                        if (firemen.busy<=dateSim) {
                            dateRobots[idxRobot] = firemen.moveAllTheWay(posRobots[idxRobot], fire.getCase(), dateRobots[idxRobot], sim);
                            posRobots[idxRobot] = fire.getCase();
                            if (!firemen.getPath().isEmpty()) {
                                dateRobots[idxRobot] = firemen.emptyReserve(fire.getCase(), dateRobots[idxRobot], sim);
                                damage[idxFire] += firemen.water;
                                if (firemen.getWater()<=fire.getLife()-damage[idxFire]) {
                                    Case water = firemen.findNearestWaterCase(fire.getCase());
                                    dateRobots[idxRobot] = firemen.moveAllTheWay(posRobots[idxRobot], water, dateRobots[idxRobot], sim);
                                    posRobots[idxRobot] = water;
                                    dateRobots[idxRobot] = firemen.completeFill(dateRobots[idxRobot], sim);
                                }
                                break;
                            }
                        }
                        idxRobot++;
                    }
                }
                idxFire++;
            }
            dateSim++;
        }
    }

    /**
     * 
     * @return true si tous les incendies sont éteints
     */
    public boolean isOff() {
        for (int i=0; i<fires.length; i++) {
            if (fires[i].getLife()-damage[i]>0) {
                return false;
            }
        }
        return true;
    }
}
