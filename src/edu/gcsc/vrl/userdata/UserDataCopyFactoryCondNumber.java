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
