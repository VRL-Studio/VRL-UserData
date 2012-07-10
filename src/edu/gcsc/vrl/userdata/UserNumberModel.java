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
public class UserNumberModel extends UserDataModel{
    
    private static final long serialVersionUID=1L;
    
    private Double data;

    public UserNumberModel() {
        category = UserDataCategory.NUMBER;
    }
    
    

    /**
     * @return the data
     */
    @Override
    public Double getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Double data) {
        this.data = data;
    }
    
    @Override
    public void setData(Object data) {
        
        if (DimensionManager.getArrayDimension(data) == 0){
            setData(data);
            
        }else{
            System.err.println("UserNumberModel.setData(Object data): "
                    + "data has wrong size.");
        }
        
    }
}
