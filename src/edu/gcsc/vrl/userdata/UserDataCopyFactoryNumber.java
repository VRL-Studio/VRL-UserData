/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.ConstUserNumber1d;
import edu.gcsc.vrl.ug.api.ConstUserNumber2d;
import edu.gcsc.vrl.ug.api.ConstUserNumber3d;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.I_VRLUserNumber;
import edu.gcsc.vrl.ug.api.VRLUserNumber1d;
import edu.gcsc.vrl.ug.api.VRLUserNumber2d;
import edu.gcsc.vrl.ug.api.VRLUserNumber3d;
import static edu.gcsc.vrl.userdata.UserMathDataModel.createCode;
import edu.gcsc.vrl.userdata.util.DimensionUtil;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataCopyFactoryNumber extends UserDataCopyFactory {

    public static Object createUserDataCopy(int dim, UserMathDataModel.InputType inputType, String codeData, String codeDeriv, Object data) {

        I_UserDataInfo result = null;

        Double entry;
        int arrayDim = DimensionUtil.getArrayDimension(data);
        if (arrayDim == 0) {
            entry = (Double) data;
        } else {
            throw new RuntimeException(UserDataCopyFactoryNumber.class.getName()+".createUserDataCopy( ):  Data has wrong size:" + arrayDim);
        }

        switch (inputType) {
            case CONSTANT:

                switch (dim) {
                    case 1:
                        result = new ConstUserNumber1d(entry);
                        break;
                    case 2:
                        result = new ConstUserNumber2d(entry);
                        break;
                    case 3:
                        result = new ConstUserNumber3d(entry);
                        break;
                    default:
                        throw new RuntimeException(UserDataCopyFactoryNumber.class.getName()
                                + ": UserData has invalid dimension!");
                }

                break; // end CONSTANT

            case CODE:

               switch (dim) {
            case 1:
                result = new VRLUserNumber1d();
                break;
            case 2:
                result = new VRLUserNumber2d();
                break;
            case 3:
                result = new VRLUserNumber3d();
                break;
            default:
                throw new RuntimeException(UserDataCopyFactoryNumber.class.getName()
                        + ": UserData has invalid dimension!");
        }

                ((I_VRLUserNumber) result).data(createCode(codeData, dim, 0, false));

                break; // end CODE
        }

        return result;
    }

}
