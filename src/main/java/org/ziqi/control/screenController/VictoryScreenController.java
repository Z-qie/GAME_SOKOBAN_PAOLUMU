package org.ziqi.control.screenController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.ziqi.gameEngine.GameEngine;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * VictoryScreenController class to register and handle event of the VictoryScreen.
 *
 * @author Ziqi Yang
 * @see org.ziqi.view.screen.VictoryScreen
 */
public class VictoryScreenController  implements Initializable {
    @FXML
    private AnchorPane m_FxmlRoot;
    @FXML
    private StackPane m_SetChoosingButton;
    @FXML
    private StackPane m_BackHomeButton;
    @FXML
    private StackPane m_Title;

    /**
     * Reference to GameEngine instance.
     */
    private final GameEngine m_GameEngine = GameEngine.getInstance();

    /**
     * Gets the root generated from fxml file.
     *
     * @return Root generated from fxml file is returned to caller.
     */
    public AnchorPane getM_FxmlRoot() {
        return m_FxmlRoot;
    }

    /**
     * Gets the SetChoosing Button as StackPane generated from fxml file.
     *
     * @return SetChoosing Button as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_SetChoosingButton() {
        return m_SetChoosingButton;
    }

    /**
     * Gets the Back home Button as StackPane generated from fxml file.
     *
     * @return Back home Button as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_BackHomeButton() {
        return m_BackHomeButton;
    }

    /**
     * Gets the victory title as StackPane generated from fxml file.
     *
     * @return Victory title as StackPane  generated from fxml file is returned to caller.
     */
    public StackPane getM_Title() {
        return m_Title;
    }

    /**
     * On initializing: Register events and animations onto each buttons.
     *
     * @param location  URL representing a Uniform Resource Locator of fxml file.
     * @param resources Resource bundles contain locale-specific objects of fxml file.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m_BackHomeButton.setOnMouseClicked(e -> {
            m_GameEngine.getM_MusicManager().playButtonClickMusic();
            m_GameEngine.toStartScreen();
        });
        m_SetChoosingButton.setOnMouseClicked(e -> {
            m_GameEngine.getM_MusicManager().playButtonClickMusic();
            m_GameEngine.toLevelSetScreen();
        });
    }
}
