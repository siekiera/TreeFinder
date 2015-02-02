package pl.edu.pw.elka.treefinder.ui;

import pl.edu.pw.elka.treefinder.io.GraphFileReader;
import pl.edu.pw.elka.treefinder.io.GraphFileReaderException;
import pl.edu.pw.elka.treefinder.logic.algorithm.BoruvkaAlgorithm;
import pl.edu.pw.elka.treefinder.logic.algorithm.KruskalAlgorithm;
import pl.edu.pw.elka.treefinder.logic.algorithm.PrimAlgorithm;
import pl.edu.pw.elka.treefinder.logic.generator.GraphGeneratorImpl;
import pl.edu.pw.elka.treefinder.model.Edge;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.model.Vertex;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;


/**
 * Created by monika on 2015-01-15.
 */
public class MainWindow {
    private JPanel panel1;
    private JButton generate;
    private JSpinner numberOfVertices;
    private JSlider density;
    private JButton openFromFileButton;
    private JButton runPrimAlgorithmButton;
    private JButton runKruskalAlgorithmButton;
    private JButton runBoruvkaAlgorithmButton;
    private JLabel time;
    private JLabel mstWeight;
    private JLabel totalRunTimeLabel;
    private JLabel totalMSTWeight;
    private JLabel numberOfVerticesLabel;
    private JLabel densityLabel;
    private JTabbedPane tabbedPane1;
    private JPanel inputGraphPanel;
    private JPanel mstGraphPanel;
    private JButton saveToFileButton;
    private JTextField densityTextField;

    private Graph inputGraph;
    private Graph mstGraph;
    private long startTime;

    public MainWindow() {
        numberOfVertices.setModel(new SpinnerNumberModel(10, 2, 1000, 1));
        generate.addActionListener(e -> {
            try {
                inputGraph = new GraphGeneratorImpl().generate((int) numberOfVertices.getValue(), ((float) density.getValue()) / 100.0f);
                inputGraphPanel.repaint();
            } catch (Exception ex) {
                // TODO obsługa błędów
            }
        });
        openFromFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(panel1);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    inputGraph = new GraphFileReader(selectedFile.toURI()).read();
                    inputGraphPanel.repaint();
                } catch (GraphFileReaderException e1) {
                    // TODO obsługa błędów
                }
            }
        });
        runPrimAlgorithmButton.addActionListener(e -> {
            startAlgorithm();
            mstGraph = new PrimAlgorithm().calculate(inputGraph);
            stopAlgorithm();
        });
        runKruskalAlgorithmButton.addActionListener(e -> {
            startAlgorithm();
            mstGraph = new KruskalAlgorithm().calculate(inputGraph);
            stopAlgorithm();
        });
        runBoruvkaAlgorithmButton.addActionListener(e -> {
            startAlgorithm();
            mstGraph = new BoruvkaAlgorithm().calculate(inputGraph);
            stopAlgorithm();
        });

        density.addChangeListener(e -> {
            int val = validateDensityValue(density.getValue());
            densityTextField.setText(String.valueOf(val));
            density.setValue(val);
        });
        densityTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = densityTextField.getText();
                density.setValue(validateDensityValue(0));
                if (!typed.matches("\\d+")) {
                    return;
                }
                int value = validateDensityValue(Integer.parseInt(typed));
                densityTextField.setText(String.valueOf(value));
                density.setValue(value);
            }
        });
    }

    private int validateDensityValue(int value) {
        int min = 200/(int)numberOfVertices.getValue();
        return Math.max(value, min);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MST finder");
        frame.setContentPane(new MainWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void refreshInputGraph(Graphics g) {
        g.clearRect(0,0,inputGraphPanel.getWidth(),inputGraphPanel.getHeight());
        if(inputGraph == null) return;
        for(Vertex vertex : inputGraph.getVertices()) {
            g.fillOval((int)vertex.getX() * inputGraphPanel.getWidth() / 100 - 3,
                    (int)vertex.getY() * inputGraphPanel.getHeight() / 100 - 3,
                    6,6);
        }
        for(Edge edge : inputGraph.getEdges()) {
            Vertex v1 = edge.getStart();
            Vertex v2 = edge.getEnd();
            g.drawLine((int)v1.getX() * inputGraphPanel.getWidth() / 100,
                    (int)v1.getY() * inputGraphPanel.getHeight() / 100,
                    (int)v2.getX() * inputGraphPanel.getWidth() / 100,
                    (int)v2.getY() * inputGraphPanel.getHeight() / 100);
            for(int i = 0; i < inputGraph.getVertices().size(); ++i) {
                Vertex v = inputGraph.getVertices().get(i);
                v.setGroupNumber(i);
            }
            Color old = g.getColor();
            g.setColor(Color.BLUE);
            g.drawString(String.valueOf(v1.getGroupNumber()),
                    (int)v1.getX() * inputGraphPanel.getWidth() / 100,
                    (int)v1.getY() * inputGraphPanel.getHeight() / 100);
            g.drawString(String.valueOf(v2.getGroupNumber()),
                    (int)v2.getX() * inputGraphPanel.getWidth() / 100,
                    (int)v2.getY() * inputGraphPanel.getHeight() / 100);
            g.setColor(old);

            g.drawString(new DecimalFormat("#.##").format(edge.getWeight()),
                    (int) ((v1.getX() * inputGraphPanel.getWidth() / 100 + v2.getX() * inputGraphPanel.getWidth() / 100) / 2),
                    (int) ((v1.getY() * inputGraphPanel.getHeight() / 100 + v2.getY() * inputGraphPanel.getHeight() / 100)/2));
        }
    }

    private void refreshMSTGraph(Graphics g) {
        g.clearRect(0,0,mstGraphPanel.getWidth(),mstGraphPanel.getHeight());
        if(mstGraph == null) return;

        for(Vertex vertex : mstGraph.getVertices()) {
            g.fillOval((int)vertex.getX() * mstGraphPanel.getWidth() / 100,
                    (int)vertex.getY() * mstGraphPanel.getHeight() / 100,
                    5,5);
        }
        for(Edge edge : mstGraph.getEdges()) {
            Vertex v1 = edge.getStart();
            Vertex v2 = edge.getEnd();
            g.drawLine((int)v1.getX() * mstGraphPanel.getWidth() / 100,
                    (int)v1.getY() * mstGraphPanel.getHeight() / 100,
                    (int)v2.getX() * mstGraphPanel.getWidth() / 100,
                    (int)v2.getY() * mstGraphPanel.getHeight() / 100);
            for(int i = 0; i < inputGraph.getVertices().size(); ++i) {
                Vertex v = inputGraph.getVertices().get(i);
                v.setGroupNumber(i);
            }
            Color old = g.getColor();
            g.setColor(Color.BLUE);
            g.drawString(String.valueOf(v1.getGroupNumber()),
                    (int)v1.getX() * inputGraphPanel.getWidth() / 100,
                    (int)v1.getY() * inputGraphPanel.getHeight() / 100);
            g.drawString(String.valueOf(v2.getGroupNumber()),
                    (int)v2.getX() * inputGraphPanel.getWidth() / 100,
                    (int)v2.getY() * inputGraphPanel.getHeight() / 100);
            g.setColor(old);
            g.drawString(new DecimalFormat("#.##").format(edge.getWeight()),
                    (int)((v1.getX() * inputGraphPanel.getWidth() / 100 + v2.getX() * inputGraphPanel.getWidth() / 100)/2),
                    (int)((v1.getY() * inputGraphPanel.getHeight() / 100 + v2.getY() * inputGraphPanel.getHeight() / 100)/2));
        }

    }

    private void startAlgorithm() {
        startTime = System.nanoTime();
    }

    private void stopAlgorithm() {
        long timeElapsed = System.nanoTime() - startTime;
        time.setText(Long.toString(timeElapsed/1000) + " ms");
        Double weight = mstGraph.totalWeight();
        mstWeight.setText(new DecimalFormat("#.##").format(weight));
        mstGraphPanel.repaint();
    }

    private void createUIComponents() {
        inputGraphPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                refreshInputGraph(g);
            }
        };
        mstGraphPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                refreshMSTGraph(g);
            }
        };
    }
}
