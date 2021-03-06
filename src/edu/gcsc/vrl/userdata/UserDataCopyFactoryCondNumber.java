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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.ConstUserNumber;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.I_VRLCondUserNumber;
import edu.gcsc.vrl.ug.api.VRLCondUserNumber1d;
import edu.gcsc.vrl.ug.api.VRLCondUserNumber2d;
import edu.gcsc.vrl.ug.api.VRLCondUserNumber3d;
import static edu.gcsc.vrl.userdata.UserMathDataModel.createCode;
import edu.gcsc.vrl.userdata.util.DimensionUtil;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataCopyFactoryCondNumber extends UserDataCopyFactory {

    @Override
    public Object createUserDataCopy(int dim, UserMathDataModel.InputType inputType, String codeData, String codeDeriv, Object data) {

        I_UserDataInfo result = null;

        Double entry;
        int arrayDim = DimensionUtil.getArrayDimension(data);
        if (arrayDim == 0) {
            entry = (Double) data;
        } else {
            throw new RuntimeException(UserDataCopyFactoryCondNumber.class.getName() + ".createUserDataCopy( ):  Data has wrong size:" + arrayDim);
        }

        switch (inputType) {
            case CONSTANT:
                result = new ConstUserNumber(entry);

                break; // end CONSTANT

            case CODE:

                switch (dim) {
                    case 1:
                        result = new VRLCondUserNumber1d();
                        break;
                    case 2:
                        result = new VRLCondUserNumber2d();
                        break;
                    case 3:
                        result = new VRLCondUserNumber3d();
                        break;
                    default:
                        throw new RuntimeException(UserDataCopyFactoryCondNumber.class.getName()
                                + ": UserData has invalid dimension!");
                }

                ((I_VRLCondUserNumber) result).data(createCode(codeData, dim, 0, true));

                break; // end CODE
        }

        return result;
    }

}
