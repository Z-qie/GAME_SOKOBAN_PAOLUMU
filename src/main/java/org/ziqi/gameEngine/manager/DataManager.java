package org.ziqi.gameEngine.manager;

import com.opencsv.CSVReader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.Utils;
import org.ziqi.Debugger;
import org.ziqi.model.ScoreRecord;

import java.io.*;
import java.util.Arrays;

/**
 * DataManager class as game manager to take charge of recording high scores(timing and move count) of players for each levels.
 * Records will be handled by data manager to read from outside file, keep updating records and write into file.
 *
 * @author Ziqi Yang
 * @see GameManager
 */
public class DataManager extends GameManager {

    /**
     * Constant integer restricts the max timing value. <br />
     * When time out, the time integer wont be updated any more.
     */
    private static final int MAX_TIMEOUT = 3600;

    /**
     * Integer specifying the the valid move count of player in current level.
     */
    private int m_MoveCount;

    /**
     * Unformatted integer specifying the second time elapsed from last reset/start.
     *
     * @see DataManager#updateTimeString()
     * @see Utils#getTimeString(long)
     */
    private int m_secondTimer;

    /**
     * Formatted SimpleStringProperty specifying the time elapsed from last reset/start.
     *
     * @see DataManager#updateTimeString()
     * @see Utils#getTimeString(long)
     */
    private final SimpleStringProperty m_TimeString = new SimpleStringProperty();

    /**
     *  ObservableList of type ScoreRecord containing all records read from data file.
     */
    private ObservableList<ScoreRecord> m_ScoreRecords;

    /**
     * DataManager constructor. <br />
     * When instantiating the DataManager object, a timeline is played to keep track of time elapsed during playing the game.
     *
     * @see DataManager#startTimer()
     * @see Timeline
     */
    public DataManager() {
        startTimer();
    }

    /**
     * Gets the formatted SimpleStringProperty specifying the time elapsed from last reset/start.
     *
     * @return  The formatted SimpleStringProperty is returned to the caller.
     * @see     DataManager#updateTimeString()
     * @see     Utils#getTimeString(long)
     */
    public SimpleStringProperty getM_TimeString() {
        return m_TimeString;
    }

    /**
     * Gets the unformatted integer time specifying the time elapsed from last reset/start.
     *
     * @return  The formatted SimpleStringProperty is returned to the caller.
     * @see     DataManager#updateTimeString()
     * @see     Utils#getTimeString(long)
     */
    public int getM_secondTimer() {
        return m_secondTimer;
    }

    /**
     * Gets the integer specifying the the valid move count of player in current level.
     *
     * @return  The integer specifying the the valid move count of player in current level is returned to the caller.
     */
    public int getM_MoveCount() {
        return m_MoveCount;
    }

    /**
     * Increments the move count by given value. <br />
     * When player is grounded, move count increment by 1 for every valid move; <br />
     * When player is flying, move count increment by 2 for every valid move.
     *
     * @param  increment  Integer specifying the value to increment on move count.
     * @see    org.ziqi.model.Player
     */
    public void incrementMoveCount(int increment) {
        this.m_MoveCount += increment;
    }

    /**
     * Resets the move count integer to zero. <br />
     * This method is called when user intends to restart the level or a new level begins.
     */
    public void resetMoveCount() {
        this.m_MoveCount = 0;
    }

    /**
     * Resets the second time integer to zero. <br />
     * This method is called when user intends to restart the level or a new level begins.
     */
    public void resetTime() {
        m_secondTimer = 0;
    }

    /**
     * Starts to play a new timeline as timer to keep track of time elapsed. <br />
     * This method is called when the DataManager is instantiated.
     *
     * @see Timeline
     * @see DataManager#updateTimeString()
     * @see DataManager#m_secondTimer
     */
    public void startTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            m_secondTimer++;
            updateTimeString();
            Debugger.debugBegin(false, "Time passed: " + m_secondTimer);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Reads all permanent high-score records from record file into a list of records. <br />
     * This method is called when the score board screen or pop up screen is displayed.
     *
     * @return  An ObservableList of type ScoreBoard containing all permanent high-score records is returned to caller.
     * @see     ScoreRecord
     * @see     org.ziqi.control.screenController.ScoreBoardScreenController
     */
    public ObservableList<ScoreRecord> readScoreRecords() {
        m_ScoreRecords = FXCollections.observableArrayList();
        InputStream recordFilePath;
        try {
            File file = new File(System.getProperty("user.dir") + "/data/ScoreBoard.csv");
            recordFilePath = new FileInputStream(file);
            // Read existing file
            CSVReader reader = new CSVReader(new InputStreamReader(recordFilePath));
            for (String[] fields : reader) {
                ScoreRecord record = new ScoreRecord(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
                m_ScoreRecords.add(record);
            }
        } catch (FileNotFoundException e) {
            File directory = new File(System.getProperty("user.dir") + "/data");
            directory.mkdirs();
        }
        return m_ScoreRecords;
    }

    /**
     * Finds and gets the high score record of current level from m_ScoreRecords observableList. <br />
     * If no record exists for this level, then create an empty record and add it to the m_ScoreRecords observableList. <br />
     * The new create empty record has value -1 as time and move count and "anonymous" as player name.
     *
     * @return  A ScoreRecord object either found from high score records or new-created is return to caller.
     * @see     ScoreRecord
     * @see     DataManager#m_ScoreRecords
     */
    public ScoreRecord getTopRecord() {
        String currentSetName = GameEngine.getInstance().getM_LevelManager().getM_CurrentSetName();
        String currentLevelName = GameEngine.getInstance().getM_LevelManager().getM_CurrentLevel().getM_LevelName();

        // get current top record from list
        for (ScoreRecord currentRecord : m_ScoreRecords) {
            if (currentRecord.getM_SetName().equals(currentSetName) && currentRecord.getM_LevelName().equals(currentLevelName))
                return currentRecord;
        }
        // if no record for this level, then create one to list
        ScoreRecord newRecord = new ScoreRecord(currentSetName, currentLevelName, "-1", "anonymous", "-1", "anonymous");
        m_ScoreRecords.add(newRecord);
        return newRecord;
    }

    /**
     * Compares and updates the fields of given ScoreRecord object by given parameters. <br />
     * After updating the ScoreRecord object, this method calls writeAllScoreRecords() to write new records into data file.
     *
     * @param  fastestTime  Integer specifying current fastest time record of current level.
     *                       If this value is equal to value of given scoreRecord, then no need to update this record.
     * @param  minimumMove  Integer specifying current minimum move record of current level.
     *                       If this value is equal to value of given scoreRecord, then no need to update this record.
     * @param  playerName   String value specifying player's name got from input.
     * @param  scoreRecord  ScoreRecord object to be updated by above parameters.
     * @see    DataManager#writeAllScoreRecords()
     * @see    ScoreRecord
     * @see    DataManager#m_ScoreRecords
     */
    public void updateRecord(int fastestTime, int minimumMove, String playerName, ScoreRecord scoreRecord) {
        if (fastestTime != Integer.parseInt(scoreRecord.getM_FastestTime())) {
            scoreRecord.setM_FastestTime(String.valueOf(fastestTime));
            scoreRecord.setM_PlayerOfFT(playerName);
        }
        if (minimumMove != Integer.parseInt(scoreRecord.getM_MinimumMove())) {
            scoreRecord.setM_MinimumMove(String.valueOf(minimumMove));
            scoreRecord.setM_PlayerOfMM(playerName);
        }
        writeAllScoreRecords();
    }

    /**
     * Writes all score records contained in ObservableList<ScoreRecord> m_ScoreRecords into data file permanently. <br />
     * This method is called when any record is updated from m_ScoreRecords.
     *
     * @see  ScoreRecord
     * @see  DataManager#m_ScoreRecords
     * @see  DataManager#updateRecord(int, int, String, ScoreRecord) 
     */
    private void writeAllScoreRecords() {
        FileWriter writer;
        try {
            writer = new FileWriter(System.getProperty("user.dir") + "/data/ScoreBoard.csv", false);
            Debugger.debugBegin(false, "New board contains items of " + m_ScoreRecords.size());
            for (ScoreRecord record : m_ScoreRecords) {
                writer.write(record.toCSVFormat() + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            m_Logger.severe(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Transforms the second time integer into formatted string of m_TimeString variable. <br />
     * If the time is greater than or equals to the MAX_TIMEOUT, then the time string won't be updated.
     *
     * @see  DataManager#m_TimeString
     * @see  DataManager#m_ScoreRecords
     * @see  DataManager#MAX_TIMEOUT
     * @see  Utils#getTimeString(long)
     */
    private void updateTimeString() {
        if (m_secondTimer <= MAX_TIMEOUT)
            m_TimeString.set(Utils.getTimeString(m_secondTimer));
    }
}
