/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_CondUserNumber;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class CondPairArrayType extends ArrayBaseType {

    public CondPairArrayType() {
        setValueName("Array");
        setType(CondUserNumberPair[].class);
    }
}
