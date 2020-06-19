import java.awt.Color;
import java.awt.Graphics;

public class Edge {
    protected Node start;
    protected Node end;
    private Color color;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
        this.color = Color.BLACK;
    }

    public Node getStartNode() {
        return this.start;
    }

    public Node getEndNode() {
        return this.end;
    }

    public void setStartNode(Node node) {
        this.start = node;
    }

    public void setEndNode(Node node) {
        this.end = node;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isMouseOver(int mx, int my) {
        Float a = (float)(this.start.y - this.end.y) / (float)(this.start.x - this.end.x);
        Float b = (float)this.start.y - a * (float)this.start.x;
        if (mx >= Math.min(this.start.x, this.end.x) && mx <= Math.max(this.start.x, this.end.x) && my >= Math.min(this.start.y, this.end.y) && my <= Math.max(this.start.y, this.end.y)) {
            return (double)Math.abs(a * (float)mx - (float)my + b) / Math.sqrt((double)(a * a + 1.0F)) < 3.0D;
        } else {
            return false;
        }
    }

    void draw(Graphics g) {
        g.setColor(this.color);
        g.drawLine(this.start.x, this.start.y, this.end.x, this.end.y);
    }

    public String toString() {
        String kolor = this.color.toString();
        String var10000 = this.start.toString();
        return "[" + var10000 + " ==> " + this.end.toString() + " edge's color = " + kolor.substring(14) + "]";
    }
}
