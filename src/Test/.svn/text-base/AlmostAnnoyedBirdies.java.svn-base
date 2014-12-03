package Test;

import Engine.Constraint.SpringConstraint;
import Engine.EngineStarter;
import Engine.Events.CollisionEvent;
import Engine.Events.CollisionListener;
import Engine.Objects.*;
import Engine.Utilities.Vector2D;
import Engine.World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 2/2/12
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlmostAnnoyedBirdies extends Canvas implements MouseListener, MouseMotionListener, CollisionListener {

    JFrame frame = null;
    JPanel panel = null;

    Random rand = new Random(Calendar.getInstance().getTimeInMillis());
    World world = new World();

    File brickImage = new File("src\\images\\Brick.JPG");
    File birdieAngryImage = new File("src\\images\\BirdieAngry.PNG");
    File birdieCalmImage = new File("src\\images\\BirdieCalm.PNG");
    File weaponImage = new File("src\\images\\Weapon.PNG");
    File groundImage = new File("src\\images\\Ground.PNG");
    File ballImage = new File("src\\images\\BallImage.PNG");
    File pigNormalImage = new File("src\\images\\PigNormal.PNG");
    File pigKingImage = new File("src\\images\\PigKing.PNG");
    File backgroundImage = new File("src\\images\\BG.JPG");
    File iceBrickImage = new File("src\\images\\IceBrick.PNG");

    final static int MENU = 0;
    final static int LEVEL_SELECT = 1;
    final static int GAME = 2;
    int gameState = MENU;

    RectangularObject weaponBase;
    CircularObject canon;

    ArrayList<RectangularObject> menu = new ArrayList<RectangularObject>();

    public AlmostAnnoyedBirdies() {
        int osScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int osScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        world.setScreenHeight(osScreenHeight);
        world.setScreenWidth(osScreenWidth);
        world.setPlayHeight(osScreenHeight - 80);
        world.setBackground(backgroundImage);

        frame = new JFrame("Almost Annoyed Birdies - By IUST Khafanzg Group");
        frame.setLayout(null);

        frame.setUndecorated(true);

        setBounds(0, 0, World.getScreenWidth(), World.getScreenHeight());

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(World.getScreenWidth(), World.getScreenHeight()));
        panel.setSize(World.getScreenWidth(), World.getScreenHeight());
        panel.setLayout(null);
        panel.add(this);

        frame.setBounds(0, 0, World.getScreenWidth() + 250, World.getScreenHeight());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(panel);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setResizable(false);
        createBufferStrategy(2);
        world.strategy = getBufferStrategy();
        requestFocus();
        addMouseListener(this);
        addMouseMotionListener(this);
        World.addCollisionListener(this);
    }

    public static void main(String[] args) {
        AlmostAnnoyedBirdies inv = new AlmostAnnoyedBirdies();
        inv.game();
    }

    private void game() {
        world.usedTime = 1000;
        initWorld();

        EngineStarter engineStarter = new EngineStarter(world);
        engineStarter.start();

        createTheMenu();

        while (gameState == MENU) {
            for (int i = 0; i < 20; i++) {
                int x = (int) (rand.nextDouble() * World.getScreenWidth());
                int y = (int) (rand.nextDouble() * world.getScreenHeight());
                int rad = ((int) (rand.nextDouble() * 20)) + 10;

                CircularObject obj = new CircularObject(x, y - world.getScreenHeight(), rad, false, 1, 0.1, 0.01);
                obj.setImage(ballImage);
                obj.setBirthTime(Calendar.getInstance().getTimeInMillis());
                obj.setType(ObjectTypes.TEMP);
                World.addObject(obj);
            }

            for (Object toBeTested : World.getObjects()) {
                if (((Object2D) toBeTested).getType().equals(ObjectTypes.TEMP) && Calendar.getInstance().getTimeInMillis() - ((Object2D) toBeTested).getBirthTime() > 4000) {
                    World.removeObject((Object2D) toBeTested);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        createLevelSelect();

        while (gameState == LEVEL_SELECT) {

            for (int i = 0; i < 20; i++) {
                int x = (int) (rand.nextDouble() * World.getScreenWidth());
                int y = (int) (rand.nextDouble() * World.getScreenHeight());
                int rad = ((int) (rand.nextDouble() * 20)) + 10;

                CircularObject obj = new CircularObject(x, y - World.getScreenHeight(), rad, false, 1, 0.1, 0.01);
                obj.setImage(ballImage);
                obj.setBirthTime(Calendar.getInstance().getTimeInMillis());
                obj.setType(ObjectTypes.TEMP);
                World.addObject(obj);
            }

            for (Object toBeTested : World.getObjects()) {
                if (((Object2D) toBeTested).getType().equals(ObjectTypes.TEMP) && Calendar.getInstance().getTimeInMillis() - ((Object2D) toBeTested).getBirthTime() > 4000) {
                    World.removeObject((Object2D) toBeTested);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        //createLevelOne();

    }

    public void initWorld() {
        World.init((double) 1 / 3);
        World.setCollisionResponseMode(World.SELECTIVE);
        World.setImpactResponseMode(World.OPTIMIZED);
        World.addMasslessForce(new Vector2D(0, 3));
    }

    private void createLevelSelect() {
        MenuItemObject menuItem = new MenuItemObject(World.getScreenWidth() / 2 - 225, world.getScreenHeight() / 2, 100, 40, 0, true, 1, 0.5, 0.5);
        menuItem.setType(ObjectTypes.MENU);
        menuItem.setText("Level 1");
        World.addObject(menuItem);
        menu.add(menuItem);

        menuItem = new MenuItemObject(World.getScreenWidth() / 2 - 75, world.getScreenHeight() / 2, 100, 40, 0, true, 1, 0.5, 0.5);
        menuItem.setType(ObjectTypes.MENU);
        menuItem.setText("Level 2");
        World.addObject(menuItem);
        menu.add(menuItem);

        menuItem = new MenuItemObject(World.getScreenWidth() / 2 + 75, world.getScreenHeight() / 2, 100, 40, 0, true, 1, 0.5, 0.5);
        menuItem.setType(ObjectTypes.MENU);
        menuItem.setText("Level 3");
        World.addObject(menuItem);
        menu.add(menuItem);

        menuItem = new MenuItemObject(World.getScreenWidth() / 2 + 225, world.getScreenHeight() / 2, 100, 40, 0, true, 1, 0.5, 0.5);
        menuItem.setType(ObjectTypes.MENU);
        menuItem.setText("Level 4");
        World.addObject(menuItem);
        menu.add(menuItem);
    }

    public void createTheMenu() {
        MenuItemObject menuItem = new MenuItemObject(World.getScreenWidth() / 2 - 75, world.getScreenHeight() / 2, 100, 40, 0, true, 1, 0.5, 0.5);
        menuItem.setType(ObjectTypes.MENU);
        menuItem.setText("Play");
        World.addObject(menuItem);
        menu.add(menuItem);

        menuItem = new MenuItemObject(World.getScreenWidth() / 2 + 75, world.getScreenHeight() / 2, 100, 40, 0, true, 1, 0.5, 0.5);
        menuItem.setType(ObjectTypes.MENU);
        menuItem.setText("Exit");
        World.addObject(menuItem);
        menu.add(menuItem);
    }

    private void createLevelOne() {
        gameState = GAME;
        clearWorld();

        MenuItemObject menuItem = new MenuItemObject(World.getScreenWidth() - 60, 30, 100, 40, 0, true, 1, 0.1, 0.5);
        menuItem.setText("Exit");
        menuItem.setType(ObjectTypes.MENU);
        World.addObject(menuItem);

        menuItem = new MenuItemObject(World.getScreenWidth() - 160, 30, 100, 40, 0, true, 1, 0.1, 0.5);
        menuItem.setText("Retry Level 1");
        menuItem.setType(ObjectTypes.MENU);
        World.addObject(menuItem);

        int rows = 6;
        int wid = 20;

        for (int k = 0; k < rows + 1; k++) {
            for (int j = rows - k; j >= 1; j--) {
                //These 100 and 60 numbers are just for calibrating with the world!!!
                int x = World.getScreenWidth() - j * (wid + 2) - 180 - ((wid / 2) * k);
                int y = World.getScreenHeight() - k * (wid + 2) - 60;

                RectangularObject temp = new RectangularObject(x, y, wid, wid, 0, false, 0.1, 0.2, 0.5);
                /*temp.setType(ObjectTypes.DESTROY);*/
                temp.setImage(iceBrickImage);
                World.addObject(temp);
            }
        }

        weaponBase = new RectangularObject(200, World.getScreenHeight() - 128, 20, 200, 0, true, 1, 0.1, 0.5);
        weaponBase.setCollidable(true);
        weaponBase.setImage(weaponImage);
        World.addObject(weaponBase);

        canon = new CircularObject(180, World.getScreenHeight() - 200, 10, false, 4, 0.6, 0.5);
        canon.setType(ObjectTypes.DRAGGABLE);
        canon.setImage(birdieCalmImage);
        World.addObject(canon);

        SpringConstraint spring = new SpringConstraint(canon, (Object2D) weaponBase.getCornerObjects().get(0), 0.3);
        spring.setCollidable(true);
        spring.setCollisionRectWidth(1);
        spring.getCollisionRect().setVisible(true);
        spring.setCollisionRectScale(0.6);
        World.addConstraint(spring);

        CircularObject pigNormal = new CircularObject(World.getScreenWidth() - 160, World.getScreenHeight() - 60, 30, false, 10, 0.1, 0.1);
        pigNormal.setImage(pigNormalImage);
        pigNormal.setType(ObjectTypes.DESTROY);
        World.addObject(pigNormal);
    }

    private void createLevelTwo() {
        //gameState = GAME;
        JOptionPane.showMessageDialog(null, "Level 2 Coming Soon!!!");
    }

    private void createLevelThree() {
        //gameState = GAME;
        JOptionPane.showMessageDialog(null, "Level 3 Coming Soon!!!");
    }

    private void createLevelFour() {
        //gameState = GAME;
        JOptionPane.showMessageDialog(null, "Level 4 Coming Soon!!!");
    }

    private void clearWorld() {
        World.clearWorldObjects();
        world.createLeftWall(0.1, 0.5);
        world.createRightWall(0.1, 0.5);
        world.createUpWall(0.1, 0.5);
        world.createDownWall(0.1, 0.08, groundImage);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        for (Object obj : World.getObjects()) {
            if (((Object2D) obj).getType().equals(ObjectTypes.MENU)) {
                double xPos = ((RectangularObject) obj).getPosition().x;
                double yPos = ((RectangularObject) obj).getPosition().y;

                boolean cond = Math.abs(xPos - x) < (Double) (((RectangularObject) obj).getExtents().get(0)) &&
                        Math.abs(yPos - y) < (Double) (((RectangularObject) obj).getExtents().get(1));

                if (cond) {
                    if (((MenuItemObject) obj).getText().equals("Exit")) {
                        System.exit(0);
                    } else if (((MenuItemObject) obj).getText().equals("Play")) {
                        gameState = LEVEL_SELECT;
                        menu = new ArrayList<RectangularObject>();
                        World.clearWorldObjects();
                    } else if (((MenuItemObject) obj).getText().equals("Level 1")) {
                        createLevelOne();
                    } else if (((MenuItemObject) obj).getText().equals("Level 2")) {
                        //World.clearWorldObjects();
                        createLevelTwo();
                    } else if (((MenuItemObject) obj).getText().equals("Level 3")) {
                        //World.clearWorldObjects();
                        createLevelThree();
                    } else if (((MenuItemObject) obj).getText().equals("Level 4")) {
                        //World.clearWorldObjects();
                        createLevelFour();
                    } else if (((MenuItemObject) obj).getText().equals("Retry Level 1")) {
                        createLevelOne();
                    }
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (Object obj : World.getObjects()) {
            if (((Object2D) obj).getType().equals(ObjectTypes.DRAGGABLE)) {
                double xPos = ((CircularObject) obj).getPosition().x;
                double yPos = ((CircularObject) obj).getPosition().y;
                double radius = ((CircularObject) obj).getRadius();

                boolean cond = Math.sqrt(Math.pow(xPos - x, 2) + Math.pow(yPos - y, 2)) < radius;

                if (cond) {
                    ((Object2D) obj).setIfBeingDragged(true);
                }

                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }*/
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (canon != null && canon.isIfBeingDragged()) {
            canon.setIfBeingDragged(false);

            int x = e.getX();
            int y = e.getY();

            double baseX = 0;
            double baseY = 0;

            baseX = ((CircularObject) (weaponBase).getCornerObjects().get(0)).getPosition().x;
            baseY = ((CircularObject) (weaponBase).getCornerObjects().get(0)).getPosition().y;

            double distance = Math.sqrt(Math.pow(x - baseX, 2) + Math.pow(y - baseY, 2));

            Vector2D newSpeed = new Vector2D(baseX - x, baseY - y);
            newSpeed = newSpeed.normalize();
            newSpeed = newSpeed.mult(distance / 5);

            canon.setVelocity(newSpeed);
            canon.setImage(birdieAngryImage);

            for (Object obj2 : World.getAllConstraints()) {
                if (((SpringConstraint) obj2).getP1().equals(weaponBase.getCornerObjects().get(0))) {
                    World.removeConstraint((SpringConstraint) obj2);
                    World.removeObject(weaponBase);
                    break;
                } else if (((SpringConstraint) obj2).getP2().equals(weaponBase.getCornerObjects().get(0))) {
                    World.removeConstraint((SpringConstraint) obj2);
                    World.removeObject(weaponBase);
                    break;
                }
            }

        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (Object obj : World.getObjects()) {
            if (((Object2D) obj).getType().equals(ObjectTypes.DRAGGABLE) && ((Object2D) obj).isIfBeingDragged()) {
                ((Object2D) obj).setPositionX(x);
                ((Object2D) obj).setPositionY(y);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void collisionOccurred(CollisionEvent e) {
        if (((Object2D) e.getSource()).getType().equals(ObjectTypes.DESTROY) && ((Object2D) e.getOther()).getType().equals(ObjectTypes.DESTROY)) {
            return;
        }

        boolean firstCond = ((Object2D) e.getSource()).getType().equals(ObjectTypes.DESTROY) && !((Object2D) e.getOther()).getType().equals(ObjectTypes.GROUND); /*&& ((Object2D) e.getOther()).getType().equals(ObjectTypes.DRAGGABLE)*/
        boolean secondCond = ((Object2D) e.getOther()).getType().equals(ObjectTypes.DESTROY) && !((Object2D) e.getSource()).getType().equals(ObjectTypes.GROUND); /*&& ((Object2D) e.getSource()).getType().equals(ObjectTypes.DRAGGABLE)*/

        if (firstCond) {
            if (((Object2D) e.getOther()).getVelocity().magnitude() >= 3) {
                World.removeObject((Object2D) e.getSource());
            }
        } else if (secondCond) {
            if (((Object2D) e.getSource()).getVelocity().magnitude() >= 3) {
                World.removeObject((Object2D) e.getOther());
            }
        }
    }
}
