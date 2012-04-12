/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserVectorModel extends UserDataModel{
    private Double[] data;

    /**
     * @return the data
     */
    public Double[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Double[] data) {
        this.data = data;
    }
}
