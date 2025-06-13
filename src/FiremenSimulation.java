
import gui.GUISimulator;
import io.DonneesSimulation;
import io.LecteurDonnees;

import machines.RobotsManager;
import simulator.Simulator;

import simulator.Events.Exceptions.MoveImpossibleException;
import simulator.Events.Exceptions.TurnOffImpossibleException;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class FiremenSimulation {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException, MoveImpossibleException, TurnOffImpossibleException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();

        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 1920), Math.min(nbCol*size, 1080), Color.BLACK);
        Simulator sim = new Simulator(gui, data);

        RobotsManager chief = new RobotsManager(data.getFire(), data.getRobots(), sim);
        chief.strategyElem();
    }
}
