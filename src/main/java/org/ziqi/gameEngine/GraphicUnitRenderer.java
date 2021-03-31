package org.ziqi.gameEngine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.ziqi.Debugger;
import org.ziqi.control.screenController.GamingScreenController;
import org.ziqi.gameEngine.base.GameObject;
import org.ziqi.gameEngine.base.GameScreen;
import org.ziqi.gameEngine.manager.EventManager;
import org.ziqi.gameEngine.manager.MusicManager;
import org.ziqi.model.Crate;
import org.ziqi.model.Player;
import org.ziqi.view.GraphicUnit;

import java.awt.*;

/**
 * GraphicUnitRenderer class takes charge of rendering graphic units of specific GameObjects on gaming screen. <br />
 * Each type of GameObject will be rendered as a graphic unit on its corresponding layer(AnchorPane) of gaming screen. <br />
 *
 * @author Ziqi Yang
 * @see GraphicUnit
 * @see org.ziqi.view.screen.GamingScreen
 * @see org.ziqi.control.screenController.GamingScreenController
 */
public class GraphicUnitRenderer {

    /**
     * Generates and renders one set of graphic units on given layers accordingly by given GameObject array at the same position. <br />
     * This method will be called by GamingScreenController class everytime when the screen needs to be updated,
     * as a user is playing the game(gaming screen is showing) and inputs a key that is handled;
     *
     * @param gameObjects Array of GameObjects provided by level iterator as all layers of objects at the same position.
     * @param layers      Array of AnchorPanes that will shows the whole rendered level(layers of graphic units)
     * @see org.ziqi.control.screenController.GamingScreenController
     * @see GamingScreenController#updateGamingScreen()
     * @see org.ziqi.model.Level.LevelIterator
     * @see GraphicUnit
     */
    public static void renderObjectToGraphic(GameObject[] gameObjects, AnchorPane[] layers) {
        // no need to render floor
        for (int layer = GraphicUnit.LAYER_OF_DIAMOND; layer < GraphicUnit.NUM_OF_LAYER; layer++) {
            GraphicUnit graphicUnit = new GraphicUnit(gameObjects[layer]);
            if (gameObjects[layer] == null && graphicUnit.getImage() == null)
                continue;

            Point position = gameObjects[layer].getM_Position();

            if (layer == GraphicUnit.LAYER_OF_PLAYER)
                renderPlayerGraphic(graphicUnit, position);
            else if (layer == GraphicUnit.LAYER_OF_CRATE)
                renderCrateGraphic(((Crate) gameObjects[layer]), graphicUnit, position);
            else
                setUpGraphicLayout(graphicUnit, position, 0, 0);

            layers[layer].getChildren().add(graphicUnit);
        }
        Debugger.debugEnd(false, "ALl units updated!");
    }

    /**
     * Renders Crate object on its specific AnchorPane layer at given position. <br />
     * This method will check the status of the Crate object and render different effect accordingly:  <br />
     * If the crate is sealing, meaning the last move of player must be pushing the crate to hit a diamond, then a blooming animation with music will be played and render the sealed graphic on finished.  <br />
     * Otherwise, a normal graphic of the crate is then rendered by setUpGraphicLayout() method. <br />
     * Note: When an animation is playing, player input handler is temporarily unsubscribed to avoid refresh off the animation.
     *
     * @param crateModel   Crate object to be checked and rendered.
     * @param crateGraphic The default graphic for crate, meaning the crate has not yet hit any diamond.
     * @param position     Point object specifying the position to be rendered on the layer.
     * @see Crate
     * @see org.ziqi.model.Crate.CrateStatus
     * @see GraphicUnitRenderer#setUpGraphicLayout(GraphicUnit, Point, double, double)
     * @see MusicManager#playBloomingMusic()
     * @see EventManager#subscribe()
     * @see EventManager#unsubscribe()
     */
    private static void renderCrateGraphic(Crate crateModel, GraphicUnit crateGraphic, Point position) {
        GameEngine gameEngine = GameEngine.getInstance();

        // play blooming animation
        if (crateModel.getM_CrateStatus() == Crate.CrateStatus.CRATE_SEALING) {
            // play blooming music
            gameEngine.getM_MusicManager().playBloomingMusic();

            // play animation
            KeyFrame k1 = new KeyFrame(Duration.seconds(0), event -> {
                setUpGraphicLayout(crateGraphic, position, 0, 0);
                // stop handle player input
                gameEngine.getM_EventManager().unsubscribe();
            });
            KeyFrame k2 = new KeyFrame(Duration.seconds(0.8), event -> {
                crateModel.setM_CrateStatus(Crate.CrateStatus.CRATE_SEALED);
                crateGraphic.setImage(gameEngine.getM_ResourceManager().loadSprite(crateModel.getM_CrateStatusAsString()));
                // resume player input
                gameEngine.getM_EventManager().subscribe();
            });

            Timeline timeline = new Timeline(k1, k2);
            timeline.setCycleCount(1);
            timeline.play();
        } else {
            setUpGraphicLayout(crateGraphic, position, 0, 0);
        }
    }

    /**
     * Renders Player object on its specific AnchorPane layer at given position. <br />
     * This method will check the current status of the Player object and render different effect accordingly:  <br />
     * 1. If the Player is FLYING_OFF, an animation with flying-off music will be played and then a flying graphic is rendered on finish; <br />
     * 2. If the Player is LANDING, the renderer check if the player can land:
     * If so, an animation with landing music will be played and then the idle graphic is render on finished; Otherwise, the cannotLand sound effect is played to alert player. <br />
     * 3. If the Player is PUSHING/FLYING/IDLE, then the according graphic and music will be normally renderer. <br />
     * Note: When an animation is playing, player input handler is temporarily unsubscribed to avoid refresh off the animation.
     *
     * @param playerGraphic Player object to be checked and rendered.
     * @param position      Point object specifying the position to be rendered on the layer.
     * @see Player
     * @see org.ziqi.model.Player.PlayerStatus
     * @see GraphicUnitRenderer#setUpGraphicLayout(GraphicUnit, Point, double, double)
     * @see MusicManager#playFlyingOffMusic()
     * @see MusicManager#playLandingMusic()
     * @see MusicManager#playCannotLandMusic()
     * @see MusicManager#playPushingMusic()
     * @see EventManager#subscribe()
     * @see EventManager#unsubscribe()
     */
    private static void renderPlayerGraphic(GraphicUnit playerGraphic, Point position) {
        GameEngine gameEngine = GameEngine.getInstance();
        Player player = gameEngine.getM_LevelManager().getM_CurrentLevel().getM_Player();

        // play flying off animation
        if (player.getM_PlayerStatus() == Player.PlayerStatus.FLYING_OFF) {
            // player input handler is temporarily unsubscribed to avoid refresh off the animation.
            gameEngine.getM_EventManager().unsubscribe();
            setUpGraphicLayout(playerGraphic, position, 0, 0);
            // drop flying shadow
            addDropShadow(playerGraphic);
            // play sound effect
            gameEngine.getM_MusicManager().playFlyingOffMusic();
            TranslateTransition ttFlyingOff = new TranslateTransition();
            GameScreen.makeTranslateTransition(ttFlyingOff, playerGraphic, 800, 0, -GraphicUnit.FLYING_OFFSET * GraphicUnit.UNIT_SIZE, 1);
            ttFlyingOff.setOnFinished(e -> {
                player.setM_PlayerStatus(Player.PlayerStatus.FLYING);
                playerGraphic.setImage(gameEngine.getM_ResourceManager().loadSprite(player.getM_PlayerStatusAsString() + "_" + player.getM_PlayerDirectionAsString()));
                gameEngine.getM_EventManager().subscribe();
            });
        }
        // play landing animation
        else if (player.getM_PlayerStatus() == Player.PlayerStatus.LANDING) {
            // player input handler is temporarily unsubscribed to avoid refresh off the animation.
            gameEngine.getM_EventManager().unsubscribe();
            setUpGraphicLayout(playerGraphic, position, -GraphicUnit.FLYING_OFFSET, 0);
            TranslateTransition ttFlyingOff = new TranslateTransition();
            GameScreen.makeTranslateTransition(ttFlyingOff, playerGraphic, 800, 0, GraphicUnit.FLYING_OFFSET * GraphicUnit.UNIT_SIZE, 1);
            // drop flying shadow
            addDropShadow(playerGraphic);
            // play sound effect
            gameEngine.getM_MusicManager().playLandingMusic();
            ttFlyingOff.setOnFinished(e -> {
                playerGraphic.setEffect(null);
                player.setM_PlayerStatus(Player.PlayerStatus.IDLE);
                playerGraphic.setImage(gameEngine.getM_ResourceManager().loadSprite(player.getM_PlayerStatusAsString() + "_" + player.getM_PlayerDirectionAsString()));
                gameEngine.getM_EventManager().subscribe();
            });
        }
        // set offset when player is pushing up
        else if (player.getM_PlayerStatus() == Player.PlayerStatus.PUSHING) {
            gameEngine.getM_MusicManager().playPushingMusic();
            if (player.getM_PlayerDirection() == Player.PlayerDirection.UP)
                setUpGraphicLayout(playerGraphic, position, -GraphicUnit.PUSHING_OFFSET, 0);
            else
                setUpGraphicLayout(playerGraphic, position, 0, 0);
        }
        // set offset when player is flying
        else if (player.getM_PlayerStatus() == Player.PlayerStatus.FLYING) {
            addDropShadow(playerGraphic);
            setUpGraphicLayout(playerGraphic, position, -GraphicUnit.FLYING_OFFSET, 0);
        }
        // render idle graphic
        else
            setUpGraphicLayout(playerGraphic, position, 0, 0);
    }

    private static void setUpGraphicLayout(GraphicUnit graphicUnit, Point position, double offsetX, double offsetY) {
        AnchorPane.setLeftAnchor(graphicUnit, ((position.y + offsetY) * GraphicUnit.UNIT_SIZE));
        AnchorPane.setTopAnchor(graphicUnit, ((position.x + offsetX) * GraphicUnit.UNIT_SIZE));
    }

    /**
     * Adds shadow effect on player graphic when player is flying.
     *
     * @param imageView The Player graphic to be added.
     * @see DropShadow
     * @see javafx.scene.effect.Effect
     */
    private static void addDropShadow(ImageView imageView) {
        DropShadow dropShadow = new DropShadow();

        //setting the type of blur for the shadow
        dropShadow.setBlurType(BlurType.ONE_PASS_BOX);

        //Setting color for the shadow
        dropShadow.setColor(new Color(0, 0, 0, 0.2));

        //Setting the width of the shadow
        dropShadow.setWidth(3);

        //setting the offset of the shadow
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(14);

        //Applying shadow effect to the text
        imageView.setEffect(dropShadow);
    }
}
