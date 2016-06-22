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

import edu.gcsc.vrl.ug.api.ConstUserVector1d;
import edu.gcsc.vrl.ug.api.ConstUserVector2d;
import edu.gcsc.vrl.ug.api.ConstUserVector3d;
import edu.gcsc.vrl.ug.api.I_ConstUserVector;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.I_VRLUserVector;
import edu.gcsc.vrl.ug.api.VRLUserVector1d;
import edu.gcsc.vrl.ug.api.VRLUserVector2d;
import edu.gcsc.vrl.ug.api.VRLUserVector3d;
import static edu.gcsc.vrl.userdata.UserMathDataModel.createCode;
import edu.gcsc.vrl.userdata.util.DimensionUtil;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataCopyFactoryVector extends UserDataCopyFactory{

    
    @Override
    public Object createUserDataCopy(int dim, UserMathDataModel.InputType inputType, String codeData, String codeDeriv, Object data) {

        I_UserDataInfo result = null;

        switch (inputType) {
            case CONSTANT:

                switch (dim) {
                    case 1:
                        result = new ConstUserVector1d();
                        break;
                    case 2:
                        result = new ConstUserVector2d();
                        break;
                    case 3:
                        result = new ConstUserVector3d();
                        break;
                    default:
                        throw new RuntimeException( UserDataCopyFactoryVector.class.getName()+ ": UserData has invalid dimension!");
                }

                int arrayDim = DimensionUtil.getArrayDimension(data);
                if (arrayDim == 1) {
                    Double[] entries = (Double[]) data;

                    for (int i = 0; i < entries.length; i++) {
                        ((I_ConstUserVector) result).set_entry(i, entries[i]);
                    }

                } else {
                    throw new RuntimeException( UserDataCopyFactoryVector.class.getName() +".createUserDataCopy( ):  Data has wrong size:" + arrayDim);
                }

                break; // end CONSTANT

            case CODE:

                switch (dim) {
                    case 1:
                        result = new VRLUserVector1d();
                        break;
                    case 2:
                        result = new VRLUserVector2d();
                        break;
                    case 3:
                        result = new VRLUserVector3d();
                        break;
                    default:
                        throw new RuntimeException( UserDataCopyFactoryVector.class.getName() + ": UserData has invalid dimension!");
                }

                ((I_VRLUserVector) result).data(createCode(codeData, dim, 1, false));

                break; // end CODE
        }

        return result;
    }

    
}
