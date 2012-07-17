/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.UserDataModel;
import edu.gcsc.vrl.userdata.UserDataWindow;
import edu.gcsc.vrl.userdata.UserMatrixModel;
import edu.gcsc.vrl.userdata.UserMatrixWindow;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.visual.Canvas;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = I_UserMatrix.class, input = true, output = false, style = "default")
public class UserMatrixType extends UserDataType implements Serializable {

    private static final long serialVersionUID = 1;

    public UserMatrixType() {
        super();
    }

//    @Override
//    public Object getViewValue() {
//
//        I_UserMatrix result = null;
//
//        try {
//            boolean isConst = model.isConstData();
//
//            if (isConst) {
//                
//                System.out.println("UserMatrixType.getViewValue(): "
//                        + "model.getData() = "+ model.getData());
//                
//                result = arrayToUserMatrix(model.getData());
//                
//                System.out.println("result = " + result);
//            } else {
//
//                switch (model.getDimension()) {
//                    case 1:
//                        I_VRLUserMatrix1d vector1d = new VRLUserMatrix1d();
//                        vector1d.data(create1dCode(model.getCode()));
//                        result = vector1d;
//                        break;
//                    case 2:
//                        I_VRLUserMatrix2d vector2d = new VRLUserMatrix2d();
//                        vector2d.data(create2dCode(model.getCode()));
//                        result = vector2d;
//                        break;
//                    case 3:
//                        I_VRLUserMatrix3d vector3d = new VRLUserMatrix3d();
//                        vector3d.data(create3dCode(model.getCode()));
//                        result = vector3d;
//                        break;
//                    default:
//                        System.out.println(">> UserMatrixType: UserData has invalid dimension!");
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
//    
//    private static I_ConstUserMatrix createMatrix(int dim) {
//        if (dim == 1) {
//            return new ConstUserMatrix1d();
//        } else if (dim == 2) {
//            return new ConstUserMatrix2d();
//        } else if (dim == 3) {
//            return new ConstUserMatrix3d();
//        }
//
//        return null;
//    }
//
//    private static I_ConstUserMatrix arrayToUserMatrix(Double[][] data) {
//
//        I_ConstUserMatrix result = createMatrix(data.length);
//
//        for (int j = 0; j < data.length; j++) {
//            for (int i = 0; i < data[j].length; i++) {
//                result.set_entry(i, j, data[i][j]);
//            }
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
        return new UserMatrixModel();
    }

    @Override
    protected UserDataWindow createUserDataWindow(UserDataModel userDataModel,
            UserDataType userDataType, String title, Canvas mainCanvas) {

        return new UserMatrixWindow(model, this, title, mainCanvas);
    }

    @Override
    protected I_IIPData createVRLUserDataFromModel(UserDataModel model) {

        /* TODO: Ask why there is no I_VRLUserMatrix which has also access to
         *       the method data(String).
        */
        I_UserMatrix result = null;
        
        int dim = model.getDimension();
        int type = 2; //means Matrix, see docu of createCode()

        switch (dim) {
            case 1:
                I_VRLUserMatrix1d matrix1d = new VRLUserMatrix1d();
                matrix1d.data(createCode(model.getCode(), dim, type, false));
                result = matrix1d;
                break;
            case 2:
                I_VRLUserMatrix2d matrix2d = new VRLUserMatrix2d();
                matrix2d.data(createCode(model.getCode(), dim, type, false));
                result = matrix2d;
                break;
            case 3:
                I_VRLUserMatrix3d matrix3d = new VRLUserMatrix3d();
                matrix3d.data(createCode(model.getCode(), dim, type, false));
                result = matrix3d;
                break;
            default:
                System.out.println(">> "+this.getClass().getSimpleName()
                        +": UserData has invalid dimension!");
                break;
        }
        
        return result;
    }

    @Override
    protected I_IIPData createConstUserDataFromModel(UserDataModel model) {

        I_ConstUserMatrix result = null;
        int dim = model.getDimension();

        if (dim == 1) {
            result = new ConstUserMatrix1d();
        } else if (dim == 2) {
            result = new ConstUserMatrix2d();
        } else if (dim == 3) {
            result = new ConstUserMatrix3d();
        }

        Double[][] data = (Double[][]) model.getData();

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[j].length; i++) {
                result.set_entry(i, j, data[i][j]);
            }
        }

        return result;
    }
}
