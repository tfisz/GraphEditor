import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GraphEditor extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final String APP_AUTHOR = "Autor: Michal Tryba\n  Data: grudzien 2018";
    private static final String APP_TITLE = "Prosty edytor grafow";
    private static final String APP_INSTRUCTION = "                  O P I S   P R O G R A M U \n\nAktywna klawisze:\n   " +
                                                  "strzalki ==> przesuwanie calego grafu\n   SHIFT + strzalki ==> " +
                                                  "szybkie przesuwanie calego grafu\n\nPonadto gdy kursor wskazuje wezel/krawedz:\n   " +
                                                  "DEL   ==> kasowanie wezla/krawedzi\n   +, -   ==> powiekszanie, pomniejszanie " +
                                                  "wezla\n   r,g,b ==> zmiana koloru wezla\n\nOperacje myszka:\n   LPM ==> " +
                                                  "przesuwanie wezla/krawedzi\n   PPM ==> tworzenie nowego wezla\nPonadto " +
                                                  "gdy kursor wskazuje wezel:\n   PPM ==> usuwanie wezla\n                    " +
                                                  "tworzenie krawedzi\n                    zmiana nazwy\n                    " +
                                                  "zmiana koloru\nPonadto gdy kursor wskazuje krawedz:\n   PPM ==> usuwanie " +
                                                  "krawedzi\n                    zmiana koloru";

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenu menuGraph = new JMenu("Graph");
    private JMenu menuHelp = new JMenu("Help");
    private JMenuItem menuNew = new JMenuItem("New", 78);
    private JMenuItem menuSave = new JMenuItem("Save", 83);
    private JMenuItem menuShowExample = new JMenuItem("Example", 69);
    private JMenuItem menuExit = new JMenuItem("Exit", 88);
    private JMenuItem menuListOfNodes = new JMenuItem("List of Nodes", 78);
    private JMenuItem menuListOfEdges = new JMenuItem("List of Edges", 78);
    private JMenuItem menuAuthor = new JMenuItem("Author", 65);
    private JMenuItem menuInstruction = new JMenuItem("Instruction", 73);
    private GraphPanel panel = new GraphPanel();

    public static void main(String[] args) {
        new GraphEditor();
    }

    public GraphEditor() {
        super(APP_TITLE);
        this.setDefaultCloseOperation(3);
        this.setSize(400, 400);
        this.setLocationRelativeTo((Component)null);
        this.setContentPane(this.panel);
        this.createMenu();
        this.showBuildinExample();
        this.setVisible(true);
    }

    private void showListOfNodes(Graph graph) {
        Node[] array = graph.getNodes();
        StringBuilder message = new StringBuilder("Liczba wezlow: " + array.length + "\n");
        Node[] var4 = array;
        int var5 = array.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Node node = var4[var6];
            message.append(node + "    ");
            message.append("\n");
        }

        JOptionPane.showMessageDialog(this, message, APP_TITLE + " - Lista wezlow", -1);
    }

    private void showListOfEdges(Graph graph) {
        Edge[] array = graph.getEdges();
        StringBuilder message = new StringBuilder("Liczba krawedzi: " + array.length + "\n");
        Edge[] var4 = array;
        int var5 = array.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Edge edge = var4[var6];
            message.append(edge + "    ");
            message.append("\n");
        }

        JOptionPane.showMessageDialog(this, message, APP_TITLE + " - Lista krawedzi", -1);
    }

    private void createMenu() {
        this.menuNew.addActionListener(this);
        this.menuSave.addActionListener(this);
        this.menuShowExample.addActionListener(this);
        this.menuExit.addActionListener(this);
        this.menuListOfNodes.addActionListener(this);
        this.menuListOfEdges.addActionListener(this);
        this.menuAuthor.addActionListener(this);
        this.menuInstruction.addActionListener(this);
        this.menuFile.setMnemonic(70);
        this.menuFile.add(this.menuNew);
        this.menuFile.add(this.menuSave);
        this.menuFile.addSeparator();
        this.menuFile.add(this.menuShowExample);
        this.menuFile.addSeparator();
        this.menuFile.add(this.menuExit);
        this.menuGraph.setMnemonic(71);
        this.menuGraph.add(this.menuListOfNodes);
        this.menuGraph.add(this.menuListOfEdges);
        this.menuHelp.setMnemonic(72);
        this.menuHelp.add(this.menuInstruction);
        this.menuHelp.add(this.menuAuthor);
        this.menuBar.add(this.menuFile);
        this.menuBar.add(this.menuGraph);
        this.menuBar.add(this.menuHelp);
        this.setJMenuBar(this.menuBar);
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == this.menuNew) {
            this.panel.setGraph(new Graph());
            this.repaint();
        }

        if (source == this.menuSave) {
            this.saveGraph(this.panel.getGraph());
        }

        if (source == this.menuShowExample) {
            this.showBuildinExample();
            this.repaint();
        }

        if (source == this.menuListOfNodes) {
            this.showListOfNodes(this.panel.getGraph());
        }

        if (source == this.menuListOfEdges) {
            this.showListOfEdges(this.panel.getGraph());
        }

        if (source == this.menuAuthor) {
            JOptionPane.showMessageDialog(this, APP_AUTHOR, APP_TITLE, 1);
        }

        if (source == this.menuInstruction) {
            JOptionPane.showMessageDialog(this, APP_INSTRUCTION);
        }

        if (source == this.menuExit) {
            System.exit(0);
        }

    }

    private void showBuildinExample() {
        Graph graph = new Graph();
        Node n1 = new Node(100, 100);
        n1.setName("A");
        n1.setColor(Color.RED);
        Node n2 = new Node(100, 200);
        n2.setName("X");
        n2.setColor(Color.CYAN);
        n2.setR(15);
        Node n3 = new Node(200, 100);
        n3.setR(20);
        Node n4 = new Node(200, 250);
        n4.setColor(Color.GREEN);
        n4.setR(30);
        Edge e1 = new Edge(n1, n4);
        Edge e2 = new Edge(n3, n2);
        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        this.panel.setGraph(graph);
    }

    private void saveGraph(Graph graph) {
        try {
            FileOutputStream outFile = new FileOutputStream("data.dat");
            ObjectOutputStream objOutput = new ObjectOutputStream(outFile);
            objOutput.writeObject(graph);
            objOutput.close();
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }
}
