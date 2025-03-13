package tests;


import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import field.Direction;

import java.awt.Color;

import gui.GUISimulator;
import io.DonneesSimulation;
import io.LecteurDonnees;
import simulator.Simulator;
import simulator.Events.Move;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;

public class TestMove {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        Move top = new Move(0, data.getRobots()[0], Direction.NORD);
        Move right = new Move(200, data.getRobots()[0], Direction.EST);
        Move bot = new Move(400, data.getRobots()[0], Direction.SUD);
        Move left = new Move(600, data.getRobots()[0], Direction.OUEST);
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulator sim = new Simulator(gui, data);
        sim.addEvents(top);
        sim.addEvents(right);
        sim.addEvents(bot);
        sim.addEvents(left);
        top.setSim(sim);
    }
}
