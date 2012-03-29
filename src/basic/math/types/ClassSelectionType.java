/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.types;

import basic.math.helpers.ClassSelection;
import eu.mihosoft.vrl.types.SelectionInputType;
import java.io.Serializable;

/**
 * THis is the typrepresentation for the class <code>ClassSelection</code>.
 *
 * @author night
 */
public class ClassSelectionType extends SelectionInputType implements Serializable {
    private static final long serialVersionUID = 1L;

    public ClassSelectionType() {

        setType(ClassSelection.class);
        setValueName("Class<?>:");

        setValue(new ClassSelection());
    }


}
