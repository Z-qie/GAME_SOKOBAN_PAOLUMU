package org.ziqi.gameEngine.manager;
import javafx.event.EventHandler;
import javafx.event.EventType;
import org.ziqi.gameEngine.GameEngine;
import java.util.HashMap;
import java.util.Map;

/**
 * EventManager class as game manager to take charge of subscribe or unsubscribe any event handlers to current scene.
 *
 * @author Ziqi Yang
 * @see GameManager
 */
public class EventManager extends GameManager {

    /**
     * HashMap contains all handler to be subscribed on scenes.
     */
    private final HashMap<EventType, EventHandler> m_Handlers = new HashMap<>();

    /**
     * Adds a new eventHandler handling given eventType onto m_Handlers hashMap.
     *
     * @param  eventHandler  EventHandler specifying the handler object to be added.
     * @param  eventType     EventType specifying the type the given handler object handles.
     * @see    org.ziqi.control.PlayerController
     */
    public void register(EventHandler eventHandler, EventType eventType) {
        m_Handlers.put(eventType, eventHandler);
    }

    /**
     * Subscribes all handlers for the current scene.
     */
    public void subscribe() {
        for (Map.Entry<EventType, EventHandler> entry : m_Handlers.entrySet())
            GameEngine.getInstance().getM_GameScene().addEventHandler(entry.getKey(), entry.getValue());
    }

    /**
     * Unsubscribes all handlers for the current scene.
     */
    public void unsubscribe() {
        for (Map.Entry<EventType, EventHandler> entry : m_Handlers.entrySet())
            GameEngine.getInstance().getM_GameScene().removeEventHandler(entry.getKey(), entry.getValue());
    }
}