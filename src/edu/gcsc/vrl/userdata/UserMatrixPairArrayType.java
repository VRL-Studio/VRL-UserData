/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserMatrixPairArrayType extends ArrayBaseType {

    public UserMatrixPairArrayType() {
        setValueName("Array");
        setType(UserMatrixPair[].class);
    }
}
