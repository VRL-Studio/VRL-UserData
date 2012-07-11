/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.userdata.helpers.UserDataCategory;
import edu.gcsc.vrl.userdata.managers.DimensionManager;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public abstract class UserDataModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean constData;
    private String code;
    private int dimension;
    protected UserDataCategory category;
    protected String modelKey;

    public UserDataModel() {

        constData = true;

        dimension = DimensionManager.TWO; // default value);

        code = "//Available Parameters are: t, si \n"
                + "//and depending on dimension: x, y, z \n";
    }

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

    /**
     * @return the category
     */
    public UserDataCategory getCategory() {
        return category;
    }

    /**
     * @return the modelKey
     */
    public String getModelKey() {
        return modelKey;
    }

    /**
     * @return the data
     */
    public abstract Object getData();

    /**
     * @param data the data to set
     */
    public abstract void setData(Object data);
}
