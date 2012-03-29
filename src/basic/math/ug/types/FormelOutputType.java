/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;


import basic.math.helpers.Formel;
import eu.mihosoft.vrl.reflection.RepresentationType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.TransparentLabel;
import java.io.Serializable;

/**
 * Defines the look of an output that use this typerepresentation.
 *
 * @author Night
 */
public class FormelOutputType extends TypeRepresentationBase implements Serializable{
    private static final long serialVersionUID = 1L;

    private TransparentLabel view = new TransparentLabel("");

    /*
     * Defines the look of an output that use "Formel" as ValueName,
     * which can be set as a methode information in VRL.
     *
     * ist falsch -> key word "default"
     * siehe Pick..Type..
     */

    /**
     * Set the supported RepresentationType to OUTPUT.
     * Set the supported style to "default".
     */
    public FormelOutputType() {
        setType(Formel.class);
        setValueName("Formel");
//        setSupportedStyle("default");
        setStyleName("default");
        addSupportedRepresentationType(RepresentationType.OUTPUT);
        add(nameLabel);
//        add(view);
    }

    /**
     * Removes all entries from the inner TransparentLabel variable view.
     */
    @Override
    public void emptyView() {
        value = null;
    }

    /**
     * Call this function to display your object on the inner TransparentLabel.
     * Your Object should be an instance of <code>Formel</code> otherwise one
     * whitespace would be displayed.
     *
     * @param o Object that is added to the inner TransparentLabel variable view,
     *          if it is an instance of <code>Formel</code>
     *          else a whitespace is added to variable view
     */
    @Override
    public void setViewValue(Object o) {
        if (o instanceof Formel) {
            Formel formel = (Formel) o;
            view.setText(formel.toString());
        } else {
            view.setText(" ");
        }
    }
}
