package org.ziqi.view.popUp;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.control.screenController.ScoreBoardPopUpController;
import org.ziqi.gameEngine.viewPlayer.ViewPlayer;
import org.ziqi.gameEngine.base.GameScreen;
import org.ziqi.gameEngine.base.PopUpScreen;

import java.io.InputStream;


/**
 * ScoreBoardPopUp class as type of POP_UP_SCREEN view when user complete a level.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer#POP_UP_SCREEN
 */
public class ScoreBoardPopUp extends PopUpScreen {

    private final ScoreBoardPopUpController m_ScoreBoardPopUpController;
    GameEngine m_GameEngine = GameEngine.getInstance();

    /**
     * ScoreBoardPopUp constructor initializes the title of pop up screen and sets up text fonts, animations and sound effects of the screen.
     * This is called by ViewAdapter which is called by ScreenPlayer in an adapter pattern.
     *
     * @see org.ziqi.gameEngine.viewPlayer.ViewPlayer
     * @see org.ziqi.gameEngine.viewPlayer.ViewAdapter
     * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer
     * @see PopUpScreen
     */
    public ScoreBoardPopUp() {
        this.m_Title = "ScoreBoard";
        m_ScoreBoardPopUpController = (ScoreBoardPopUpController) this.getInitializable("ScoreBoardPopUpView");

        // graphics
        // set game text font
        InputStream fontStream = m_GameEngine.getM_ResourceManager().loadFont("TextPixelFont.ttf");
        Font font = Font.loadFont(fontStream, 10);
        for (Node node : m_ScoreBoardPopUpController.getM_FxmlRoot().lookupAll("Text"))
            ((Text) node).setFont(font);

        // set game title font
        fontStream =m_GameEngine.getM_ResourceManager().loadFont("ButtonPixelFont.ttf");
        font = Font.loadFont(fontStream, 15);
        for (Node node : m_ScoreBoardPopUpController.getM_FxmlRoot().lookupAll(".Title")) {
            ((Text) node).setFont(font);
            TranslateTransition tt = new TranslateTransition();
            GameScreen.makeTranslateTransition(tt, node, 500, 0, 5);
        }
        // set button font
        fontStream = m_GameEngine.getM_ResourceManager().loadFont("TextPixelFont.ttf");
        font = Font.loadFont(fontStream, 8);
        for (Node node : m_ScoreBoardPopUpController.getM_FxmlRoot().lookupAll(".Button"))
            ((Text) node).setFont(font);


        // set button effect
        TranslateTransition ttSaveButton = new TranslateTransition();
        m_ScoreBoardPopUpController.getM_SaveButton().setOnMouseEntered(e -> {
            m_GameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttSaveButton, m_ScoreBoardPopUpController.getM_SaveButton(), 200, 0, 3);
        });
        m_ScoreBoardPopUpController.getM_SaveButton().setOnMouseExited(e -> ttSaveButton.stop());

        TranslateTransition ttNextLevelButton = new TranslateTransition();
        m_ScoreBoardPopUpController.getM_NextLevelButton().setOnMouseEntered(e -> {
            m_GameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttNextLevelButton, m_ScoreBoardPopUpController.getM_NextLevelButton(), 200, 0, 3);
        });
        m_ScoreBoardPopUpController.getM_NextLevelButton().setOnMouseExited(e -> ttNextLevelButton.stop());

        TranslateTransition ttRestartButton = new TranslateTransition();
        m_ScoreBoardPopUpController.getM_RestartButton().setOnMouseEntered(e -> {
            m_GameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttRestartButton, m_ScoreBoardPopUpController.getM_RestartButton(), 200, 0, 3);
        });
        m_ScoreBoardPopUpController.getM_RestartButton().setOnMouseExited(e -> ttRestartButton.stop());

        TranslateTransition ttBackHomeButton = new TranslateTransition();
        m_ScoreBoardPopUpController.getM_BackHomeButton().setOnMouseEntered(e -> {
            m_GameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttBackHomeButton, m_ScoreBoardPopUpController.getM_BackHomeButton(), 200, 0, 3);
        });
        m_ScoreBoardPopUpController.getM_BackHomeButton().setOnMouseExited(e -> ttBackHomeButton.stop());
    }

    /**
     * Override popUp method sets and shows the pop up score board screen to inform user's record and high score from history.
     *
     * @see ScoreBoardPopUpController
     */
    @Override
    public void popUp() {
        m_PopUpWindow.initModality(Modality.APPLICATION_MODAL);
        m_PopUpWindow.setTitle(m_Title);
        Scene scene = new Scene(m_ScoreBoardPopUpController.getM_FxmlRoot());
        m_PopUpWindow.setScene(scene);
        m_PopUpWindow.setResizable(false);
        // Shows this stage and waits for it to be hidden (closed) before returning to the caller.
        m_PopUpWindow.setOnCloseRequest(e -> {
            e.consume(); // Marks this Event as consumed. This stops its further propagation.
            m_GameEngine.getM_ScreenPlayer().initScreen(new AlertPopUp("Oops", "Please use buttons provided!"), ViewPlayer.POP_UP_SCREEN);
        });
        m_PopUpWindow.showAndWait();
    }
}
