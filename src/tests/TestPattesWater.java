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

public class TestPattesWater {
    @Test
    @Category(GUITest.class)
    public void testPattesWater() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        Robots firemen = data.getRobots()[2];
        Move left1 = new Move(0, firemen, Direction.OUEST);
        Move left2 = new Move(left1.getDateEnd(), firemen, Direction.OUEST);
        Move left3 = new Move(left2.getDateEnd(), firemen, Direction.OUEST);
        Move left4 = new Move(left3.getDateEnd(), firemen, Direction.OUEST);
        Move left5 = new Move(left4.getDateEnd(), firemen, Direction.OUEST);
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        Simulator sim = new Simulator(gui, data);
        firemen.addEvents(left2);
        firemen.addEvents(left3);
        firemen.addEvents(left4);
        firemen.addEvents(left5);
        firemen.addEvents(left1);
        left1.setSim(sim);
        MoveImpossibleException exception = assertThrows(MoveImpossibleException.class, () -> {
            for (long i = 0; i<left5.getDateEnd(); i++) {
                sim.execute();
            }
        });

        assertEquals("Déplacement sur l'eau impossible", exception.getMessage());
    }
}
