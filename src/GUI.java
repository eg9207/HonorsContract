import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GUI implements ActionListener, ChangeListener {
    // PROPERTIES


    JFrame theframe = new JFrame("Projectile Motion");

    // Panels
    JPanel mainPanel;
    JSplitPane splitPaneV;
    JSplitPane splitPaneH;

    // ProjPanel
    ProjPanel projPanel;
    JSlider sliderHeight;
    JLabel labelHeight;
    JLabel labelTimer;
    JTextField fieldTimer;

    // ControlPanel
    ControlPanel controlPanel;
    JSlider sliderV1;
    JSlider sliderAngle;
    JLabel labelV1;
    JLabel labelAngle;
    JButton butLaunch;
    JButton butReset;
    JButton butTarget;

    boolean blnLaunch = false;

    // StatPanel
    JPanel statPanel;
    JButton butPosition;
    JButton butVelocity;
    int intStatPanel;
    JTextArea txtStats;

    JLabel winner;


    // Timers
    Timer thetimer = new Timer(1000/48, this);
    long longStartTime;
    long longElapsedTime;
    double dblElapsedSeconds;

    // Local Projectile Variables
    double[] dblCurrentPos;
    double dblHeight;
    double dblV1;
    double dblAngle;

    // METHODS
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == thetimer) {
            if (blnLaunch == true) {
                dblV1 = this.sliderV1.getValue();
                dblAngle = this.sliderAngle.getValue();
                Ball projectile = new Ball(dblV1, dblAngle, dblHeight);

                longElapsedTime = System.currentTimeMillis() - longStartTime;
                dblElapsedSeconds = longElapsedTime / 1000.0000;
                dblCurrentPos = projectile.getCurrentPos(dblElapsedSeconds);

                if (dblCurrentPos[1] <= 0) {
                    dblCurrentPos[0] = Ball.dblDistX;
                    dblCurrentPos[1] = 0;
                    dblElapsedSeconds = Math.floor(Ball.dblTime * 1000)/1000;
                    blnLaunch = false;
                }
                this.fieldTimer.setText(dblElapsedSeconds + "s");

                this.projPanel.intBallX = 85 + (int)(dblCurrentPos[0]);
                this.projPanel.intBallY = 340 - (int)(dblCurrentPos[1]);


                if(this.projPanel.intBallX <= this.projPanel.targetX + 40 && this.projPanel.intBallX >= this.projPanel.targetX - 10) {
                  if(this.projPanel.intBallY <= this.projPanel.targetY + 5 && this.projPanel.intBallY >= this.projPanel.targetY - 5) {
                         this.winner.setText("You hit the target");
                        blnLaunch = false;
                    }
                }

                loadStatPanel();
            }
        }
        else if (evt.getSource() == butLaunch) {
            this.blnLaunch = true;
            this.butLaunch.setEnabled(false);
            this.butTarget.setEnabled(false);
            this.sliderHeight.setEnabled(false);
            this.sliderV1.setEnabled(false);
            this.sliderAngle.setEnabled(false);

            longStartTime = System.currentTimeMillis();
        }
        else if (evt.getSource() == butReset) {
            blnLaunch = false;
            this.butLaunch.setEnabled(true);
            this.sliderHeight.setEnabled(true);
            this.sliderV1.setEnabled(true);
            this.sliderAngle.setEnabled(true);
            this.butTarget.setEnabled(true);

            this.projPanel.intBallX = 85;
            this.projPanel.intBallY = 340 - (int)(dblHeight);

            this.fieldTimer.setText("0.000s");
            this.fieldTimer.setFont(fieldTimer.getFont().deriveFont(Font.BOLD, 14f)); // Bold timer font
            this.winner.setText("");
        }
        else if(evt.getSource() == butTarget){
            Random rand = new Random();
            this.projPanel.targetX = rand.nextInt(900-150)+150;
            this.projPanel.targetY = rand.nextInt(335-5) + 5;

        }

        else if (evt.getSource() == butPosition) {
            intStatPanel = 0;
            loadStatPanel();
        } else if (evt.getSource() == butVelocity) {
            intStatPanel = 1;
            loadStatPanel();
        }
        this.projPanel.repaint();
    }
    public void stateChanged(ChangeEvent evt) {
        if (evt.getSource() == this.sliderHeight) {
            dblHeight = sliderHeight.getValue();
            this.projPanel.intBallY = 340 - (int)(dblHeight);
            this.labelHeight.setText("Height (" + dblHeight + "m):");
        }
        if (evt.getSource() == this.sliderV1) {
            dblV1 = sliderV1.getValue();
            this.labelV1.setText("Initial Velocity (" + dblV1 + "m/s):");
        }
        if (evt.getSource() == this.sliderAngle) {
            dblAngle = this.sliderAngle.getValue();
            this.labelAngle.setText("Angle (" + dblAngle + "\u00B0):");
            if(dblHeight == 0 && dblAngle <= 0) {
                dblAngle = 1;
                this.sliderAngle.setValue(1);
            }
        }

    }

    public void createProjPanel() { // Method to create ProjPanel & its JComponents
        this.projPanel = new ProjPanel();
        this.projPanel.setPreferredSize(new Dimension(960, 360));
        this.projPanel.setLayout(null);


        // Height slider
        this.sliderHeight = new JSlider(JSlider.VERTICAL, 0, 250, 0);
        dblHeight = this.sliderHeight.getValue();
        this.labelHeight = new JLabel("Height (" + dblHeight + "m):", SwingConstants.CENTER); // Height label
        this.sliderHeight.setPaintTicks(true);
        this.sliderHeight.setPaintLabels(true);
        this.sliderHeight.setSnapToTicks(true);
        this.sliderHeight.setMinorTickSpacing(10);
        this.sliderHeight.setMajorTickSpacing(50);
        this.sliderHeight.addChangeListener(this);
        this.projPanel.add(this.labelHeight);
        this.projPanel.add(this.sliderHeight);
        this.labelHeight.setSize(86,30);
        this.labelHeight.setLocation(0,65);
        this.sliderHeight.setSize(50,260);
        this.sliderHeight.setLocation(15,94);

        // Projectile timer
        this.labelTimer = new JLabel("Timer:", SwingConstants.CENTER);
        this.fieldTimer = new JTextField("0.000s");
        this.fieldTimer.setFont(fieldTimer.getFont().deriveFont(Font.BOLD, 14f));
        this.fieldTimer.setEditable(false);
        this.projPanel.add(this.labelTimer);
        this.projPanel.add(this.fieldTimer);
        this.labelTimer.setSize(83,30);
        this.labelTimer.setLocation(0,1);
        this.fieldTimer.setSize(83,40);
        this.fieldTimer.setLocation(0,25);
    }
    public void createControlPanel() { // Method to create ControlPanel & its JComponents
        this.controlPanel = new ControlPanel();
        this.controlPanel.setPreferredSize(new Dimension(560,180));
        this.controlPanel.setLayout(null);

        // Initial Velocity Slider
        this.sliderV1 = new JSlider(0, 100, 40);
        dblV1 = this.sliderV1.getValue();
        this.labelV1 = new JLabel("Initial Velocity (" + dblV1 + "m/s):", SwingConstants.CENTER); // Initial velocity label, center aligned
        this.sliderV1.setPaintTicks(true);
        this.sliderV1.setPaintLabels(true);
        this.sliderV1.setMinorTickSpacing(10);
        this.sliderV1.setMajorTickSpacing(20);
        this.sliderV1.addChangeListener(this);
        this.controlPanel.add(this.labelV1);
        this.controlPanel.add(this.sliderV1);
        this.labelV1.setSize(170,15);
        this.labelV1.setLocation(40,10);
        this.sliderV1.setSize(250,50);
        this.sliderV1.setLocation(5,30);

        // Angle Slider
        this.sliderAngle = new JSlider(-90, 90, 45);
        dblAngle = this.sliderAngle.getValue();
        this.labelAngle = new JLabel("Angle (" + dblAngle + "\u00B0):", SwingConstants.CENTER); // Angle label, center aligned
        this.sliderAngle.setPaintTicks(true);
        this.sliderAngle.setPaintLabels(true);
        this.sliderAngle.setMinorTickSpacing(15);
        this.sliderAngle.setMajorTickSpacing(45);

        //this.sliderAngle.setSnapToTicks(true);
        this.sliderAngle.addChangeListener(this);
        this.controlPanel.add(this.labelAngle);
        this.controlPanel.add(this.sliderAngle);
        this.labelAngle.setSize(100,15);
        this.labelAngle.setLocation(83,105);
        this.sliderAngle.setSize(250,50);
        this.sliderAngle.setLocation(5,120);

        // Launch Button
        this.butLaunch = new JButton("Launch");
        this.butLaunch.addActionListener(this);
        this.controlPanel.add(this.butLaunch);
        this.butLaunch.setSize(100,30);
        this.butLaunch.setLocation(300,40);

        // Reset Button
        this.butReset = new JButton("Reset");
        this.butReset.addActionListener(this);
        this.controlPanel.add(this.butReset);
        this.butReset.setSize(100,30);
        this.butReset.setLocation(300,90);

        //New Target Button
        this.butTarget = new JButton("New Target");
        this.butTarget.addActionListener(this);
        this.controlPanel.add(this.butTarget);
        this.butTarget.setSize(100,30);
        this.butTarget.setLocation(300,140);

        winner = new JLabel("", SwingConstants.CENTER);
        this.controlPanel.add(winner);
        this.winner.setSize(128, 15);
        this.winner.setLocation(420, 50);
        this.controlPanel.repaint();

    }
    public void createStatPanel() { // Method to create StatPanel & its JComponents
        this.statPanel = new JPanel();
        this.statPanel.setPreferredSize(new Dimension(400,180));
        this.statPanel.setLayout(null);

        // Position Button
        this.butPosition = new JButton("Position");
        this.statPanel.add(this.butPosition);
        this.butPosition.addActionListener(this);
        this.butPosition.setSize(110,30);
        this.butPosition.setLocation(20,0);

        // Velocity Button
        this.butVelocity = new JButton("Velocity");
        this.statPanel.add(this.butVelocity);
        this.butVelocity.addActionListener(this);
        this.butVelocity.setSize(110,30);
        this.butVelocity.setLocation(135,0);

        this.txtStats = new JTextArea(10,40);
        this.txtStats.setEditable(false);
        this.statPanel.add(this.txtStats);
        this.txtStats.setSize(370,140);
        this.txtStats.setLocation(15,35);

        intStatPanel = 0;
        loadStatPanel();
    }
    public void loadStatPanel() { // Method to determine which stats to show in StatsPanel
        if (intStatPanel == 0) {
            loadStatPosition();
        } else if (intStatPanel == 1) {
            loadStatVelocity();
        }
    }
    public void loadStatPosition() { // Load Position Stats
        this.txtStats.setText("");
        try {
            this.txtStats.append("x: " + Math.floor(dblCurrentPos[0]*1000)/1000 + "m");
        } catch (NullPointerException e) {
            this.txtStats.append("x: 0.0m");
        }
        try {
            this.txtStats.append("\ny: " + Math.floor(dblCurrentPos[1]*1000)/1000 + "m");
        } catch (NullPointerException e) {
            this.txtStats.append("\ny: 0.0m");
        }
        this.txtStats.append("\n\nRange: " + Math.floor(Ball.dblDistX*1000)/1000 + "m");
        this.txtStats.append("\nMax Height: " + Math.floor(Ball.dblDistYMax*1000)/1000 + "m");
        this.txtStats.append("\n\nTime: " + Math.floor(Ball.dblTime*1000)/1000 + "s");

    }
    public void loadStatVelocity() { // Load Velocity Stats
        this.txtStats.setText("");
        this.txtStats.append("Magnitude of Velocity");
        this.txtStats.append("\n v: " + Math.floor(Ball.dblVel*10)/10 + "m/s");
        this.txtStats.append("\n\nComponents");
        this.txtStats.append("\n vx = " + Math.floor(Ball.dblVel1X*10)/10 + "m/s");
        this.txtStats.append("\n vy = " + Math.floor(Ball.dblVelY*10)/10 + "m/s");
        this.txtStats.append("\n Angle: " + Math.floor(Ball.dblCurrentAngle*10/10) + "\u00B0");
    }


    // CONSTRUCTOR
    public GUI() { // Construct GUI object
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());

        createProjPanel();
        createControlPanel();
        createStatPanel();

        // Create split panes
        this.splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPaneH.setLeftComponent(this.controlPanel);
        this.splitPaneH.setRightComponent(this.statPanel);

        this.splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.mainPanel.add(splitPaneV, BorderLayout.CENTER);
        this.splitPaneV.setLeftComponent(this.projPanel);
        this.splitPaneV.setRightComponent(this.splitPaneH);

        this.splitPaneH.setDividerSize(0);
        this.splitPaneV.setDividerSize(0);

        // JFrame
        this.theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.theframe.setContentPane(mainPanel);
        this.theframe.pack();
        this.theframe.setVisible(true);
        this.theframe.setResizable(true);
        this.theframe.setLocationRelativeTo(null);

        this.thetimer.start();
    }
}
