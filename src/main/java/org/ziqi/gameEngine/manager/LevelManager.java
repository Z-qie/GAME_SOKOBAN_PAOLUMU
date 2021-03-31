package org.ziqi.gameEngine.manager;

import org.ziqi.gameEngine.GameEngine;
import org.ziqi.Debugger;
import org.ziqi.Utils;
import org.ziqi.model.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * LevelManager class as game manager to take charge of loading, arranging levels and sets of levels for user to choose and play.
 *
 * @author Ziqi Yang
 * @see GameManager
 */
public class LevelManager extends GameManager {
    /**
     * Set as List of levels specifying the current set the user is playing.
     */
    private List<Level> m_CurrentSet;

    /**
     * String value specifying the name of current set the user is playing.
     *
     * @see Utils#SET_NAMES
     */
    private String m_CurrentSetName;

    /**
     * Level object specifying the current level the user is playing.
     */
    private Level m_CurrentLevel;

    /**
     * Integer specifying the index of current level the user is playing.
     *
     * @see Level
     */
    private int m_CurrentLevelIndex = 0;


    /**
     * Gets the the name of current set the user is playing.
     *
     * @return  String value specifying the name of current set the user is playing is returned to caller.
     */
    public String getM_CurrentSetName() {
        return m_CurrentSetName;
    }

    /**
     * Gets the current level the user is playing.
     *
     * @return  Level object specifying the current level the user is playing is returned to caller.
     */
    public Level getM_CurrentLevel() {
        return m_CurrentLevel;
    }

    /**
     * Gets the current set of levels the user is playing.
     *
     * @return  Set as List of levels specifying the current set the user is playing is returned to caller.
     */
    public List<Level> getM_CurrentSet() {
        return m_CurrentSet;
    }

    /**
     * Gets the index number of the level the user is playing.
     *
     * @return  Integer specifying the index of current level the user is playing is returned to caller.
     */
    public int getM_CurrentLevelIndex() {
        return m_CurrentLevelIndex;
    }


    /**
     * Loads, decodes and saves all levels of a given set inputStream from resource file. <br />
     * When a level is down decoding, a new level object is instantiated and add to set list. <br />
     * This method will be called when user intends to choose a set of levels or start the new game.
     *
     * @param  input    InputStream specifying the input as set of levels to read from.
     * @param  setName  String value specifying the name of set to be loaded.
     * @see Level
     * @see LevelManager#m_CurrentSet
     * @see org.ziqi.control.screenController.LevelSetScreenController
     * @see org.ziqi.control.screenController.StartScreenController
     */
    public void loadSet(InputStream input, String setName) {
        m_CurrentSet = new ArrayList<>();
        m_CurrentSetName = setName;
        m_CurrentLevelIndex = 0;
        int levelIndex = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            List<String> rawLevel = new ArrayList<>();
            String levelName = "";
            boolean parsedFirstLevel = false;

            while (true) {
                String line = reader.readLine();

                // Break the loop if EOF is reached
                if (line == null) {
                    if (rawLevel.size() != 0) {
                        Debugger.debugBegin(false, "Parsing level from raw file..");
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        m_CurrentSet.add(parsedLevel);
                        Debugger.debugEnd(false, "Parse done!" + m_CurrentSet.size());
                    }
                    break;
                }

                // load LevelName
                if (line.contains("LevelName")) {
                    if (parsedFirstLevel) {
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        m_CurrentSet.add(parsedLevel);
                        rawLevel = new ArrayList<>();
                    } else {
                        parsedFirstLevel = true;
                    }
                    levelName = line.replace("LevelName: ", "");
                    continue;
                }

                // load map of current level
                line = line.trim().toUpperCase();
                // if the line begins with '='. then read as level
                if (line.matches("=.*"))
                    rawLevel.add(line.substring(1));
                Debugger.debugBegin(false, line);
            }
        } catch (IOException e) {
            m_Logger.severe("Error trying to load the game file: ");
            e.printStackTrace();
        } catch (NullPointerException e) {
            m_Logger.severe("Cannot open the requested file: " + e);
        }
    }

    /**
     * Gets the next level from current set.
     *
     * @return  If there exists a next level, then Level object is returned to caller;
     *          Otherwise, GameEngine is then requested to show Victory Screen to inform to user the completion of the current set, null is returned.
     * @see     GameEngine#toVictoryScreen()
     */
    public Level nextLevel() {
        if (m_CurrentLevelIndex == m_CurrentSet.size()){
            GameEngine.getInstance().toVictoryScreen();
            return null;
        }
        // when currentLevel = 0, load first level
        m_CurrentLevel = m_CurrentSet.get(m_CurrentLevelIndex++);
        return m_CurrentLevel;
    }
}
