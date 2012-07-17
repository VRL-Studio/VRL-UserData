/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.CondUserNumberModel;
import edu.gcsc.vrl.userdata.UserDataModel;
import eu.mihosoft.vrl.annotation.TypeInfo;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = I_CondUserNumber.class, input = true, output = false, style = "default")
public class CondUserNumberType extends UserDataType implements Serializable {

    private static final long serialVersionUID = 1;

    public CondUserNumberType() {
        super();
    }

//    @Override
//    public Object getViewValue() {
//
//        I_CondUserNumber result = null;
//
//        try {
//            switch (model.getDimension()) {
//                case 1:
//                    I_VRLCondUserNumber1d vector1d = new VRLCondUserNumber1d();
//                    vector1d.data(create1dCode(model.getCode()));
//                    result = vector1d;
//                    break;
//                case 2:
//                    I_VRLCondUserNumber2d vector2d = new VRLCondUserNumber2d();
//                    vector2d.data(create2dCode(model.getCode()));
//                    result = vector2d;
//                    break;
//                case 3:
//                    I_VRLCondUserNumber3d vector3d = new VRLCondUserNumber3d();
//                    vector3d.data(create3dCode(model.getCode()));
//                    result = vector3d;
//                    break;
//                default:
//                    break;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace(System.err);
//        }
//
//        Object finalResult = createFinalUserData(result);
//
//        return finalResult;
//    }

    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }

    @Override
    protected UserDataModel createUserDataModel() {
        return new CondUserNumberModel();
    }

//    @Override
//    protected UserDataWindow createUserDataWindow(UserDataModel userDataModel,
//            UserDataType userDataType, String title, Canvas mainCanvas) {
//        return new CondUserNumberWindow(model, this, title, mainCanvas);
//    }

    @Override
    protected I_IUserData createVRLUserDataFromModel(UserDataModel model) {

        /*
         * TODO: Ask why there is no I_VRLCondUserNumber which has also access
         * to the method data(String).
         */
        I_CondUserNumber result = null;

        int dim = model.getDimension();
        int type = 0; //means Number, see docu of createCode()

        switch (dim) {
            case 1:
                I_VRLCondUserNumber1d number1d = new VRLCondUserNumber1d();
                number1d.data(createCode(model.getCode(), dim, type, true));
                result = number1d;
                break;
            case 2:
                I_VRLCondUserNumber2d number2d = new VRLCondUserNumber2d();
                number2d.data(createCode(model.getCode(), dim, type, true));
                result = number2d;
                break;
            case 3:
                I_VRLCondUserNumber3d number3d = new VRLCondUserNumber3d();
                number3d.data(createCode(model.getCode(), dim, type, true));
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
    protected I_IUserData createConstUserDataFromModel(UserDataModel model) {
        return new ConstUserNumber((Double) model.getData());
    }
}
