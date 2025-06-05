package tests;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.awt.Color;

import gui.GUISimulator;
import io.DonneesSimulation;
import io.LecteurDonnees;
import simulator.Simulator;
import simulator.Events.Refill;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;
import tests.category.GUITest;

public class TestFillPattes {
    @Test
    @Category(GUITest.class)
    public void testFillPattes() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {

        RefillImpossibleException exception = assertThrows(RefillImpossibleException.class, () -> {
            DonneesSimulation data = new DonneesSimulation();
            data = LecteurDonnees.lire("cartes/carteSujet.map");
            int nbLine = data.getMap().getNbLine();
            int nbCol = data.getMap().getNbCol();
            int size = data.getMap().getSizeCase();
            Refill fill = new Refill(0, data.getRobots()[2]);
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 1920), Math.min(nbCol*size, 1080), Color.BLACK);
            Simulator sim = new Simulator(gui, data);
            sim.addEvents(fill);
            fill.setSim(sim);
            sim.execute();

        });

        assertEquals("Recharge Impossible sur un robot à pattes", exception.getMessage());
        
    }
}
