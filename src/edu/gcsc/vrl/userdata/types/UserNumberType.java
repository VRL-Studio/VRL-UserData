/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.UserDataModel;
import edu.gcsc.vrl.userdata.UserDataWindow;
import edu.gcsc.vrl.userdata.UserNumberModel;
import edu.gcsc.vrl.userdata.UserNumberWindow;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.visual.Canvas;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = I_UserNumber.class, input = true, output = false, style = "default")
public class UserNumberType extends UserDataType implements Serializable {

    private static final long serialVersionUID = 1;

    public UserNumberType() {
        super();
    }

//    @Override
//    public Object getViewValue() {
//
//        I_UserNumber result = null;
//
//        try {
//            boolean isConst = model.isConstData();
//
//            if (isConst) {
//                I_ConstUserNumber number = new ConstUserNumber(model.getData());
//
//                result = number;
//            } else {
//
//                switch (model.getDimension()) {
//                    case 1:
//                        I_VRLUserNumber1d number1d = new VRLUserNumber1d();
//                        number1d.data(create1dCode(model.getCode()));
//                        result = number1d;
//                        break;
//                    case 2:
//                        I_VRLUserNumber2d number2d = new VRLUserNumber2d();
//                        number2d.data(create2dCode(model.getCode()));
//                        result = number2d;
//                        break;
//                    case 3:
//                        I_VRLUserNumber3d number3d = new VRLUserNumber3d();
//                        number3d.data(create3dCode(model.getCode()));
//                        result = number3d;
//                        break;
//                    default:
//                        break;
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace(System.err);
//        }
//
//        return createFinalUserData(result);
//    }

    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }

    @Override
    protected UserDataModel createUserDataModel() {
        return new UserNumberModel();
    }

    @Override
    protected UserDataWindow createUserDataWindow(UserDataModel userDataModel,
            UserDataType userDataType, String title, Canvas mainCanvas) {
        return new UserNumberWindow(model, this, title, mainCanvas);
    }

    @Override
    protected I_IIPData createVRLUserDataFromModel(UserDataModel model) {

        /*
         * TODO: Ask why there is no I_VRLUserNumber which has also access to
         * the method data(String).
         */
        I_UserNumber result = null;

        int dim = model.getDimension();
        int type = 0; //means Number, see docu of createCode()


        switch (dim) {
            case 1:
                I_VRLUserNumber1d number1d = new VRLUserNumber1d();
                number1d.data(createCode(model.getCode(), dim, type, false));
                result = number1d;
                break;
            case 2:
                I_VRLUserNumber2d number2d = new VRLUserNumber2d();
                number2d.data(createCode(model.getCode(), dim, type, false));
                result = number2d;
                break;
            case 3:
                I_VRLUserNumber3d number3d = new VRLUserNumber3d();
                number3d.data(createCode(model.getCode(), dim, type, false));
                result = number3d;
                break;
            default:
                System.out.println(">> " + this.getClass().getSimpleName()
                        + ": UserData has invalid dimension!");
                break;
        }

        return result;
    }

    @Override
    protected I_IIPData createConstUserDataFromModel(UserDataModel model) {
        return new ConstUserNumber((Double) model.getData());
    }
}
