package org.ziqi.gameEngine;

import org.ziqi.gameEngine.base.GameObject;
import org.ziqi.model.*;

import java.awt.*;

/**
 * GameObjectFactory class takes charge of generating specific GameObject by given type and position. <br />
 * This follows a factory design pattern.
 *
 * @author Ziqi Yang
 * @see GameObject
 */
public class GameObjectFactory {
    
    /**
     * Instantiates GameObject specified by given char type and its position. <br />
     * This method will be called by Level class when initializing or refreshing the level object decoded from the raw level file.
     *
     * @param type     Char value specifying the type of GameObject to be generated.
     * @param position Point object specifying the initial point of the GameObject.
     * @return The new GameObject is returned with initialized position.
     * @see Level#refreshLevel()
     * @see GameObject
     */
    public static GameObject getGamModelFromChar(char type, Point position) {
        type = Character.toUpperCase(type);

        return switch (type) {
            case 'W' -> new Wall(position, Wall.WallStatus.ORTHOTIC);
            case 'Q' -> new Wall(position, Wall.WallStatus.PLAN);
            case ' ' -> new Floor(position);
            case 'C' -> new Crate(position);
            case 'D' -> new Diamond(position);
            case 'S' -> new Player(position);
            default -> null;
        };
    }
}
