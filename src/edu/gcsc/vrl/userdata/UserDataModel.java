/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import java.io.Serializable;

/**
 *
 * @author andreasvogel
 */
public abstract class UserDataModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Status of user data, indicating if data is valid or invalid etc.
     */
    public enum Status {

        VALID,
        WARNING,
        INVALID
    }

    /**
     * Category of user Data
     */
    public enum Category {
        COND_NUMBER,
        NUMBER,
        VECTOR,
        MATRIX,
        SUBSET,
        DEPENDENT_SUBSET,
        LINKER
    }
    
    protected Status status = Status.WARNING;
    protected Category category;
    protected boolean externTriggered = false;

    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @return the data
     */
    public abstract Object getData();

    /**
     * @param data the data to set
     */
    public abstract void setData(Object data);

    /**
     * @param data the data to set
     */
    public abstract void setModel(UserDataModel data);

    /**
     * Adjusts data for new DataInfo (e.g. for new dimension) and returns in 
     * with state the data is after modification to new Info.
     * 
     * @param data      new data
     */
    public abstract void adjustData(Object data);

    /**
     *  creates the user data
     * @return  the created user data
     */
    public abstract Object createUserData();

    /**
     *  checks if user data can be created
     * returns empty message if everything ok, error message else
     */
    public abstract String checkUserData();

    /**
     * returns if the Data setup (e.g. dimension of data) is triggered externally,
     * e.g. due to some file loading
     * 
     * @return flag is externally triggered
     */
    public boolean isExternTriggered() {
        return externTriggered;
    }

    /**
     * sets if the Data setup (e.g. dimension of data) is triggered externally,
     * e.g. due to some file loading
     * 
     * @param externTriggered flag is externally triggered
     */
    public void setExternTriggered(boolean externTriggered) {
        this.externTriggered = externTriggered;
    }
}
