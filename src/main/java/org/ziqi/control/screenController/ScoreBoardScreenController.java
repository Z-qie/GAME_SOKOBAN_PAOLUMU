package org.ziqi.control.screenController;

import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.gameEngine.base.GameScreen;
import org.ziqi.gameEngine.manager.DataManager;
import org.ziqi.model.ScoreRecord;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ScoreBoardScreenController class to register and handle event of the ScoreBoardScreen.
 *
 * @author Ziqi Yang
 * @see org.ziqi.view.screen.ScoreBoardScreen
 */
public class ScoreBoardScreenController implements Initializable {

    @FXML
    private AnchorPane m_FxmlRoot;
    @FXML
    private TableColumn<ScoreRecord, SimpleStringProperty> m_SetNameColumn;
    @FXML
    private TableColumn<ScoreRecord, SimpleStringProperty> m_LevelNameColumn;
    @FXML
    private TableColumn<ScoreRecord, SimpleStringProperty> m_FTColumn;
    @FXML
    private TableColumn<ScoreRecord, SimpleStringProperty> m_PlayerFTColumn;
    @FXML
    private TableColumn<ScoreRecord, SimpleStringProperty> m_MMColumn;
    @FXML
    private TableColumn<ScoreRecord, SimpleStringProperty> m_PlayerMMColumn;
    @FXML
    private TableView<ScoreRecord> m_ScoreTable;
    @FXML
    private Text m_BackButton;

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
     * On initializing:
     * 1. Reads record data from outside file by calling DataManager and inserts then into tableView sorted by set name and level name respectively.
     * 2. Register events for each buttons.
     *
     * @param location  URL representing a Uniform Resource Locator of fxml file.
     * @param resources Resource bundles contain locale-specific objects of fxml file.
     * @see DataManager#readScoreRecords()
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // set table content
        ObservableList<ScoreRecord> scoreRecords = m_GameEngine.getM_DataManager().readScoreRecords();
        m_SetNameColumn.setCellValueFactory(new PropertyValueFactory<>("m_SetName"));
        m_LevelNameColumn.setCellValueFactory(new PropertyValueFactory<>("m_LevelName"));
        m_FTColumn.setCellValueFactory(new PropertyValueFactory<>("m_FastestTime"));
        m_PlayerFTColumn.setCellValueFactory(new PropertyValueFactory<>("m_PlayerOfFT"));
        m_MMColumn.setCellValueFactory(new PropertyValueFactory<>("m_MinimumMove"));
        m_PlayerMMColumn.setCellValueFactory(new PropertyValueFactory<>("m_PlayerOfMM"));
        m_ScoreTable.setItems(scoreRecords);

        // set button event
        TranslateTransition ttBackButton = new TranslateTransition();
        m_BackButton.setOnMouseEntered(e -> {
            m_GameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttBackButton, m_BackButton, 300, 0, 4);
        });
        m_BackButton.setOnMouseExited(e -> ttBackButton.stop());
        m_BackButton.setOnMouseClicked(e -> GameEngine.getInstance().toStartScreen());

        // set sort order
        m_ScoreTable.getSortOrder().addAll(m_SetNameColumn, m_LevelNameColumn);
    }
}
