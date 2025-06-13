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
import machines.Robots;
import simulator.Simulator;
import simulator.Events.Move;
import simulator.Events.Refill;
import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.RefillImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;
import tests.category.GUITest;

public class TestRefillImpossible {
    @Test
    @Category(GUITest.class)
    public void testRefillImpossible() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        Robots firemen = data.getRobots()[0];
        Move right = new Move(0, firemen, Direction.EST);
        Refill fill = new Refill(right.getDateEnd(), firemen);
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 1920), Math.min(nbCol*size, 1080), Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulator sim = new Simulator(gui, data);
        firemen.addEvents(fill);
        firemen.addEvents(right);
        fill.setSim(sim);
        RefillImpossibleException exception = assertThrows(RefillImpossibleException.class, () -> {
            for (long i = 0; i<fill.getDateEnd(); i++) {
                sim.execute();
            }
        });

        assertEquals("Recharge possible que sur une case EAU", exception.getMessage());
    }
}
