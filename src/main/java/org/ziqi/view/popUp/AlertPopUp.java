package org.ziqi.view.popUp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.ziqi.gameEngine.base.PopUpScreen;

/**
 * AlertPopUp class as type of POP_UP_SCREEN view when user makes any invalid operation.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer#POP_UP_SCREEN
 */
public class AlertPopUp extends PopUpScreen {

    /**
     * AlertPopUp constructor initializes the title and message of pop up screen by given parameters.
     * This is called by ViewAdapter which is called by ScreenPlayer in an adapter pattern.
     *
     * @param title String value specifying the title of pop up screen.
     * @param msg   String value specifying the message to be shown in pop up screen.
     * @see org.ziqi.gameEngine.viewPlayer.ViewPlayer
     * @see org.ziqi.gameEngine.viewPlayer.ViewAdapter
     * @see org.ziqi.gameEngine.viewPlayer.ScreenPlayer
     * @see PopUpScreen
     */
    public AlertPopUp(String title, String msg) {
        this.m_Title = title;
        this.m_Message = msg;
    }

    /**
     * Override popUp method sets and shows the pop up alert screen to alert user's any invalid operation.
     */
    @Override
    public void popUp() {
        m_PopUpWindow.initModality(Modality.APPLICATION_MODAL);
        m_PopUpWindow.setTitle(m_Title);
        m_PopUpWindow.setMinWidth(200);
        m_PopUpWindow.setResizable(false);

        Label label = new Label(m_Message);
        label.setText(m_Message);
        Button closeButton = new Button("Okay");
        closeButton.setOnAction(e -> m_PopUpWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 300);
        m_PopUpWindow.setScene(scene);

        // Shows this stage and waits for it to be hidden (closed) before returning to the caller.
        m_PopUpWindow.showAndWait();
    }
}
