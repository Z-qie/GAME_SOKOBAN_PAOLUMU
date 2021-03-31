package org.ziqi.model;

import javafx.beans.property.SimpleStringProperty;
import org.ziqi.gameEngine.manager.DataManager;

/**
 * ScoreRecord class as to store information of high scores of each level.
 *
 * @author Ziqi Yang
 * @see DataManager
 */
public class ScoreRecord {

    /**
     * SimpleStringProperty specifying set name.
     */
    private final SimpleStringProperty m_SetName;

    /**
     * SimpleStringProperty specifying level name.
     */
    private final SimpleStringProperty m_LevelName;

    /**
     * SimpleStringProperty specifying formatted fastest time.
     */
    private final SimpleStringProperty m_FastestTime;

    /**
     * SimpleStringProperty specifying the record breaker's name of the fastest time.
     */
    private final SimpleStringProperty m_PlayerOfFT;

    /**
     * SimpleStringProperty specifying minimum move.
     */
    private final SimpleStringProperty m_MinimumMove;

    /**
     * SimpleStringProperty specifying the record breaker's name of the minimum move.
     */
    private final SimpleStringProperty m_PlayerOfMM;

    /**
     * ScoreRecord constructor to initialize all information stored in this object by given parameters. <br />
     * It is DataManager's job to instantiate new ScoreRecord, update and save them into file.
     *
     * @param m_SetName     String value specifying set name.
     * @param m_LevelName   String value specifying level name.
     * @param m_FastestTime String value specifying formatted fastest time.
     * @param m_PlayerOfFT  String value specifying record breaker's name of the fastest time.
     * @param m_MinimumMove String value specifying minimum move.
     * @param m_PlayerOfMM  String value specifying record breaker's name of the minimum move.
     * @see DataManager#getTopRecord()
     */
    public ScoreRecord(String m_SetName, String m_LevelName, String m_FastestTime,
                       String m_PlayerOfFT, String m_MinimumMove, String m_PlayerOfMM) {
        this.m_SetName = new SimpleStringProperty(m_SetName);
        this.m_LevelName = new SimpleStringProperty(m_LevelName);
        this.m_FastestTime = new SimpleStringProperty(m_FastestTime);
        this.m_PlayerOfFT = new SimpleStringProperty(m_PlayerOfFT);
        this.m_MinimumMove = new SimpleStringProperty(m_MinimumMove);
        this.m_PlayerOfMM = new SimpleStringProperty(m_PlayerOfMM);
    }

    /**
     * Sets new fastest time by given String value into ScoreRecord object.
     *
     * @param m_FastestTime String value specifying new fastest time;
     */
    public void setM_FastestTime(String m_FastestTime) {
        this.m_FastestTime.set(m_FastestTime);
    }

    /**
     * Sets new breaker name by given String value into ScoreRecord object.
     *
     * @param m_PlayerOfFT String value specifying breaker name.
     */
    public void setM_PlayerOfFT(String m_PlayerOfFT) {
        this.m_PlayerOfFT.set(m_PlayerOfFT);
    }

    /**
     * Sets new minimum move count by given String value into ScoreRecord object.
     *
     * @param m_MinimumMove String value specifying new minimum move count.
     */
    public void setM_MinimumMove(String m_MinimumMove) {
        this.m_MinimumMove.set(m_MinimumMove);
    }

    /**
     * Sets new breaker name by given String value into ScoreRecord object.
     *
     * @param m_PlayerOfMM String value specifying breaker name.
     */
    public void setM_PlayerOfMM(String m_PlayerOfMM) {
        this.m_PlayerOfMM.set(m_PlayerOfMM);
    }

    /**
     * Gets set name as String of this ScoreRecord object.
     *
     * @return A string specifying set name of this ScoreRecord object is returned.
     */
    public String getM_SetName() {
        return m_SetName.get();
    }

    /**
     * Gets level name as String of this ScoreRecord object.
     *
     * @return A string specifying level name of this ScoreRecord object is returned.
     */
    public String getM_LevelName() {
        return m_LevelName.get();
    }

    /**
     * Gets the fastest time as String of this ScoreRecord object.
     *
     * @return A string specifying the fastest time of this ScoreRecord object is returned.
     */
    public String getM_FastestTime() {
        return m_FastestTime.get();
    }

    /**
     * Gets breaker name of fastest time as String of this ScoreRecord object.
     *
     * @return A string specifying breaker name of fastest time of this ScoreRecord object is returned.
     */
    public String getM_PlayerOfFT() {
        return m_PlayerOfFT.get();
    }

    /**
     * Gets minimum move as String of this ScoreRecord object.
     *
     * @return A string specifying minimum move of this ScoreRecord object is returned.
     */
    public String getM_MinimumMove() {
        return m_MinimumMove.get();
    }

    /**
     * Gets breaker name of minimum move as String of this ScoreRecord object.
     *
     * @return A string specifying breaker name of minimum move of this ScoreRecord object is returned.
     */
    public String getM_PlayerOfMM() {
        return m_PlayerOfMM.get();
    }

    /**
     * Gets CSV formatted record string to be store into outside file permanently. <br />
     * This method will be called by DataManager when intending to write records into file.
     *
     * @return A CSV formatted record string is returned.
     * @see DataManager
     */
    public String toCSVFormat() {
        return String.join(",", new String[]{m_SetName.get(), m_LevelName.get(), m_FastestTime.get(), m_PlayerOfFT.get(), m_MinimumMove.get(), m_PlayerOfMM.get()});
    }
}

