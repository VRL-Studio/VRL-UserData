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
public class UserMatrixPair implements Serializable {
    private static final long serialVersionUID = 1L;
    private String subset;
    private I_UserMatrix data;

    public UserMatrixPair() {
        //
    }

    public UserMatrixPair(String subset, I_UserMatrix data) {
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
    public I_UserMatrix getData() {
        return data;
    }

    /**
     * @param cond the cond to set
     */
    public void setData(I_UserMatrix cond) {
        this.data = cond;
    }
}
