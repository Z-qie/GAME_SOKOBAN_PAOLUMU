package org.ziqi.model;


import javafx.scene.input.KeyCode;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.gameEngine.base.GameObject;
import org.ziqi.Debugger;
import org.ziqi.gameEngine.manager.MusicManager;
import org.ziqi.view.GraphicUnit;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Player class as GameObject, possessing PlayerDirection and PlayerStatus enumeration.
 *
 * @author Ziqi Yang
 * @see GameObject
 */
public class Player extends GameObject {
    /**
     * PlayerStatus specifying the current status of Player object.
     */
    private PlayerStatus m_PlayerStatus;

    /**
     * PlayerDirection specifying the current facing direction of Player object.
     */
    private PlayerDirection m_PlayerDirection;

    /**
     * Constant integer value specifying the move count number for each move when player is in FLYING status as punishment.
     *
     * @see PlayerStatus#FLYING
     * @see Player#move(Point)
     */
    private final static int FLYING_MOVE_COUNT = 2;

    /**
     * Player constructor to initialize the Player object at given position and set the status as IDLE and facing direction as DOWN.
     *
     * @param position Point object specifying the initial position the object.
     * @see PlayerStatus
     * @see PlayerDirection
     */
    public Player(Point position) {
        super(position);
        m_PlayerStatus = PlayerStatus.IDLE;
        m_PlayerDirection = PlayerDirection.DOWN;
    }

    /**
     * PlayerDirection enumeration defines all facing direction of Player object.
     * This is used to render player object as facing different direction.
     */
    public enum PlayerDirection {
        DOWN("DOWN"),
        LEFT("LEFT"),
        RIGHT("RIGHT"),
        UP("UP");

        /**
         * String value describing the Player facing direction.
         */
        private final String m_playerDirection;

        /**
         * PlayerDirection constructor with its specific string value.
         *
         * @param m_playerDirection String value describes the specific facing direction.
         */
        PlayerDirection(String m_playerDirection) {
            this.m_playerDirection = m_playerDirection;
        }

        /**
         * Gets the PlayerDirection as string.
         *
         * @return A string describing the Player facing direction is returned.
         */
        public String toString() {
            return m_playerDirection;
        }
    }

    /**
     * PlayerStatus enumeration defines all status of Player object.
     * This is used to render player object as different status.
     */
    public enum PlayerStatus {
        FLYING("FLYING"),
        FLYING_OFF("FLYING_OFF"),
        IDLE("IDLE"),
        LANDING("LANDING"),
        PUSHING("PUSHING");

        /**
         * String value describing the Player status.
         */
        private final String m_playerStatus;

        /**
         * PlayerStatus constructor with its specific string value.
         *
         * @param m_playerStatus String value describes the specific status.
         */
        PlayerStatus(String m_playerStatus) {
            this.m_playerStatus = m_playerStatus;
        }

        /**
         * Gets the PlayerDirection as string.
         *
         * @return A string describing the Player facing direction is returned.
         */
        public String toString() {
            return m_playerStatus;
        }
    }

    /**
     * Gets the current status of the Player object.
     *
     * @return The PlayerStatus variable contained in Player object is returned to caller.
     */
    public PlayerStatus getM_PlayerStatus() {
        return m_PlayerStatus;
    }

    /**
     * Gets the current status as string value of the Player object.
     *
     * @return The string value describing the Player's status is returned.
     */
    public String getM_PlayerStatusAsString() {
        return m_PlayerStatus.toString();
    }

    /**
     * Gets the current facing direction of the Player object.
     *
     * @return The PlayerDirection variable contained in Player object is returned to caller.
     */
    public PlayerDirection getM_PlayerDirection() {
        return m_PlayerDirection;
    }

    /**
     * Gets the current facing direction as string value of the Player object.
     *
     * @return The string value describing the Player's facing direction is returned.
     */
    public String getM_PlayerDirectionAsString() {
        return m_PlayerDirection.toString();
    }

    /**
     * Sets the status of the Player object.
     *
     * @param m_PlayerStatus PlayerStatus specifying the new status of Player object.
     */
    public void setM_PlayerStatus(PlayerStatus m_PlayerStatus) {
        this.m_PlayerStatus = m_PlayerStatus;
    }

    /**
     * Gets an arraylist of strings specifying all sprite path names of Player game object for ResourceManager to load from resource folder beforehand.
     *
     * @return An arraylist of strings specifying all sprite path names is returned.
     * @see org.ziqi.gameEngine.manager.ResourceManager
     * @see org.ziqi.gameEngine.manager.ResourceManager#loadSpritesFromResource(HashMap)
     * @see PlayerStatus#toString()
     * @see PlayerDirection#toString()
     */
    public static ArrayList<String> getSpriteNames() {
        ArrayList<String> list = new ArrayList<>();
        for (PlayerStatus status : PlayerStatus.values())
            for (PlayerDirection direction : PlayerDirection.values())
                list.add("WITCH/" + status.toString() + "_" + direction.toString() + ".gif");
        return list;
    }

    /**
     * Checks if the Player can move to given direction. <br />
     * This method will get all targets at the moving direction of the Player and call their the override move method to check: <br />
     * This method is called by PlayerController when handling valid user input(move).
     *
     * @param direction A Point object specifying the direction vector the GameObject to be moved to.
     * @return Boolean value specifying if the pusher(player) can move is returned; <br />
     * If so, then the Player is moved to new position, move count increment according to current status(FLYING/grounded) and true is returned to specify the player has moved; <br />
     * Otherwise, false is returned.
     * @see org.ziqi.gameEngine.manager.DataManager#incrementMoveCount(int)
     * @see org.ziqi.control.PlayerController
     * @see Layer
     */
    @Override
    public boolean move(Point direction) {
        GameEngine gameEngine = GameEngine.getInstance();
        Level currentLevel = gameEngine.getM_LevelManager().getM_CurrentLevel();
        HashMap<Integer, GameObject> Targets = new HashMap<>();

        // find all game objects in target place
        for (int layer = GraphicUnit.LAYER_OF_FLOOR; layer <= GraphicUnit.LAYER_OF_CRATE; layer++)
            Targets.put(layer, currentLevel.getGameObjectBy(layer, getM_Position(), direction));


        boolean moved = false;
        // if player is grounded
        if (m_PlayerStatus == PlayerStatus.IDLE || m_PlayerStatus == PlayerStatus.PUSHING) {
            if (Targets.get(GraphicUnit.LAYER_OF_FLOOR) != null)
                moved = true;

            if (Targets.get(GraphicUnit.LAYER_OF_CRATE) != null)
                moved = (Targets.get(GraphicUnit.LAYER_OF_CRATE)).move(direction);

            if (Targets.get(GraphicUnit.LAYER_OF_WALL) != null)
                moved = false;
        }
        // if player is flying
        else if (m_PlayerStatus == PlayerStatus.FLYING)
            if (Targets.get(GraphicUnit.LAYER_OF_FLOOR) != null || Targets.get(GraphicUnit.LAYER_OF_WALL) != null)
                moved = true;


        if (moved) {
            currentLevel.getM_Layers()[GraphicUnit.LAYER_OF_PLAYER].translateObjectBy(getM_Position(), direction);
            // if player is flying, double move count
            if (m_PlayerStatus != PlayerStatus.FLYING)
                gameEngine.getM_DataManager().incrementMoveCount(1);
            else
                gameEngine.getM_DataManager().incrementMoveCount(FLYING_MOVE_COUNT);
            Debugger.debugBegin(true, "m_MovesCount: " + gameEngine.getM_DataManager().getM_MoveCount());
        }
        return moved;
    }

    /**
     * Updates the player status and facing direction by given key code and direction. <br />
     * If SPACE is pressed by user, then this method call checkFlyingStatus() to handle flying status switching; <br />
     * Otherwise, checkMovingStatus() is called.
     *
     * @param code      The input key code by user.
     * @param direction The new facing direction.
     * @see org.ziqi.control.PlayerController
     * @see Player#checkFlyingStatus()
     * @see Player#checkMovingStatus(KeyCode, Point)
     */
    public void updatePlayerStatus(KeyCode code, Point direction) {
        if (code == KeyCode.SPACE)
            checkFlyingStatus();
        else
            checkMovingStatus(code, direction);
    }

    /**
     * Checks current flying/ground status and updates new player status. <br />
     * If player is grounded, then updates status to flying-off. <br />
     * If player is flying, then check if player can land. If so, player status will be set as landing.
     * Otherwise, alert sound effect wil be played.<br />
     *
     * @see MusicManager#playCannotLandMusic()
     */
    private void checkFlyingStatus() {
        // if grounded, ready to fly (fly off)
        if (m_PlayerStatus == PlayerStatus.IDLE || m_PlayerStatus == PlayerStatus.PUSHING)
            m_PlayerStatus = PlayerStatus.FLYING_OFF;
            // if flying, check if can land and ready to land
        else if (m_PlayerStatus == PlayerStatus.FLYING) {
            Level currentLevel = GameEngine.getInstance().getM_LevelManager().getM_CurrentLevel();
            GameObject crate = currentLevel.getGameObjectAt(GraphicUnit.LAYER_OF_CRATE, getM_Position());
            GameObject wall = currentLevel.getGameObjectAt(GraphicUnit.LAYER_OF_WALL, getM_Position());

            if (crate == null && wall == null)
                m_PlayerStatus = PlayerStatus.LANDING;
            else
                GameEngine.getInstance().getM_MusicManager().playCannotLandMusic();

        }
        // if the player is landing or taking off, do handle this input.
    }

    /**
     * Checks current moving status and facing direction and updates new player status. <br />
     */
    private void checkMovingStatus(KeyCode code, Point direction) {
        switch (code) {
            case UP:
                m_PlayerDirection = PlayerDirection.UP;
                break;
            case RIGHT:
                m_PlayerDirection = PlayerDirection.RIGHT;
                break;
            case DOWN:
                m_PlayerDirection = PlayerDirection.DOWN;
                break;
            case LEFT:
                m_PlayerDirection = PlayerDirection.LEFT;
                break;
            default:
                break;
        }
        // if grounded, update player status idle/push
        if (m_PlayerStatus != PlayerStatus.FLYING) {
            Level currentLevel = GameEngine.getInstance().getM_LevelManager().getM_CurrentLevel();
            GameObject crate = currentLevel.getGameObjectBy(GraphicUnit.LAYER_OF_CRATE, getM_Position(), direction);
            if (crate == null || ((Crate) crate).getM_CrateStatus() != Crate.CrateStatus.CRATE_RUNE)
                m_PlayerStatus = PlayerStatus.IDLE;
            else
                m_PlayerStatus = PlayerStatus.PUSHING;
        }
    }
}
