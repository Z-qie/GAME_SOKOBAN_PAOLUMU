package org.ziqi;

import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.ziqi.control.PlayerController;
import org.ziqi.gameEngine.GameEngine;

/**
 * Main class for the program to run. This class is called to launch(initialize) GameEngine and therefore start the game.
 *
 * @see GameEngine
 * @author Ziqi Yang-modified
 */
public class Main extends Application {

    /**
     * This override method is the main entry point for JavaFX applications. <br/>
     * When start() method is called, it will: <br/>
     * 1. launch(initialize) the GameEngine class to build game environment; <br/>
     * 2. call MusicManager to load media from resource folder; <br/>
     * 3. call EventManager to register input handler; <br/>
     * 4. call GameEngine to present start screen for user.
     *
     * @param  primaryStage  This Stage object represents the primary window of JavaFX application.
     * @see    GameEngine
     * @see    org.ziqi.gameEngine.manager.MusicManager
     * @see    org.ziqi.gameEngine.manager.EventManager
     */
    @Override
    public void start(Stage primaryStage) {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.getM_MusicManager().attachMediaPlayers();
        gameEngine.getM_EventManager().register(new PlayerController(), KeyEvent.ANY);
        gameEngine.toStartScreen();
    }

    /**
     * Main method to be called by java program launcher to run the program.
     *
     * @param  args  Contains the supplied command-line arguments as an array of String objects.
     */
    public static void main(String[] args) {
        Debugger.debugBegin(true, "Launching Engine...");
        launch(args);
        Debugger.debugEnd(true, "End of Program");
    }
}
