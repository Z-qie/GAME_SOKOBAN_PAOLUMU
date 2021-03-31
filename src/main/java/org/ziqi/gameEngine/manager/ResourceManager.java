package org.ziqi.gameEngine.manager;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import org.ziqi.Utils;
import org.ziqi.gameEngine.GameEngine;
import org.ziqi.model.Crate;
import org.ziqi.model.Diamond;
import org.ziqi.model.Player;
import org.ziqi.model.Wall;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * ResourceManager class as game manager to take charge of loading, storing and transferring resources when running the game. <br />
 *
 * @author Ziqi Yang
 * @see GameManager
 */
public class ResourceManager extends GameManager {
    /**
     * HashMap containing all sprites for gaming screen to render graphicUnits according to gameObjects.
     *
     * @see org.ziqi.view.GraphicUnit
     * @see org.ziqi.gameEngine.GraphicUnitRenderer
     * @see org.ziqi.gameEngine.base.GameObject
     */
    private final HashMap<String, Image> m_Sprites = new HashMap<>();

    /**
     * HashMap containing all music and sound effects for musicManager to use.
     *
     * @see MusicManager
     */
    private final HashMap<String, Media> m_Medias = new HashMap<>();

    /**
     * HashMap containing all background images for gaming screen to change accordingly to different sets.
     *
     * @see GameEngine#setM_GamingBG(Image)
     */
    private final HashMap<String, Image> m_Images = new HashMap<>();

    /**
     * ResourceManager constructor loads and stores all medias, sprites and background images from resource folder when the GameEngine is launched. <br />
     * This will enhance runtime performance when load game levels and switch screens as resources are hold by ResourceManager beforehand.
     *
     * @see ResourceManager#loadMediaFromResource(HashMap)
     * @see ResourceManager#loadImagesFromResource(HashMap)
     * @see ResourceManager#loadSpritesFromResource(HashMap)
     */
    public ResourceManager() {
        loadMediaFromResource(m_Medias);
        loadSpritesFromResource(m_Sprites);
        loadImagesFromResource(m_Images);
    }

    /**
     * Loads a set resource as inputStream by given set name from resource folder. <br />
     * This method will be called when new game begins or user has chosen one set to begin.
     *
     * @param setNameInput String value specifying the set name to be load from resource folder.
     * @return An inputStream of the set resource is returned to caller;
     * If no file is found by given set name, null is returned and message will be logged.
     * @see Utils#SET_NAMES
     * @see Utils#getResourcePathAsStream(String)
     */
    public InputStream getLevelSet(String setNameInput) {
        String setPath = "assets/levelSets" + Utils.getFullFileName(setNameInput, "skb");
        InputStream set = Utils.getResourcePathAsStream(setPath);

        if (set != null)
            return set;
        else {
            m_Logger.severe(setPath + "does not exist!");
            return null;
        }
    }

    /**
     * Gets a background image by given image name from HashMap hold by ResourceManager. <br />
     * This method will be called when set switching.
     *
     * @param imageName String value specifying the background image name.
     * @return The background image is returned by searching from HashMap by given name; If no matches, null is returned.
     * @see Utils#SET_BG_NAMES
     * @see ResourceManager#m_Images
     */
    public Image loadImage(String imageName) {
        return m_Images.get(imageName);
    }

    /**
     * Gets a sprite by given sprite name from HashMap hold by ResourceManager. <br />
     * This method will be called when rendering graphicUnit onto gaming screen.
     *
     * @param spriteName String value specifying the sprite name described by status of gameObject to be rendered.
     * @return The sprite image is returned by searching from HashMap by given name; If no matches, null is returned.
     * @see ResourceManager#m_Sprites
     * @see org.ziqi.model.Player.PlayerStatus
     * @see org.ziqi.model.Wall.WallStatus
     * @see org.ziqi.model.Crate.CrateStatus
     */
    public Image loadSprite(String spriteName) {
        return m_Sprites.get(spriteName);
    }

    /**
     * Gets a media by given music name from HashMap hold by ResourceManager. <br />
     *
     * @param musicName String value specifying the music name.
     * @return A media is returned by searching from HashMap by given name; If no matches, null is returned.
     * @see MusicManager
     */
    public Media loadMusic(String musicName) {
        return m_Medias.get(musicName);
    }

    /**
     * Gets a inputStream specifying a font resource by given font name. <br />
     *
     * @param fontName String value specifying the music name.
     * @return A inputStream to font resource is returned by searching from HashMap by given name; If no matches, null is returned.
     */
    public InputStream loadFont(String fontName) {
        String fontPath = "assets/fonts/" + fontName;
        InputStream fontStream = Utils.getResourcePathAsStream(fontPath);
        if (fontStream != null)
            return fontStream;
        else
            m_Logger.severe(fontName + " does not exist!");
        return null;
    }

    /**
     * Loads and stores all sprite images from resource folder for further use. <br />
     * This method will only be called with the launching of GameEngine. <br />
     * All sprite names are provided by according GameObjects.
     *
     * @param sprites A HashMap containing the image resources read from resource folder.
     * @see org.ziqi.gameEngine.base.GameObject
     * @see Crate#getSpriteNames()
     * @see Diamond#getSpriteNames()
     * @see Player#getSpriteNames()
     * @see Wall#getSpriteNames()
     */
    public void loadSpritesFromResource(HashMap<String, Image> sprites) {
        ArrayList<String> spriteSets = Crate.getSpriteNames();
        spriteSets.addAll(Diamond.getSpriteNames());
        spriteSets.addAll(Player.getSpriteNames());
        spriteSets.addAll(Wall.getSpriteNames());

        for (String spriteFileName : spriteSets) {
            InputStream imageStream = Utils.getResourcePathAsStream("assets/sprites/" + spriteFileName);
            String spriteName = spriteFileName.substring(spriteFileName.indexOf("/") + 1, spriteFileName.indexOf("."));
            if (imageStream != null)
                sprites.put(spriteName, new Image(imageStream));
            else
                m_Logger.severe(spriteName + " does not exist!");
        }
    }

    /**
     * Loads and stores all medias from resource folder for further use. <br />
     * This method will only be called with the launching of GameEngine. <br />
     * All media names are provided by MusicManager
     *
     * @param medias A HashMap containing the media resources read from resource folder.
     * @see MusicManager#getMusicNames()
     */
    public void loadMediaFromResource(HashMap<String, Media> medias) {
        for (String musicName : MusicManager.getMusicNames()) {
            String musicStream = Utils.getResourcePath("assets/musics/" + musicName + ".mp3");
            Media music = new Media(musicStream);
            medias.put(musicName, music);
        }
    }

    /**
     * Loads and stores all background images from resource folder for further use. <br />
     * This method will only be called with the launching of GameEngine. <br />
     * All background image names are provided by Utils class.
     *
     * @param images A HashMap containing the media resources read from resource folder.
     * @see Utils#SET_BG_NAMES
     */
    public void loadImagesFromResource(HashMap<String, Image> images) {
        for (String imageName : Utils.SET_BG_NAMES) {
            InputStream imageStream = Utils.getResourcePathAsStream("assets/images/" + imageName + ".png");
            Image image = new Image(imageStream);
            images.put(imageName, image);
        }
    }
}
