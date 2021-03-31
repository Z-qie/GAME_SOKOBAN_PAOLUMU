package org.ziqi.gameEngine.base;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.ziqi.Utils;

import java.io.IOException;
import java.net.URL;

/**
 * GameScreen class as super class(parent) of all other show-able GameScreens in the game. <br />
 * This class contains some reusable methods to render the screen and a root pane javafx layout. <br />
 * The display of this object will be implemented as an adapter design pattern by ScreenPlayer.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer
 * @see org.ziqi.view.screen.GamingScreen
 * @see org.ziqi.view.screen.LevelSetScreen
 * @see org.ziqi.view.screen.ScoreBoardScreen
 * @see org.ziqi.view.screen.StartScreen
 * @see org.ziqi.view.screen.VictoryScreen
 * @see PopUpScreen
 */
public abstract class GameScreen {

    /**
     * A protected pane layout as a root of the scene of the GameScreen to be shown.
     */
    protected Pane m_Root;

    /**
     * Gets the root layout of the GameScreen.
     *
     * @return A pane layout as a root of the scene of the GameScreen to be shown is returned.
     */
    public Pane getM_Root() {
        return m_Root;
    }

    /**
     * Initializes the GameScreen read from a 'fxml' file and gets the controller of this 'fxml' file. <br />
     * This method is called when the GameScreen uses the help of 'fxml' file. <br />
     * The layout of 'fxml' will be added onto the root layout.
     *
     * @param viewName String value specifying the name of 'fxml' file to read.
     * @return The controller class of read 'fxml' file is returned to make further operation of the GameScreen.
     * @see org.ziqi.control.screenController.GamingScreenController
     * @see org.ziqi.control.screenController.LevelSetScreenController
     * @see org.ziqi.control.screenController.ScoreBoardScreenController
     * @see org.ziqi.control.screenController.StartScreenController
     * @see org.ziqi.control.screenController.VictoryScreenController
     */
    public Initializable getInitializable(String viewName) {
        FXMLLoader loader = new FXMLLoader();
        URL url = Utils.getResourceURL("views/" + viewName + ".fxml");
        loader.setLocation(url);
        try {
            m_Root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return loader.getController();
    }

    /**
     * Defines a given FadeTransition by given parameters and sets it on the given node infinitely.
     *
     * @param ft        New fadeTransition to be set by given parameter.
     * @param node      The node which the FadeTransition will be add on.
     * @param millis    Integer value specifying the millisecond duration of the FadeTransition.
     * @param fromValue The double value specifying the starting opacity of the transition.
     * @param toValue   The double value specifying the ending opacity of the transition.
     * @see FadeTransition
     */
    public static void makeFadeTransition(FadeTransition ft, Node node, int millis, double fromValue, double toValue) {
        ft.setDuration(Duration.millis(millis));
        ft.setFromValue(fromValue);
        ft.setToValue(toValue);
        ft.setCycleCount(Animation.INDEFINITE);
        ft.setAutoReverse(true);
        ft.setNode(node);
        ft.play();
    }

    /**
     * Defines a given TranslateTransition by given parameters and sets it on the given node infinitely.
     *
     * @param tt      New TranslateTransition to be set by given parameter.
     * @param node    The node which the TranslateTransition will be add on.
     * @param millis  Integer value specifying the millisecond duration of the TranslateTransition.
     * @param offsetX Double value specifying the horizontal offset translation.
     * @param offsetY Double value specifying the vertical offset translation.
     * @see TranslateTransition
     */
    public static void makeTranslateTransition(TranslateTransition tt, Node node, int millis, double offsetX, double offsetY) {
        tt.setDuration(Duration.millis(millis));
        tt.setByX(offsetX);
        tt.setByY(offsetY);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setNode(node);
        tt.play();
    }

    /**
     * Defines a given TranslateTransition by given parameters and sets it on the given node for a given cycles.
     *
     * @param tt         New TranslateTransition to be set by given parameter.
     * @param node       The node which the TranslateTransition will be add on.
     * @param millis     Integer value specifying the millisecond duration of the TranslateTransition.
     * @param offsetX    Double value specifying the horizontal offset translation.
     * @param offsetY    Double value specifying the vertical offset translation.
     * @param cycleCount Integer value specifying the count of cycles the translation will loop.
     * @see TranslateTransition
     * @see TranslateTransition#setCycleCount(int) ;
     */
    public static void makeTranslateTransition(TranslateTransition tt, Node node, int millis, double offsetX, double offsetY, int cycleCount) {
        tt.setDuration(Duration.millis(millis));
        tt.setByX(offsetX);
        tt.setByY(offsetY);
        tt.setCycleCount(cycleCount);
        tt.setNode(node);
        tt.play();
    }
}
