/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.UserConvDiffDataTuple;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author A. Vogel
 */
@TypeInfo(type=UserConvDiffDataTuple[].class, input=true, output=false, style="array")
public class UserConvDiffDataTupleArrayType extends ArrayBaseType {

    public UserConvDiffDataTupleArrayType() {
        setValueName("Array");
//        setType(UserMatrixPair[].class);
    }
}
