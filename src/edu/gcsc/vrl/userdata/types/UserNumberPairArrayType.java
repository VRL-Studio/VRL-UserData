/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.UserNumberPair;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=UserNumberPair[].class, input=true, output=false, style="array")
public class UserNumberPairArrayType extends ArrayBaseType {

    public UserNumberPairArrayType() {
        setValueName("Array");
//        setType(UserNumberPair[].class);
    }
}
