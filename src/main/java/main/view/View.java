package main.view;

import main.controller.IController;
import main.model.IModel;
import main.model.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dmitriy Albot
 */
public class View implements IView, Observer {
    private IController controller;
    private IModel model;
    private JFrame frame;
    private JPanel desk;
    private JButton startConnection;
    private JButton pauseConnection;
    private JButton stopConnection;
    private JLabel status;
    private static View instance;
    private int deckSize;

    public View() {//todo delete
        deckSize = 500;
        initGraphics();
    }

    private View(IModel model) {
        this(model, 500);
    }

    private View(IModel model, int deckSize) {
        this.model = model;
        this.deckSize = deckSize;
        initGraphics();
    }

    private void initGraphics() {
        frame = new JFrame("Graphical client");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        desk = new JPanel();
        desk.setPreferredSize(new Dimension(deckSize, deckSize));
        desk.setBackground(new Color(255, 255, 255));
        startConnection = new JButton("Start connection");
        pauseConnection = new JButton("Pause connection");
        stopConnection = new JButton("Stop connection");
        status = new JLabel("Ok");
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(desk)
                        .addComponent(status))
                .addGroup(layout.createParallelGroup()
                        .addComponent(startConnection)
                        .addComponent(pauseConnection)
                        .addComponent(stopConnection)));
        layout.linkSize(SwingConstants.HORIZONTAL, pauseConnection);
        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(desk)
                        .addComponent(status))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(startConnection)
                        .addComponent(pauseConnection)
                        .addComponent(stopConnection)));
        layout.linkSize(desk);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static View getInstance(IModel model) {
        if (instance == null) {
            instance = new View(model);
        }
        return instance;
    }

    @Override
    public void update() {

    }

    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }
}
