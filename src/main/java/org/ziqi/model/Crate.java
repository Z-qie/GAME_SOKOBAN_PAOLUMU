package org.ziqi.model;

import org.ziqi.gameEngine.GameEngine;
import org.ziqi.gameEngine.base.GameObject;
import org.ziqi.view.GraphicUnit;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Crate class as GameObject, possessing CrateStatus enumeration.
 *
 * @author Ziqi Yang
 * @see GameObject
 * @see CrateStatus
 */
public class Crate extends GameObject {

    /**
     * CrateStatus specifying the current status of crate object.
     */
    private CrateStatus m_CrateStatus;

    /**
     * Crate constructor to initialize the Crate object at given position and set the status as CRATE_RUNE.
     *
     * @param position Point object specifying the initial position the object.
     * @see CrateStatus
     */
    public Crate(Point position) {
        super(position);
        m_CrateStatus = CrateStatus.CRATE_RUNE;
    }

    /**
     * CrateStatus enumeration defines all status of Crate object.
     */
    public enum CrateStatus {
        CRATE_RUNE("CRATE_RUNE"),
        CRATE_SEALED("CRATE_SEALED"),
        CRATE_SEALING("CRATE_SEALING");

        /**
         * String value describing the crate status.
         */
        private final String m_crateStatus;

        /**
         * CrateStatus constructor with its specific string value.
         *
         * @param m_crateStatus String value describes the specific status.
         */
        CrateStatus(String m_crateStatus) {
            this.m_crateStatus = m_crateStatus;
        }

        /**
         * Gets the CrateStatus as string.
         *
         * @return A string describing the crate status is returned.
         */
        public String toString() {
            return m_crateStatus;
        }
    }

    /**
     * Gets the current status of the Crate object.
     *
     * @return The CrateStatus variable contained in Crate object is returned to caller.
     */
    public CrateStatus getM_CrateStatus() {
        return m_CrateStatus;
    }

    /**
     * Gets the current status as string value of the Crate object.
     *
     * @return The string value describing the crate status is returned.
     */
    public String getM_CrateStatusAsString() {
        return m_CrateStatus.toString();
    }

    /**
     * Sets the status of the Crate object.
     *
     * @param m_CrateStatus CrateStatus specifying the new status of crate object.
     */
    public void setM_CrateStatus(CrateStatus m_CrateStatus) {
        this.m_CrateStatus = m_CrateStatus;
    }

    /**
     * Gets an arraylist of strings specifying all sprite path names of Crate game object for ResourceManager to load from resource folder beforehand.
     *
     * @return An arraylist of strings specifying all sprite path names is returned.
     * @see org.ziqi.gameEngine.manager.ResourceManager
     * @see org.ziqi.gameEngine.manager.ResourceManager#loadSpritesFromResource(HashMap)
     * @see CrateStatus#toString()
     */
    public static ArrayList<String> getSpriteNames() {
        ArrayList<String> list = new ArrayList<>();
        for (CrateStatus status : CrateStatus.values())
            list.add("CRATE/" + status.toString() + ".gif");
        return list;
    }

    /**
     * Checks if the pusher(Player) can move to given direction. <br />
     * If so, then the crate is moved to new position and true is returned to specify the player can move; <br />
     * Otherwise, false is returned.
     *
     * @param direction A Point object specifying the direction vector the GameObject to be moved to.
     * @return Boolean value specifying if the pusher(player) can move is returned.
     */
    @Override
    public boolean move(Point direction) {

        if (m_CrateStatus != CrateStatus.CRATE_RUNE)
            return false;

        Level currentLevel = GameEngine.getInstance().getM_LevelManager().getM_CurrentLevel();
        HashMap<Integer, GameObject> Targets = new HashMap<>();

        for (int layer = GraphicUnit.LAYER_OF_FLOOR; layer <= GraphicUnit.LAYER_OF_CRATE; layer++)
            Targets.put(layer, currentLevel.getGameObjectBy(layer, getM_Position(), direction));

        if (Targets.get(GraphicUnit.LAYER_OF_FLOOR) != null && Targets.get(GraphicUnit.LAYER_OF_CRATE) == null) {
            // exchange position
            if (currentLevel.getGameObjectBy(GraphicUnit.LAYER_OF_DIAMOND, getM_Position(), direction) != null)
                m_CrateStatus = CrateStatus.CRATE_SEALING;

            currentLevel.getM_Layers()[GraphicUnit.LAYER_OF_CRATE].translateObjectBy(getM_Position(), direction);
            return true;
        } else
            return false;
    }
}
