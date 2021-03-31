package org.ziqi.gameEngine.viewPlayer;

import org.ziqi.gameEngine.base.GameScreen;
import org.ziqi.gameEngine.base.PopUpScreen;

/**
 * ViewAdapter class takes charge of initializing game views that are pop-up screen type. <br />
 * This follows an adapter design pattern. <br />
 *
 * @author Ziqi Yang
 * @see ScreenPlayer
 * @see ViewAdapter
 */
public class ViewAdapter implements ViewPlayer{

    /**
     * Casts back the game screen to pop up class and calls its pop-up method to show the view. <br />
     * This method will only be called by ScreenPlayer to adapt to show pop-up screen.
     *
     * @param gameScreen The GameScreen object to be shown.
     * @param screenType String value specifying the type of screen to be shown; If SCREEN, then screenPlayer is used; Otherwise, ViewAdapter is used.
     * @see PopUpScreen#popUp()
     * @see org.ziqi.view.popUp.ScoreBoardPopUp
     * @see org.ziqi.view.popUp.QuitGamePopUp
     * @see org.ziqi.view.popUp.AlertPopUp
     */
    @Override
    public void initScreen(GameScreen gameScreen, String screenType) {
        PopUpScreen popUpScreen = (PopUpScreen) gameScreen;
        popUpScreen.popUp();
    }
}
