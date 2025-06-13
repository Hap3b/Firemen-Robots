package tests;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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

public class TestWheelRock {
    @Test
    @Category(GUITest.class)
    public void testWheelRock() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        
        MoveImpossibleException exception = assertThrows(MoveImpossibleException.class, () -> {
            DonneesSimulation data = new DonneesSimulation();
            data = LecteurDonnees.lire("cartes/carteSujet.map");
            int nbLine = data.getMap().getNbLine();
            int nbCol = data.getMap().getNbCol();
            int size = data.getMap().getSizeCase();
            Robots firemen = data.getRobots()[1];
            Move left = new Move(0, firemen, Direction.OUEST);
            Move top1 = new Move(left.getDateEnd(), firemen, Direction.NORD);
            Move top2 = new Move(top1.getDateEnd(), firemen, Direction.NORD);
            Move top3 = new Move(top2.getDateEnd(), firemen, Direction.NORD);
            Move top4 = new Move(top3.getDateEnd(), firemen, Direction.NORD);
            Move top5 = new Move(top4.getDateEnd(), firemen, Direction.NORD);
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
            // crée l'invader, en l'associant à la fenêtre graphique précédente
            Simulator sim = new Simulator(gui, data);
            firemen.addEvents(top1);
            firemen.addEvents(top2);
            firemen.addEvents(top3);
            firemen.addEvents(top4);
            firemen.addEvents(top5);
            firemen.addEvents(left);
            top1.setSim(sim);
            for (long i = 0; i<top5.getDateEnd(); i++) {
                sim.execute();
            }
        });

        assertEquals("Déplacement Impossible sur ROCHE", exception.getMessage());
    }
}
