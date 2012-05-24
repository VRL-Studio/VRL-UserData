/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.UserVectorPair;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=UserVectorPair[].class, input=true, output=false, style="array")
public class UserVectorPairArrayType extends ArrayBaseType {

    public UserVectorPairArrayType() {
        setValueName("Array");
//        setType(UserVectorPair[].class);
    }
}
