/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_UserNumber;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserNumberPair implements Serializable {
    private static final long serialVersionUID = 1L;
    private String subset;
    private I_UserNumber data;

    public UserNumberPair() {
        //
    }

    public UserNumberPair(String subset, I_UserNumber data) {
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
    public I_UserNumber getData() {
        return data;
    }

    /**
     * @param cond the cond to set
     */
    public void setData(I_UserNumber cond) {
        this.data = cond;
    }
}
