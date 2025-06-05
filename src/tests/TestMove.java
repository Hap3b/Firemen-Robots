package tests;


import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import field.Direction;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import gui.GUISimulator;
import io.DonneesSimulation;
import io.LecteurDonnees;
import simulator.Simulator;
import simulator.Events.Move;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;
import tests.category.GUITest;

public class TestMove {
    @Test
    @Category(GUITest.class)
    public void testMove() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        Move top = new Move(0, data.getRobots()[0], Direction.NORD);

        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        Simulator sim = new Simulator(gui, data);
        sim.addEvents(top);

        top.setSim(sim);
        for (long i = 0; i < top.getDateEnd(); i++) {
            sim.execute();
        }
        assertEquals(data.getRobots()[0].getPosition().getLine(), 2);
        assertEquals(data.getRobots()[0].getPosition().getColumn(), 3);
    }
}
