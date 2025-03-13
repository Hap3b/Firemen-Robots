package tests;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.awt.Color;

import field.Direction;
import gui.GUISimulator;
import io.DonneesSimulation;
import io.LecteurDonnees;
import simulator.Simulator;
import simulator.Events.Move;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;

public class TestPattesRock {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        Move top1 = new Move(0, data.getRobots()[2], Direction.NORD);
        Move top2 = new Move(1000, data.getRobots()[2], Direction.NORD);
        Move top3 = new Move(2000, data.getRobots()[2], Direction.NORD);
        Move left1 = new Move(5000, data.getRobots()[2], Direction.OUEST);
        Move top4 = new Move(8000, data.getRobots()[2], Direction.NORD);
        Move left2 = new Move(9000, data.getRobots()[2], Direction.OUEST);
        Move left3 = new Move(10000, data.getRobots()[2], Direction.OUEST);
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulator sim = new Simulator(gui, data);
        sim.addEvents(top1);
        sim.addEvents(top2);
        sim.addEvents(top3);
        sim.addEvents(left1);
        sim.addEvents(left2);
        sim.addEvents(top4);
        sim.addEvents(left3);
        left1.setSim(sim);
    }
}
