/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import edu.gcsc.vrl.userdata.helpers.UserDataCategory;
import java.io.Serializable;

/**
 *
 * @author andreasvogel
 */
public abstract class UserDataModel implements Serializable {
 
    protected UserDataCategory category;
    protected boolean externTriggered = false;

    /**
     * @return the category
     */
    public UserDataCategory getCategory() {
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
   
    public abstract boolean adjustData(UGXFileInfo info);
    
    public abstract Object createUserData();

    public boolean isExternTriggered() {
        return externTriggered;
    }

    public void setExternTriggered(boolean externTriggered) {
        this.externTriggered = externTriggered;
    }

}

