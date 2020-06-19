import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Node {
    protected int x;
    protected int y;
    protected int r;
    private Color color;
    public String name;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = 20;
        this.name = "";
        this.color = Color.WHITE;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return this.r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMouseOver(int mx, int my) {
        return (this.x - mx) * (this.x - mx) + (this.y - my) * (this.y - my) <= this.r * this.r;
    }

    void draw(Graphics g) {
        Font font = new Font("Arial", 1, 14);
        g.setColor(this.color);
        g.fillOval(this.x - this.r, this.y - this.r, 2 * this.r, 2 * this.r);
        g.setColor(Color.BLACK);
        g.drawOval(this.x - this.r, this.y - this.r, 2 * this.r, 2 * this.r);
        g.setFont(font);
        g.drawString(this.name, this.x - 4, this.y + 5);
    }

    public String toString() {
        String kolor = this.color.toString();
        String var10000 = this.name;
        return var10000 + "(" + this.x + ", " + this.y + ", " + kolor.substring(14) + ")";
    }
}
