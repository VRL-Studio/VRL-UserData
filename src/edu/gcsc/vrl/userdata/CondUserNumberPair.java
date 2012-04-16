/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_CondUserNumber;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class CondUserNumberPair implements Serializable {
    private static final long serialVersionUID = 1L;
    private String subset;
    private I_CondUserNumber cond;

    public CondUserNumberPair() {
        //
    }

    public CondUserNumberPair(String subset, I_CondUserNumber cond) {
        this.subset = subset;
        this.cond = cond;
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
    public I_CondUserNumber getCond() {
        return cond;
    }

    /**
     * @param cond the cond to set
     */
    public void setCond(I_CondUserNumber cond) {
        this.cond = cond;
    }
}
