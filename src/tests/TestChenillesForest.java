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
        Move right1 = new Move(0, data.getRobots()[0], Direction.EST);
        Move top = new Move(right1.getDateEnd(), data.getRobots()[0], Direction.NORD);
        Move right2 = new Move(top.getDateEnd(), data.getRobots()[0], Direction.EST);
        Move bot = new Move(right2.getDateEnd(), data.getRobots()[0], Direction.SUD);

        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        Simulator sim = new Simulator(gui, data);
        sim.addEvents(right1);
        sim.addEvents(top);
        sim.addEvents(right2);
        sim.addEvents(bot);
        top.setSim(sim);

        for (long i = 0; i < bot.getDateEnd(); i++) {
            sim.execute();
        }

        int speed = (int) data.getRobots()[0].getSpeed(null);

        assertEquals(speed, 15);
    }
}

