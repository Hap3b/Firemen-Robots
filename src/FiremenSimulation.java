import field.Case;
import field.Direction;
import gui.GUISimulator;
import io.DonneesSimulation;
import io.LecteurDonnees;
import machines.Robots;
import paths.GPS;
import simulator.Simulator;
import simulator.Events.Move;
import simulator.Events.Refill;
import simulator.Events.TurnOff;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class FiremenSimulation {
    public static void main(String[] args) throws FileNotFoundException, DataFormatException {
        DonneesSimulation data = new DonneesSimulation();
        data = LecteurDonnees.lire("cartes/carteSujet.map");
        Robots firemen = data.getRobots()[1];
        Case destination = new Case(5, 3);
        GPS.costPaths(firemen.getPosition(), destination, firemen);
        int nbLine = data.getMap().getNbLine();
        int nbCol = data.getMap().getNbCol();
        int size = data.getMap().getSizeCase();

        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(Math.min(nbLine*size, 1920), Math.min(nbCol*size, 1080), Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulator sim = new Simulator(gui, data);
        
        Move top = new Move(0, firemen, Direction.NORD);
        top.setSim(sim);
        long dateEvt = top.getDateEnd();
        sim.addEvents(top);
        for (int i = 0; i < (int) firemen.getReserve()/firemen.getQuantityWater(); i++) {
            TurnOff intervention = new TurnOff(dateEvt, firemen);
            sim.addEvents(intervention);
            dateEvt = intervention.getDateEnd();
        }
        Move left1 = new Move(dateEvt, firemen, Direction.OUEST);
        sim.addEvents(left1);
        dateEvt = left1.getDateEnd();
        Move left2 = new Move(dateEvt, firemen, Direction.OUEST);
        sim.addEvents(left2);
        dateEvt = left2.getDateEnd();
        // System.out.println(dateEvt);
        Refill fill = new Refill(dateEvt, firemen);
        sim.addEvents(fill);
        dateEvt = fill.getDateEnd();
        Move right1 = new Move(dateEvt, firemen, Direction.EST);
        sim.addEvents(right1);
        dateEvt = right1.getDateEnd();
        Move right2 = new Move(dateEvt, firemen, Direction.EST);
        sim.addEvents(right2);
        dateEvt = right2.getDateEnd();
        for (int i = 0; i<30; i++) {
            TurnOff intervention = new TurnOff(dateEvt, firemen);
            sim.addEvents(intervention);
            dateEvt = intervention.getDateEnd();
        }
        Move right3 = new Move(dateEvt, firemen, Direction.EST);
        sim.addEvents(right3);
    }
}
