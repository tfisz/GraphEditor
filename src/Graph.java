import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph implements Serializable {
    private static final long serialVersionUID = 1L;
    public transient List<Node> nodes = new ArrayList();
    public transient List<Edge> edges = new ArrayList();

    public Graph() {
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public void removeNode(Node node) {
        for(int i = 0; i < this.edges.size(); ++i) {
            Edge e = (Edge)this.edges.get(i);
            Node start = e.getStartNode();
            Node end = e.getEndNode();
            if (node.x == start.x && node.y == start.y || node.x == end.x && node.y == end.y) {
                this.removeEdge(e);
                --i;
            }
        }

        this.nodes.remove(node);
    }

    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
    }

    public Node[] getNodes() {
        Node[] array = new Node[0];
        return (Node[])this.nodes.toArray(array);
    }

    public Edge[] getEdges() {
        Edge[] array = new Edge[0];
        return (Edge[])this.edges.toArray(array);
    }

    public void draw(Graphics g) {
        Iterator var2 = this.edges.iterator();

        while(var2.hasNext()) {
            Edge edge = (Edge)var2.next();
            edge.draw(g);
        }

        var2 = this.nodes.iterator();

        while(var2.hasNext()) {
            Node node = (Node)var2.next();
            node.draw(g);
        }

    }
}
