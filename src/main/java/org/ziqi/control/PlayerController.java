package org.ziqi.control;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.model.*;
import java.awt.*;

/**
 * PlayerController class implementing EventHandler<KeyEvent> to handle all user input when playing gaming.
 * This class will be registered and subscribed/unsubscribed onto the game when needed by EventManager.
 *
 * @author Ziqi Yang-modified
 * @see org.ziqi.gameEngine.manager.EventManager
 */
public class PlayerController implements EventHandler<KeyEvent> {

    /**
     * Handles a valid user keycode input and update the state of current level, including Player and other layers of GameObjects. <br />
     * After updating the state of level, gaming screen is updates by informing GameEngine to call screenPlayer to update screen correspondingly: <br />
     * 1. If the level completed after this handling, screenPlayer will pop up score board to indicate user.
     * 2. if the level completed and the whole set is end, screenPlayer will initialize victory screen to indicate user.
     * 3. Otherwise, the screenPlayer informs GamingScreenController to update screen.
     *
     * @param keyEvent KeyEvent specifying the user input.
     * @see org.ziqi.control.screenController.GamingScreenController
     * @see GameEngine#toVictoryScreen()
     * @see GameEngine#popUpScoreBoard()
     * @see GameEngine#updateGamingScreen()
     * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        GameEngine gameEngine =  GameEngine.getInstance();
        Player player = gameEngine.getM_LevelManager().getM_CurrentLevel().getM_Player();
        Level currentLevel = gameEngine.getM_LevelManager().getM_CurrentLevel();
        boolean moved = false;

        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (keyEvent.getCode()) {
                case UP:
                    moved = player.move(new Point(-1, 0));
                    player.updatePlayerStatus(keyEvent.getCode(), new Point(-1, 0));
                    break;

                case RIGHT:
                    moved = player.move(new Point(0, 1));
                    player.updatePlayerStatus(keyEvent.getCode(), new Point(0, 1));
                    break;

                case DOWN:
                    moved = player.move(new Point(1, 0));
                    player.updatePlayerStatus(keyEvent.getCode(), new Point(1, 0));
                    break;

                case LEFT:
                    moved = player.move(new Point(0, -1));
                    player.updatePlayerStatus(keyEvent.getCode(), new Point(0, -1));
                    break;

                case SPACE:
                    player.updatePlayerStatus(keyEvent.getCode(), new Point(0, 0));

                default:
                    break;
            }

            GameEngine.getInstance().updateGamingScreen();

            if (moved && currentLevel.isLevelComplete())
                if (gameEngine.getM_LevelManager().getM_CurrentLevelIndex() == gameEngine.getM_LevelManager().getM_CurrentSet().size())
                    GameEngine.getInstance().toVictoryScreen();
                else
                    gameEngine.popUpScoreBoard();
        }
    }
}
