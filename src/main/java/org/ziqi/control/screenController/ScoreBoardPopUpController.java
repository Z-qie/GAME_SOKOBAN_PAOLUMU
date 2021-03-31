package org.ziqi.control.screenController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.gameEngine.manager.DataManager;
import org.ziqi.gameEngine.viewPlayer.ViewPlayer;
import org.ziqi.model.ScoreRecord;
import org.ziqi.view.popUp.AlertPopUp;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ScoreBoardPopUpController class to register and handle event of the ScoreBoardPopUp.
 *
 * @author Ziqi Yang
 * @see org.ziqi.view.popUp.ScoreBoardPopUp
 */
public class ScoreBoardPopUpController implements Initializable {

    @FXML
    private AnchorPane m_FxmlRoot;
    @FXML
    private Text m_TimeString;
    @FXML
    private Text m_MoveCountString;
    @FXML
    private Text m_FTString;
    @FXML
    private Text m_MMString;
    @FXML
    private VBox m_InputArea;
    @FXML
    private VBox m_ButtonArea;
    @FXML
    private TextField m_PlayerInput;
    @FXML
    private StackPane m_SaveButton;
    @FXML
    private StackPane m_NextLevelButton;
    @FXML
    private StackPane m_RestartButton;
    @FXML
    private StackPane m_BackHomeButton;

    /**
     * Reference to GameEngine instance.
     */
    private final GameEngine m_GameEngine = GameEngine.getInstance();

    /**
     * The high-score record of current level.
     */
    private ScoreRecord topRecord;

    /**
     * Integer value specifying the fastest time of the level.
     */
    private int fastestTime;

    /**
     * Integer value specifying the minimum move of the level.
     */
    private int minimumMove;

    /**
     * Integer value specifying the player's completion time of the level.
     */
    private int playerTime;

    /**
     * Integer value specifying the player's completion move count of the level.
     */
    private int playerMove;

    /**
     * Gets the root generated from fxml file.
     *
     * @return Root generated from fxml file is returned to caller.
     */
    public AnchorPane getM_FxmlRoot() {
        return m_FxmlRoot;
    }

    /**
     * Gets the save button generated from fxml file.
     *
     * @return Save button as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_SaveButton() {
        return m_SaveButton;
    }

    /**
     * Gets the next level button generated from fxml file.
     *
     * @return Next level button as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_NextLevelButton() {
        return m_NextLevelButton;
    }

    /**
     * Gets the restart button generated from fxml file.
     *
     * @return Restart button as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_RestartButton() {
        return m_RestartButton;
    }

    /**
     * Gets the back home button generated from fxml file.
     *
     * @return Back home button as StackPane generated from fxml file is returned to caller.
     */
    public StackPane getM_BackHomeButton() {
        return m_BackHomeButton;
    }

    /**
     * When the score board pop up view is initialized from fxml file, GameEngine calls DataManger to read record from file and show them to player.<br />
     * If the player break any record of the current level, input area is initialize to ask player's name to record; <br />
     * Otherwise, options are provided for user to restart/ go to next level or go back to start screen.
     *
     * @param location  URL representing a Uniform Resource Locator of fxml file.
     * @param resources Resource bundles contain locale-specific objects of fxml file.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // update score board.
        m_GameEngine.getM_DataManager().readScoreRecords();
        showPlayerScore();
        getRecordOfCurrentLevel();
        if (canUpdateRecord())
            enableInputArea();
        else
            enableButtonArea();
    }

    /**
     * Generate messages to inform user the history record and user's record.
     */
    private void showPlayerScore() {
        playerTime = m_GameEngine.getM_DataManager().getM_secondTimer();
        playerMove = m_GameEngine.getM_DataManager().getM_MoveCount();
        m_TimeString.setText("Your Time: " + m_GameEngine.getM_DataManager().getM_TimeString().getValue());
        m_MoveCountString.setText("Your Move: " + playerMove + " Steps");
    }

    /**
     * Gets the high-score record from DataManager of the current level.
     *
     * @see DataManager#getTopRecord()
     */
    private void getRecordOfCurrentLevel() {
        topRecord = m_GameEngine.getM_DataManager().getTopRecord();
        fastestTime = Integer.parseInt(topRecord.getM_FastestTime());
        minimumMove = Integer.parseInt(topRecord.getM_MinimumMove());
    }

    /**
     * Checks if the user breaks any record and show corresponding message to inform user.
     *
     * @return If any record is broken, true is returned to inform user to input name; Otherwise, false is returned.
     */
    private boolean canUpdateRecord() {
        boolean canUpdateRecord = false;
        if (playerTime < fastestTime || fastestTime == -1) {
            fastestTime = playerTime;
            m_FTString.setText("You make new record of fastest time!");
            canUpdateRecord = true;
        } else {
            m_FTString.setText("Fastest Time: " + fastestTime + " By " + topRecord.getM_PlayerOfFT());
        }

        if (playerMove < minimumMove || minimumMove == -1) {
            minimumMove = playerMove;
            m_MMString.setText("You make new record of minimum move!");
            canUpdateRecord = true;
        } else {
            m_MMString.setText("Minimum Move" + " By " + topRecord.getM_PlayerOfMM());
        }
        return canUpdateRecord;
    }

    /**
     * Enables the input area to ask for user's name if any record is broken. <br />
     * Input valid check is used here by calling isValidName() method. <br />
     * Once user input the name successfully, button area is initialized to provide user more options to restart/ go to next level or go back to start screen.
     *
     * @see #isValidName(String)
     * @see #enableButtonArea()
     * @see Node#setVisible(boolean)
     */
    private void enableInputArea() {
        m_InputArea.setVisible(true);
        m_ButtonArea.setVisible(false);

        // set saving name button clicking handler
        m_SaveButton.setOnMouseClicked(e -> {
            m_GameEngine.getM_MusicManager().playButtonClickMusic();
            String playerName = m_PlayerInput.getText(); // get input
            if (isValidName(playerName)) {
                m_GameEngine.getM_DataManager().updateRecord(fastestTime, minimumMove, playerName, topRecord);
                enableButtonArea();
            } else
                m_GameEngine.getM_ScreenPlayer().initScreen(new AlertPopUp("Invalid Name", "Name has to be 1-16 word characters!"), ViewPlayer.POP_UP_SCREEN);
        });
    }

    /**
     * Enables button area to provide user more options to restart/ go to next level or go back to start screen.
     *
     * @see Node#setVisible(boolean)
     */
    private void enableButtonArea() {
        m_InputArea.setVisible(false);
        m_ButtonArea.setVisible(true);

        // set NextLevel handler
        m_NextLevelButton.setOnMouseClicked(e -> {
            m_GameEngine.getM_MusicManager().playButtonClickMusic();
            m_GameEngine.getM_MusicManager().playGamingMusic();
            m_GameEngine.getM_LevelManager().nextLevel();
            m_GameEngine.getM_DataManager().resetMoveCount();
            m_GameEngine.updateGamingScreen();
            popOff();
        });
        // set Restart clicking handler
        m_RestartButton.setOnMouseClicked(e -> {
            m_GameEngine.getM_MusicManager().playButtonClickMusic();
            m_GameEngine.getM_MusicManager().playGamingMusic();
            m_GameEngine.getM_LevelManager().getM_CurrentLevel().refreshLevel();
            m_GameEngine.updateGamingScreen();
            popOff();
        });
        // set BackHome clicking handler
        m_BackHomeButton.setOnMouseClicked(e -> {
            m_GameEngine.getM_MusicManager().playButtonClickMusic();
            popOff();
            m_GameEngine.toStartScreen();
        });
    }

    /**
     * Check if the user input is valid.
     *
     * @param playerName String value specifying the name of user.
     * @return True is returned if the input is from 1-16 word char; Otherwise, false is returned.
     */
    private boolean isValidName(String playerName) {
        return playerName.matches("\\w{1,16}");
    }

    /**
     * Pops off the score board pop up screen by itself once user clicked any button to do further operation.
     */
    private void popOff() {
        ((Stage) (m_FxmlRoot.getScene().getWindow())).close();
    }

}