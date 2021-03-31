package org.ziqi.gameEngine.manager;

import javafx.scene.media.MediaPlayer;
import org.ziqi.Debugger;
import org.ziqi.gameEngine.GameEngine;

import java.util.HashMap;

/**
 * MusicManager class as game manager to take charge of loading, arranging levels and sets of levels for user to choose and play. <br />
 * This class apply an observer pattern as subject with all observer manually attached/register by itself to reduce unnecessary new classes. <br />
 * When one MediaPlayer is playing or trying to stop, this class informs to all its attached MediaPlayers to update their status, either stop or continue to play.
 *
 * @author Ziqi Yang
 * @see GameManager
 * @see MediaPlayer
 */
public class MusicManager extends GameManager{

    /**
     * BackgroundMusic MediaPlayer observer for start screen.
     */
    private MediaPlayer m_BackgroundMusic;

    /**
     * GamingMusic MediaPlayer observer for gaming screen.
     */
    private MediaPlayer m_GamingMusic;

    /**
     * CompletionMusic MediaPlayer observer for every complete pop up score board screen.
     */
    private MediaPlayer m_CompletionMusic;

    /**
     * VictoryScreenMusic MediaPlayer observer for victory screen when a whole set is completed.
     */
    private MediaPlayer m_VictoryScreenMusic;

    /**
     * ScoreboardMusic MediaPlayer observer for full score board screen.
     */
    private MediaPlayer m_ScoreboardMusic;

    /**
     * SetChoosingMusic MediaPlayer observer for set choosing screen.
     */
    private MediaPlayer m_SetChoosingMusic;

    /**
     * ButtonOverMusic MediaPlayer observer when a button is hovered over.
     */
    private MediaPlayer m_ButtonOverMusic;

    /**
     * ButtonClickMusic MediaPlayer observer when a button is clicked.
     */
    private MediaPlayer m_ButtonClickMusic;

    /**
     * BloomingMusic MediaPlayer observer when a crate is sealed by a magic gap(hits the diamond).
     */
    private MediaPlayer m_BloomingMusic;

    /**
     * CannotLandMusic MediaPlayer observer when the user intends to land on crate/wall.
     */
    private MediaPlayer m_CannotLandMusic;

    /**
     * PushingMusic MediaPlayer observer when user pushing the crate.
     */
    private MediaPlayer m_PushingMusic;

    /**
     * FlyingOffMusic MediaPlayer observer when the user intends to take off.
     */
    private MediaPlayer m_FlyingOffMusic;

    /**
     * LandingMusic MediaPlayer observer when the user lands successfully.
     */
    private MediaPlayer m_LandingMusic;

    /**
     * Array of strings specifying the file names from resource.
     */
    private static final String[] MUSIC_NAMES = new String[]{
            "BGM",
            "GamingMusic",
            "CompletionMusic",
            "VictoryScreenMusic",
            "ScoreBoardMusic",
            "SetChoosingMusic",
            "ButtonOverMusic",
            "ButtonClickMusic",
            "BloomingMusic",
            "CannotLandMusic",
            "PushingMusic",
            "FlyingOffMusic",
            "LandingMusic"};

    /**
     * Gets the array of strings specifying file names from resource. <br />
     * This method will be called to help resource manager loading media source when engine launched.
     *
     * @return  An array of strings specifying file names from resource is returned to caller.
     * @see     ResourceManager#loadMediaFromResource(HashMap) 
     */
    public static String[] getMusicNames() {
        return MUSIC_NAMES;
    }

    /**
     * Attaches, with the help of ResourceManager, all media sources to mediaPlayer observers for musicManager to update. <br />
     * This method will be called just after the GameEngine is launched.
     *
     * @see ResourceManager
     */
    public void attachMediaPlayers() {
        // attaches and sets up music
        ResourceManager resourceManager = GameEngine.getInstance().getM_ResourceManager();
        m_BackgroundMusic = new MediaPlayer(resourceManager.loadMusic("BGM"));
        m_BackgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);

        m_GamingMusic = new MediaPlayer(resourceManager.loadMusic("GamingMusic"));
        m_GamingMusic.setCycleCount(MediaPlayer.INDEFINITE);

        m_CompletionMusic = new MediaPlayer(resourceManager.loadMusic("CompletionMusic"));
        m_CompletionMusic.setCycleCount(MediaPlayer.INDEFINITE);

        m_VictoryScreenMusic = new MediaPlayer(resourceManager.loadMusic("VictoryScreenMusic"));
        m_VictoryScreenMusic.setCycleCount(MediaPlayer.INDEFINITE);

        m_ScoreboardMusic = new MediaPlayer(resourceManager.loadMusic("ScoreBoardMusic"));
        m_ScoreboardMusic.setCycleCount(MediaPlayer.INDEFINITE);

        m_SetChoosingMusic = new MediaPlayer(resourceManager.loadMusic("SetChoosingMusic"));
        m_SetChoosingMusic.setCycleCount(MediaPlayer.INDEFINITE);

        m_ButtonOverMusic =  new MediaPlayer(resourceManager.loadMusic("ButtonOverMusic"));
        m_ButtonOverMusic.setCycleCount(1);

        m_ButtonClickMusic =  new MediaPlayer(resourceManager.loadMusic("ButtonClickMusic"));
        m_ButtonClickMusic.setCycleCount(1);

        m_BloomingMusic =  new MediaPlayer(resourceManager.loadMusic("BloomingMusic"));
        m_BloomingMusic.setVolume(0.5);
        m_BloomingMusic.setRate(2);
        m_BloomingMusic.setCycleCount(1);

        m_CannotLandMusic =  new MediaPlayer(resourceManager.loadMusic("CannotLandMusic"));
        m_CannotLandMusic.setCycleCount(1);

        m_PushingMusic =  new MediaPlayer(resourceManager.loadMusic("PushingMusic"));
        m_PushingMusic.setCycleCount(1);

        m_FlyingOffMusic =  new MediaPlayer(resourceManager.loadMusic("FlyingOffMusic"));
        m_FlyingOffMusic.setCycleCount(1);

        m_LandingMusic =  new MediaPlayer(resourceManager.loadMusic("LandingMusic"));
        m_LandingMusic.setCycleCount(1);
    }

    /**
     * Plays background music for start screen and informs all other media players. <br />
     * This observer pattern update method for m_BackgroundMusic.
     *
     * @see MusicManager#m_BackgroundMusic
     * @see MusicManager#notifyOtherMediaPlayer()
     */
    public void playBGM() {
        notifyOtherMediaPlayer();
        m_BackgroundMusic.play();
    }

    /**
     * Plays gaming music for gaming screen and informs all other media players. <br />
     * This observer pattern update method for m_GamingMusic.
     *
     * @see MusicManager#m_GamingMusic
     * @see MusicManager#notifyOtherMediaPlayer()
     */
    public void playGamingMusic() {
        notifyOtherMediaPlayer();
        m_GamingMusic.play();
    }

    /**
     * Plays completion music when a level completed and informs all other media players. <br />
     * This observer pattern update method for m_CompletionMusic.
     *
     * @see MusicManager#m_CompletionMusic
     * @see MusicManager#notifyOtherMediaPlayer()
     */
    public void playCompletionMusic() {
        notifyOtherMediaPlayer();
        m_CompletionMusic.play();
    }

    /**
     * Plays victory music for victory screen when a set completed and informs all other media players. <br />
     * This observer pattern update method for m_VictoryScreenMusic.
     *
     * @see MusicManager#m_VictoryScreenMusic
     * @see MusicManager#notifyOtherMediaPlayer()
     */
    public void playVictoryScreenMusic() {
        notifyOtherMediaPlayer();
        m_VictoryScreenMusic.play();
    }

    /**
     * Plays score board music for score board screen and informs all other media players. <br />
     * This observer pattern update method for m_ScoreboardMusic.
     *
     * @see MusicManager#m_ScoreboardMusic
     * @see MusicManager#notifyOtherMediaPlayer()
     */
    public void playScoreBoardMusic() {
        notifyOtherMediaPlayer();
        m_ScoreboardMusic.play();
    }

    /**
     * Plays SetChoosing music for SetChoosing screen and informs all other media players. <br />
     * This observer pattern update method for m_SetChoosingMusic.
     *
     * @see MusicManager#m_SetChoosingMusic
     * @see MusicManager#notifyOtherMediaPlayer()
     */
    public void playSetChoosingMusic(){
        notifyOtherMediaPlayer();
        m_SetChoosingMusic.play();
    }

    /**
     * Stops(if any) the same effect that is currently playing and plays sound effect when a button is hovered over. <br />
     *
     * @see MusicManager#m_ButtonOverMusic
     */
    public void playButtonOverMusic(){
        m_ButtonOverMusic.stop();
        m_ButtonOverMusic.play();
    }

    /**
     * Stops(if any) the same effect that is currently playing and plays sound effect when a button is clicked. <br />
     *
     * @see MusicManager#m_ButtonClickMusic
     */
    public void playButtonClickMusic(){
        m_ButtonClickMusic.stop();
        m_ButtonClickMusic.play();
    }

    /**
     * Stops(if any) the same effect that is currently playing and plays sound effect when the user is flying off. <br />
     *
     * @see MusicManager#m_FlyingOffMusic
     */
    public void playFlyingOffMusic(){
        m_FlyingOffMusic.stop();
        m_FlyingOffMusic.play();
    }


    /**
     * Stops(if any) the same effect that is currently playing and plays sound effect when the user intends to land on crate or wall. <br />
     *
     * @see MusicManager#m_CannotLandMusic
     */
    public void playCannotLandMusic(){
        m_CannotLandMusic.stop();
        m_CannotLandMusic.play();
    }

    /**
     * Stops(if any) the same effect that is currently playing and plays sound effect when the user is pushing a crate. <br />
     *
     * @see MusicManager#m_PushingMusic
     */
    public void playPushingMusic(){
        m_PushingMusic.stop();
        m_PushingMusic.play();
    }

    /**
     * Stops(if any) the same effect that is currently playing and plays sound effect when a crate is sealed by a magic gap(hits the diamond). <br />
     *
     * @see MusicManager#m_BloomingMusic
     */
    public void playBloomingMusic(){
        m_BloomingMusic.stop();
        m_BloomingMusic.play();
    }

    /**
     * Stops(if any) the same effect that is currently playing and plays sound effect when the user lands successfully. <br />
     *
     * @see MusicManager#m_LandingMusic
     */
    public void playLandingMusic(){
        m_LandingMusic.stop();
        m_LandingMusic.play();
    }

    /**
     * Notifies and handles all attached mediaPlayers when one music is playing. <br />
     * This ensure all background version music will stop to let another to play, while the one-time sound effect won't be affected.
     *
     * @see MusicManager#m_BackgroundMusic
     * @see MusicManager#m_GamingMusic
     * @see MusicManager#m_CompletionMusic
     * @see MusicManager#m_VictoryScreenMusic
     * @see MusicManager#m_ScoreboardMusic
     * @see MusicManager#m_SetChoosingMusic
     */
    private void notifyOtherMediaPlayer() {
            m_BackgroundMusic.stop();
            m_GamingMusic.stop();
            m_CompletionMusic.stop();
            m_VictoryScreenMusic.stop();
            m_ScoreboardMusic.stop();
            m_SetChoosingMusic.stop();
    }
}
