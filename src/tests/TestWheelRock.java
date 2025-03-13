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

public class TestWheelRock {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        Move left = new Move(0, data.getRobots()[1], Direction.OUEST);
        Move top1 = new Move(375, data.getRobots()[1], Direction.NORD);
        Move top2 = new Move(750, data.getRobots()[1], Direction.NORD);
        Move top3 = new Move(1125, data.getRobots()[1], Direction.NORD);
        Move top4 = new Move(1500, data.getRobots()[1], Direction.NORD);
        Move top5 = new Move(1875, data.getRobots()[1], Direction.NORD);
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulator sim = new Simulator(gui, data);
        sim.addEvents(top1);
        sim.addEvents(top2);
        sim.addEvents(top3);
        sim.addEvents(top4);
        sim.addEvents(top5);
        sim.addEvents(left);
        top1.setSim(sim);
    }
}
