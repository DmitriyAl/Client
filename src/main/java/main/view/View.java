package main.view;

import main.controller.IController;
import main.model.*;

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
    private Painter painter;
    private static View instance;
    private int deckSize;
    private Command currentCommand;
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

    @Override
    public Command getCurrentCommand() {
        return currentCommand;
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
        desk = new JPanel();
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
                    status.setText("Connection paused");
                } else {
                    controller.resumeConnection();
                    status.setText("Connection stopped");
                }
            }
        });
    }

    @Override
    public void updateGraphics() {
        Graphics updatedGraphic = painter.draw(desk, model.getCurrentCommand());
        desk.paintComponents(updatedGraphic);
        desk.repaint();
//        desk.update(updatedGraphic);
    }

    @Override
    public void updateModelObserver() {
        serverStatus = model.getServerStatus();
        if (serverStatus == ServerStatus.SERVER_IS_UNAVAILABLE) {
            startConnection.setEnabled(true);
            status.setText("Server is unavailable");
        }
    }

    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }
}
