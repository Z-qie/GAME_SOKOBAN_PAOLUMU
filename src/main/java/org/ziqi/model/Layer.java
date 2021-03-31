package org.ziqi.model;

import org.ziqi.Utils;
import org.ziqi.gameEngine.base.GameObject;
import java.awt.*;

/**
 * Layer class as to store grids(2-d array) of GameObjects with the same type into one layer.
 * Layers will be instantiated when a level is created.
 *
 * @author Ziqi Yang-modified
 * @see Level
 * @see org.ziqi.view.GraphicUnit#NUM_OF_LAYER
 * @see org.ziqi.gameEngine.GraphicUnitRenderer
 */
public class Layer {
    /**
     * Integer specifying column of the layer.
     */
    private final int COLUMNS;

    /**
     * Integer specifying row of the layer.
     */
    private final int ROWS;

    /**
     * 2-D array of GameObjects holding the content of the layer .
     */
    private final GameObject[][] m_Layer;

    /**
     * Layer constructor to initialize the size of layer by given rows and cols.
     *
     * @param rows    Integer specifying rows of layer.
     * @param columns Integer specifying columns of layer.
     */
    public Layer(int rows, int columns) {
        ROWS = rows;
        COLUMNS = columns;
        m_Layer = new GameObject[ROWS][COLUMNS];
    }

    /**
     * Gets the GameObject at specific position.
     *
     * @param p Point object specifying the position of GameObject.
     * @return If p is not null or the position is not out of bound, then the specific GameObject is returned;
     * Otherwise, null is returned.
     */
    public GameObject getGameObjectAt(Point p) {
        if (p == null || isPointOutOfBounds(p))
            return null;
        return m_Layer[p.x][p.y];
    }

    /**
     * Translates the GameObject specified by given position in the layer by a given vector distance.
     *
     * @param from  Point object specifying the original position of the GameObject to be translated.
     * @param delta Point object specifying the vector distance to translate to.
     */
    public void translateObjectBy(Point from, Point delta) {
        GameObject gameObject = getGameObjectAt(from);
        Point to = Utils.translatePoint(from, delta);
        gameObject.setM_Position(to);
        putGameObjectAt(gameObject, to);
        removeGameObjectAt(from);
    }

    /**
     * Sets a new GameObject at the given position.
     *
     * @param gameObject GameObject to be added into the layer.
     * @param p          Point object specifying the position the GameObject to be added to.
     */
    public void putGameObjectAt(GameObject gameObject, Point p) {
        if (p == null || isPointOutOfBounds(p))
            return;
        m_Layer[p.x][p.y] = gameObject;
    }

    /**
     * Checks if the given position is out of bound of the layer.
     *
     * @param p Point object specifying the position to be checked.
     * @return If out of bound, false is returned; Otherwise, true is returned.
     */
    private boolean isPointOutOfBounds(Point p) {
        return (p.x < 0 || p.y < 0 || p.x >= ROWS || p.y >= COLUMNS);
    }

    /**
     * Removes the GameObject reference from given position.
     *
     * @param position Point object specifying the position the GameObject reference to be removed from.
     */
    public void removeGameObjectAt(Point position) {
        putGameObjectAt(null, position);
    }
}