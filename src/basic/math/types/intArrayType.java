/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.types;

import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 * This typrepresentation allows to work with a flexible number integern as
 * input and output parameters.
 *
 * @author night
 */
public class intArrayType extends ArrayBaseType {

    public intArrayType() {
        setType(int[].class);
        setValueName("int[]");
    }
}
