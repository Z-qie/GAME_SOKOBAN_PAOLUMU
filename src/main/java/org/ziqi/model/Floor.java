package org.ziqi.model;

import org.ziqi.gameEngine.base.GameObject;

import java.awt.*;

/**
 * Floor class as GameObject.
 *
 * @author Ziqi Yang
 * @see GameObject
 */
public class Floor extends GameObject {

    /**
     * Floor constructor to initialize the Floor object at given position.
     *
     * @param position Point object specifying the initial position the object.
     */
    public Floor(Point position){
        super(position);
    }

    /**
     * Checks if the pusher(Player) can move to given direction. <br />
     * True is always returned to player as Floor in this game is not block-able.
     *
     * @param direction A Point object specifying the direction vector the GameObject to be moved to.
     * @return Boolean value specifying if the pusher(player) can move is returned.
     */
    @Override
    public boolean move(Point direction) {
        return true;
    }
}
