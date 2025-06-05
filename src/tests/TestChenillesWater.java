package tests;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import field.Direction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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

public class TestChenillesWater {
    @Test
    @Category(GUITest.class)
    public void testChenillesWater() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteTestsChenilles-4x4.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        Move left = new Move(0, data.getRobots()[0], Direction.OUEST);
        Move top = new Move(left.getDateEnd(), data.getRobots()[0], Direction.NORD);

        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        Simulator sim = new Simulator(gui, data);
        sim.addEvents(left);
        sim.addEvents(top);
        top.setSim(sim);

        MoveImpossibleException exception = assertThrows(MoveImpossibleException.class, () -> {
            for (long i = 0; i < top.getDateEnd(); i++) {
                sim.execute();
            }
        });

        assertEquals("Déplacement Impossible sur EAU", exception.getMessage());
    }
}
