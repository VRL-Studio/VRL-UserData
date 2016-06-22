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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataCopyFactory {

    public Object createUserDataCopy(int dim, String inputType, String codeData, String codeDeriv, Object data) {

        UserMathDataModel.InputType type = null;

        if (UserMathDataModel.InputType.CODE.toString().equals(inputType)) {
            type = UserMathDataModel.InputType.CODE;
        } else if (UserMathDataModel.InputType.CONSTANT.toString().equals(inputType)) {
            type = UserMathDataModel.InputType.CONSTANT;
        } else if (UserMathDataModel.InputType.SCALAR_CONSTANT.toString().equals(inputType)) {
            type = UserMathDataModel.InputType.SCALAR_CONSTANT;
        } else if (UserMathDataModel.InputType.LINKER.toString().equals(inputType)) {
            type = UserMathDataModel.InputType.LINKER;
        }

        return createUserDataCopy(dim, type, codeData, codeDeriv, data);
    }

    /**
     * This method is a helper for getModelAsCode() to create a copy of an existing UserData
     * with the specified parameters.
     
     @param dim is the dimension of the userdata
     @param inputType declares if the userdata is a SCALAR_CONSTANT, CONSTANT or CODE
     @see edu.gcsc.vrl.userdata.UserMathDataModel#inputType
     @param codeData that is written in the editor of a UserDataWindow for the data
     @param codeDeriv  that is written in the editor of a UserDataWindow for the data
     @param data are the entries of User- Number/Vector/Matrix 
     @return a temporal object which creates the copy of an existing userdata
     */
    public Object createUserDataCopy(int dim, UserMathDataModel.InputType inputType, String codeData, String codeDeriv, Object data) {
        return new UnsupportedOperationException("Needs to be implemented by a derived class.");
    }

}
