/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.UserDataModel;
import edu.gcsc.vrl.userdata.UserDataWindow;
import edu.gcsc.vrl.userdata.UserVectorModel;
import edu.gcsc.vrl.userdata.UserVectorWindow;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.visual.Canvas;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = I_UserVector.class, input = true, output = false, style = "default")
public class UserVectorType extends UserDataType implements Serializable {

    private static final long serialVersionUID = 1;

    public UserVectorType() {
        super();
    }

//    @Override
//    public Object getViewValue() {
//
//        I_UserVector result = null;
//
//        try {
//            boolean isConst = model.isConstData();
//
//            if (isConst) {
//                result = arrayToUserVector(model.getData());
//            } else {
//
//                switch (model.getDimension()) {
//                    case 1:
//                        I_VRLUserVector1d vector1d = new VRLUserVector1d();
//                        vector1d.data(create1dCode(model.getCode()));
//                        result = vector1d;
//                        break;
//                    case 2:
//                        I_VRLUserVector2d vector2d = new VRLUserVector2d();
//                        vector2d.data(create2dCode(model.getCode()));
//                        result = vector2d;
//                        break;
//                    case 3:
//                        I_VRLUserVector3d vector3d = new VRLUserVector3d();
//                        vector3d.data(create3dCode(model.getCode()));
//                        result = vector3d;
//                        break;
//                    default:
//                        System.out.println(">> " + this.getClass().getSimpleName() + ": UserData has invalid dimension!");
//                        break;
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace(System.err);
//        }
//
//
//        return createFinalUserData(result);
//    }

//    private static I_ConstUserVector createVector(int dim) {
//        if (dim == 1) {
//            return new ConstUserVector1d();
//        } else if (dim == 2) {
//            return new ConstUserVector2d();
//        } else if (dim == 3) {
//            return new ConstUserVector3d();
//        }
//
//        return null;
//    }
//    private static I_ConstUserVector arrayToUserVector(Double[] data) {
//
//        I_ConstUserVector result = createVector(data.length);
//
//        for (int i = 0; i < data.length; i++) {
//            result.set_entry(i, data[i]);
//        }
//
//        return result;
//    }
    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }

    @Override
    protected UserDataModel createUserDataModel() {
        return new UserVectorModel();
    }

    @Override
    protected UserDataWindow createUserDataWindow(UserDataModel userDataModel,
            UserDataType userDataType, String title, Canvas mainCanvas) {
        return new UserVectorWindow(model, this, title, mainCanvas);
    }

    @Override
    protected I_IIPData createVRLUserDataFromModel(UserDataModel model) {

        /* TODO: Ask why there is no I_VRLUserVector which has also access to
         *       the method data(String).
        */
        I_UserVector result = null;

        int dim = model.getDimension();
        int type = 1; //means Vector, see docu of createCode()


        switch (dim) {
            case 1:
                I_VRLUserVector1d vector1d = new VRLUserVector1d();
                vector1d.data(createCode(model.getCode(), dim, type, false));
                result = vector1d;
                break;
            case 2:
                I_VRLUserVector2d vector2d = new VRLUserVector2d();
                vector2d.data(createCode(model.getCode(), dim, type, false));
                result = vector2d;
                break;
            case 3:
                I_VRLUserVector3d vector3d = new VRLUserVector3d();
                vector3d.data(createCode(model.getCode(), dim, type, false));
                result = vector3d;
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

        I_ConstUserVector result = null;

        int dim = model.getDimension();
        int type = 2; //means Matrix, see docu of createCode()

        if (dim == 1) {
            result = new ConstUserVector1d();
        } else if (dim == 2) {
            result = new ConstUserVector2d();
        } else if (dim == 3) {
            result = new ConstUserVector3d();
        }

        Double[] data = (Double[]) model.getData();

        for (int i = 0; i < data.length; i++) {
            result.set_entry(i, data[i]);
        }

        return result;
    }
}
