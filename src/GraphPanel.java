import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    protected Graph graph;
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean mouseButtonLeft = false;
    private boolean mouseButtonRigth = false;
    private boolean edgeCreation = false;
    protected int mouseCursor = 0;
    protected Node nodeUnderCursor = null;
    protected Edge edgeUnderCursor = null;
    protected Node firstNode = null;

    public GraphPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    public Graph getGraph() {
        return this.graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    private Node findNode(int mx, int my) {
        Node[] var3 = this.graph.getNodes();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Node node = var3[var5];
            if (node.isMouseOver(mx, my)) {
                return node;
            }
        }

        return null;
    }

    private Node findNode(MouseEvent event) {
        return this.findNode(event.getX(), event.getY());
    }

    private Edge findEdge(int mx, int my) {
        Iterator var3 = this.graph.edges.iterator();

        Edge edge;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            edge = (Edge)var3.next();
        } while(!edge.isMouseOver(mx, my));

        return edge;
    }

    private Edge findEdge(MouseEvent event) {
        return this.findEdge(event.getX(), event.getY());
    }

    protected void setMouseCursor(MouseEvent event) {
        this.nodeUnderCursor = this.findNode(event);
        this.edgeUnderCursor = this.findEdge(event);
        if (this.nodeUnderCursor != null) {
            this.mouseCursor = 12;
        } else if (this.edgeUnderCursor != null) {
            this.mouseCursor = 1;
        } else if (this.edgeCreation) {
            this.mouseCursor = 3;
        } else if (this.mouseButtonLeft) {
            this.mouseCursor = 13;
        } else {
            this.mouseCursor = 0;
        }

        this.setCursor(Cursor.getPredefinedCursor(this.mouseCursor));
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }

    protected void setMouseCursor() {
        this.nodeUnderCursor = this.findNode(this.mouseX, this.mouseY);
        this.edgeUnderCursor = this.findEdge(this.mouseX, this.mouseY);
        if (this.nodeUnderCursor != null) {
            this.mouseCursor = 12;
        } else if (this.edgeUnderCursor != null) {
            this.mouseCursor = 1;
        } else if (this.edgeCreation) {
            this.mouseCursor = 3;
        } else if (this.mouseButtonLeft) {
            this.mouseCursor = 13;
        } else {
            this.mouseCursor = 0;
        }

        this.setCursor(Cursor.getPredefinedCursor(this.mouseCursor));
    }

    private void moveNode(int dx, int dy, Node node) {
        node.setX(node.getX() + dx);
        node.setY(node.getY() + dy);
    }

    private void moveEdge(int dx, int dy, Edge edge) {
        edge.start.setX(edge.start.getX() + dx);
        edge.start.setY(edge.start.getY() + dy);
        edge.end.setX(edge.end.getX() + dx);
        edge.end.setY(edge.end.getY() + dy);
    }

    private void moveAllNodes(int dx, int dy) {
        Node[] var3 = this.graph.getNodes();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Node node = var3[var5];
            this.moveNode(dx, dy, node);
        }

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.graph != null) {
            this.graph.draw(g);
        }
    }

    public void mouseClicked(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
        if (event.getButton() == 1) {
            this.mouseButtonLeft = true;
        }

        if (event.getButton() == 3) {
            this.mouseButtonRigth = true;
        }

        this.setMouseCursor(event);
    }

    public void mouseReleased(MouseEvent event) {
        if (event.getButton() == 1 && this.edgeCreation && this.nodeUnderCursor != null) {
            Edge edge = new Edge(this.firstNode, this.nodeUnderCursor);
            this.graph.addEdge(edge);
            this.repaint();
            this.edgeCreation = false;
        }

        if (event.getButton() == 1) {
            this.mouseButtonLeft = false;
        }

        if (event.getButton() == 3) {
            this.mouseButtonRigth = false;
        }

        this.setMouseCursor(event);
        if (event.getButton() == 3) {
            if (this.nodeUnderCursor != null) {
                this.createPopupMenu(event, this.nodeUnderCursor);
            } else if (this.edgeUnderCursor != null) {
                this.createPopupMenu(event, this.edgeUnderCursor);
            } else {
                this.createPopupMenu(event);
            }
        }

    }

    public void mouseDragged(MouseEvent event) {
        if (this.mouseButtonLeft) {
            if (this.nodeUnderCursor != null) {
                this.moveNode(event.getX() - this.mouseX, event.getY() - this.mouseY, this.nodeUnderCursor);
            } else if (this.edgeUnderCursor != null) {
                this.moveEdge(event.getX() - this.mouseX, event.getY() - this.mouseY, this.edgeUnderCursor);
            } else {
                this.moveAllNodes(event.getX() - this.mouseX, event.getY() - this.mouseY);
            }
        }

        this.mouseX = event.getX();
        this.mouseY = event.getY();
        this.repaint();
    }

    public void mouseMoved(MouseEvent event) {
        this.setMouseCursor(event);
    }

    public void keyPressed(KeyEvent event) {
        byte dist;
        if (event.isShiftDown()) {
            dist = 10;
        } else {
            dist = 1;
        }

        switch(event.getKeyCode()) {
            case 37:
                this.moveAllNodes(-dist, 0);
                break;
            case 38:
                this.moveAllNodes(0, -dist);
                break;
            case 39:
                this.moveAllNodes(dist, 0);
                break;
            case 40:
                this.moveAllNodes(0, dist);
                break;
            case 127:
                if (this.nodeUnderCursor != null) {
                    this.graph.removeNode(this.nodeUnderCursor);
                    this.nodeUnderCursor = null;
                } else if (this.edgeUnderCursor != null) {
                    this.graph.removeEdge(this.edgeUnderCursor);
                    this.edgeUnderCursor = null;
                }
        }

        this.repaint();
        this.setMouseCursor();
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
        char znak = event.getKeyChar();
        if (this.nodeUnderCursor != null) {
            int r;
            switch(znak) {
                case '-':
                    r = this.nodeUnderCursor.getR() - 10;
                    if (r >= 10) {
                        this.nodeUnderCursor.setR(r);
                    }
                    break;
                case '=':
                    r = this.nodeUnderCursor.getR() + 10;
                    this.nodeUnderCursor.setR(r);
                    break;
                case 'b':
                    this.nodeUnderCursor.setColor(Color.BLUE);
                    break;
                case 'g':
                    this.nodeUnderCursor.setColor(Color.GREEN);
                    break;
                case 'r':
                    this.nodeUnderCursor.setColor(Color.RED);
            }
        }

        if (this.edgeUnderCursor != null && this.nodeUnderCursor == null) {
            switch(znak) {
                case 'b':
                    this.edgeUnderCursor.setColor(Color.BLUE);
                    break;
                case 'g':
                    this.edgeUnderCursor.setColor(Color.GREEN);
                    break;
                case 'r':
                    this.edgeUnderCursor.setColor(Color.RED);
            }
        }

        this.repaint();
        this.setMouseCursor();
    }

    protected void createPopupMenu(MouseEvent event) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Create new node");
        menuItem.addActionListener((action) -> {
            this.graph.addNode(new Node(event.getX(), event.getY()));
            this.repaint();
        });
        popup.add(menuItem);
        popup.show(event.getComponent(), event.getX(), event.getY());
    }

    protected void createPopupMenu(MouseEvent event, Node node) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem colorItem = new JMenuItem("Change node color");
        colorItem.addActionListener((a) -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Background Color", node.getColor());
            if (newColor != null) {
                node.setColor(newColor);
            }

            this.repaint();
        });
        JMenuItem nameItem = new JMenuItem("Change node name");
        nameItem.addActionListener((a) -> {
            JDialog dialog = new JDialog(new JFrame(), "Change node name");
            JPanel panel = new JPanel((LayoutManager)null);
            JButton OKButton = new JButton("  OK  ");
            JButton CancelButton = new JButton("Cancel");
            JTextField nameField = new JTextField(10);
            JLabel Header = new JLabel("Enter new name:");
            int startingX = 30;
            int startingY = 0;
            Header.setBounds(startingX, startingY, 200, 30);
            OKButton.setBounds(startingX - 2, 62, 80, 20);
            CancelButton.setBounds(startingX + 82, 62, 80, 20);
            nameField.setBounds(startingX, 32, 162, 28);
            panel.add(Header);
            panel.add(nameField);
            panel.add(OKButton);
            panel.add(CancelButton);
            dialog.add(panel);
            dialog.setSize(240, 140);
            dialog.setVisible(true);
            dialog.setResizable(false);
            dialog.setAlwaysOnTop(true);
            dialog.setLocationRelativeTo((Component)null);
            OKButton.addActionListener((b) -> {
                String name = nameField.getText();
                if (name != "") {
                    node.setName(name);
                }

                this.repaint();
                dialog.dispose();
            });
            CancelButton.addActionListener((b) -> {
                dialog.dispose();
            });
            this.repaint();
        });
        JMenuItem removeItem = new JMenuItem("Remove this node");
        removeItem.addActionListener((action) -> {
            this.graph.removeNode(node);
            this.repaint();
        });
        JMenuItem edgeItem = new JMenuItem("Create new edge from this node");
        edgeItem.addActionListener((action) -> {
            this.firstNode = node;
            this.edgeCreation = true;
        });
        popup.add(removeItem);
        popup.add(edgeItem);
        popup.add(colorItem);
        popup.add(nameItem);
        popup.show(event.getComponent(), event.getX(), event.getY());
    }

    protected void createPopupMenu(MouseEvent event, Edge edge) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem removeItem = new JMenuItem("Remove edge");
        removeItem.addActionListener((a) -> {
            this.graph.removeEdge(edge);
            this.repaint();
        });
        JMenuItem colorItem = new JMenuItem("Change color of the edge");
        colorItem.addActionListener((a) -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Background Color", edge.getColor());
            if (newColor != null) {
                edge.setColor(newColor);
            }

            this.repaint();
        });
        popup.add(removeItem);
        popup.add(colorItem);
        popup.show(event.getComponent(), event.getX(), event.getY());
    }
}
