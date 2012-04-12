/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserDataModel implements Serializable{
    private static final long serialVersionUID=1L;
    
    private boolean constData;
    private String code;
    private int dimension;

    /**
     * @return the constData
     */
    public boolean isConstData() {
        return constData;
    }

    /**
     * @param constData the constData to set
     */
    public void setConstData(boolean constData) {
        this.constData = constData;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the dimension
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * @param dimension the dimension to set
     */
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
}
