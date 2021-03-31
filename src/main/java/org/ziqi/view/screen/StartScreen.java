package org.ziqi.view.screen;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.control.screenController.StartScreenController;
import org.ziqi.gameEngine.base.GameScreen;

import java.io.InputStream;

/**
 * StartScreen class as type of SCREEN view as home page of the app.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer#SCREEN
 */
public class StartScreen extends GameScreen {

    /**
     * StartScreen constructor: on instantiating,
     * 1. 'StartScreenView' fxml file is rendered and a StartScreenController is returned.
     * 2. text font, animations are set for this screen with the help of ResourceManager.
     *
     * @see #getInitializable(String)
     */
    public StartScreen() {
        StartScreenController startScreenController = (StartScreenController) this.getInitializable("StartScreenView");

        // graphics
        // set game title font
        InputStream fontStream = GameEngine.getInstance().getM_ResourceManager().loadFont("TitlePixelFont.ttf");
        Font font = Font.loadFont(fontStream, 40);
        startScreenController.getM_GameTitle().setFont(font);

        // set button font
        fontStream = GameEngine.getInstance().getM_ResourceManager().loadFont("ButtonPixelFont.ttf");
        font = Font.loadFont(fontStream, 14);
        for (Node node : startScreenController.getM_MenuPanel().lookupAll(".Button"))
            ((Text) node).setFont(font);

        // set title animation
        makeFadeTransition(new FadeTransition(), startScreenController.getM_MenuPane(), 500, 0.5, 0.3);
        makeTranslateTransition(new TranslateTransition(), startScreenController.getM_GameTitle(), 700, 0, 8);
    }
}
