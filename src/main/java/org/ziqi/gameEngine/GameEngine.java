package org.ziqi.gameEngine;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import org.ziqi.Debugger;
import org.ziqi.Utils;
import org.ziqi.gameEngine.base.GameScreen;
import org.ziqi.gameEngine.viewPlayer.ScreenPlayer;
import org.ziqi.gameEngine.viewPlayer.ViewPlayer;
import org.ziqi.gameEngine.manager.*;
import org.ziqi.view.popUp.QuitGamePopUp;
import org.ziqi.view.popUp.ScoreBoardPopUp;
import org.ziqi.view.screen.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * GameEngine singleton class to take charge of all sectors of the running game by dispatching jobs to its attached managers and screen player.
 * All tasks requested from other managers and classes will be received and reassigned by GameEngine class.
 *
 * @author Ziqi Yang-modified
 * @see EventManager
 * @see LevelManager
 * @see DataManager
 * @see MusicManager
 * @see ResourceManager
 * @see ScreenPlayer
 */
public class GameEngine {

    /**
     * Instance singleton of GameEngine object.
     */
    private static GameEngine instance; // singleton

    /**
     * Constant string value specifying the name of game.
     */
    public static final String GAME_NAME = "DANIEL'S SOKOBAN - PAOLUMU WITCH!";

    /**
     * EventManager object to be used by GameEngine.
     */
    private final EventManager m_EventManager = new EventManager();

    /**
     * LevelManager object to be used by GameEngine.
     */
    private final LevelManager m_LevelManager = new LevelManager();

    /**
     * DataManager object to be used by GameEngine.
     */
    private final DataManager m_DataManager = new DataManager();

    /**
     * MusicManager object to be used by GameEngine.
     */
    private final MusicManager m_MusicManager = new MusicManager();

    /**
     * ResourceManager object to be used by GameEngine.
     */
    private final ResourceManager m_ResourceManager = new ResourceManager();

    /**
     * ScreenPlayer object to be used by GameEngine.
     */
    private final ScreenPlayer m_ScreenPlayer = new ScreenPlayer();

    /**
     * Scene object that is currently showing and kept track by GameEngine.
     */
    private Scene m_GameScene;

    /**
     * Background image that is currently or will be used in the gaming screen.
     */
    private Image m_GamingBG;

    /**
     * This is a singleton getter to keep track of and return the unique instance of GameEngine class.
     *
     * @return  If there exists an instance of GameEngine class, then this object is returned;
     *          Otherwise, a new one is instantiated and returned by called its constructor.
     */
    public static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();
        return instance;
    }

    /**
     * Gets the ScreenPlayer object member variable of GameEngine instance.
     *
     * @return  The ScreenPlayer object member variable is returned to the caller.
     */
    public ScreenPlayer getM_ScreenPlayer() {
        return m_ScreenPlayer;
    }

    /**
     * Gets the LevelManager object member variable of GameEngine instance.
     *
     * @return  The LevelManager object member variable is returned to the caller.
     */
    public LevelManager getM_LevelManager() {
        return m_LevelManager;
    }

    /**
     * Gets the ResourceManager object member variable of GameEngine instance.
     *
     * @return  The ResourceManager object member variable is returned to the caller.
     */
    public ResourceManager getM_ResourceManager() {
        return m_ResourceManager;
    }

    /**
     * Gets the DataManager object member variable of GameEngine instance.
     *
     * @return  The DataManager object member variable is returned to the caller.
     */
    public DataManager getM_DataManager() {
        return m_DataManager;
    }

    /**
     * Gets the MusicManager object member variable of GameEngine instance.
     *
     * @return  The MusicManager object member variable is returned to the caller.
     */
    public MusicManager getM_MusicManager() {
        return m_MusicManager;
    }

    /**
     * Gets the EventManager object member variable of GameEngine instance.
     *
     * @return  The EventManager object member variable is returned to the caller.
     */
    public EventManager getM_EventManager() {
        return m_EventManager;
    }

    /**
     * Gets the scene that is currently showed in the stage of ScreenPlayer.
     *
     * @return  The scene object, currently showing and kept track by GameEngine, is returned.
     * @see     ScreenPlayer
     */
    public Scene getM_GameScene() {
        return m_GameScene;
    }

    /**
     * Gets the background image that is currently or will be used in the gaming screen.
     *
     * @return  A image as background image of gaming screen is returned.
     * @see Utils#SET_BG_NAMES
     */
    public Image getM_GamingBG() {
        return m_GamingBG;
    }

    /**
     * Sets the background image that will be used in the coming gaming screen after choosing set.
     *
     * @param  m_GamingBG  Image object that will be used in the coming gaming screen after choosing set.
     * @see     org.ziqi.control.screenController.LevelSetScreenController#initialize(URL, ResourceBundle)
     */
    public void setM_GamingBG(Image m_GamingBG) {
        this.m_GamingBG = m_GamingBG;
    }

    /**
     * Assigns tasks to GameManagers and ScreenPlayer to present start screen for the user. <br />
     * When called, MusicManager will be called to play start screen background music; <br />
     * ScenePlayer will be called to initialize start screen;
     * GameEngine will keep track of the scene that is displayed by ScreenPlayer;
     *
     * @see MusicManager#playBGM() 
     * @see ScreenPlayer#initScreen(GameScreen, String)
     * @see StartScreen
     */
    public void toStartScreen() {
        m_MusicManager.playBGM();
        m_ScreenPlayer.initScreen(new StartScreen(), ViewPlayer.SCREEN);
        m_GameScene = m_ScreenPlayer.getScene();
    }

    /**
     * Assigns tasks to GameManagers and ScreenPlayer to present gaming screen for the user. <br />
     * When called, MusicManager will be called to play gaming screen background music; <br />
     * ScenePlayer will be called to initialize gaming screen; <br />
     * GameEngine will keep track of the scene that is displayed by ScreenPlayer; <br />
     * EventManager will subscribe PlayerController for the gaming scene;
     *
     * @see MusicManager#playGamingMusic()
     * @see ScreenPlayer#initScreen(GameScreen, String)
     * @see EventManager#subscribe()
     * @see org.ziqi.control.PlayerController
     * @see GamingScreen
     */
    public void toGamingScreen() {
        m_MusicManager.playGamingMusic();
        Debugger.debugBegin(true, "Start rendering level view...");
        m_ScreenPlayer.initScreen(new GamingScreen(), ViewPlayer.SCREEN); // initialize and render level
        m_GameScene = m_ScreenPlayer.getScene();  // set current game scene as the scene of new level view
        Debugger.debugEnd(true, "Render completed!");
        m_EventManager.subscribe();   // refresh and re-subscribe the event handler for this scene
        Debugger.debugEnd(true, "Re-registration player controller events done.");
    }

    /**
     * Assigns tasks to GameManagers and ScreenPlayer to present level selecting screen for the user. <br />
     * When called, MusicManager will be called to play background music for level selecting screen ; <br />
     * ScenePlayer will be called to initialize level selecting screen; <br />
     * GameEngine will keep track of the scene that is displayed by ScreenPlayer;
     *
     * @see MusicManager#playSetChoosingMusic()
     * @see ScreenPlayer#initScreen(GameScreen, String)
     * @see LevelSetScreen
     */
    public void toLevelSetScreen() {
        m_MusicManager.playSetChoosingMusic();
        m_ScreenPlayer.initScreen(new LevelSetScreen(), ViewPlayer.SCREEN);
        m_GameScene = m_ScreenPlayer.getScene();
    }

    /**
     * Assigns tasks to GameManagers and ScreenPlayer to present score board screen for the user. <br />
     * When called, MusicManager will be called to play background music for score board screen; <br />
     * ScenePlayer will be called to initialize score board screen; <br />
     * GameEngine will keep track of the scene that is displayed by ScreenPlayer;
     *
     * @see MusicManager#playScoreBoardMusic()
     * @see ScreenPlayer#initScreen(GameScreen, String)
     * @see ScoreBoardScreen
     */
    public void toScoreBoardScreen() {
        m_MusicManager.playScoreBoardMusic();
        m_ScreenPlayer.initScreen(new ScoreBoardScreen(), ViewPlayer.SCREEN);
        m_GameScene = m_ScreenPlayer.getScene();
    }

    /**
     * Assigns tasks to GameManagers and ScreenPlayer to present victory screen for the user. <br />
     * This method will only be called when a user completes a whole set of levels. <br />
     * When called, MusicManager will be called to play background music for victory screen; <br />
     * ScenePlayer will be called to initialize victory screen; <br />
     * GameEngine will keep track of the scene that is displayed by ScreenPlayer;
     *
     * @see MusicManager#playVictoryScreenMusic()
     * @see ScreenPlayer#initScreen(GameScreen, String)
     * @see VictoryScreen
     */
    public void toVictoryScreen() {
        m_MusicManager.playVictoryScreenMusic();
        m_ScreenPlayer.initScreen(new VictoryScreen(), ViewPlayer.SCREEN);
        m_GameScene = m_ScreenPlayer.getScene();
    }

    /**
     * Assigns tasks to GameManagers and ScreenPlayer to pop up a new screen as score board for the user. <br />
     * This method will be called when a user completes a level. <br />
     * When called, MusicManager will be called to play background music for score board pop up screen; <br />
     * ScenePlayer will be called to initialize score board screen;
     *
     * @see MusicManager#playCompletionMusic()
     * @see ScreenPlayer#initScreen(GameScreen, String)
     * @see ScoreBoardPopUp
     */
    public void popUpScoreBoard() {
        m_MusicManager.playCompletionMusic();
        m_ScreenPlayer.initScreen(new ScoreBoardPopUp(), ViewPlayer.POP_UP_SCREEN);
    }

    /**
     * Assigns a task to ScreenPlayer to pop up a new screen as alert window for the user. <br />
     * This method will be called when a user intends to exit the program. <br />
     * When called, ScenePlayer will be called to initialize the alert window;
     *
     * @see ScreenPlayer#initScreen(GameScreen, String)
     * @see QuitGamePopUp
     */
    public void quit() {
        m_ScreenPlayer.initScreen(new QuitGamePopUp(), ViewPlayer.POP_UP_SCREEN);
    }

    /**
     * Assigns a task to ScreenPlayer to update the control panel and gaming view of GamingScreen. <br />
     * This method will be called when a user is playing the game(gaming screen is showing) and inputs a key that is handled; <br />
     * When called, ScenePlayer will be called to update its scene by calling its gaming screen controller;
     *
     * @see ScreenPlayer#initScreen(GameScreen, String)
     * @see GamingScreen
     * @see org.ziqi.control.screenController.GamingScreenController
     */
    public void updateGamingScreen() {
        m_ScreenPlayer.updateGamingScreen();
    }

}