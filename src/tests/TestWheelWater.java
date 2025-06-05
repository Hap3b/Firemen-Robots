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

public class TestWheelWater {
    @Test
    @Category(GUITest.class)
    public void testWheelWater() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        
        MoveImpossibleException exception = assertThrows(MoveImpossibleException.class, () -> {
            DonneesSimulation data = new DonneesSimulation();
            data = LecteurDonnees.lire("cartes/carteSujet.map");
            int nbLine = data.getMap().getNbLine();
            int nbCol = data.getMap().getNbCol();
            int size = data.getMap().getSizeCase();
            Move left1 = new Move(0, data.getRobots()[1], Direction.OUEST);
            Move left2 = new Move(left1.getDateEnd(), data.getRobots()[1], Direction.OUEST);
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
            // crée l'invader, en l'associant à la fenêtre graphique précédente
            Simulator sim = new Simulator(gui, data);
            sim.addEvents(left1);
            sim.addEvents(left2);
            left1.setSim(sim);
            for (long i = 0; i<left2.getDateEnd(); i++) {
                sim.execute();
            }
        });

        assertEquals("Déplacement Impossible sur EAU", exception.getMessage());    }
}
