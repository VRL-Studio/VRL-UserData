/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_IIPData;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserDataPair implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String subset;
    protected I_IIPData data;

    public UserDataPair() {
        //
    }

    public UserDataPair(String subset, I_IIPData data) {
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
    public I_IIPData getData() {
        return data;
    }

    /**
     * @param cond the cond to set
     */
    public void setData(I_IIPData cond) {
        this.data = cond;
    }
}
