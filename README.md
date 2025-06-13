# Simulation d'intervention de robots pompiers

> Projet inspiré du sujet de Programmation Orientée Objet (POO) de l'ENSIMAG.

## Objectif

Le but de ce projet est de simuler l’extinction de feux à l’aide de différents types de robots, chacun ayant des caractéristiques spécifiques.

## Commandes disponibles

Les commandes suivantes sont disponibles via le `Makefile` :

| Commande              | Description                                                                 |
|-----------------------|-----------------------------------------------------------------------------|
| `make`                | Compile l'intégralité du projet                                              |
| `make test`           | Exécute tous les tests (graphiques et non graphiques)                       |
| `make test_gui`       | Exécute uniquement les tests graphiques                                     |
| `make test_no_gui`    | Exécute uniquement les tests non graphiques                                 |
| `make doc`            | Génère la documentation (via Doxygen, par exemple)                          |
| `make clean`          | Nettoie le projet (suppression des fichiers générés)                        |
| `make coverage_report`| Génère un rapport de couverture des tests (voir `coverage_report/index.html`) |
| `make run`            | Lance l'exécution de la simulation                                           |

## Recommandation d'utilisation

Dans l’**interface graphique**, l’échelle de temps utilisée est très grande.  
Il est donc conseillé de régler le délai entre deux affichages à **1 ms** pour une meilleure fluidité de la simulation.
