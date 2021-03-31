package org.ziqi.view.popUp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.gameEngine.base.PopUpScreen;

/**
 * QuitGamePopUp class as type of POP_UP_SCREEN view when user intends to quit the game.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer#POP_UP_SCREEN
 */
public class QuitGamePopUp extends PopUpScreen {

    /**
     * QuitGamePopUp constructor initialize the title and message of pop up screen.
     * This is called by ViewAdapter which is called by ScreenPlayer in an adapter pattern.
     *
     * @see org.ziqi.gameEngine.viewPlayer.ViewPlayer
     * @see org.ziqi.gameEngine.viewPlayer.ViewAdapter
     * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer
     * @see PopUpScreen
     */
    public QuitGamePopUp() {
        this.m_Title = "Close Confirmation";
        this.m_Message = "Are you sure to exit?";
    }

    /**
     * Override popUp method sets and shows the pop up confirmation screen to double check with user's intention of quit.
     * If user clicks Yse button, the app quits by requesting GameEngine to handle the quitting process;
     * If user clicks No button, the pop up screen popOffs by itself and resumes the game.
     *
     * @see GameEngine#quit()
     */
    @Override
    public void popUp() {
        Label label = new Label(m_Message);
        label.setText(m_Message);

        // two button
        Button buttonYes = new Button("Yes");
        Button buttonNo = new Button("No");
        buttonYes.setOnAction(e -> {
            m_PopUpWindow.close();
            GameEngine.getInstance().getM_ScreenPlayer().close();
        });
        buttonNo.setOnAction(e -> m_PopUpWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, buttonYes, buttonNo);
        layout.setAlignment(Pos.CENTER);

        m_PopUpWindow.initModality(Modality.APPLICATION_MODAL);
        m_PopUpWindow.setTitle(m_Title);
        Scene scene = new Scene(layout, 300, 150);
        m_PopUpWindow.setScene(scene);
        m_PopUpWindow.setResizable(false);
        // Shows this stage and waits for it to be hidden (closed) before returning to the caller.
        m_PopUpWindow.showAndWait();
    }
}
