/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_UserNumber;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserNumberArrayType extends ArrayBaseType {

    public UserNumberArrayType() {
        setValueName("Array");
        setType(I_UserNumber[].class);
    }
}
