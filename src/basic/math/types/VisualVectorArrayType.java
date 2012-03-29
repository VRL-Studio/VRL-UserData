/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.types;

import basic.math.elements.interfaces.VisualVectorInterface;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 * This typrepresentation allows to work with a flexible number
 * <code>VisualVector</code>s. This is done e.g. in <code>AddOperation</code>.
 *
 * @author night
 */
public class VisualVectorArrayType extends ArrayBaseType {

    public VisualVectorArrayType() {

        setType(VisualVectorInterface[].class);
        setValueName("VisualVectorArray");

    }
}
