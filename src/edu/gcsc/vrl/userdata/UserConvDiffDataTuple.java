/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_UserNumber;
import edu.gcsc.vrl.ug.api.I_UserVector;
import edu.gcsc.vrl.ug.api.I_UserMatrix;
import java.io.Serializable;

/**
 *
 * @author Andreas Vogel
 */
public class UserConvDiffDataTuple implements Serializable {
    private static final long serialVersionUID = 1L;
    private String subset;
    private I_UserNumber source;
    private I_UserVector velocity;
    private I_UserMatrix diffusion;

    public UserConvDiffDataTuple() {
        //
    }

    public UserConvDiffDataTuple(String subset, 
                                 I_UserMatrix Diffusion,
                                I_UserVector Velocity,
                                 I_UserNumber Source) {
        this.subset = subset;
        this.source = Source;
        this.velocity = Velocity;
        this.diffusion = Diffusion;
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
    public I_UserNumber getSource() {
        return source;
    }

    /**
     * @param cond the cond to set
     */
    public void setSource(I_UserNumber cond) {
        this.source = cond;
    }
    
       /**
     * @return the cond
     */
    public I_UserVector getVelocity() {
        return velocity;
    }

    /**
     * @param cond the cond to set
     */
    public void setVelocity(I_UserVector cond) {
        this.velocity = cond;
    }

       /**
     * @return the cond
     */
    public I_UserMatrix getDiffusion() {
        return diffusion;
    }

    /**
     * @param cond the cond to set
     */
    public void setDiffusion(I_UserMatrix cond) {
        this.diffusion = cond;
    }

}
