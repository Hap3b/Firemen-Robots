package tests;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import field.Direction;
import gui.GUISimulator;
import io.DonneesSimulation;
import io.LecteurDonnees;
import machines.Robots;
import simulator.Simulator;
import simulator.Events.Move;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;
import tests.category.GUITest;

public class TestChenillesForest {
    @Test
    @Category(GUITest.class)
    public void testChenillesForest() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteTestsChenilles-4x4.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        Robots firemen = data.getRobots()[0];
        
        Move right1 = new Move(0, firemen, Direction.EST);
        Move top = new Move(right1.getDateEnd(), firemen, Direction.NORD);

        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        Simulator sim = new Simulator(gui, data);
        firemen.addEvents(right1);
        firemen.addEvents(top);
        top.setSim(sim);

        for (long i = 0; i < top.getDateEnd(); i++) {
            sim.execute();
        }

        int speed = (int) data.getRobots()[0].getSpeed(null);

        assertEquals(30, speed);
    }
}

