/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_IUserData;
import edu.gcsc.vrl.ug.api.I_UserVector;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserVectorPair extends UserDataPair implements Serializable{
    private static final long serialVersionUID = 1L;

    public UserVectorPair() {
        //
    }

    public UserVectorPair(String subset, I_IUserData data) {
        this.subset = subset;
        this.data = data;
    }
    
    /**
     * @return the cond
     */
    @Override
    public I_UserVector getData() {
        return (I_UserVector) data;
    }

}
