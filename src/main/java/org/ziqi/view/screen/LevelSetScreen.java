package org.ziqi.view.screen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.Utils;
import org.ziqi.control.screenController.LevelSetScreenController;
import org.ziqi.gameEngine.base.GameScreen;

import java.io.InputStream;

/**
 * LevelSetScreen class as type of SCREEN view when user intends to select a set to play.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer#SCREEN
 */
public class LevelSetScreen extends GameScreen {

    /**
     * Reference to GameEngine instance.
     */
    private final GameEngine m_GameEngine = GameEngine.getInstance();

    /**
     * LevelSetScreen constructor: on instantiating,
     * 1. 'LevelSetScreenView' fxml file is rendered and a LevelSetScreenController is returned.
     * 2. text font, animations, sound effects are set for this screen with the help of ResourceManager and MusicManager.
     *
     * @see #getInitializable(String)
     */
    public LevelSetScreen() {
        LevelSetScreenController levelSetScreenController = (LevelSetScreenController) this.getInitializable("LevelSetScreenView");

        // graphics
        // set game text font
        InputStream fontStream = GameEngine.getInstance().getM_ResourceManager().loadFont("TextPixelFont.ttf");
        Font font = Font.loadFont(fontStream, 20);
        for (Node node : levelSetScreenController.getM_FxmlRoot().lookupAll(".Text"))
            ((Text) node).setFont(font);

        // set setName font
        fontStream = GameEngine.getInstance().getM_ResourceManager().loadFont("SubTitlePixelFont.ttf");
        font = Font.loadFont(fontStream, 50);
        for (Node node : levelSetScreenController.getM_FxmlRoot().lookupAll(".SetName"))
            ((Text) node).setFont(font);

        // set back home button animation
        TranslateTransition ttBackHomeButton = new TranslateTransition();
        levelSetScreenController.getM_BackHomeButton().setOnMouseEntered(e -> {
            m_GameEngine.getM_MusicManager().playButtonOverMusic();
            GameScreen.makeTranslateTransition(ttBackHomeButton, levelSetScreenController.getM_BackHomeButton(), 200, 0, 1);
        });
        levelSetScreenController.getM_BackHomeButton().setOnMouseExited(e -> ttBackHomeButton.stop());

        // set crate choosing effect and show set name
        levelSetScreenController.getM_SetName().setText("CHOOSE A SET");
        setChoosingEffect(levelSetScreenController.getM_Set1(), levelSetScreenController.getM_SetName(), Utils.SET_NAMES[0]);
        setChoosingEffect(levelSetScreenController.getM_Set2(), levelSetScreenController.getM_SetName(), Utils.SET_NAMES[1]);
        setChoosingEffect(levelSetScreenController.getM_Set3(), levelSetScreenController.getM_SetName(), Utils.SET_NAMES[2]);
        setChoosingEffect(levelSetScreenController.getM_Set4(), levelSetScreenController.getM_SetName(), Utils.SET_NAMES[3]);


        // set title animation
        makeTranslateTransition(new TranslateTransition(), levelSetScreenController.getM_Prompt(), 800, 0, 8);
        makeTranslateTransition(new TranslateTransition(), levelSetScreenController.getM_SetName(), 800, 0, 8);
    }

    /**
     * This is a reusable method to help LevelSetScreen to set up interactive animation effect when user hover the mouse on a set represented by a crate.
     *
     * @param imageView   ImageView which to play the animation on.
     * @param setNameText Text to show the name of set.
     * @param setName     String value specifying the name of set to be shown on the given Text.
     */
    private void setChoosingEffect(ImageView imageView, Text setNameText, String setName) {
        Image unselectedImage = imageView.getImage();
        KeyFrame k1 = new KeyFrame(Duration.seconds(0), event -> imageView.setImage(m_GameEngine.getM_ResourceManager().loadSprite("CRATE_SEALING")));
        KeyFrame k2 = new KeyFrame(Duration.seconds(0.4), event -> imageView.setImage(m_GameEngine.getM_ResourceManager().loadSprite("CRATE_SEALED")));
        Timeline timeline = new Timeline(k1, k2);
        timeline.setCycleCount(1);
        imageView.setOnMouseEntered(e -> {
            m_GameEngine.getM_MusicManager().playButtonOverMusic();
            timeline.play();
            setNameText.setText(setName);
        });
        imageView.setOnMouseExited(e -> {
            timeline.stop();
            imageView.setImage(unselectedImage);
            setNameText.setText("CHOOSE A SET");
        });
    }
}
