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

public class TestOutOfBond {
    @Test
    @Category(GUITest.class)
    public void testOutOfBond() throws FileNotFoundException, DataFormatException,
    MoveImpossibleException, RefillImpossibleException, TurnOffImpossibleException {
        
        MoveImpossibleException exception = assertThrows(MoveImpossibleException.class, () -> {
            DonneesSimulation data = new DonneesSimulation();
            data = LecteurDonnees.lire("cartes/carteSujet.map");
            int nbLine = data.getMap().getNbLine();
            int nbCol = data.getMap().getNbCol();
            int size = data.getMap().getSizeCase();
            Move top1 = new Move(0, data.getRobots()[0], Direction.NORD);
            Move top2 = new Move(top1.getDateEnd(), data.getRobots()[0], Direction.NORD);
            Move top3 = new Move(top2.getDateEnd(), data.getRobots()[0], Direction.NORD);
            Move top4 = new Move(top3.getDateEnd(), data.getRobots()[0], Direction.NORD);
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
            // crée l'invader, en l'associant à la fenêtre graphique précédente
            Simulator sim = new Simulator(gui, data);
            sim.addEvents(top1);
            sim.addEvents(top2);
            sim.addEvents(top3);
            sim.addEvents(top4);
            top1.setSim(sim);
            for (long i = 0; i < top4.getDateEnd(); i++) {
                sim.execute();
            }
        });

        assertEquals("Déplacements Hors Limites", exception.getMessage());
    }
}
