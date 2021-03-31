package org.ziqi.model;

import org.ziqi.gameEngine.base.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Wall class as GameObject, possessing WallStatus enumeration.
 *
 * @author Ziqi Yang
 * @see GameObject
 */
public class Wall extends GameObject {

    /**
     * WallStatus specifying the current status of crate object.
     */
    private final WallStatus m_WallStatus;

    /**
     * Wall constructor to initialize the Wall object at given position and set the status as given status read from level resource file to achieve 2.5D map effect.
     *
     * @param position Point object specifying the initial position the object.
     * @see Crate.CrateStatus
     */
    public Wall(Point position, WallStatus wallStatus){
        super(position);
        m_WallStatus = wallStatus;
    }

    /**
     * WallStatus enumeration defines all status of Wall object.
     */
    public enum WallStatus {
        PLAN("PLAN"),
        ORTHOTIC("ORTHOTIC");

        /**
         * String value describing the Wall status.
         */
        private final String m_WallStatus;

        /**
         * WallStatus constructor with its specific string value.
         *
         * @param m_WallStatus String value describes the specific status.
         */
        WallStatus(String m_WallStatus) {
            this.m_WallStatus = m_WallStatus;
        }

        /**
         * Gets the WallStatus as string.
         *
         * @return A string describing the Wall status is returned.
         */
        public String toString() {
            return m_WallStatus;
        }
    }

    /**
     * Gets the current status as string value of the Wall object.
     *
     * @return The string value describing the crate status is returned.
     */
    public String getM_WallStatusAsString() {
        return m_WallStatus.toString();
    }

    /**
     * Gets an arraylist of strings specifying all sprite path names of Wall game object for ResourceManager to load from resource folder beforehand.
     *
     * @return An arraylist of strings specifying all sprite path names is returned.
     * @see org.ziqi.gameEngine.manager.ResourceManager
     * @see org.ziqi.gameEngine.manager.ResourceManager#loadSpritesFromResource(HashMap)
     * @see WallStatus#toString()
     */
    public static ArrayList<String> getSpriteNames(){
        ArrayList<String> list =  new ArrayList<>();
        for (WallStatus status : WallStatus.values())
            list.add("WALL/" + status.toString() + ".png");
        return list;
    }

    /**
     * Checks if the pusher(Player) can move to given direction. <br />
     * False is always returned to player as Wall in this game is block-able.
     *
     * @param direction A Point object specifying the direction vector the GameObject to be moved to.
     * @return Boolean value specifying if the pusher(player) can move is returned.
     */
    @Override
    public boolean move(Point direction) {
        return false;
    }
}
