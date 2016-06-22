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

import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.UGXFileInfo;
import java.util.ArrayList;

/**
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
public abstract class DataLinkerModel extends UserDataModel {

    public ArrayList<String> getCode() {
        return code;
    }

    public void setCode(ArrayList<String> code) {
        this.code = code;
    }

    private static final long serialVersionUID = 1L;
    protected int dimension;
    protected ArrayList<String> code;

    public DataLinkerModel() {

        // default values

        code = new ArrayList<String>();
        dimension = 2;
    }

    /**
     * returns the number of elements in the tuple
     */
    public int size() {
        return code.size();
    }

    /**
     * returns the number of elements in the tuple
     */
    public void enlarge(int newSize) {
        while(code.size() < newSize){
            code.add(new String(""));
        }
    }

    /**
     * clears all data of the tuple. This also sets the size to zero.
     */
    public void clear() {
        code.clear();
    }

    /**
     * @return the code
     */
    public String getTheCode(int i) {
        return code.get(i);
    }

    /**
     * @param code the code to set
     */
    public void setTheCode(int i, String code) {
        this.code.set(i, code);
    }

    /**
     * @return the dimension
     */
    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
    

    @Override
    public void adjustData(Object info) {

        if (info != null) {
            if (getStatus() == Status.INVALID) {
                setStatus(Status.WARNING);
            }

        } else {
            setStatus(Status.INVALID);
        }
    }

    @Override
    public void setModel(UserDataModel model) {
        if (model instanceof DataLinkerModel) {

            DataLinkerModel m = (DataLinkerModel) model;

            setStatus(m.getStatus());
            for(int i = 0; i < size(); ++i){
                 setTheCode(i, m.getTheCode(i));
            }

        } else {
            throw new RuntimeException("UserData could be set from other UserDataModel.");

        }
    }

    public String getToolTipText() {

        String toolTip = "";

        toolTip = "<html><pre>";
        toolTip += getTheCode(0);
        toolTip += "</pre><html>";

        return toolTip;
    }

    /**
     *
     * @param code that is written in the editor of a UserDataWindow
     * @param dim is an Integer between 1 and 3 indicating the space dimension
     * @param type is an Integer between 0 and 3,<br>
     * 0 = CondNumber or Number,<br>
     * 1 = Vector, <br>
     * 2 = Matrix, <br>
     * 3 = Tensor <br>
     * @param cond if true CondUserDataCompiler is used else UserDataCompiler
     *
     * @return the code that is returned by one of the above named ug-compilers
     */
    protected static String createCode(String code, int dim, int type, boolean cond) {

        
        // TODO: THIS IS NOT READY !!! HARD_CODED for one comp!!! GENERALIZE IN 
        //          DERIVED CLASSES, PLEASE !!!
        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("c");
          
        if (dim >= 1) {
            paramNames.add("x");
        }
        if (dim >= 2) {
            paramNames.add("y");
        }
        if (dim >= 3) {
            paramNames.add("z");
        }
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, type,
                paramNames, UserData.returnTypes[type]);
            
        return code;
    }
}
