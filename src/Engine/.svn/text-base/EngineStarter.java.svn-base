package Engine;

import Engine.World.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Sina Solaimanpour
 * Date: 10/20/11
 * Time: 12:09 AM
 */
public class EngineStarter {

    Timer timer = null;
    public World world;
    int initialDelay = 1000;

    /**
     * This Will Create an EngineStarter Object For Staring The Simulation With Help of a Timer.
     *
     * @param world The Engine's World We Want to Start The Simulation in
     */
    public EngineStarter(World world) {
        /*world.setClock(timer);*/
        this.world = world;
    }

    /**
     * Class For Updating The Engine's World.
     */
    private class UpdateWorld implements ActionListener {

        /**
         * This Method Will Be Called Each Time The Timer Ticks.
         *
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            World.step();
            long startTime = System.currentTimeMillis();
            World.updateWorld();
            world.paintWorld();
            world.usedTime = System.currentTimeMillis() - startTime;
        }
    }

    /**
     * This is The Engine Start Point.
     * This Method Will Start The Whole Simulation and Handles The Timer Object and FrameRate Related Stuff.
     */
    public void start() {
        timer = new Timer(world.getSpeed(), new UpdateWorld());
        world.setClock(timer);
        timer.setInitialDelay(initialDelay);
        timer.start();
    }

    /**
     * @return Worlds Initial Delay
     */

    public int getInitialDelay() {
        return initialDelay;
    }

    /**
     * @param initialDelay Sets The Worlds Initial Delay Before Simulation Starts
     */
    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }
}
