/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.managers.DimensionManager;
import edu.gcsc.vrl.userdata.helpers.UserDataCategory;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserVectorModel extends UserDataModel {

    private Double[] data;

    public UserVectorModel() {

        category = UserDataCategory.VECTOR;
    }

    /**
     * @return the data
     */
    @Override
    public Double[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Double[] data) {
        this.data = data;
    }

    @Override
    public void setData(Object data) {

        if (DimensionManager.getArrayDimension(data) == 1) {
            setData(data);

        } else {
            System.err.println("UserVectorModel.setData(Object data): "
                    + "data has wrong size.");
        }

    }
}
