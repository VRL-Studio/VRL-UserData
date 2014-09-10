/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.ConstUserMatrix1d;
import edu.gcsc.vrl.ug.api.ConstUserMatrix2d;
import edu.gcsc.vrl.ug.api.ConstUserMatrix3d;
import edu.gcsc.vrl.ug.api.I_ConstUserMatrix;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.I_VRLUserMatrix;
import edu.gcsc.vrl.ug.api.VRLUserMatrix1d;
import edu.gcsc.vrl.ug.api.VRLUserMatrix2d;
import edu.gcsc.vrl.ug.api.VRLUserMatrix3d;
import static edu.gcsc.vrl.userdata.UserMathDataModel.createCode;
import edu.gcsc.vrl.userdata.util.DimensionUtil;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataCopyFactoryMatrix extends UserDataCopyFactory {

    public static Object createUserDataCopy(int dim, UserMathDataModel.InputType inputType, String codeData, String codeDeriv, Object data) {

        I_UserDataInfo result = null;

        switch (inputType) {
            case CONSTANT:

                switch (dim) {
                    case 1:
                        result = new ConstUserMatrix1d();
                        break;
                    case 2:
                        result = new ConstUserMatrix2d();
                        break;
                    case 3:
                        result = new ConstUserMatrix3d();
                        break;
                    default:
                        throw new RuntimeException(UserDataCopyFactoryMatrix.class.getName() + ": UserData has invalid dimension!");
                }

                int arrayDim = DimensionUtil.getArrayDimension(data);
                if (arrayDim == 2) {
                    Double[][] entries = (Double[][]) data;

                    for (int j = 0; j < entries.length; j++) {
                        for (int i = 0; i < entries[j].length; i++) {
                            ((I_ConstUserMatrix) result).set_entry(i, j, entries[i][j]);
                        }
                    }

                } else {
                    throw new RuntimeException(UserDataCopyFactoryMatrix.class.getName() + ".createUserDataCopy( ):  Data has wrong size:" + arrayDim);
                }

                break; // end CONSTANT

            case CODE:

                switch (dim) {
                    case 1:
                        result = new VRLUserMatrix1d();
                        break;
                    case 2:
                        result = new VRLUserMatrix2d();
                        break;
                    case 3:
                        result = new VRLUserMatrix3d();
                        break;
                    default:
                        throw new RuntimeException(UserDataCopyFactoryMatrix.class.getName() + ": UserData has invalid dimension!");
                }

                ((I_VRLUserMatrix) result).data(createCode(codeData, dim, 2, false));

                break; // end CODE
        }

        return result;
    }

}
