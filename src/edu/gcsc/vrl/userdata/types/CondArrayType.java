/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.I_CondUserNumber;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=I_CondUserNumber[].class, input=true, output=false, style="default")
public class CondArrayType extends ArrayBaseType {

    public CondArrayType() {
        setValueName("Array");
//        setType(I_CondUserNumber[].class);
    }
}
