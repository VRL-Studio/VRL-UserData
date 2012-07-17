/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_IUserData;
import edu.gcsc.vrl.ug.api.I_UserNumber;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserNumberPair extends UserDataPair implements Serializable{
    private static final long serialVersionUID = 1L;

    public UserNumberPair() {
        //
    }

    public UserNumberPair(String subset, I_IUserData userData) {
        this.subset = subset;
        this.data = userData;
    }
    

    /**
     * @return the cond
     */
    @Override
    public I_UserNumber getData() {
        return (I_UserNumber) data;
    }

}
