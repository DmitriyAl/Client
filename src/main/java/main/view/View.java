package main.view;

import main.controller.IController;
import main.model.*;
import main.view.painters.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private JPanel configPanel;
    private JButton startConnection;
    private JButton stopConnection;
    private JButton clearScreen;
    private JRadioButton pauseConnection;
    private JSlider accuracySlider;
    private JTextField host;
    private JTextField port;
    private JTextField currentAccuracy;
    private JLabel hostLabel;
    private JLabel portLabel;
    private JLabel bezierAccuracyDescription;
    private JLabel status;
    private JComboBox<DrawingType> drawingTypeJComboBox;
    private Color errorColor;
    private Color backGround;
    private DeskPainter deskPainter;
    private static View instance;
    private ServerStatus serverStatus;


    private View(IModel model) {
        this.model = model;
        serverStatus = ServerStatus.SERVER_IS_UNAVAILABLE;
        errorColor = new Color(255, 140, 140);
        backGround = new Color(255, 255, 255);
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

    private void initGraphics() {
        frame = new JFrame("Graphical client");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        configPanel = new JPanel();
        GroupLayout layout = new GroupLayout(configPanel);
        configPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        desk = new DrawingBoard();
        desk.setBackground(backGround);
        startConnection = new JButton("Start connection");
        stopConnection = new JButton("Stop connection");
        stopConnection.setEnabled(false);
        clearScreen = new JButton("Clear screen");
        clearScreen.setEnabled(false);
        host = new JTextField("127.0.0.1");
        port = new JTextField("29228");
        accuracySlider = new JSlider(SwingConstants.HORIZONTAL, 10, 200, 100);
        accuracySlider.setPreferredSize(new Dimension(50, 20));
        accuracySlider.setPaintTicks(true);
        accuracySlider.setPaintTrack(true);
        accuracySlider.setSnapToTicks(true);
        currentAccuracy = new JTextField(String.valueOf(accuracySlider.getValue()));
        pauseConnection = new JRadioButton("Pause connection");
        pauseConnection.setEnabled(false);
        status = new JLabel();
        hostLabel = new JLabel("Server host");
        portLabel = new JLabel("Server port");
        bezierAccuracyDescription = new JLabel("Bezier smoothing level");
        drawingTypeJComboBox = new JComboBox<>(DrawingType.values());
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(drawingTypeJComboBox)
                .addComponent(bezierAccuracyDescription)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(accuracySlider)
                        .addComponent(currentAccuracy))
                .addComponent(hostLabel)
                .addComponent(host)
                .addComponent(portLabel)
                .addComponent(port)
                .addComponent(startConnection)
                .addComponent(stopConnection)
                .addComponent(pauseConnection)
                .addComponent(clearScreen));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(drawingTypeJComboBox)
                .addComponent(bezierAccuracyDescription)
                .addGroup(layout.createParallelGroup()
                        .addComponent(accuracySlider)
                        .addComponent(currentAccuracy))
                .addComponent(hostLabel)
                .addComponent(host)
                .addComponent(portLabel)
                .addComponent(port)
                .addComponent(startConnection)
                .addComponent(stopConnection)
                .addComponent(pauseConnection)
                .addComponent(clearScreen));
        layout.linkSize(drawingTypeJComboBox, host, port, currentAccuracy);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.add(desk);
        frame.add(BorderLayout.EAST, configPanel);
        frame.add(BorderLayout.SOUTH, status);
        frame.setSize(screenSize);
        frame.setVisible(true);
        buttonListenersConfig();
    }

    private void buttonListenersConfig() {
        startConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                controller.clearScreen();
                host.setBackground(backGround);
                port.setBackground(backGround);
                boolean isCorrectHost = controller.setHost(host.getText());
                boolean isCorrectPort = controller.setPort(port.getText());
                if (!isCorrectHost) {
                    host.setBackground(errorColor);
                    status.setText("Incorrect host");
                    return;
                }
                if (!isCorrectPort) {
                    port.setBackground(errorColor);
                    status.setText("Incorrect port");
                    return;
                }
                controller.startConnection();
                deskPainter = DeskPaintersFactory.getPainter((DrawingType) drawingTypeJComboBox.getSelectedItem());
                if (drawingTypeJComboBox.getSelectedItem() == DrawingType.BEZIER) {
                    ((BezierDeskPainter) deskPainter).setAccuracy(accuracySlider.getValue());
                }
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
        accuracySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                status.setText("");
                currentAccuracy.setText(String.valueOf(accuracySlider.getValue()));
            }
        });
        currentAccuracy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(currentAccuracy.getText());
                    if (value > 200 || value < 10) {
                        throw new IncorrectBezierAccuracyValue("Incorrect value");
                    }
                    accuracySlider.setValue(value);
                } catch (RuntimeException e1) {
                    currentAccuracy.setText(String.valueOf(accuracySlider.getValue()));
                    status.setText("Incorrect number for accuracy");
                }
            }
        });
        clearScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clearScreen();
            }
        });
        stopConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stopConnection();
            }
        });
    }

    private void isSuccessfulConnection(boolean b) {
        startConnection.setEnabled(!b);
        drawingTypeJComboBox.setEnabled(!b);
        accuracySlider.setEnabled(!b);
        currentAccuracy.setEnabled(!b);
        port.setEnabled(!b);
        host.setEnabled(!b);
        pauseConnection.setEnabled(b);
        stopConnection.setEnabled(b);
        clearScreen.setEnabled(b);
    }

    @Override
    public void updateGraphics() {
        deskPainter.draw(desk, model.getCommandPool());
        desk.repaint();
    }

    @Override
    public void updateModelObserver() {
        serverStatus = model.getServerStatus();
        switch (serverStatus) {
            case SERVER_IS_UNAVAILABLE:
                isSuccessfulConnection(false);
                status.setText("Server is unavailable");
                break;
            case SERVER_IS_AVAILABLE:
                status.setText("Connection established");
                isSuccessfulConnection(true);
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

    @Override
    public void clearScreen() {
        deskPainter.clearScreen();
    }
}
