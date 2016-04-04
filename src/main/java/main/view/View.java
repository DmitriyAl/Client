package main.view;

import main.controller.IController;
import main.model.*;
import main.model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dmitriy Albot
 */
public class View implements IView, Observer {
    private IController controller;
    private IModel model;
    private JFrame frame;
    private JPanel desk;
    private JButton startConnection;
    private JRadioButton pauseConnection;
    private JButton stopConnection;
    private JLabel status;
    private Painter painter;
    private static View instance;
    private int deckSize;

    private View(IModel model) {
        this(model, 500);
    }

    private View(IModel model, int deckSize) {
        this.model = model;
        this.deckSize = deckSize;
        model.addObserver(this);
        initGraphics();
    }

    public static View getInstance(IModel model) {
        if (instance == null) {
            instance = new View(model);
        }
        return instance;
    }

    @Override
    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    private void initGraphics() {
        frame = new JFrame("Graphical client");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        desk = new DrawPanel();
        desk.setPreferredSize(new Dimension(deckSize, deckSize));
        desk.setBackground(new Color(255, 255, 255));
        startConnection = new JButton("Start connection");
        pauseConnection = new JRadioButton("Pause connection");
        stopConnection = new JButton("Stop connection");
        pauseConnection.setEnabled(false);
        stopConnection.setEnabled(false);
        status = new JLabel("Ok");
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(desk)
                        .addComponent(status))
                .addGroup(layout.createParallelGroup()
                        .addComponent(startConnection)
                        .addComponent(stopConnection)
                        .addComponent(pauseConnection)));
        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(desk)
                        .addComponent(status))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(startConnection)
                        .addComponent(stopConnection)
                        .addComponent(pauseConnection)));
        layout.linkSize(desk);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        buttonListenersConfig();
    }

    private void buttonListenersConfig() {
        startConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startConnection();
                startConnection.setEnabled(false);
                pauseConnection.setEnabled(true);
                stopConnection.setEnabled(true);
            }
        });
        pauseConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseConnection.isSelected()) {
                    controller.pauseConnection();
                    status.setText("Connection paused");
                } else {
                    controller.resumeConnection();
                    status.setText("Connection stopped");
                }
            }
        });
    }

    @Override
    public void update() {
        drawPoint(model.getCurrentCommand());
    }

    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }

    private void drawPoint(Command command) {
        painter.paint(desk,command.getPoint());
    }

    private void drawNewLine(Point point) {
    }

    private void drawContinuousLine(Point point) {

    }
}
