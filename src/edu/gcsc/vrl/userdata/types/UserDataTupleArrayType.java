/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.UserDataTuple;
import eu.mihosoft.vrl.annotation.TypeInfo;

/**
 *
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = UserDataTuple[].class, input = true, output = false, style = "array")
public class UserDataTupleArrayType extends UserDataArrayBaseType {

    public UserDataTupleArrayType() {
        setValueName("Data Tuple Array");
    }

    /**
     * Evaluates the contract, e.g., checks for correct data type or range
     * condition.
     */
    @Override
    protected void evaluateContract() {
        // deactivated contract evaluation to prevent multiple error messages
        // due to null array

        if (value == null) {
            invalidateValue();
        }
    }

}
