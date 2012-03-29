/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;


import basic.math.helpers.*;
import eu.mihosoft.vrl.reflection.RepresentationType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.reflection.VisualObject;
import eu.mihosoft.vrl.visual.CanvasWindow;
import eu.mihosoft.vrl.visual.MessageBox;
import eu.mihosoft.vrl.visual.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**<p>
 * Defines the look of an input that use this typerepresentation.
 * </p>
 * Add the functionality of resizing an FormelEntry.
 *
 * @author Night
 */
public class FormelInputType extends TypeRepresentationBase implements Serializable{
    private static final long serialVersionUID = 1L;

    private FormelPane formelPane = new FormelPane();
    private Formel formel;

    /*
     * <p>
     * Set the key word "Formel" that should be added to a methode,
     * if she should use this TypeRepresentation.
     *</p>
     * ist falsch -> key word "default"
     * siehe Pick..Type..
     */
    /**
     * Set the supported RepresentationType to INPUT.
     * Set the supported style to "default".
     * Adds a <code>FormelPane</code> to itself.
     */
    public FormelInputType() {
        setType(Formel.class);
        setValueName("Formel");
//        setSupportedStyle("default");
        setStyleName("default");
        addSupportedRepresentationType(RepresentationType.INPUT);

        add(nameLabel);
        add(formelPane);
    }

    /**<p>
     * Call this function to display your object on the inner <code>FormelPane</code>.
     * Your Object should be an instance of <code>Formel</code> otherwise one
     * whitespace would be displayed.
     * </p>
     * <p>
     * If youd add an instance of <code>Formel</code> each <code>FormelEntry</code>
     * is combined with an ActionListener. Which is used to allow a right click to the
     * <code>FormelEntry</code> and set the size, if the clicked <code>FormelEntry</code>
     * represents an editable entry, e.g. scalar, vector, matrix.
     * </p>
     *
     * @param o Object that is added to the inner <code>FormelPane</code>. variable formelPane,
     *          if it is an instance of <code>Formel</code>
     *          else a whitespace is added to variable formelPane
     */
    @Override
    public void setViewValue(Object o) {
        formelPane.clean();

        formel = null;

        if (o instanceof Formel) {
            formel = (Formel) o;

            for (final FormelEntry entry : formel) {
                try {
                    /** the FormelEntrie is added to the FormelPane with anActionListner and the information isEditable()
                     */
                    formelPane.addFormel(entry.getFormel(),
                            /**in this ActionListner is the logic of opening
                             * the MatrixWindow for a FormelEntry
                             * and the popup menu set size by right click on the FormalEntry
                             */
                            new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    if (e.getActionCommand() == "clicked") {
                                        boolean windowAlreadyOpened = false;

                                        for (CanvasWindow w : getMainCanvas().getWindows()) {

                                            if (w instanceof MatrixWindow) {
                                                MatrixWindow mWindow = (MatrixWindow) w;

                                                if (mWindow.getFormelEntry().equals(entry)) {
                                                    windowAlreadyOpened = true;
                                                    break;
                                                }
                                            }
                                        }

                                        if (!windowAlreadyOpened) {
                                            getMainCanvas().getWindows().
                                                    add(new MatrixWindow("Edit " +
                                                    entry.getShortName(),
                                                    getMainCanvas(), entry));
                                        }
                                    }

                                    if (e.getActionCommand() == "set size") {
                                        boolean windowAlreadyOpened = false;

                                        for (CanvasWindow w : getMainCanvas().getWindows()) {

                                            if (w instanceof VisualObject) {
                                                VisualObject vWindow = (VisualObject) w;

                                                VisualCanvas canvas = (VisualCanvas) getMainCanvas();
                                                int objID = vWindow.getObjectRepresentation().getID();
                                                Object o = canvas.getInspector().getObject(objID);

                                                if (o instanceof SetSizeInterface) {
                                                    SetSizeInterface s = (SetSizeInterface) o;

                                                    if (s.getFormelEntry().equals(entry)) {
                                                        windowAlreadyOpened = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                        if (!windowAlreadyOpened) {
                                            boolean editWindowOpened = false;

                                            for (CanvasWindow w : getMainCanvas().getWindows()) {

                                                if (w instanceof MatrixWindow) {
                                                    MatrixWindow mWindow = (MatrixWindow) w;

                                                    if (mWindow.getFormelEntry().equals(entry)) {
//                                                        windowAlreadyOpened = true;
                                                        editWindowOpened = true;
                                                        break;
                                                    }
                                                }
                                            }

                                            VisualCanvas canvas = (VisualCanvas) getMainCanvas();

                                            if (editWindowOpened) {
                                                MessageBox mBox = canvas.getMessageBox();
                                                mBox.addMessage("Cannot change element size:",
                                                        "Please close the edit window before changing the element size!",
                                                        MessageType.ERROR);
                                            } else {
                                                canvas.addObject(new SetSize(entry));
                                            }

                                        } // end if (!windowAlreadyOpened)
                                    }

                                }
                            }// end of new ActionListener()
                            , entry.isEditable()); // end of formelPane.addFormel( String, ActionListener, Boolean )

                } catch (Exception ex) {
                    String message = "unkown error";
                    if (ex.getMessage() != null) {
                        message = ex.getMessage();
                    }
                    if (getMainCanvas() != null) {
                        MessageBox mBox = getMainCanvas().getMessageBox();
                        mBox.addMessage("Can´t display MathML expression",
                                message, getConnector(), MessageType.ERROR);
                    }
                } // end catch
            }// end for each loop
        }// end of if(o instanceof Formel)
    }// end of setViewValue()

    /**
     *
     * @return the inner <code>Formel</code>
     */
    @Override
    public Object getViewValue() {
        return formel;
    }

    /**
     * Cleans the inner <code>Formel</code>
     */
    @Override
    public void emptyView() {
        formelPane.clean();
        //formel = null;
    }
}
