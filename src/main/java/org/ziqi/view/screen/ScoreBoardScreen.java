package org.ziqi.view.screen;

import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.control.screenController.ScoreBoardScreenController;
import org.ziqi.gameEngine.base.GameScreen;

import java.io.InputStream;

/**
 * ScoreBoardScreen class as type of SCREEN view when user intends to check all high-score records.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer#SCREEN
 */
public class ScoreBoardScreen extends GameScreen {

    /**
     * ScoreBoardScreen constructor: on instantiating,
     * 1. 'ScoreBoardScreenView' fxml file is rendered and a ScoreBoardScreenController is returned.
     * 2. text fonts are set for this screen with the help of ResourceManager.
     *
     * @see #getInitializable(String)
     */
    public ScoreBoardScreen() {
        ScoreBoardScreenController scoreBoardScreenController = (ScoreBoardScreenController) this.getInitializable("ScoreBoardScreenView");

        // graphics
        // set game text font
        InputStream fontStream = GameEngine.getInstance().getM_ResourceManager().loadFont("TextPixelFont.ttf");
        Font font = Font.loadFont(fontStream, 20);
        for (Node node : scoreBoardScreenController.getM_FxmlRoot().lookupAll(".Text"))
            ((Text) node).setFont(font);
    }

}
