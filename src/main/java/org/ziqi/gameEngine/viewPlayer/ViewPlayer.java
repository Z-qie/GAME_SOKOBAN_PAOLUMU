package org.ziqi.gameEngine.viewPlayer;

import org.ziqi.gameEngine.base.GameScreen;

/**
 * ViewPlayer interface to be implemented by ScreenPlayer and ViewAdapter to initialize screens(Stage). <br />
 * This follows an adapter design pattern by ViewAdapter and ScreenPlayer. <br />
 *
 * @author Ziqi Yang
 * @see ScreenPlayer
 * @see ViewAdapter
 */
public interface ViewPlayer {
    /**
     * Constant string value specifying the screen type of view to be shown.
     */
    String SCREEN = "SCREEN";
    /**
     * Constant string value specifying the pop-up-screen type of view to be shown.
     */
    String POP_UP_SCREEN = "POP_UP_SCREEN";

    /**
     * Initializes and shows the screen(view) by given GameScreen object and its type.
     *
     * @param gameScreen The GameScreen object to be shown.
     * @param screenType String value specifying the type of screen to be shown; If SCREEN, then screenPlayer is used; Otherwise, ViewAdapter is used.
     * @see ScreenPlayer#initScreen(GameScreen, String)
     * @see ViewAdapter#initScreen(GameScreen, String)
     */
    void initScreen(GameScreen gameScreen, String screenType);
}
