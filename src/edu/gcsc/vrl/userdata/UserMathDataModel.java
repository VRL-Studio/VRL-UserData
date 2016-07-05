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

import edu.gcsc.vrl.ug.CondUserDataCompiler;
import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.UGXFileInfo;
import java.util.ArrayList;
import javax.swing.table.TableModel;

/**
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public abstract class UserMathDataModel extends UserDataModel {

    public static enum InputType {

        SCALAR_CONSTANT,
        CONSTANT,
        CODE,
        LINKER
    }

    private static final long serialVersionUID = 1L;
    protected InputType inputType;
    protected int dimension;
    protected String code;
    /**
     * Boolean to differ between normal UserDataModel and an UserDataModel which
     * should represent a Condition, which has no const data and therefore no
     * visualization for const data will be available in an UserDataWindow.
     */
    protected boolean condition;

    public UserMathDataModel() {

        // default values
        inputType = InputType.CONSTANT;

        dimension = 2;

        code = "";

        condition = false;
    }

    /**
     * @return the constData
     */
    public InputType getInputType() {
        return inputType;
    }

    /**
     * @param constData the constData to set
     */
    public void setInputType(InputType inputType) {
        this.inputType = inputType;
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

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    /**
     * @param dimension the dimension to set
     */
    public Status setDimensionWithAdjust(int dim) {
        this.dimension = dim;
        return adjustDataForDimension(dim);
    }

    public abstract void setDataFromTable(TableModel tableModel);

    /**
     * @return the condition
     */
    public boolean isCondition() {
        return condition;
    }

    /**
     * sets the condition
     */
    public void setCondition(boolean isCondition) {
        condition = isCondition;
    }

    @Override
    public void adjustData(Object info) {
        
        if (info != null) {
            if (!(info instanceof UGXFileInfo))
                throw new RuntimeException("UserMathDataModel: Passed data is not of required type UGXFileInfo.");
        
            if (getStatus() == Status.INVALID) {
                setStatus(Status.VALID);
            }
            int dim = ((UGXFileInfo)info).const__grid_world_dimension(0);
            setStatus(setDimensionWithAdjust(dim));
        } else {
            setStatus(Status.INVALID);
        }
    }

    public abstract Status adjustDataForDimension(int dim);

    @Override
    public void setModel(UserDataModel model) {
        if (model instanceof UserMathDataModel) {

            UserMathDataModel m = (UserMathDataModel) model;

            setDimensionWithAdjust(m.getDimension());
            setInputType(m.getInputType());
            setCondition(m.isCondition());
            setStatus(m.getStatus());

            setData(m.getData());
            setCode(m.getCode());

        } else {
            throw new RuntimeException("UserData could be set from other UserDataModel.");

        }
    }

    @Override
    public Object createUserData() {
//        public Object createUserData() and NOT public I_UserDataInfo createUserData() 
//        BECAUSE UserSubsetModel returns String

        I_UserDataInfo result = null;

        switch (getInputType()) {
            case CONSTANT:
                result = createConstUserData();
                break;
            case CODE:
                result = createVRLUserData();
                break;
        }

        return result;
    }

    protected abstract I_UserDataInfo createVRLUserData();

    protected abstract I_UserDataInfo createConstUserData();

    public abstract String getToolTipText();

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

        ArrayList<String> paramNames = new ArrayList<String>();

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

        if (cond) {

            code = CondUserDataCompiler.getUserDataImplCode(code, paramNames);

        } else {
            code = UserDataCompiler.getUserDataImplCode(code, type,
                    paramNames, UserData.returnTypes[type]);
        }
        return code;
    }
}
