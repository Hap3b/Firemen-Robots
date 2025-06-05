package paths;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import field.Case;
import field.Direction;
import machines.Robots;

public class GPS {
    /**
     * Calcule le temps nécessaire à un robot pour se rendre d'un case A à une case B.
     * @param source Point de départ du robot.
     * @param destination Point d'arrivée du robot.
     * @param machine Robot qui se déplace.
     * @return Plus cours temps pour se rendre de source à destination.
     */
    public static long costPaths(Case source, Case destination, Robots machine) {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        int dim = source.getMap().getNbCol();
        long[] dist = new long[dim*dim];
        Direction[] prev = new Direction[dim*dim];

        int target = destination.getLine()*dim+destination.getColumn();
        int src = source.getLine()*dim+source.getColumn();

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, Direction.NONE);

        Map<Direction, Double>[] graph = machine.getGraph();

        dist[src] = 0;
        pq.add(new Node(src, 0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int u = node.getVertex();

            if (u == target) break;

            for (Direction direction : Direction.values()) {
                if (direction == Direction.NONE) {
                    break;
                }
                double speed = graph[u].get(direction);
                if (speed != Double.MAX_VALUE) {

                    int v = 0;

                    switch (direction) {
                        case NORD:
                            v = u - dim;
                            break;
                        case SUD:
                            v = u + dim;
                            break;
                        case EST:
                            v = u + 1;
                            break;
                        case OUEST:
                            v = u - 1;
                            break;
                        default:
                            break;
                    }

                    long weight = (long) ((long) ((long) 3.6*source.getMap().getSizeCase())/speed); // Convertion

                    if (dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                        prev[v] = direction;
                        pq.add(new Node(v, dist[u] + weight));
                    }
                }
            }
        }
        machine.setPath(reconstructPath(prev, src, target, dim));
        return dist[target];
    }

    /**
     * Reconstruit le Chemin à emprunter en fonction des precedents sommets parcourus.
     * @param prev Tableaux de DIRECTIONS.
     * @param src Début du chemin.
     * @param target Fin du chemin.
     * @param dim Dimension de la carte.
     * @return Liste de Directions à prendre pour se diriger de source à destination.
     */
    public static List<Direction> reconstructPath(Direction[] prev, int src, int target, int dim) {
        List<Direction> path = new LinkedList<>();
        int cur = target;
        while (prev[cur] != Direction.NONE) {
            Direction dir = prev[cur];
            switch (dir) {
                case NORD:
                    cur = cur+dim;
                    break;
                case SUD:
                    cur = cur-dim;
                    break;
                case EST:
                    cur = cur-1;
                    break;
                case OUEST:
                    cur = cur+1;
                    break;
                default:
                    break;
            }
            path.addFirst(dir);
        }
        return cur == src ? path : Collections.emptyList();
    }
}
