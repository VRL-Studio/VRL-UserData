/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012–2016 Goethe Universität Frankfurt am Main, Germany
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 */
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

