package paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import field.Case;
import field.Direction;
import machines.Robots;

public class GPS {
    public static long costPaths(Case source, Case destination, Robots machine) {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        int dim = source.getMap().getNbCol();
        long[] dist = new long[dim*dim];
        Direction[] prev = new Direction[dim*dim];

        int target = destination.getLine()*dim+destination.getColumn();

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        int line = source.getLine();
        int col = source.getColumn();

        int src = line*dim+col;

        Map<Direction, Double>[] graph = machine.getGraph();

        dist[src] = 0;
        pq.add(new Node(src, 0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int u = node.getVertex();

            if (u == target) break;

            for (Direction direction : Direction.values()) {
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

                    long weight = (long) ((long) ((long) 3.6*source.getMap().getSizeCase())/speed);

                    if (dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                        prev[v] = direction;
                        pq.add(new Node(v, dist[u] + weight));
                    }
                }
            }
        }
        return dist[target];
    }

/*     public static List<Direction> reconstructPath(Direction[] prev, int src, int target) {
        List<Direction> path = new ArrayList<>();
        for (int at = target; at != -1; at = prev[at]) {
            path.addFirst(null);
        }
        Collections.reverse(path);
        return path.get(0) == src ? path : Collections.emptyList();
    } */
}
