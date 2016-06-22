/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009–2012 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2012 by Michael Hoffer
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
     * @return a string which has the code to construct a new copy an existing model 
     */
    public abstract String getModelAsCode();

//    /**
//     * @return the dimension
//     */
//    public abstract int getDimension();

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
     * @return empty message if everything ok, error message else
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
