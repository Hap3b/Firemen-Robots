package tests;

import gui.GUISimulator;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import simulator.*;
import io.*;

public class TestPart1 {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 5000), Math.min(nbCol*size, 5000), Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        @SuppressWarnings("unused")
        Simulator sim = new Simulator(gui, data);
    }
}
