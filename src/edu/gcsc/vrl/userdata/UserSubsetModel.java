/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.UGXFileInfo;
import eu.mihosoft.vrl.lang.VLangUtils;

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
            setStatus(m.getStatus());

        } else {
            throw new RuntimeException("UserData could be set from other UserDataModel.");

        }
    }

    @Override
    public void adjustData(Object info) {

        if (info != null) {
            if (!(info instanceof UGXFileInfo))
                throw new RuntimeException("UserSubsetModel: Passed data is not of required type UGXFileInfo.");
        
            for (int i = 0; i < ((UGXFileInfo)info).const__num_subsets(0, 0); ++i) {
                String newSubset = ((UGXFileInfo)info).const__subset_name(0, 0, i);

                // in this case we can stay with the old selected subset
                if (newSubset.equals(data)) {
                    if (getStatus() != Status.VALID) {
                        setStatus(Status.WARNING);
                    }
                    return;
                }
            }

            // reset data
            if (((UGXFileInfo)info).const__num_subsets(0, 0) > 0) {
                data = ((UGXFileInfo)info).const__subset_name(0, 0, 0);
            }
            else {
                data = "";
            }
            setStatus(Status.INVALID);
        } else {
            setStatus(Status.INVALID);
        }
    }

    @Override
    public String checkUserData() {
        return "";
    }

//    @Override
//    public int getDimension() {
//        //a subset has no dimension, so we return just zero.
//        return 0;
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public String getModelAsCode() {

        StringBuilder sb = new StringBuilder();

        sb.append('"')
                .append(VLangUtils.addEscapesToCode(data))
                .append('"');

        return sb.toString();
    }
}

