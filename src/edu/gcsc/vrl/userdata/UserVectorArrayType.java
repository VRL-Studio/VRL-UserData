/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_UserVector;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserVectorArrayType extends ArrayBaseType {

    public UserVectorArrayType() {
        setValueName("Array");
        setType(I_UserVector[].class);
    }
}
