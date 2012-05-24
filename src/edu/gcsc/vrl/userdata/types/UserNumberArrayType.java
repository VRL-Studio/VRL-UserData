/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.I_UserNumber;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=I_UserNumber[].class, input=true, output=false, style="array")
public class UserNumberArrayType extends ArrayBaseType {

    public UserNumberArrayType() {
        setValueName("Array");
//        setType(I_UserNumber[].class);
    }
}
