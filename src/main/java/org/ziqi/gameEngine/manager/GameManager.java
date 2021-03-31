package org.ziqi.gameEngine.manager;

import org.ziqi.gameEngine.Logger;

import java.io.IOException;

/**
 * GameManager super class to be extended by all children managers. <br />
 * This class possesses a logger to keep recording error messages for the manager when instantiated by its constructor.
 *
 * @author Ziqi Yang
 * @see Logger
 * @see GameManager#m_Logger
 * @see GameManager#GameManager()
 */
public class GameManager {

    /**
     * Logger object of every managers extending GameManager to keep track of error messages.
     *
     * @see Logger
     */
    protected Logger m_Logger;

    /**
     * GameManager constructor as super constructor to be called when any other manager is instantiated. <br />
     * This constructor will initialize the m_Logger to keep recording error messages for the manager when instantiated by its constructor.
     *
     * @see Logger
     * @see GameManager#m_Logger
     */
    public GameManager() {
       try {
           m_Logger = new Logger(getClass().getSimpleName());
       } catch (IOException e) {
           System.out.println("Cannot create logger.");
           e.printStackTrace();
       }
    }
}
