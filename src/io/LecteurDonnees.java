package io;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
import field.Carte;
import field.Case;
import field.Incendie;
import field.NatureTerrain;
import machines.*;

/**
 * Lecteur de cartes au format spectifié dans le cahier des charges.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 */
public class LecteurDonnees {

    /**
     * Lit et crée une simulation avec le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
    public static DonneesSimulation lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        Carte map = lecteur.buildCarte();
        Incendie[] fires = lecteur.buildIncendies(map);
        Robots[] fireRobots = lecteur.buildRobots(map);
        DonneesSimulation sim = new DonneesSimulation(map, fireRobots, fires);
        scanner.close();
        return sim;
    }


    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Construit la carte.
     * @throws ExceptionFormatDonnees
     */
    private Carte buildCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();
            
            Carte map = new field.Carte(nbLignes, nbColonnes, tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    NatureTerrain biome = getNature(lig, col);
                    Case pos = new Case(lig, col, biome, map);
                    map.setCase(pos, lig, col);
                }
            }
            return map;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }


    /**
     * Renvoie la nature du terrain d'une case.
     */
    private NatureTerrain getNature(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();
        try {
            chaineNature = scanner.next();

            verifieLigneTerminee();

            return NatureTerrain.valueOf(chaineNature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
    }


    /**
     * Construit un tableaux d'incendies.
     */
    private Incendie[] buildIncendies(Carte map) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            Incendie[] fires = new Incendie[nbIncendies];
            for (int i = 0; i < nbIncendies; i++) {
                fires[i] = buildIncendie(i, map);
            }
            return fires;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et construit le i-eme incendie.
     * @param i
     */
    private Incendie buildIncendie(int i, Carte map) throws DataFormatException {
        ignorerCommentaires();

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            Incendie fire = new Incendie(map.getCase(lig, col), intensite);
            map.getCase(lig, col).setFire(fire);
            return fire;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et construit un tableau de robots.
     */
    private Robots[] buildRobots(Carte map) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            Robots[] firemen = new Robots[nbRobots];
            for (int i = 0; i < nbRobots; i++) {
                Robots machine = buildRobot(map);
                firemen[i] = machine;
            }
            return firemen;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et construit le i-eme robot.
     * @param i
     */
    private Robots buildRobot(Carte map) throws DataFormatException {
        ignorerCommentaires();

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            String type = scanner.next();

            Kind kindRobtos = Kind.valueOf(type);
            Case pos = map.getCase(lig, col);

            // lecture eventuelle d'une vitesse du robot (entier)
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?

            switch (kindRobtos) {
                case DRONE:
                    if (s != null) {
                        int vitesse = Integer.parseInt(s);
                        verifieLigneTerminee();
                        return new machines.Drone(pos, vitesse);
                    }
                    verifieLigneTerminee();
                    return new machines.Drone(pos);
                case ROUES:
                    if (s != null) {
                        int vitesse = Integer.parseInt(s);
                        verifieLigneTerminee();
                        return new machines.Roues(pos, vitesse);
                    }
                    verifieLigneTerminee();
                    return new machines.Roues(pos);

                case CHENILLES:
                    if (s != null) {
                        int vitesse = Integer.parseInt(s);
                        verifieLigneTerminee();
                        return new machines.Chenilles(pos, vitesse);
                    }
                    verifieLigneTerminee();
                    return new machines.Chenilles(pos);

                case PATTES:
                    verifieLigneTerminee();
                    return new machines.Pattes(pos);
            
                default:
                    return null;
            }
            
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }

    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
