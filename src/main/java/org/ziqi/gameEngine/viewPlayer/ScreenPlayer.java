package org.ziqi.gameEngine.viewPlayer;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.Debugger;
import org.ziqi.gameEngine.base.GameScreen;
import org.ziqi.view.screen.GamingScreen;

/**
 * ScreenPlayer class takes charge of initializing game views by given type of screens. <br />
 * This follows an adapter design pattern. <br />
 *
 * @author Ziqi Yang
 * @see ViewPlayer
 * @see ViewAdapter
 */
public class ScreenPlayer extends Stage implements ViewPlayer {
    /**
     * GameScreen object that is currently shown or initialed by the ScreenPlayer.
     */
    private GameScreen m_CurrentScreen;

    /**
     * Initializes and shows the screen(view) by given GameScreen object and its type.
     *
     * @param gameScreen The GameScreen object to be shown.
     * @param screenType String value specifying the type of screen to be shown; If SCREEN, then screenPlayer is used; Otherwise, ViewAdapter is used.
     */
    @Override
    public void initScreen(GameScreen gameScreen, String screenType) {
        if (screenType.equals(SCREEN)) {
            this.m_CurrentScreen = gameScreen;
            Scene scene = new Scene(gameScreen.getM_Root());
            Debugger.debugBegin(false, "Rendering Stage..");
            this.setScene(scene);
            this.setTitle("Name: " + GameEngine.GAME_NAME);
            this.setResizable(false);
            this.setOnCloseRequest(e -> {
                e.consume(); // Marks this Event as consumed. This stops its further propagation.
                GameEngine.getInstance().quit();
            });
            this.show();
        } else if (screenType.equals(POP_UP_SCREEN)) {
            ViewAdapter viewAdapter = new ViewAdapter();
            viewAdapter.initScreen(gameScreen, screenType);
        }
    }

    /**
     * Updates the control panel and layers of graphic units of the gaming screen. <br />
     * This method will be called when a user is playing the game(gaming screen is showing) and inputs a key that is handled; <br />
     * This method will be called by GameEngine as assigned task. Further operation will be handled by GamingScreen Controller and GraphicUnitRenderer.
     *
     * @see GameEngine#updateGamingScreen()
     * @see org.ziqi.control.screenController.GamingScreenController
     * @see org.ziqi.gameEngine.GraphicUnitRenderer
     * @see org.ziqi.view.GraphicUnit
     */
    public void updateGamingScreen() {
        ((GamingScreen) m_CurrentScreen).getM_GamingScreenController().updateGamingScreen();
    }

}
