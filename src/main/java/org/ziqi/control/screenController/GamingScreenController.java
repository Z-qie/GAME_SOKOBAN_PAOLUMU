package org.ziqi.control.screenController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.gameEngine.GraphicUnitRenderer;
import org.ziqi.gameEngine.base.GameObject;
import org.ziqi.gameEngine.manager.DataManager;
import org.ziqi.model.Level;
import org.ziqi.view.GraphicUnit;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GamingScreenController class to register and handle event of the GamingScreen.
 *
 * @author Ziqi Yang
 * @see org.ziqi.view.screen.GamingScreen
 */
public class GamingScreenController implements Initializable {

    @FXML
    private AnchorPane m_FxmlRoot;
    @FXML
    private AnchorPane m_LevelPane;
    @FXML
    private Text m_SetName;
    @FXML
    private Text m_LevelName;
    @FXML
    private Text m_MoveCount;
    @FXML
    private StackPane m_RestartButton;
    @FXML
    private StackPane m_BackHomeButton;
    @FXML
    private StackPane m_NextLevelButton;
    @FXML
    private StackPane m_Instruction;
    @FXML
    private Text m_TimeString;

    /**
     * Constant double indicating the horizontal offset of game view when rendering layers of GameObjects onto screen.
     */
    private static final double GAME_VIEW_OFFSET_X = 120.0;

    /**
     * Constant double indicating the vertical offset of game view when rendering layers of GameObjects onto screen.
     */
    private static final double GAME_VIEW_OFFSET_Y = 80.0;

    /**
     * Reference to GameEngine instance.
     */
    private final GameEngine m_GameEngine = GameEngine.getInstance();

    /**
     * Reference to current level object.
     */
    private Level m_CurrentLevel;

    /**
     * Reference to layers of AnchorPane to render GraphicUnits on.
     *
     * @see GraphicUnit
     * @see GraphicUnitRenderer
     */
    private AnchorPane[] m_Layers;

    /**
     * Sets the name of set on text on control panel to show.
     *
     * @param m_SetName String value specifying the name of set.
     */
    public void setM_SetName(String m_SetName) {
        this.m_SetName.setText(m_SetName);
    }

    /**
     * Sets the name of level on text on control panel to show.
     *
     * @param m_LevelName String value specifying the name of level.
     */
    public void setM_LevelName(String m_LevelName) {
        this.m_LevelName.setText(m_LevelName);
    }

    /**
     * Sets the current move count as String on control panel to show.
     *
     * @param m_MoveCount Integer value from DataManager specifying the current move count of user.
     * @see DataManager#getM_MoveCount()
     */
    public void setM_MoveCount(Integer m_MoveCount) {
        this.m_MoveCount.setText(m_MoveCount.toString());
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
     * Gets the restart button generated from fxml file.
     *
     * @return Restart as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_RestartButton() {
        return m_RestartButton;
    }

    /**
     * Gets the BackHome button generated from fxml file.
     *
     * @return BackHome as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_BackHomeButton() {
        return m_BackHomeButton;
    }

    /**
     * Gets the next level button generated from fxml file.
     *
     * @return Next level as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_NextLevelButton() {
        return m_NextLevelButton;
    }

    /**
     * Gets the Instruction stackPane generated from fxml file.
     *
     * @return Instruction as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_Instruction() {
        return m_Instruction;
    }

    /**
     * When the gaming screen is initialized from fxml file, <br />
     * 1. Initialize layers of AnchorPane to render GraphicUnits on; <br />
     * 2. Generate the new level to be played; <br />
     * 3. Update the screen to render GraphicUnits.
     * 4. Register event for buttons.
     *
     * @param location  URL representing a Uniform Resource Locator of fxml file.
     * @param resources Resource bundles contain locale-specific objects of fxml file.
     * @see #initializeLayers()
     * @see #updateGamingScreen()
     * @see GraphicUnitRenderer
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // set layers for sprite graphics
        initializeLayers();

        // find the first level in the first set
        if (m_CurrentLevel == null)
            m_CurrentLevel = m_GameEngine.getM_LevelManager().nextLevel();
        // update level object
        m_CurrentLevel.refreshLevel();

        // update graphic layers on gaming screen
        updateGamingScreen();

        // bind timer display of the control board with the real timer thread of dataManager
        m_TimeString.textProperty().bind(m_GameEngine.getM_DataManager().getM_TimeString());

        // set restart button clicking handler
        m_RestartButton.setOnMouseClicked(e -> {
            m_GameEngine.getM_MusicManager().playButtonClickMusic();
            m_GameEngine.getM_MusicManager().playGamingMusic();

            m_GameEngine.getM_LevelManager().getM_CurrentLevel().refreshLevel();
            updateGamingScreen();
        });

        // set back home button clicking handler
        m_BackHomeButton.setOnMouseClicked(e -> {
            m_GameEngine.getM_MusicManager().playButtonClickMusic();
            m_GameEngine.toStartScreen();
        });

        // set next level button clicking handler
        m_NextLevelButton.setOnMouseClicked(e -> {
            m_GameEngine.getM_MusicManager().playButtonClickMusic();
            m_GameEngine.getM_MusicManager().playGamingMusic();
            m_GameEngine.getM_LevelManager().nextLevel();
            m_GameEngine.getM_DataManager().resetMoveCount();
            m_GameEngine.getM_LevelManager().getM_CurrentLevel().refreshLevel();
            updateGamingScreen();
        });
    }

    /**
     * Updates the states of both control panel and gaming view(layers of GraphicUnits)
     *
     * @see #updateControlPanel()
     * @see #updateGraphicLayers()
     */
    public void updateGamingScreen() {
        m_CurrentLevel = m_GameEngine.getM_LevelManager().getM_CurrentLevel();
        updateControlPanel();  // update control panel section of gaming screen
        updateGraphicLayers(); // update graphic layers section of gaming screen
    }

    /**
     * Updates the states of both control panel by update information of set name, level name and move count.
     */
    public void updateControlPanel() {
        // bind content of information panel
        this.setM_SetName(m_GameEngine.getM_LevelManager().getM_CurrentSetName());
        this.setM_LevelName(m_CurrentLevel.getM_LevelName());
        this.setM_MoveCount(m_GameEngine.getM_DataManager().getM_MoveCount());
    }

    /**
     * Updates graphic layers section for every GameObject of gaming screen by iterate through the level object and calling GraphicUnitRenderer to render.
     *
     * @see Level.LevelIterator
     * @see GraphicUnitRenderer#renderObjectToGraphic(GameObject[], AnchorPane[])
     */
    public void updateGraphicLayers() {
        Level.LevelIterator levelGridIterator = (Level.LevelIterator) m_CurrentLevel.iterator();
        for (int layer = GraphicUnit.LAYER_OF_FLOOR; layer < GraphicUnit.NUM_OF_LAYER; layer++)
            m_Layers[layer].getChildren().clear();
        while (levelGridIterator.hasNext())
            GraphicUnitRenderer.renderObjectToGraphic(levelGridIterator.next(), m_Layers);
    }

    /**
     * Initializes layers of anchorPane by setting its offset and adding to m_LevelPane.
     */
    private void initializeLayers() {
        m_Layers = new AnchorPane[GraphicUnit.NUM_OF_LAYER];
        for (int layer = GraphicUnit.LAYER_OF_FLOOR; layer < GraphicUnit.NUM_OF_LAYER; layer++) {
            m_Layers[layer] = new AnchorPane();
            AnchorPane.setTopAnchor(m_Layers[layer], GAME_VIEW_OFFSET_Y);
            AnchorPane.setLeftAnchor(m_Layers[layer], GAME_VIEW_OFFSET_X);
            m_LevelPane.getChildren().add(m_Layers[layer]);
        }
    }
}
