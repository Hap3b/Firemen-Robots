package tests;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.awt.Color;

import field.Direction;
import gui.GUISimulator;
import io.DonneesSimulation;
import io.LecteurDonnees;
import machines.Robots;
import simulator.Simulator;
import simulator.Events.Move;
import simulator.Events.Refill;
import simulator.Events.TurnOff;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;

public class TestScenario1 {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 1920), Math.min(nbCol*size, 1080), Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulator sim = new Simulator(gui, data);
        Robots firemen = data.getRobots()[1];
        Move top = new Move(0, firemen, Direction.NORD);
        top.setSim(sim);
        long dateEvt = top.getDateEnd();
        sim.addEvents(top);
        for (int i = 0; i < (int) firemen.getReserve()/firemen.getQuantityWater(); i++) {
            TurnOff intervention = new TurnOff(dateEvt, firemen);
            sim.addEvents(intervention);
            dateEvt = intervention.getDateEnd();
        }
        Move left1 = new Move(dateEvt, firemen, Direction.OUEST);
        sim.addEvents(left1);
        dateEvt = left1.getDateEnd();
        Move left2 = new Move(dateEvt, firemen, Direction.OUEST);
        sim.addEvents(left2);
        dateEvt = left2.getDateEnd();
        Refill fill = new Refill(dateEvt, firemen);
        sim.addEvents(fill);
        dateEvt = fill.getDateEnd();
        Move right1 = new Move(dateEvt, firemen, Direction.EST);
        sim.addEvents(right1);
        dateEvt = right1.getDateEnd();
        Move right2 = new Move(dateEvt, firemen, Direction.EST);
        sim.addEvents(right2);
        dateEvt = right2.getDateEnd();
        for (int i = 0; i < (int) firemen.getReserve()/firemen.getQuantityWater(); i++) {
            TurnOff intervention = new TurnOff(dateEvt, firemen);
            sim.addEvents(intervention);
            dateEvt = intervention.getDateEnd();
        }
        Move bot = new Move(dateEvt, firemen, Direction.SUD);
        sim.addEvents(bot);
    }
}
