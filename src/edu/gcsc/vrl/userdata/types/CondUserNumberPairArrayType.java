/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.CondUserNumberPair;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=CondUserNumberPair[].class, input=true, output=false, style="array")
public class CondUserNumberPairArrayType extends ArrayBaseType {

    public CondUserNumberPairArrayType() {
        setValueName("Array");
//        setType(CondUserNumberPair[].class);
    }
}
