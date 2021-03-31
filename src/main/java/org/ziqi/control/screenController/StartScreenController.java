package org.ziqi.control.screenController;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.Utils;
import org.ziqi.gameEngine.base.GameScreen;
import org.ziqi.view.GraphicUnit;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * StartScreenController class to register and handle event of the StartScreen.
 *
 * @author Ziqi Yang
 * @see org.ziqi.view.screen.StartScreen
 */
public class StartScreenController implements Initializable {

    @FXML
    private Text m_GameTitle;
    @FXML
    private Rectangle m_MenuPane;
    @FXML
    private StackPane m_NewGameButton;
    @FXML
    private StackPane m_ScoreBoardButton;
    @FXML
    private StackPane m_QuitButton;
    @FXML
    private StackPane m_LevelSetButton;
    @FXML
    private AnchorPane m_FxmlRoot;
    @FXML
    private VBox m_MenuPanel;

    /**
     * Gets the MenuPane as Rectangle generated from fxml file.
     *
     * @return MenuPane as Rectangle generated from fxml file is returned to caller.
     */
    public Rectangle getM_MenuPane() {
        return m_MenuPane;
    }

    /**
     * Gets the GameTitle as Text generated from fxml file.
     *
     * @return GameTitle as Text generated from fxml file is returned to caller.
     */
    public Text getM_GameTitle() {
        return m_GameTitle;
    }

    /**
     * Gets the root generated from fxml file.
     *
     * @return Root generated from fxml file is returned to caller.
     */
    public AnchorPane getM_FxmlRoot() {
        return m_FxmlRoot;
    }

    /**
     * Gets the MenuPanel as VBox generated from fxml file.
     *
     * @return MenuPanel as VBox generated from fxml file is returned to caller.
     */
    public VBox getM_MenuPanel() {
        return m_MenuPanel;
    }

    /**
     * On initializing: Register events and animations onto each buttons.
     *
     * @param location  URL representing a Uniform Resource Locator of fxml file.
     * @param resources Resource bundles contain locale-specific objects of fxml file.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameEngine gameEngine = GameEngine.getInstance();
        // set gaming screen button
        TranslateTransition ttNewGameButton = new TranslateTransition();
        m_NewGameButton.setOnMouseEntered(e -> {
            GameScreen.makeTranslateTransition(ttNewGameButton, m_NewGameButton, 400, 0, 8);
            gameEngine.getM_MusicManager().playButtonOverMusic();
        });
        m_NewGameButton.setOnMouseExited(e -> ttNewGameButton.stop());
        m_NewGameButton.setOnMouseClicked(e -> {
            InputStream setInputStream = gameEngine.getM_ResourceManager().getLevelSet(Utils.SET_NAMES[0]);
            gameEngine.getM_LevelManager().loadSet(setInputStream, Utils.SET_NAMES[0]);
            gameEngine.setM_GamingBG(gameEngine.getM_ResourceManager().loadImage(Utils.SET_BG_NAMES[0]));
            GraphicUnit.setM_WallColorHue(GraphicUnit.HUE_GROUND);
            gameEngine.toGamingScreen();
            gameEngine.getM_MusicManager().playButtonClickMusic();
        });

        // set score board button
        TranslateTransition ttScoreBoardButton = new TranslateTransition();
        m_ScoreBoardButton.setOnMouseEntered(e -> {
            gameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttScoreBoardButton, m_ScoreBoardButton, 400, 0, 8);
        });
        m_ScoreBoardButton.setOnMouseExited(e -> ttScoreBoardButton.stop());
        m_ScoreBoardButton.setOnMouseClicked(e -> {
            gameEngine.getM_MusicManager().playButtonClickMusic();
            gameEngine.toScoreBoardScreen();
        });

        // set quit button
        TranslateTransition ttLevelSetButton = new TranslateTransition();
        m_LevelSetButton.setOnMouseEntered(e -> {
            gameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttLevelSetButton, m_LevelSetButton, 400, 0, 8);
        });
        m_LevelSetButton.setOnMouseExited(e -> ttLevelSetButton.stop());
        m_LevelSetButton.setOnMouseClicked(e -> {
            gameEngine.getM_MusicManager().playButtonClickMusic();
            gameEngine.toLevelSetScreen();
        });

        // set quit button
        TranslateTransition ttQuitButton = new TranslateTransition();
        m_QuitButton.setOnMouseEntered(e -> {
            gameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttQuitButton, m_QuitButton, 400, 0, 8);
        });
        m_QuitButton.setOnMouseExited(e -> ttQuitButton.stop());
        m_QuitButton.setOnMouseClicked(e -> {
            gameEngine.getM_MusicManager().playButtonClickMusic();
            gameEngine.quit();
        });

    }
}
