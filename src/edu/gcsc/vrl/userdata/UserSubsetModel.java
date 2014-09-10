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
    public void adjustData(UGXFileInfo info) {

        if (info != null) {
            for (int i = 0; i < info.const__num_subsets(0, 0); ++i) {
                String newSubset = info.const__subset_name(0, 0, i);

                // in this case we can stay with the old selected subset
                if (newSubset.equals(data)) {
                    if (getStatus() != Status.VALID) {
                        setStatus(Status.WARNING);
                    }
                    return;
                }
            }

            // reset data
            if (info.const__num_subsets(0, 0) > 0) {
                data = info.const__subset_name(0, 0, 0);
            } else {
                data = "";
            }
            setStatus(Status.WARNING);
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

//        return VLangUtils.addEscapesToCode(sb.toString());
        return sb.toString();
    }
}

