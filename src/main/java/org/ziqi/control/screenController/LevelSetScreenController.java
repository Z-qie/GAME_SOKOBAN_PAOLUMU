package org.ziqi.control.screenController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.Utils;
import org.ziqi.view.GraphicUnit;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * LevelSetScreenController class to register and handle event of the LevelSetScreen.
 *
 * @author Ziqi Yang
 * @see org.ziqi.view.screen.LevelSetScreen
 */
public class LevelSetScreenController implements Initializable {

    @FXML
    private AnchorPane m_FxmlRoot;
    @FXML
    private Text m_SetName;
    @FXML
    private Text m_BackHomeButton;
    @FXML
    private Text m_Prompt;
    @FXML
    private ImageView m_Set1;
    @FXML
    private ImageView m_Set2;
    @FXML
    private ImageView m_Set3;
    @FXML
    private ImageView m_Set4;

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
     * Gets the set name Text generated from fxml file.
     *
     * @return Set name as Text generated from fxml file is returned to caller.
     */
    public Text getM_SetName() {
        return m_SetName;
    }

    /**
     * Gets the back home button generated from fxml file.
     *
     * @return Back home button as Text generated from fxml file is returned to caller.
     */
    public Text getM_BackHomeButton() {
        return m_BackHomeButton;
    }

    /**
     * Gets the prompt Text generated from fxml file.
     *
     * @return Prompt as Text generated from fxml file is returned to caller.
     */
    public Text getM_Prompt() {
        return m_Prompt;
    }

    /**
     * Gets the set1 ImageView generated from fxml file.
     *
     * @return Set1 ImageView generated from fxml file is returned to caller.
     */
    public ImageView getM_Set1() {
        return m_Set1;
    }

    /**
     * Gets the set2 ImageView generated from fxml file.
     *
     * @return Set2 ImageView generated from fxml file is returned to caller.
     */
    public ImageView getM_Set2() {
        return m_Set2;
    }

    /**
     * Gets the set3 ImageView generated from fxml file.
     *
     * @return Set3 ImageView generated from fxml file is returned to caller.
     */
    public ImageView getM_Set3() {
        return m_Set3;
    }

    /**
     * Gets the set4 ImageView generated from fxml file.
     *
     * @return Set4 ImageView generated from fxml file is returned to caller.
     */
    public ImageView getM_Set4() {
        return m_Set4;
    }

    /**
     * Register events for each buttons.
     *
     * @param location  URL representing a Uniform Resource Locator of fxml file.
     * @param resources Resource bundles contain locale-specific objects of fxml file.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // set back home button clicking handler
        m_BackHomeButton.setOnMouseClicked(e -> m_GameEngine.toStartScreen());

        m_Set1.setOnMouseClicked(e -> {
            InputStream setInputStream = m_GameEngine.getM_ResourceManager().getLevelSet(Utils.SET_NAMES[0]);
            m_GameEngine.getM_LevelManager().loadSet(setInputStream, Utils.SET_NAMES[0]);
            m_GameEngine.setM_GamingBG(m_GameEngine.getM_ResourceManager().loadImage(Utils.SET_BG_NAMES[0]));
            GraphicUnit.setM_WallColorHue(GraphicUnit.HUE_GROUND);
            m_GameEngine.toGamingScreen();
        });

        m_Set2.setOnMouseClicked(e -> {
            InputStream setInputStream = m_GameEngine.getM_ResourceManager().getLevelSet(Utils.SET_NAMES[1]);
            m_GameEngine.getM_LevelManager().loadSet(setInputStream, Utils.SET_NAMES[1]);
            m_GameEngine.setM_GamingBG(m_GameEngine.getM_ResourceManager().loadImage(Utils.SET_BG_NAMES[1]));
            GraphicUnit.setM_WallColorHue(GraphicUnit.HUE_EMERALD);
            m_GameEngine.toGamingScreen();
        });

        m_Set3.setOnMouseClicked(e -> {
            InputStream setInputStream = m_GameEngine.getM_ResourceManager().getLevelSet(Utils.SET_NAMES[2]);
            m_GameEngine.getM_LevelManager().loadSet(setInputStream, Utils.SET_NAMES[2]);
            m_GameEngine.setM_GamingBG(m_GameEngine.getM_ResourceManager().loadImage(Utils.SET_BG_NAMES[2]));
            GraphicUnit.setM_WallColorHue(GraphicUnit.HUE_PAOLUMU);
            m_GameEngine.toGamingScreen();
        });

        m_Set4.setOnMouseClicked(e -> {
            InputStream setInputStream = m_GameEngine.getM_ResourceManager().getLevelSet(Utils.SET_NAMES[3]);
            m_GameEngine.getM_LevelManager().loadSet(setInputStream, Utils.SET_NAMES[3]);
            m_GameEngine.setM_GamingBG(m_GameEngine.getM_ResourceManager().loadImage(Utils.SET_BG_NAMES[3]));
            GraphicUnit.setM_WallColorHue(GraphicUnit.HUE_SAPPHIRE);
            m_GameEngine.toGamingScreen();
        });
    }
}
