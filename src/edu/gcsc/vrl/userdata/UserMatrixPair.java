/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_UserMatrix;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserMatrixPair extends UserDataPair implements Serializable{
    private static final long serialVersionUID = 1L;
    

    public UserMatrixPair() {
        //
    }

    public UserMatrixPair(String subset, I_UserMatrix data) {
        this.subset = subset;
        this.data = data;
    }
    
    /**
     * @return the cond
     */
    @Override
    public I_UserMatrix getData() {
        return (I_UserMatrix) data;
    }

}
