package org.ziqi.view;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.gameEngine.base.GameObject;
import org.ziqi.gameEngine.manager.ResourceManager;
import org.ziqi.model.*;

/**
 * GraphicUnit class as factory to generate ImageView according GameObjects and their status.
 *
 * @author Ziqi Yang-modified
 * @see org.ziqi.gameEngine.GraphicUnitRenderer
 * @see ResourceManager
 */
public class GraphicUnit extends ImageView {

    /**
     * Constant integer specifying size the each unit to be rendered.
     */
    public static final int UNIT_SIZE = 32;

    /**
     * Constant integer specifying number of layers to render on AnchorPanes.
     */
    public static final int NUM_OF_LAYER = 5;

    /**
     * Constant integer specifying the index of Player layer.
     */
    public static final int LAYER_OF_PLAYER = 4;

    /**
     * Constant integer specifying the index of Crate layer.
     */
    public static final int LAYER_OF_CRATE = 3;

    /**
     * Constant integer specifying the index of Wall layer.
     */
    public static final int LAYER_OF_WALL = 2;

    /**
     * Constant integer specifying the index of Diamond layer.
     */
    public static final int LAYER_OF_DIAMOND = 1;

    /**
     * Constant integer specifying the index of Floor layer.
     */
    public static final int LAYER_OF_FLOOR = 0;

    /**
     * Constant double specifying the vertical offset when rendering flying player to achieve 2.5D effect.
     */
    public static final double FLYING_OFFSET = 0.5;

    /**
     * Constant double specifying vertical offset when rendering player pushing-UP to achieve 2.5D effect.
     */
    public static final double PUSHING_OFFSET = 0.6;

    /**
     * Double value specifying the hue value to adjust on original color.
     */
    private static double m_WallColorHue = 0.0;

    /**
     * Double value specifying the hue value to adjust to emerald from original color.
     */
    public static final double HUE_EMERALD = 0.8;

    /**
     * Double value specifying the hue value to adjust to PAOLUMU(low-saturation pink) from original color.
     */
    public static final double HUE_PAOLUMU = -0.45;

    /**
     * Double value specifying the hue value to adjust to original color.
     */
    public static final double HUE_GROUND = 0;

    /**
     * Double value specifying the hue value to adjust to sapphire color.
     */
    public static final double HUE_SAPPHIRE = 1;



    /**
     * GraphicUnit constructor loads sprites from ResourceManager according to types of given GameObjects and their current status.
     *
     * @param obj GameObject specifying what sprites should the GraphicUnit contains for the GameObject.
     * @see ResourceManager
     * @see ResourceManager#loadSprite(String)
     * @see Crate.CrateStatus#getM_CrateStatusAsString()
     * @see Wall.WallStatus#getM_WallStatusAsString()
     * @see Player.PlayerStatus#getM_PlayerStatusAsString()
     * @see Player.PlayerDirection#getM_PlayerDirection()
     */
    public GraphicUnit(GameObject obj) {
        ResourceManager resourceManager = GameEngine.getInstance().getM_ResourceManager();

        Image sprite = null;

        if (obj instanceof Wall) {
            sprite = resourceManager.loadSprite(((Wall) obj).getM_WallStatusAsString());
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(m_WallColorHue);
            this.setEffect(colorAdjust);
        }
        else if (obj instanceof Crate)
            sprite = resourceManager.loadSprite(((Crate) obj).getM_CrateStatusAsString());
        else if (obj instanceof Diamond)
            sprite = resourceManager.loadSprite("DIAMOND_CLEFT");
        else if (obj instanceof Player) {
            sprite = resourceManager.loadSprite(((Player) obj).getM_PlayerStatusAsString() + "_" + ((Player) obj).getM_PlayerDirectionAsString());
        }
        if (sprite != null) {
            this.setImage(sprite);
            this.setFitWidth(UNIT_SIZE);
            this.setFitHeight(UNIT_SIZE);
            this.setPreserveRatio(false);
        }
    }

    /**
     * Sets the color of wall to be rendered on different set.
     *
     * @param m_WallColorHue Double value specifying the hue value to adjust on original color.
     */
    public static void setM_WallColorHue(double m_WallColorHue) {
        GraphicUnit.m_WallColorHue = m_WallColorHue;
    }
}
