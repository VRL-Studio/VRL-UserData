/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.I_UserNumber;
import edu.gcsc.vrl.ug.api.VRLUserLinkerNumberNumber1d;
import edu.gcsc.vrl.ug.api.VRLUserLinkerNumberNumber2d;
import edu.gcsc.vrl.ug.api.VRLUserLinkerNumberNumber3d;
import edu.gcsc.vrl.userdata.util.DimensionUtil;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserDataCopyFactoryLinkerNumber extends UserDataCopyFactory {

    public static Object createUserDataCopy(int dim, UserMathDataModel.InputType inputType, String codeData, String codeDeriv, Object data) {

        I_UserNumber result = null;

        int type = 0; //means Number, see docu of createCode()
        int numArgs = 1; //see DataLinkerModelNumberNumber.createUserData()
        
        switch (dim) {
            case 1:
                VRLUserLinkerNumberNumber1d r = new VRLUserLinkerNumberNumber1d();
                result = r;
                r.data(DataLinkerModel.createCode(codeData, dim, type, false), numArgs);
                r.deriv(0, DataLinkerModel.createCode(codeDeriv, dim, type, false));
                break;
            case 2:
                VRLUserLinkerNumberNumber2d r2 = new VRLUserLinkerNumberNumber2d();
                result = r2;
                r2.data(DataLinkerModel.createCode(codeData, dim, type, false), numArgs);
                r2.deriv(0, DataLinkerModel.createCode(codeDeriv, dim, type, false));
                break;
            case 3:
                VRLUserLinkerNumberNumber3d r3 = new VRLUserLinkerNumberNumber3d();
                result = r3;
                r3.data(DataLinkerModel.createCode(codeData, dim, type, false), numArgs);
                r3.deriv(0, DataLinkerModel.createCode(codeDeriv, dim, type, false));
                break;
            default:
                throw new RuntimeException(UserDataCopyFactoryLinkerNumber.class.getName()
                        + ": UserData has invalid dimension!");
        }

        return result;
    }

}


