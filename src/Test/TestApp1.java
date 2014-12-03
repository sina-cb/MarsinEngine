package Test;

import Engine.Constraint.SpringConstraint;
import Engine.EngineStarter;
import Engine.Objects.CircularObject;
import Engine.Objects.Object2D;
import Engine.Objects.RectangularObject;
import Engine.Objects.WheelObject;
import Engine.Utilities.Vector2D;
import Engine.World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Sina Solaimanpour
 * Date: 10/18/11
 * Time: 10:48 PM
 */
public class TestApp1 extends Canvas implements KeyListener {

    JFrame frame = null;

    private World world = new World();

    WheelObject wheelObjectA;
    WheelObject wheelObjectB;

    File brick = new File("src\\images\\Brick.JPG");
    File wheel = new File("src\\images\\Wheel.PNG");
    File brickWheel = new File("src\\images\\BrickWheel.PNG");

    public TestApp1() {
        world.setScreenWidth(800);
        world.setScreenHeight(600);
        world.setPlayHeight(520);

        frame = new JFrame("MarsinEngine - By IUST Khafanzg Group");
        frame.setLayout(new BorderLayout(5, 5));

        setBounds(0, 0, World.getScreenWidth(), world.getScreenHeight());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(World.getScreenWidth(), world.getScreenHeight()));
        panel.setLayout(null);
        panel.add(this);

        frame.setBounds(0, 0, World.getScreenWidth() + 250, world.getScreenHeight());
        frame.setVisible(true);

        JScrollPane scrollPane = new JScrollPane();

        JPanel testPanel = new JPanel(new GridLayout(31, 1, 10, 10));
        createTestPanel(testPanel);

        scrollPane.getViewport().add(testPanel);
        scrollPane.setPreferredSize(new Dimension(220, getHeight()));

        frame.add(panel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setResizable(false);
        frame.setLocation(300, 100);
        createBufferStrategy(2);
        world.strategy = getBufferStrategy();
        requestFocus();
        addKeyListener(this);
    }

    /**
     * Create a testCollision bench for testing The Engine
     *
     * @param panel This is a container for controls we want
     */
    @SuppressWarnings("unchecked")
    private void createTestPanel(JPanel panel) {

        panel.setSize(200, getHeight());

        JPanel layoutPanel;

        final File[] image = {brick};

        JButton resetBtn = new JButton("Reset");
        JButton pauseBtn = new JButton("Pause");
        JButton addBtn = new JButton("Add Object");
        JButton addCarBtn = new JButton("Add Car");
        JButton frictionTestBtn = new JButton("Friction Test");
        JButton elasticityTestBtn = new JButton("Elasticity Test");
        JButton randomObjectsBtn = new JButton("Stress Test");
        JButton liquidBtn = new JButton("Liquid Sim");
        JButton circlesBtn = new JButton("Circles Test");
        JButton clearWorldBtn = new JButton("Clear World");
        JButton carTestBtn = new JButton("Car Test");
        final JButton browseForImageBtn = new JButton("Browse for Image");

        JLabel addLbl = new JLabel("Add New Object:");
        JLabel massLbl = new JLabel("Mass");
        JLabel lenLbl = new JLabel("Length");
        JLabel widLbl = new JLabel("Width");
        JLabel xPosLbl = new JLabel("X");
        JLabel yPosLbl = new JLabel("Y");
        JLabel xVelLbl = new JLabel("X Speed");
        JLabel yVelLbl = new JLabel("Y Speed");
        JLabel fiLbl = new JLabel("fi");
        JLabel elaLbl = new JLabel("Elasticity");
        JLabel frictionLbl = new JLabel("Friction");
        JLabel controlSimLbl = new JLabel("Control Simulation:");
        JLabel testCases = new JLabel("Test Cases");

        final JTextField massTxt = new JTextField();
        massTxt.setColumns(10);
        massTxt.setText("1");

        final JTextField lenTxt = new JTextField();
        lenTxt.setColumns(10);
        lenTxt.setText("30");

        final JTextField widTxt = new JTextField();
        widTxt.setColumns(10);
        widTxt.setText("30");

        final JTextField xPosTxt = new JTextField();
        xPosTxt.setColumns(10);
        xPosTxt.setText("100");

        final JTextField yPosTxt = new JTextField();
        yPosTxt.setColumns(10);
        yPosTxt.setText("100");

        final JTextField xVelTxt = new JTextField();
        xVelTxt.setColumns(10);
        xVelTxt.setText("0");

        final JTextField yVelTxt = new JTextField();
        yVelTxt.setColumns(10);
        yVelTxt.setText("0");

        final JTextField fiTxt = new JTextField();
        fiTxt.setColumns(10);
        fiTxt.setText(Math.PI / 4 + "");

        final JTextField elaTxt = new JTextField();
        elaTxt.setColumns(10);
        elaTxt.setText("0.01");

        final JTextField frictionTxt = new JTextField();
        frictionTxt.setColumns(10);
        frictionTxt.setText("0.1");

        final JCheckBox solidState = new JCheckBox("ifSolid?");
        final JCheckBox rndState = new JCheckBox("Random Generation");

        final JCheckBox hasImageState = new JCheckBox("Need Image");
        hasImageState.setSelected(false);
        browseForImageBtn.setEnabled(false);

        final JComboBox objType = new JComboBox();
        objType.addItem("Rectangular");
        objType.addItem("Circular");

        hasImageState.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                browseForImageBtn.setEnabled(hasImageState.isSelected());
            }
        });

        browseForImageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("src\\images\\");
                fileChooser.showDialog(frame, "Choose");

                image[0] = fileChooser.getSelectedFile();
            }
        });

        addCarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = Integer.parseInt(xPosTxt.getText());
                addCarMethod(x);
            }
        });

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String lengthStr = lenTxt.getText();
                String widthStr = widTxt.getText();
                String xPosStr = xPosTxt.getText();
                String yPosStr = yPosTxt.getText();
                String typeStr = (String) objType.getSelectedItem();
                String fiStr = fiTxt.getText();
                String xVelStr = xVelTxt.getText();
                String yVelStr = yVelTxt.getText();
                String massStr = massTxt.getText();
                String frictionStr = frictionTxt.getText();
                String elaStr = elaTxt.getText();

                boolean condition = lengthStr != null && lengthStr != "" && widthStr != null
                        && widthStr != "" && xPosStr != null && xPosStr != "" && yPosStr != null
                        && yPosStr != "" && fiStr != null && fiStr != "" && xVelStr != null && xVelStr != ""
                        && yVelStr != null && yVelStr != "" && massStr != null && massStr != ""
                        && elaStr != null && elaStr != "" && frictionStr != null
                        && frictionStr != "";


                if (condition) {

                    double length = Double.parseDouble(lengthStr);
                    double width = Double.parseDouble(widthStr);
                    double xPos = Double.parseDouble(xPosStr);
                    double yPos = Double.parseDouble(yPosStr);
                    double xVel = Double.parseDouble(xVelStr);
                    double yVel = Double.parseDouble(yVelStr);
                    double fi = Double.parseDouble(fiStr);
                    double mass = Double.parseDouble(massStr);
                    double friction = Double.parseDouble(frictionStr);
                    double elasticity = Double.parseDouble(elaStr);

                    Object2D obj;
                    if (typeStr.equals("Rectangular")) {
                        obj = new RectangularObject(xPos, yPos, width, length, fi, solidState.isSelected(), mass, elasticity, friction);
                    } else {
                        obj = new CircularObject(xPos, yPos, width / 2, solidState.isSelected(), mass, elasticity, friction);
                    }
                    obj.setVelocity(new Vector2D(xVel, yVel));

                    if (hasImageState.isSelected() && image != null) {
                        obj.setImage(image[0]);
                    }

                    World.addObject(obj);
                }

                if (rndState.isSelected()) {
                    Random rnd = new Random();
                    xPosTxt.setText((rnd.nextDouble() * world.getScreenWidth()) + "");
                    yPosTxt.setText((rnd.nextDouble() * world.getScreenHeight()) + "");
                    xVelTxt.setText((rnd.nextDouble() * 10) + "");
                    yVelTxt.setText((rnd.nextDouble() * 10) + "");
                    widTxt.setText((rnd.nextDouble() * 200) + "");
                    lenTxt.setText((rnd.nextDouble() * 200) + "");
                    fiTxt.setText((rnd.nextDouble() * 2) + "");
                    massTxt.setText((rnd.nextDouble() * 10) + "");
                    frictionTxt.setText((rnd.nextDouble() * 1) + "");
                    elaTxt.setText((rnd.nextDouble() * 1) + "");
                }
            }
        });

        rndState.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (rndState.isSelected()) {
                    Random rnd = new Random();
                    xPosTxt.setText((rnd.nextDouble() * world.getScreenWidth()) + "");
                    yPosTxt.setText((rnd.nextDouble() * world.getScreenHeight()) + "");
                    xVelTxt.setText((rnd.nextDouble() * 10) + "");
                    yVelTxt.setText((rnd.nextDouble() * 10) + "");
                    widTxt.setText((rnd.nextDouble() * 200) + "");
                    lenTxt.setText((rnd.nextDouble() * 200) + "");
                    fiTxt.setText((rnd.nextDouble() * 2) + "");
                    massTxt.setText((rnd.nextDouble() * 10) + "");
                    frictionTxt.setText((rnd.nextDouble() * 1) + "");
                    elaTxt.setText((rnd.nextDouble() * 1) + "");
                } else {
                    xPosTxt.setText("100");
                    yPosTxt.setText("100");
                    xVelTxt.setText("0");
                    yVelTxt.setText("0");
                    widTxt.setText("30");
                    lenTxt.setText("30");
                    fiTxt.setText(Math.PI / 4 + "");
                    massTxt.setText("1");
                    frictionTxt.setText("0.1");
                    elaTxt.setText("0.01");
                }
            }
        });

        resetBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                world.getClock().start();
            }
        });

        pauseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                world.getClock().stop();
            }
        });

        frictionTestBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearWorld();

                RectangularObject shelf = new RectangularObject(World.getScreenWidth() / 2 - 100, 100, World.getScreenWidth(), 20, 0, true, 1, 0.01, 0.01);
                World.addObject(shelf);

                shelf = new RectangularObject(World.getScreenWidth() / 2 - 100, 200, World.getScreenWidth(), 20, 0, true, 1, 0.01, 0.01);
                World.addObject(shelf);

                shelf = new RectangularObject(World.getScreenWidth() / 2 - 100, 300, World.getScreenWidth(), 20, 0, true, 1, 0.01, 0.01);
                World.addObject(shelf);

                shelf = new RectangularObject(World.getScreenWidth() / 2 - 100, 400, World.getScreenWidth(), 20, 0, true, 1, 0.01, 0.01);
                World.addObject(shelf);

                RectangularObject throwingObject = new RectangularObject(20, 75, 30, 30, 0, false, 1, 0.01, 0.01);
                throwingObject.setVelocity(new Vector2D(20, 0));
                World.addObject(throwingObject);

                throwingObject = new RectangularObject(30, 175, 30, 30, 0, false, 1, 0.01, 0.02);
                throwingObject.setVelocity(new Vector2D(20, 0));
                World.addObject(throwingObject);

                throwingObject = new RectangularObject(30, 275, 30, 30, 0, false, 1, 0.01, 0.03);
                throwingObject.setVelocity(new Vector2D(20, 0));
                World.addObject(throwingObject);

                throwingObject = new RectangularObject(30, 375, 30, 30, 0, false, 1, 0.01, 0.04);
                throwingObject.setVelocity(new Vector2D(20, 0));
                World.addObject(throwingObject);
            }
        });

        elasticityTestBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearWorld();

                CircularObject circle = new CircularObject(100, 100, 30, false, 1, 0.1, 0.1);
                World.addObject(circle);

                circle = new CircularObject(200, 100, 30, false, 1, 0.3, 0.1);
                World.addObject(circle);

                circle = new CircularObject(300, 100, 30, false, 1, 0.5, 0.1);
                World.addObject(circle);

                circle = new CircularObject(400, 100, 30, false, 1, 0.7, 0.1);
                World.addObject(circle);

                circle = new CircularObject(500, 100, 30, false, 1, 0.8, 0.1);
                World.addObject(circle);
            }
        });

        randomObjectsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearWorld();

                Random rand = new Random();
                int numberOfObjects = 150;

                for (int i = 0; i < numberOfObjects; i++) {
                    int choice = rand.nextInt(99999);
                    if (choice % 2 == 0) {
                        double xPos = rand.nextDouble() * World.getScreenWidth();
                        double yPos = rand.nextDouble() * world.getScreenHeight();
                        double xVel = rand.nextDouble() * 10;
                        double yVel = rand.nextDouble() * 10;
                        double wid = rand.nextDouble() * 80;
                        double len = rand.nextDouble() * 80;
                        double fi = rand.nextDouble() * 2;
                        double mass = rand.nextDouble() * 10;
                        double friction = rand.nextDouble() * 1;
                        double elasticity = rand.nextDouble() * 1;

                        RectangularObject rect = new RectangularObject(xPos, yPos, wid, len, fi, false, mass, elasticity, friction);
                        rect.setVelocity(new Vector2D(xVel, yVel));
                        World.addObject(rect);
                    } else {
                        double xPos = rand.nextDouble() * World.getScreenWidth();
                        double yPos = rand.nextDouble() * world.getScreenHeight();
                        double xVel = rand.nextDouble() * 10;
                        double yVel = rand.nextDouble() * 10;
                        double rad = rand.nextDouble() * 50;
                        double mass = rand.nextDouble() * 10;
                        double friction = rand.nextDouble() * 1;
                        double elasticity = rand.nextDouble() * 1;

                        CircularObject circle = new CircularObject(xPos, yPos, rad, false, mass, elasticity, friction);
                        circle.setVelocity(new Vector2D(xVel, yVel));
                        World.addObject(circle);
                    }
                }
            }
        });

        liquidBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearWorld();

                Random rand = new Random();
                for (int i = 0; i < 1000; i++) {
                    double xPos = rand.nextDouble() * World.getScreenWidth();
                    double yPos = rand.nextDouble() * world.getScreenHeight();
                    //RectangularObject rect = new RectangularObject(xPos, yPos, 5, 5, Math.PI / 4, false, 0.5, 0.05, 0.1);
                    CircularObject rect = new CircularObject(xPos, yPos, 2, false, 0.1, 0.5, 0.1);
                    rect.setVelocity(new Vector2D(0, 0));
                    World.addObject(rect);
                }
            }
        });

        circlesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearWorld();

                CircularObject circle = new CircularObject(400, 100, 30, false, 100, 0.5, 0.1);
                circle.setImage(wheel);
                World.addObject(circle);

                circle = new CircularObject(400, 200, 30, false, 100, 0.5, 0.1);
                circle.setImage(wheel);
                World.addObject(circle);

                circle = new CircularObject(400, 300, 30, false, 100, 0.5, 0.1);
                circle.setImage(wheel);
                World.addObject(circle);

                circle = new CircularObject(400, 400, 30, false, 100, 0.5, 0.1);
                circle.setImage(wheel);
                World.addObject(circle);

                circle = new CircularObject(400, 500, 30, false, 100, 0.5, 0.1);
                circle.setImage(wheel);
                World.addObject(circle);
            }
        });

        carTestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCarGame();
            }
        });

        clearWorldBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearWorld();
            }
        });

        panel.add(addLbl);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(massLbl);
        layoutPanel.add(massTxt);
        panel.add(layoutPanel);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(widLbl);
        layoutPanel.add(widTxt);
        panel.add(layoutPanel);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(lenLbl);
        layoutPanel.add(lenTxt);
        panel.add(layoutPanel);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(xPosLbl);
        layoutPanel.add(xPosTxt);
        panel.add(layoutPanel);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(yPosLbl);
        layoutPanel.add(yPosTxt);
        panel.add(layoutPanel);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(xVelLbl);
        layoutPanel.add(xVelTxt);
        panel.add(layoutPanel);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(yVelLbl);
        layoutPanel.add(yVelTxt);
        panel.add(layoutPanel);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(fiLbl);
        layoutPanel.add(fiTxt);
        panel.add(layoutPanel);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(elaLbl);
        layoutPanel.add(elaTxt);
        panel.add(layoutPanel);

        layoutPanel = new JPanel(new FlowLayout());
        layoutPanel.add(frictionLbl);
        layoutPanel.add(frictionTxt);
        panel.add(layoutPanel);

        panel.add(objType);
        panel.add(solidState);
        panel.add(rndState);

        panel.add(hasImageState);
        panel.add(browseForImageBtn);

        panel.add(addBtn);
        panel.add(addCarBtn);

        panel.add(controlSimLbl);

        panel.add(resetBtn);
        panel.add(pauseBtn);

        panel.add(testCases);
        panel.add(frictionTestBtn);
        panel.add(elasticityTestBtn);
        panel.add(randomObjectsBtn);
        panel.add(liquidBtn);
        panel.add(circlesBtn);
        panel.add(carTestBtn);
        panel.add(clearWorldBtn);

    }

    private void createCarGame() {
        clearWorld();

        //Bridge 1
        RectangularObject bridgeStart = new RectangularObject(80, 70, 150, 25, 0, true, 1, 0.3, 0);
        World.addObject(bridgeStart);

        RectangularObject bridgeEnd = new RectangularObject(380, 70, 100, 25, 0, true, 1, 0.3, 0);
        World.addObject(bridgeEnd);

        /*RectangularObject bridgeEndAngle = new RectangularObject(455, 102, 100, 25, 0.8, true, 1, 0.3, 0);
        World.addObject(bridgeEndAngle);*/

        CircularObject bridgePA = new CircularObject(200, 70, 4, false, 1, 0.3, 0);
        World.addObject(bridgePA);

        CircularObject bridgePB = new CircularObject(240, 70, 4, false, 1, 0.3, 0);
        World.addObject(bridgePB);

        CircularObject bridgePC = new CircularObject(280, 70, 4, false, 1, 0.3, 0);
        World.addObject(bridgePC);

        bridgePA.setImage(brickWheel);
        bridgePB.setImage(brickWheel);
        bridgePC.setImage(brickWheel);

        SpringConstraint bridgeConnA = new SpringConstraint((Object2D) bridgeStart.getCornerObjects().get(1), bridgePA, 0.9);
        bridgeConnA.setCollidable(true);
        bridgeConnA.setCollisionRectWidth(10);
        bridgeConnA.setCollisionRectScale(0.6);
        bridgeConnA.setImage(brick);
        World.addConstraint(bridgeConnA);

        SpringConstraint bridgeConnB = new SpringConstraint(bridgePA, bridgePB, 0.9);
        bridgeConnB.setCollidable(true);
        bridgeConnB.setCollisionRectWidth(10);
        bridgeConnB.setCollisionRectScale(0.6);
        bridgeConnB.setImage(brick);

        World.addConstraint(bridgeConnB);

        SpringConstraint bridgeConnC = new SpringConstraint(bridgePB, bridgePC, 0.9);
        bridgeConnC.setCollidable(true);
        bridgeConnC.setCollisionRectWidth(10);
        bridgeConnC.setCollisionRectScale(0.6);
        bridgeConnC.setImage(brick);
        World.addConstraint(bridgeConnC);

        SpringConstraint bridgeConnD = new SpringConstraint(bridgePC, (Object2D) bridgeEnd.getCornerObjects().get(0), 0.9);
        bridgeConnD.setCollidable(true);
        bridgeConnD.setCollisionRectWidth(10);
        bridgeConnD.setCollisionRectScale(0.6);
        bridgeConnD.setImage(brick);
        World.addConstraint(bridgeConnD);

        //Bridge B
        bridgeStart = new RectangularObject(750, 200, 150, 25, 0, true, 1, 0.3, 0);
        World.addObject(bridgeStart);

        bridgeEnd = new RectangularObject(380, 250, 100, 25, 0, true, 1, 0.3, 0);
        World.addObject(bridgeEnd);

        bridgePA = new CircularObject(510, 240, 4, false, 1, 0.3, 0);
        World.addObject(bridgePA);

        bridgePB = new CircularObject(560, 230, 4, false, 1, 0.3, 0);
        World.addObject(bridgePB);

        bridgePC = new CircularObject(610, 220, 4, false, 1, 0.3, 0);
        World.addObject(bridgePC);

        bridgePA.setImage(brickWheel);
        bridgePB.setImage(brickWheel);
        bridgePC.setImage(brickWheel);

        bridgeConnA = new SpringConstraint((Object2D) bridgeEnd.getCornerObjects().get(1), bridgePA, 0.9);
        bridgeConnA.setCollidable(true);
        bridgeConnA.setCollisionRectWidth(10);
        bridgeConnA.setCollisionRectScale(0.6);
        bridgeConnA.setImage(brick);
        World.addConstraint(bridgeConnA);

        bridgeConnB = new SpringConstraint(bridgePA, bridgePB, 0.9);
        bridgeConnB.setCollidable(true);
        bridgeConnB.setCollisionRectWidth(10);
        bridgeConnB.setCollisionRectScale(0.6);
        bridgeConnB.setImage(brick);

        World.addConstraint(bridgeConnB);

        bridgeConnC = new SpringConstraint(bridgePB, bridgePC, 0.9);
        bridgeConnC.setCollidable(true);
        bridgeConnC.setCollisionRectWidth(10);
        bridgeConnC.setCollisionRectScale(0.6);
        bridgeConnC.setImage(brick);
        World.addConstraint(bridgeConnC);

        bridgeConnD = new SpringConstraint(bridgePC, (Object2D) bridgeStart.getCornerObjects().get(0), 0.9);
        bridgeConnD.setCollidable(true);
        bridgeConnD.setCollisionRectWidth(10);
        bridgeConnD.setCollisionRectScale(0.6);
        bridgeConnD.setImage(brick);
        World.addConstraint(bridgeConnD);

        //Some Other Obstacles
        for (int i = 0; i < 20; i++) {
            RectangularObject temp = new RectangularObject(i * 30, 350, 20, 20, Math.PI / 4, true, 1, 0.4, 0.1);
            World.addObject(temp);
        }

        for (int i = 0; i < 10; i++) {
            RectangularObject temp = new RectangularObject(i * 35 + 500, 450, 20, 20, Math.PI / 4, true, 1, 0.4, 0.1);
            World.addObject(temp);
        }

        //Car
        wheelObjectA = new WheelObject(60, 10, 20, false, 1, 0.3, 0, 1);
        wheelObjectA.setMass(2);
        wheelObjectA.setImage(wheel);
        World.addObject(wheelObjectA);

        wheelObjectB = new WheelObject(140, 10, 20, false, 1, 0.3, 0, 1);
        wheelObjectB.setMass(2);
        wheelObjectB.setImage(wheel);
        World.addObject(wheelObjectB);

        SpringConstraint wheelConnector = new SpringConstraint(wheelObjectA, wheelObjectB, 0.5);
        wheelConnector.setCollidable(true);
        wheelConnector.setCollisionRectWidth((double) 10);
        wheelConnector.setCollisionRectScale((double) 0.9);
        World.addConstraint(wheelConnector);
    }

    private void addCarMethod(int baseX) {
        wheelObjectA = new WheelObject(baseX, 10, 20, false, 1, 0.3, 0, 1);
        wheelObjectA.setMass(2);
        wheelObjectA.setImage(wheel);
        World.addObject(wheelObjectA);

        wheelObjectB = new WheelObject(baseX + 100, 10, 20, false, 1, 0.3, 0, 1);
        wheelObjectB.setMass(2);
        wheelObjectB.setImage(wheel);
        World.addObject(wheelObjectB);

        SpringConstraint wheelConnector = new SpringConstraint(wheelObjectA, wheelObjectB, 0.5);
        wheelConnector.setCollidable(true);
        wheelConnector.setCollisionRectWidth((double) 10);
        wheelConnector.setCollisionRectScale((double) 0.9);
        World.addConstraint(wheelConnector);
    }

    private void clearWorld() {
        /*World.moveObjectsOutOfTheWorld();
        World.updateWorld();
        world.paintWorld();*/
        World.clearWorldObjects();

        world.createLeftWall(0.1, 0.5);
        world.createRightWall(0.1, 0.5);
        world.createUpWall(0.1, 0.5);
        world.createDownWall(0.1, 0.08);
    }

    public void initWorld() {
        World.init((double) 1 / 3);
        World.setCollisionResponseMode(World.SELECTIVE);
        World.setImpactResponseMode(World.OPTIMIZED);
        World.addMasslessForce(new Vector2D(0, 3));

        world.createLeftWall(0.01, 0.5);
        world.createRightWall(0.01, 0.5);
        world.createUpWall(0.01, 0.5);
        world.createDownWall(0.01, 0.08);

        RectangularObject support = new RectangularObject(0, World.getPlayHeight() + 50, 300, 200, Math.PI / 4, true, 1, 0.01, 0.2);
        support.setImage(brick);
        World.addObject(support);

        RectangularObject rotatingRect = new RectangularObject(525, 180, 70, 14, 0, true, 1, 0.3, 0);
        rotatingRect.setImage(brick);
        World.addObject(rotatingRect);
        World.addRotatingObject(rotatingRect, 0.03);
    }

    public static void main(String[] args) {
        TestApp1 inv = new TestApp1();
        inv.game();
    }

    private void game() {
        world.usedTime = 1000;
        initWorld();

        EngineStarter engineStarter = new EngineStarter(world);
        engineStarter.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        double keySpeed = 1.0;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            wheelObjectA.setAngularVelocity(-keySpeed);
            wheelObjectB.setAngularVelocity(-keySpeed);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            wheelObjectA.setAngularVelocity(keySpeed);
            wheelObjectB.setAngularVelocity(keySpeed);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            wheelObjectA.addForce(new Vector2D(0, 100));
            wheelObjectB.addForce(new Vector2D(0, -100));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        wheelObjectA.setAngularVelocity(0);
        wheelObjectB.setAngularVelocity(0);
    }

}
