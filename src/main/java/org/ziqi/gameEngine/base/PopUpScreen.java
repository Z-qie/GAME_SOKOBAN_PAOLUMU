package org.ziqi.gameEngine.base;

import javafx.stage.Stage;

/**
 * PopUpScreen class is the second level hierarchy abstract class of the GameScreen representing a new pop up screen instead of a screen shown by stage of ScreenPlayer. <br />
 * The display of this object will be implemented as an adapter design pattern by ViewAdapter.
 *
 * @author Ziqi Yang
 * @see org.ziqi.gameEngine.viewPlayer.ViewAdapter
 */
public abstract class PopUpScreen extends GameScreen {

    /**
     * New stage to pop up instead of the main screen stage of the ScreenPlayer.
     */
    protected Stage m_PopUpWindow = new Stage();

    /**
     * String value specifying the title of pop up screen.
     */
    protected String m_Title;

    /**
     * String value specifying the message to be shown in pop up screen.
     */
    protected String m_Message;

    /**
     * The abstract method to be implemented by its children to set how to initialize pop up screen.
     */
    public abstract void popUp();
}
