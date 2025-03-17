package paths;

public class Node implements Comparable<Node>{

    private int vertex;
    private long cost;

    public Node(int v, long cost) {
        this.vertex = v;
        this.cost = cost;
    }

    public int getVertex() {
        return this.vertex;
    }

    public long getCost() {
        return this.cost;
    }

    @Override
    public int compareTo(Node u) {
        return Long.compare(cost, u.getCost());
    }

}
