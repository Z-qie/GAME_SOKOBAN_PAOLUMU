package org.ziqi;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for the facilitate other operation of the program, containing reusable and stateless helper methods and fields.
 *
 * @author Ziqi Yang
 */
public class Utils {

    /**
     * Constant array of strings to store names of each set.
     */
    public static final String[] SET_NAMES = {"Coral-Highland", "Ancient-Forest", "Elder-Recess", "Astera"};
    /**
     * Constant array of strings to store names of background image of each set.
     */
    public static final String[] SET_BG_NAMES = {"OriginalBG", "TempleBG", "TrailBG", "AsteraBG"};

    /**
     * Returns the new translated location by translating given point location by given delta vector.
     *
     * @param  sourceLocation  Original location as Point object.
     * @param  delta           Delta vector to translate from the original location as Point object.
     * @return                 A new location after translation as Point is returned without modifying the original source.
     * @see    Point
     */
    public static Point translatePoint(Point sourceLocation, Point delta) {
        Point translatedPoint = new Point(sourceLocation);
        translatedPoint.translate((int) delta.getX(), (int) delta.getY());
        return translatedPoint;
    }

    /**
     * Gets a formatted file name as string by combining the given filename and its extension to return.
     *
     * @param  fullName   A string specifying the file name.
     * @param  extension  A string specifying the file extension.
     * @return            A full file name with slash and extension as string is returned to be used as path.
     */
    public static String getFullFileName(String fullName, String extension) {
        return "/" + fullName + "." + extension;
    }

    /**
     * Gets the absolute file path as string by given file name string.
     *
     * @param  resourceName  A string specifying the file name with its extension in the resource folder.
     * @return               An absolute file path by given file name is returned.
     */
    public static String getResourcePath(String resourceName){
        return Main.class.getResource(resourceName).toExternalForm();
    }

    /**
     * Gets the file path as URL by given file name string.
     *
     * @param  resourceName  A string specifying the file name with its extension in the resource folder.
     * @return               A file path as URL by given file name is returned.
     * @see URL
     */
    public static URL getResourceURL(String resourceName){
        return Main.class.getResource(resourceName);
    }

    /**
     * Gets the file path as InputStream by given file name string.
     *
     * @param  resourceName  A string specifying the file name with its extension in the resource folder.
     * @return               A file path as InputStream by given file name is returned.
     * @see InputStream
     */
    public static InputStream getResourcePathAsStream(String resourceName){
        return Main.class.getResourceAsStream(resourceName);
    }

    /**
     * Transforms from given long integer second into formatted time string.
     *
     * @param  secondElapsed  A long integer specifying the second time elapsed to be transformed.
     * @return                A formatted string as ""%02d:%02d" specifying time is returned.
     * @see String#format(String, Object...) 
     */
    public static  String getTimeString(long secondElapsed){
        long minutes = TimeUnit.SECONDS.toMinutes(secondElapsed);
        long seconds = secondElapsed - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, seconds);
    }
}
