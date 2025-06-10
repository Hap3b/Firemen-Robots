package tests;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import field.Case;
import gui.GUISimulator;
import io.DonneesSimulation;
import io.LecteurDonnees;
import machines.Robots;
import simulator.Simulator;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;
import tests.category.GUITest;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestMoveAllTheWay {
    @Test
    @Category(GUITest.class)
    public void testMoveAllTheWay() throws FileNotFoundException, DataFormatException, MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 1920), Math.min(nbCol*size, 1080), Color.BLACK);
        Simulator sim = new Simulator(gui, data);
        Robots firemen = data.getRobots()[1];
        Case dest = new Case(5, 3);
        firemen.moveAllTheWay(dest, 0, sim);
        for (long i = 0; i<1125; i++) {
            sim.execute();
        }
        assertEquals(5, firemen.getPosition().getLine());
        assertEquals(3, firemen.getPosition().getColumn());
    }
}
