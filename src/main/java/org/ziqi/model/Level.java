package org.ziqi.model;

import javafx.scene.input.KeyEvent;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.Utils;
import org.ziqi.Debugger;
import org.ziqi.gameEngine.base.GameObject;
import org.ziqi.gameEngine.GameObjectFactory;
import org.ziqi.gameEngine.manager.DataManager;
import org.ziqi.view.GraphicUnit;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Level class represents a game level and contains all information for that game level. <br />
 *
 * @author Ziqi Yang-modified
 * @see org.ziqi.gameEngine.manager.LevelManager
 */
public final class Level implements Iterable<GameObject[]> {

    /**
     * List of strings as raw level lines read from resource file.
     * This is used to generate new level by decoding raw level or refresh the current level when user click restart button.
     *
     * @see #refreshLevel()
     */
    private final List<String> m_Raw_level;

    /**
     * Array of Layers storing layers(5 layers) of GameObject in the level, which are generated by decoding the raw level and calling GameObjectFactory.
     *
     * @see GameObjectFactory
     */
    private final Layer[] m_Layers;

    /**
     * The Player GameObject in the level.
     */
    private Player m_Player;

    /**
     * Integer specifying number of columns of the level.
     */
    private final int COLUMNS;

    /**
     * Integer specifying number of rows of the level.
     */
    private final int ROWS;

    /**
     * String value specifying the name of level.
     */
    private final String m_LevelName;

    /**
     * Integer specifying the index of the level.
     */
    private final int m_Index;

    /**
     * Integer specifying the number of diamonds in the level. This is used to check the completion of the level.
     *
     * @see #isLevelComplete()
     */
    private int m_NumberOfDiamonds = 0;


    /**
     * Level constructor initializes and stores the information of level from given parameters.
     * This constructor is called when LevelManager is loading a new set of levels, all levels in that set will be generated respectively.
     *
     * @param levelName  String value specifying the name of level.
     * @param levelIndex Integer specifying the index of the level.
     * @param raw_level  List of strings as raw level lines read from resource file.
     * @see #refreshLevel()
     * @see org.ziqi.gameEngine.manager.LevelManager
     * @see org.ziqi.gameEngine.manager.LevelManager#loadSet(InputStream, String)
     */
    public Level(String levelName, int levelIndex, List<String> raw_level) {
        // set map size - model
        m_Raw_level = raw_level;
        m_LevelName = levelName;
        m_Index = levelIndex;
        ROWS = raw_level.size();
        COLUMNS = raw_level.get(0).trim().length();
        m_Layers = new Layer[GraphicUnit.NUM_OF_LAYER];

        // initialize all layers
        refreshLevel();
    }

    /**
     * Gets array of all layers of GameObjects.
     *
     * @return Array of Layers storing layers(5 layers) of GameObject in the level is returned to caller.
     */
    public Layer[] getM_Layers() {
        return m_Layers;
    }

    /**
     * Gets Player GameObject in the current level.
     *
     * @return Player GameObject in the current level is returned to caller.
     */
    public Player getM_Player() {
        return m_Player;
    }

    /**
     * Gets the name of the current level.
     *
     * @return String value specifying the name of level is returned to caller.
     */
    public String getM_LevelName() {
        return m_LevelName;
    }

    /**
     * Gets the GameObject specified at the given position of the given layer.
     *
     * @param layer  Integer specifying the layer index.
     * @param source Point object specifying the position of GameObject.
     * @return The specified GameObject is returned; If the return value is null, meaning no GameObject exists at that position of that layer.
     */
    public GameObject getGameObjectAt(int layer, Point source) {
        return m_Layers[layer].getGameObjectAt(source);
    }

    /**
     * Gets the GameObject specified by the given position with a delta distance vector of the given layer.
     *
     * @param layer  Integer specifying the layer index.
     * @param source Point object specifying the position of GameObject.
     * @param delta  Point object specifying the delta vector to be translated.
     * @return The specified GameObject is returned; If the return value is null, meaning no GameObject exists at that position of that layer.
     */
    public GameObject getGameObjectBy(int layer, Point source, Point delta) {
        return getGameObjectAt(layer, Utils.translatePoint(source, delta));
    }

    /**
     * Generates/Resets the whole information of the current level: <br />
     * 1. Tells DataManagerReset to reset the move count of player; <br />
     * 2. Tells DataManagerReset to reset the timer; <br />
     * 3. Resets and generates all layers of GameObjects by decoding raw level list of strings; <br />
     * 
     * @see DataManager#resetMoveCount() 
     * @see DataManager#resetTime() 
     * @see org.ziqi.control.screenController.GamingScreenController#initialize(URL, ResourceBundle)
     */
    public void refreshLevel() {
        GameEngine.getInstance().getM_DataManager().resetMoveCount();
        GameEngine.getInstance().getM_DataManager().resetTime();
        m_NumberOfDiamonds = 0;
        for (int layer = 0; layer < GraphicUnit.NUM_OF_LAYER; layer++)
            m_Layers[layer] = new Layer(ROWS, COLUMNS);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                GameObject newObject = GameObjectFactory.getGamModelFromChar(m_Raw_level.get(row).charAt(col), new Point(row, col));
                Debugger.debugBegin(false, "Creating New game object: " + newObject.getClass().getName());

                if (newObject instanceof Wall)
                    m_Layers[GraphicUnit.LAYER_OF_WALL].putGameObjectAt(newObject, newObject.getM_Position());
                else {
                    m_Layers[GraphicUnit.LAYER_OF_FLOOR].putGameObjectAt(new Floor(newObject.getM_Position()), newObject.getM_Position());

                    if (newObject instanceof Player) {
                        m_Player = (Player) newObject;
                        m_Layers[GraphicUnit.LAYER_OF_PLAYER].putGameObjectAt(newObject, newObject.getM_Position());
                    }
                    if (newObject instanceof Crate)
                        m_Layers[GraphicUnit.LAYER_OF_CRATE].putGameObjectAt(newObject, newObject.getM_Position());

                    if (newObject instanceof Diamond) {
                        m_Layers[GraphicUnit.LAYER_OF_DIAMOND].putGameObjectAt(newObject, newObject.getM_Position());
                        m_NumberOfDiamonds++;
                    }
                }
                Debugger.debugBegin(false, "[ADDING LEVEL] LEVEL [" + m_Index + "]: " + m_LevelName);
                Debugger.debugBegin(false, "Row: " + ROWS + " Col: " + COLUMNS);
            }
        }
    }

    /**
     * Checks if the current level is completed by counting down all crates that hit diamonds. <br />
     * This method is called every time the PlayerController handling a valid user input.
     *
     * @return True is returned if all crates hit all diamonds; Otherwise, false is returned.
     * @see org.ziqi.control.PlayerController#handle(KeyEvent)
     */
    public boolean isLevelComplete() {
        int cratedDiamondsCount = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (m_Layers[GraphicUnit.LAYER_OF_DIAMOND].getGameObjectAt(new Point(row, col)) != null
                        && m_Layers[GraphicUnit.LAYER_OF_CRATE].getGameObjectAt(new Point(row, col)) != null) {
                    cratedDiamondsCount++;
                }
            }
        }
        Debugger.debugBegin(false, "cratedDiamondsCount: " + cratedDiamondsCount);
        Debugger.debugBegin(false, "m_NumberOfDiamonds: " + m_NumberOfDiamonds);
        return cratedDiamondsCount == m_NumberOfDiamonds;
    }

    /**
     * Override method to return LevelIterator.
     *
     * @return A LevelIterator is returned to caller.
     */
    @Override
    public Iterator<GameObject[]> iterator() {
        return new LevelIterator();
    }

    /**
     * LevelIterator class as iterator to iterate through the whole grips of layers.
     */
    public class LevelIterator implements Iterator<GameObject[]> {
        /**
         * Number of columns of the level.
         */
        int column = 0;
        /**
         * Number of rows of the level.
         */
        int row = 0;

        /**
         * Checks if there exists next entry of the level.
         * @return True is returned if the level grip is not out of bound; Otherwise, false is returned.
         */
        @Override
        public boolean hasNext() {
            return !(row == ROWS - 1 && column == COLUMNS);
        }

        /**
         * Gets next entry of levels contains layers of GameObject at the specific position.
         * @return An entry of layers of GameObjects is returned to caller.
         */
        @Override
        public GameObject[] next() {
            if (column == COLUMNS) {
                column = 0;
                row++;
            }
            GameObject[] gameObjects = new GameObject[GraphicUnit.NUM_OF_LAYER];
            for (int layer = 0; layer < GraphicUnit.NUM_OF_LAYER; layer++) {
                gameObjects[layer] = m_Layers[layer].getGameObjectAt(new Point(row, column));
                Debugger.debugEnd(false, "row: " + row + "col:" + column);
                if (gameObjects[layer] != null)
                    Debugger.debugEnd(false, gameObjects[layer].getClass().getName());
            }
            column++;
            return gameObjects;
        }
    }
}
