package org.ziqi.view.screen;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.ziqi.gameEngine.GameEngine;

import org.ziqi.control.screenController.VictoryScreenController;
import org.ziqi.gameEngine.base.GameScreen;

import java.io.InputStream;

/**
 * VictoryScreen class as type of SCREEN view when user completed all levels of a set.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer#SCREEN
 */
public class VictoryScreen extends GameScreen {

    /**
     * VictoryScreen constructor: on instantiating,
     * 1. 'VictoryScreenView' fxml file is rendered and a VictoryScreenController is returned.
     * 2. text font, animations, sound effects are set for this screen with the help of ResourceManager and MusicManager.
     *
     * @see #getInitializable(String)
     */
    public VictoryScreen() {
        VictoryScreenController victoryScreenController = (VictoryScreenController) this.getInitializable("VictoryScreenView");

        // graphics
        // set game text font
        InputStream fontStream = GameEngine.getInstance().getM_ResourceManager().loadFont("TextPixelFont.ttf");
        Font font = Font.loadFont(fontStream, 10);
        for (Node node : victoryScreenController.getM_FxmlRoot().lookupAll(".Text"))
            ((Text) node).setFont(font);

        fontStream = GameEngine.getInstance().getM_ResourceManager().loadFont("TitlePixelFont.ttf");
        font = Font.loadFont(fontStream, 23);
        for (Node node :  victoryScreenController.getM_Title().lookupAll(".Title"))
            ((Text) node).setFont(font);

        // set title animation
        TranslateTransition ttTitle = new TranslateTransition();
        GameScreen.makeTranslateTransition(ttTitle, victoryScreenController.getM_Title(), 1000, 0, 3);


        // set back home button animation
        TranslateTransition ttBackHomeButton = new TranslateTransition();
        victoryScreenController.getM_BackHomeButton().setOnMouseEntered(e -> {
            GameEngine.getInstance().getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttBackHomeButton, victoryScreenController.getM_BackHomeButton(), 200, 0, 2);
        });
        victoryScreenController.getM_BackHomeButton().setOnMouseExited(e -> ttBackHomeButton.stop());

        // set back home button animation
        TranslateTransition ttSetChoosingButton = new TranslateTransition();
        victoryScreenController.getM_SetChoosingButton().setOnMouseEntered(e -> {
            GameEngine.getInstance().getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttSetChoosingButton, victoryScreenController.getM_SetChoosingButton(), 200, 0, 2);
        });
        victoryScreenController.getM_SetChoosingButton().setOnMouseExited(e -> ttSetChoosingButton.stop());
    }
}
