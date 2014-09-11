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
