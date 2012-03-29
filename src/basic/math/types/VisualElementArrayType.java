/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.types;

import basic.math.elements.interfaces.VisualElementInterface;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 * This typrepresentation allows to work with a flexible number
 * <code>VisualElement</code>s. This is done e.g. in <code>AddOperation</code>.
 * 
 * @author night
 */
public class VisualElementArrayType extends ArrayBaseType{

    public VisualElementArrayType() {

        setType(VisualElementInterface[].class);
        setValueName("VisualElementArray");
    }



}
