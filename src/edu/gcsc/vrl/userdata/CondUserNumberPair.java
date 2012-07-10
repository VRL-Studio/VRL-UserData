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
 * 
 */
public class CondUserNumberPair extends UserDataPair implements Serializable{
    private static final long serialVersionUID = 1L;
    
    public CondUserNumberPair() {
        //
    }

    public CondUserNumberPair(String subset, I_CondUserNumber cond) {
        this.subset = subset;
        this.data = cond;
    }

    /**
     * @return the cond
     */
    @Override
    public I_CondUserNumber getData() {
        return (I_CondUserNumber) data;
    }

}
