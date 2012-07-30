/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;

/**
 *
 * @author andreasvogel
 */
public class UserSubsetModel extends UserDataModel {

    private String data = "";

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object newData) {
        if (newData instanceof String) {
            data = (String) newData;
        } else {
            throw new RuntimeException("UserSubsetModel: Passed data not String.");
        }
    }

    @Override
    public Object createUserData() {
        return data;
    }

    @Override
    public void setModel(UserDataModel model) {
        if (model instanceof UserSubsetModel) {

            UserSubsetModel m = (UserSubsetModel) model;

            setData(m.getData());

        } else {
            throw new RuntimeException("UserData could be set from other UserDataModel.");

        }
    }

    @Override
    public boolean adjustData(UGXFileInfo info) {
        if(info != null){
            for (int i = 0; i < info.const__num_subsets(0, 0); ++i) {
                String newSubset = info.const__subset_name(0, 0, i);
                
                // in this case we can stay with the old selected subset
                if(newSubset.equals(data)) return true;
            }

            // reset data
            data = "";
            return false;
        }
        
        return true;
    }
}
