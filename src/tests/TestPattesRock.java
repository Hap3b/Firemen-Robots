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

public class TestPattesRock {
    @Test
    @Category(GUITest.class)
    public void testPattesRock() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        Robots firemen = data.getRobots()[2];
        Move top1 = new Move(0, firemen, Direction.NORD);
        Move top2 = new Move(top1.getDateEnd(), firemen, Direction.NORD);
        Move top3 = new Move(top2.getDateEnd(), firemen, Direction.NORD);
        Move left1 = new Move(top3.getDateEnd(), firemen, Direction.OUEST);
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulator sim = new Simulator(gui, data);
        firemen.addEvents(top1);
        firemen.addEvents(top2);
        firemen.addEvents(top3);
        firemen.addEvents(left1);
        left1.setSim(sim);

        for (long i = 0; i < left1.getDateEnd(); i++) {
            sim.execute();
        }

        int speed = (int) data.getRobots()[2].getSpeed(null);

        assertEquals(speed, 10);
    }
}
