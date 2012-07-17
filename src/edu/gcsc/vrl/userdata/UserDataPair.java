/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_IUserData;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataPair implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String subset;
    protected I_IUserData data;

    public UserDataPair() {
        //
    }

    public UserDataPair(String subset, I_IUserData data) {
        this.subset = subset;
        this.data = data;
    }
    

    /**
     * @return the subset
     */
    public String getSubset() {
        return subset;
    }

    /**
     * @param subset the subset to set
     */
    public void setSubset(String subset) {
        this.subset = subset;
    }

    /**
     * @return the cond
     */
    public I_IUserData getData() {
        return data;
    }

    /**
     * @param cond the cond to set
     */
    public void setData(I_IUserData cond) {
        this.data = cond;
    }
}
