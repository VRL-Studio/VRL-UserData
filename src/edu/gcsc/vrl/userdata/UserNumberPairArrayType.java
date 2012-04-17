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
public class UserNumberPairArrayType extends ArrayBaseType {

    public UserNumberPairArrayType() {
        setValueName("Array");
        setType(UserNumberPair[].class);
    }
}
