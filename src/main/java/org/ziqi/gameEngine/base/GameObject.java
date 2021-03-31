package org.ziqi.gameEngine.base;

import org.ziqi.gameEngine.manager.GameManager;

import java.awt.*;

/**
 * GameObject class as super class(parent) of all other GameObject in the game. <br />
 *
 * @author Ziqi Yang-modified
 * @see org.ziqi.model.Crate
 * @see org.ziqi.model.Diamond
 * @see org.ziqi.model.Floor
 * @see org.ziqi.model.Player
 * @see org.ziqi.model.Player
 * @see org.ziqi.model.Wall
 */
public abstract class GameObject {

    /**
     * Point object specifying the position of a GameObject.
     */
    private Point m_Position;

    /**
     * GameObject super constructor to be called when instantiating a child GameObject by GameObjectFactory to initialize its position.
     *
     * @param position A Point object specifying the initial position of the new game object.
     * @see org.ziqi.gameEngine.GameObjectFactory
     */
    public GameObject(Point position) {
        this.m_Position = position;
    }

    /**
     * Gets the position of the game object.
     *
     * @return A Point object specifying the current position of its game object.
     */
    public Point getM_Position() {
        return m_Position;
    }

    /**
     * Sets the position of the game object by given Point object.
     *
     * @param m_Position A Point object specifying the new position of the game object.
     */
    public void setM_Position(Point m_Position) {
        this.m_Position = m_Position;
    }

    /**
     * Abstract method to be implemented by its children to check if the one GameObject can move to a certain given direction, if so, move the object.
     *
     * @param direction A Point object specifying the direction vector the GameObject to be moved to.
     * @return          If the GameObject can move, the method move the GameObject and true value is returned; Otherwise, false is returned.
     * @see org.ziqi.model.Player#move(Point) 
     * @see org.ziqi.model.Crate#move(Point)
     */
    public abstract boolean move(Point direction);
}