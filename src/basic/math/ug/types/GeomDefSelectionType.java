/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;

import eu.mihosoft.vrl.types.SelectionInputType;
import java.awt.Component;
import java.io.Serializable;
import javax.swing.BoxLayout;
import basic.math.ug.skin.GeomDefSelection;

/**
 *
 * @author night
 */
public class GeomDefSelectionType extends SelectionInputType implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * Default values for a GeomDefSelectionType
     */
    public GeomDefSelectionType() {
        setType(GeomDefSelection.class);
        setValueName("GeomDefSelection: ");
//        setSupportedStyle("GeomDef");

        setValue(new GeomDefSelection());

        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        getSelectionView().setAlignmentX(Component.LEFT_ALIGNMENT);

        BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(layout);
    }
}
