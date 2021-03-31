package org.ziqi.view.screen;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.control.screenController.GamingScreenController;
import org.ziqi.gameEngine.base.GameScreen;

import java.io.InputStream;

/**
 * GamingScreen class as type of SCREEN view for user playing the game.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer#SCREEN
 */
public class GamingScreen extends GameScreen {

    /**
     * GamingScreenController object to register and handle event of the GamingScreen.
     */
    private final GamingScreenController m_GamingScreenController;

    /**
     * GamingScreen constructor: on instantiating,
     * 1. 'GamingScreenView' fxml file is rendered and a GamingScreenController is returned.
     * 2. text font, animations, sound effects are set for this screen with the help of ResourceManager, MusicManager.
     *
     * @see #getInitializable(String)
     */
    public GamingScreen() {
        m_GamingScreenController = (GamingScreenController) this.getInitializable("GamingScreenView");
        GameEngine gameEngine = GameEngine.getInstance();
        // graphics
        // set game text font
        InputStream fontStream = gameEngine.getM_ResourceManager().loadFont("TextPixelFont.ttf");
        Font font = Font.loadFont(fontStream, 11);
        for (Node node : m_GamingScreenController.getM_FxmlRoot().lookupAll(".Text"))
            ((Text) node).setFont(font);

        // set game title font
        fontStream = gameEngine.getM_ResourceManager().loadFont("TitlePixelFont.ttf");
        font = Font.loadFont(fontStream, 30);
        for (Node node : m_GamingScreenController.getM_FxmlRoot().lookupAll(".Title"))
            ((Text) node).setFont(font);

        // set button font
        fontStream = gameEngine.getM_ResourceManager().loadFont("TextPixelFont.ttf");
        font = Font.loadFont(fontStream, 11);
        for (Node node : m_GamingScreenController.getM_FxmlRoot().lookupAll(".Button"))
            ((Text) node).setFont(font);

        // set restart button animation
        TranslateTransition ttRestartButton = new TranslateTransition();
        m_GamingScreenController.getM_RestartButton().setOnMouseEntered(e -> {
            gameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttRestartButton, m_GamingScreenController.getM_RestartButton(), 200, 0, 2);
        });
        m_GamingScreenController.getM_RestartButton().setOnMouseExited(e -> ttRestartButton.stop());
        // set back home button animation
        TranslateTransition ttBackHomeButton = new TranslateTransition();
        m_GamingScreenController.getM_BackHomeButton().setOnMouseEntered(e -> {
            gameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttBackHomeButton, m_GamingScreenController.getM_BackHomeButton(), 200, 0, 2);
        });
        m_GamingScreenController.getM_BackHomeButton().setOnMouseExited(e -> ttBackHomeButton.stop());
        // set next level button animation
        TranslateTransition ttNextLevelButton = new TranslateTransition();
        m_GamingScreenController.getM_NextLevelButton().setOnMouseEntered(e -> {
            gameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttNextLevelButton, m_GamingScreenController.getM_NextLevelButton(), 200, 0, 2);
        });
        m_GamingScreenController.getM_NextLevelButton().setOnMouseExited(e -> ttNextLevelButton.stop());

        // set back ground image of gaming screen
        for (Node node : m_GamingScreenController.getM_FxmlRoot().lookupAll(".GamingBG"))
            ((ImageView) node).setImage(gameEngine.getM_GamingBG());


        // set title animation
//        makeFadeTransition(new FadeTransition(), m_GamingScreenController.getM_MenuPane(), 500, 0.5, 0.3);
        makeTranslateTransition(new TranslateTransition(), m_GamingScreenController.getM_Instruction(), 300, 0, 4);
    }

    /**
     * Gets the GamingScreenController reference of this screen.
     *
     * @return GamingScreenController is returned to caller.
     */
    public GamingScreenController getM_GamingScreenController() {
        return m_GamingScreenController;
    }

}
