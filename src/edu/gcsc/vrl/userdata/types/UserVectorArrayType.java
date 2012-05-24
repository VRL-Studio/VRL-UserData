/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.I_UserVector;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=I_UserVector[].class, input=true, output=false, style="default")
public class UserVectorArrayType extends ArrayBaseType {

    public UserVectorArrayType() {
        setValueName("Array");
//        setType(I_UserVector[].class);
    }
}
