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
public class View implements IView, GraphicsObserver, ModelObserver {
    private IController controller;
    private IModel model;
    private JFrame frame;
    private JPanel desk;
    private JButton startConnection;
    private JRadioButton pauseConnection;
    private JLabel status;
    private DeskPainter deskPainter;
    private static View instance;
    private int deckSize;
    private ServerStatus serverStatus;

    private View(IModel model) {
        this(model, 500);
    }

    private View(IModel model, int deckSize) {
        this.model = model;
        this.deckSize = deckSize;
        serverStatus = ServerStatus.SERVER_IS_UNAVAILABLE;
        model.addGraphicsObserver(this);
        model.addModelObserver(this);
        initGraphics();
    }

    public static View getInstance(IModel model) {
        if (instance == null) {
            instance = new View(model);
        }
        return instance;
    }

    public void setDeskPainter(DeskPainter deskPainter) {
        this.deskPainter = deskPainter;
    }

    private void initGraphics() {
        frame = new JFrame("Graphical client");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        desk = new JPanel();
        desk.setIgnoreRepaint(true);
        desk.setPreferredSize(new Dimension(deckSize, deckSize));
        desk.setBackground(new Color(255, 255, 255));
        startConnection = new JButton("Start connection");
        pauseConnection = new JRadioButton("Pause connection");
        pauseConnection.setEnabled(false);
        status = new JLabel();
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(desk)
                        .addComponent(status))
                .addGroup(layout.createParallelGroup()
                        .addComponent(startConnection)
                        .addComponent(pauseConnection)));
        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(desk)
                        .addComponent(status))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(startConnection)
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

            }
        });
        pauseConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseConnection.isSelected()) {
                    controller.pauseConnection();
                } else {
                    controller.resumeConnection();
                }
            }
        });
    }

    @Override
    public void updateGraphics() {
        Graphics updatedGraphic = deskPainter.draw(desk, model.getCurrentCommand());
        desk.paintComponents(updatedGraphic);
    }

    @Override
    public void updateModelObserver() {
        serverStatus = model.getServerStatus();
        switch (serverStatus) {
            case SERVER_IS_UNAVAILABLE:
                startConnection.setEnabled(true);
                status.setText("Server is unavailable");
                break;
            case SERVER_IS_AVAILABLE:
                status.setText("Connection established");
                break;
            default:
                status.setText("Unknown server status");
                break;
        }
    }

    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }
}
