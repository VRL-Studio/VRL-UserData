/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.helpers.UserDataCategory;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class CondUserNumberModel extends UserDataModel {

    private static final long serialVersionUID = 1L;

    public CondUserNumberModel() {
        super();
        
        category = UserDataCategory.COND_NUMBER;
        modelKey = category+":model";
        
        setCode("return new Cond(true, 0.0);");
        
        setConstData(false);
        condition = true;
    }

    
    /**
     * Dummy methode. DO NOTHING.
     *
     * @return null
     */
    @Override
    public Object getData() {
        return null;
    }

    /**
     * Dummy methode. DO NOTHING.
     *
     * @param data Dummy parameter. DO NOTHING.
     */
    @Override
    public void setData(Object data) {
    }

    
}
