package org.ziqi.gameEngine;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * Logger class takes charge of logging different levels of console message for each GameManager. <br />
 * Each type of GameManager will possess its own logger, and each logger will produce its own log file. <br />
 *
 * @author Ziqi Yang-modified
 * @see org.ziqi.gameEngine.manager.GameManager
 */
public class Logger extends java.util.logging.Logger {

    /**
     * The original logger from java.util.logging.
     */
    private static final java.util.logging.Logger m_Logger = java.util.logging.Logger.getLogger("Logger");

    /**
     * Date format to be used to make log timing.
     */
    private final DateFormat m_DateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Calender object used to get current time when a message is generated and logged.
     */
    private final Calendar m_Calendar = Calendar.getInstance();

    /**
     * Logger constructor to generate log directory(if none exists) and its own log file for its attached GameManager.
     *
     * @param loggerName String value specifying the class name(GameManger name) the logger is attached to generate corresponding log file.
     * @throws IOException When a FileHandler cannot be initialized, IOException is thrown to GameManager to handle.
     */
    public Logger(String loggerName) throws IOException {
        super("Logger", null);

        // create logs directory
        File directory = new File(System.getProperty("user.dir") + "/" + "logs");
        directory.mkdirs();

        // create log file directory
        FileHandler fh = new FileHandler(directory + "/" + loggerName + ".log");
        m_Logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    /**
     * Generates specific formatted log message by given message string and data format specified in m_DateFormat.
     *
     * @param message String value specifying the raw message to be logged.
     * @return The formatted message string is returned with time when the message is created.
     * @see Logger#m_DateFormat
     * @see Calendar
     */
    private String createFormattedMessage(String message) {
        return m_DateFormat.format(m_Calendar.getTime()) + " -- " + message;
    }

    /**
     * Generates info level log message with specified format.
     *
     * @param message String value specifying the raw message to be logged.
     * @see Logger#createFormattedMessage(String)
     */
    public void info(String message) {
        m_Logger.info(createFormattedMessage(message));
    }

    /**
     * Generates warning level log message with specified format.
     *
     * @param message String value specifying the raw message to be logged.
     * @see Logger#createFormattedMessage(String)
     */
    public void warning(String message) {
        m_Logger.warning(createFormattedMessage(message));
    }

    /**
     * Generates severe level log message with specified format.
     *
     * @param message String value specifying the raw message to be logged.
     * @see Logger#createFormattedMessage(String)
     */
    public void severe(String message) {
        m_Logger.severe(createFormattedMessage(message));
    }
}