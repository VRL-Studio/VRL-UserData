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

import edu.gcsc.vrl.ug.api.I_UserMatrix;
import edu.gcsc.vrl.ug.api.I_UserNumber;
import edu.gcsc.vrl.ug.api.I_UserVector;
import edu.gcsc.vrl.ug.api.I_VRLUserLinkerNumberNumber;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Andreas Vogel
 */
public class UserDataTuple implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Object> data = new ArrayList<Object>();

    // if active, error while loading, apparently,
    // xml export not working 100% correctly then
    //public ArrayList<Object> getData() {return data;}
    //public void setData(ArrayList<Object> data) {this.data = data;}

    public UserDataTuple() {
    }

    /**
     * returns the number of elements in the tuple
     */
    public int size() {
        return data.size();
    }

    /**
     * clears all data of the tuple. This also sets the size to zero.
     */
    public void clear() {
        data.clear();
    }

    /**
     * Adds an user data to this UserDataTupel object by using the builder pattern.
     * @param userData the userData to set
     * @return this UserDataTupel object
     */
    public UserDataTuple add(Object userData) {
        data.add(userData);

        return this;
    }

    public Object getData(int i) {
        if (data.get(i) != null) {
            return data.get(i);
        } else {
            throw new RuntimeException("UserDataTuple: no data stored at position "
                    + i + " of tuple.");
        }
    }

    public String getSubset(int i) {
        Object o = getData(i);
        if (o instanceof String) {
            return (String) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a Subset, but " + o.getClass().getName());
        }
    }

    public I_UserNumber getNumberData(int i) {
        Object o = getData(i);
        if (o instanceof I_UserNumber) {
            return (I_UserNumber) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a UserNumber, but " + o.getClass().getName());
        }
    }

    public I_UserVector getVectorData(int i) {
        Object o = getData(i);
        if (o instanceof I_UserVector) {
            return (I_UserVector) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a UserVector, but " + o.getClass().getName());
        }
    }

    public I_UserMatrix getMatrixData(int i) {
        Object o = getData(i);
        if (o instanceof I_UserMatrix) {
            return (I_UserMatrix) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a UserMatrix, but " + o.getClass().getName());
        }
    }

    public I_VRLUserLinkerNumberNumber getDataLinkerNumberNumber(int i) {
        Object o = getData(i);
        if (o instanceof I_VRLUserLinkerNumberNumber) {
            return (I_VRLUserLinkerNumberNumber) o;
        } else {
            throw new RuntimeException("UserDataTuple: data stored at position "
                    + i + " is not a VRLUserLinkerNumberNumber, but " + o.getClass().getName());
        }
    }
}
