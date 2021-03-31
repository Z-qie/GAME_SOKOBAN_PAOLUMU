package org.ziqi.model;

import org.ziqi.gameEngine.base.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Diamond class as GameObject.
 *
 * @author Ziqi Yang
 * @see GameObject
 */
public class Diamond extends GameObject {

    /**
     * Diamond constructor to initialize the Diamond object at given position.
     *
     * @param position Point object specifying the initial position the object.
     */
    public Diamond(Point position) {
        super(position);
    }

    /**
     * Gets an arraylist of strings specifying all sprite path names of Diamond game object for ResourceManager to load from resource folder beforehand.
     *
     * @return An arraylist of strings specifying all sprite path names is returned.
     * @see org.ziqi.gameEngine.manager.ResourceManager
     * @see org.ziqi.gameEngine.manager.ResourceManager#loadSpritesFromResource(HashMap)
     */
    public static ArrayList<String> getSpriteNames() {
        ArrayList<String> list = new ArrayList<>();
        list.add("DIAMOND/" + "DIAMOND_CLEFT.gif");
        return list;
    }

    /**
     * Checks if the pusher(Player) can move to given direction. <br />
     * True is always returned to player as diamond in this game is not block-able.
     *
     * @param direction A Point object specifying the direction vector the GameObject to be moved to.
     * @return Boolean value specifying if the pusher(player) can move is returned.
     */
    @Override
    public boolean move(Point direction) {
        return true;
    }
}
