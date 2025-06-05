package tests;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import field.Case;
import io.DonneesSimulation;
import io.LecteurDonnees;
import machines.Robots;
import paths.GPS;
import tests.category.NoGUITests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;


public class TestCostPaths {
    
    @Test
    @Category(NoGUITests.class)
    public void testCostPaths() throws FileNotFoundException, DataFormatException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        Robots firemen = data.getRobots()[1];
        Case destination = new Case(5, 3);
        long sut = GPS.costPaths(firemen.getPosition(), destination, firemen);
        long result = 1125;
        assertEquals(result, sut);
    }
}
