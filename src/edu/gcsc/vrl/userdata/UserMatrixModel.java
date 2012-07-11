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
public class UserMatrixModel extends UserDataModel {

    private Double[][] data;

    public UserMatrixModel() {
        super();
        
        category = UserDataCategory.MATRIX;
        modelKey = category + ":model";
        
        Double[][] defaultdata = {{0.0, 0.0}, {0.0, 0.0}};
        data = defaultdata;

    }

    /**
     * @return the data
     */
    @Override
    public Double[][] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Double[][] data) {
        this.data = data;
    }

    @Override
    public void setData(Object data) {

        if (DimensionManager.getArrayDimension(data) == 2) {
            setData((Double[][]) data);

        } else {
            System.err.println("UserMatrixModel.setData(Object data): "
                    + "data has wrong size.");
        }

    }

}
