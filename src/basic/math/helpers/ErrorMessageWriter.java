/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.helpers;

import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.Connector;
import eu.mihosoft.vrl.visual.MessageBox;
import eu.mihosoft.vrl.visual.MessageType;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is gathering place for static methods which are used for writing
 * error messages on the message box of the vrl studio.
 *
 * @author night
 */
public class ErrorMessageWriter implements Serializable{

    private static final long serialVersionUID = 1;

    /**
     * Writes an error message on the message box.
     * Used e.g. in TensorGenerator.
     *
     * @param mainCanvas the current used canvas
     * @param title of the message
     * @param message the message which should be written in the message box
     */
    public static void writeErrorMessage(
            Canvas mainCanvas, String title, String message) {

        System.err.println(">> " + message);

        MessageBox box = mainCanvas.getMessageBox();
        box.addMessage(title, message, MessageType.ERROR);
    }

    /**
     * Writes an error message on the message box.
     * Used e.g. in TensorGenerator.
     *
     * @param mainCanvas the current used canvas
     * @param title of the message
     * @param message the message which should be written in the message box
     * @param c the connector that is involed in the data transfer causing this message
     */
    public static void writeErrorMessage(
            Canvas mainCanvas, String title, String message, Connector c) {

        System.err.println(">> " + message);

        MessageBox box = mainCanvas.getMessageBox();
        box.addMessage(title, message, c, MessageType.ERROR);
    }

    /**
     * Writes an error message on the message box.
     *
     * @param mainCanvas the current used canvas
     * @param message the error message
     * @param title of the message
     * @param c1 the first involved connection
     * @param c2 the second involved connection
     */
    public static void writeErrorMessage(
            Canvas mainCanvas, String title, String message,
            Connector c1, Connector c2) {

        System.err.println(">> " + message);

        MessageBox box = mainCanvas.getMessageBox();
        box.addMessage(title, message, c1, MessageType.ERROR);
        box.addMessage(title, message, c2, MessageType.ERROR);
    }

    /**
     * Writes an error message on the message box.
     *
     * @param mainCanvas the current used canvas
     * @param message the error message
     * @param title of the message
     * @param cArray an array of involved connections
     */
    public static void writeErrorMessage(
            Canvas mainCanvas, String title, String message,
            Connector[] cArray) {

        System.err.println(">> " + message);

        MessageBox box = mainCanvas.getMessageBox();
        for (Connector c : cArray) {
            box.addMessage(title, message, c, MessageType.ERROR);
        }
    }

    /**
     * Writes an error message on the message box.
     *
     * @param mainCanvas the current used canvas
     * @param message the error message
     * @param title of the message
     * @param cArray an array of involved connections
     */
    public static void writeErrorMessage(
            Canvas mainCanvas, String title, String message,
            ArrayList<Connector> cList) {

        System.err.println(">> " + message);

        MessageBox box = mainCanvas.getMessageBox();
        for (Connector c : cList) {
            box.addMessage(title, message, c, MessageType.ERROR);
        }
    }
}
