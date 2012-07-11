/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.UserDataModel;
import edu.gcsc.vrl.userdata.UserMatrixModel;
import edu.gcsc.vrl.userdata.UserMatrixWindow;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.VButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = I_UserMatrix.class, input = true, output = false, style = "default")
public class UserMatrixType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;
//    /**
//     * @return the MODEL_KEY
//     */
//    public static String getMODEL_KEY() {
//        return MODEL_KEY;
//    }
    private UserMatrixWindow window;
    private UserMatrixModel model;

//    private static final String MODEL_KEY = "UserMatrixType:model";
    public UserMatrixType() {

        model = new UserMatrixModel();

        evaluateCustomParamData();

        setName("");

        nameLabel.setAlignmentX(LEFT_ALIGNMENT);

        add(nameLabel);

        VButton btn = new VButton("edit");

        add(btn);

//        // a little trick to have default values if parameter of this type is used
//        getWindow().close();

//        setStyleName("default");
//        addSupportedRepresentationType(RepresentationType.INPUT);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                window = new UserMatrixWindow(model,
                        UserMatrixType.this, "User Data Input", getMainCanvas());

//                 customParamData2Window();
//                window.modelData2WindowData(model, window);
                window.updateWindow(model);

                //add InputWindow to canvas
                getMainCanvas().addWindow(window);
            }
        });
    }

//    private UserMatrixWindow getWindow() {
//        if (window == null) {
//            window = new UserMatrixWindow(model,
//                    UserMatrixType.this, "User Data Input", getMainCanvas());
//        }
//        return window;
//    }
//     private void customParamData2Window() {
//
//        if (getCustomData() != null) {
//            Object o = getCustomData().get(model.getModelKey());
//
//            if (o instanceof UserMatrixModel) {
//                UserMatrixModel model =  (UserMatrixModel) o;
//                getWindow().setModel(model);
//            }
//        }
//    }
    @Override
    public Object getViewValue() {

        I_UserMatrix result = null;

//        UserMatrixModel model = null;

//        customParamData2Window();
//        model = getWindow().getModel();

        try {
            boolean isConst = model.isConstData();

            if (isConst) {
                
                System.out.println("UserMatrixType.getViewValue(): "
                        + "model.getData() = "+ model.getData());
                
                result = arrayToUserMatrix(model.getData());
                
                System.out.println("result = " + result);
            } else {

                switch (model.getDimension()) {
                    case 1:
                        I_VRLUserMatrix1d vector1d = new VRLUserMatrix1d();
                        vector1d.data(create1dCode(model.getCode()));
                        result = vector1d;
                        break;
                    case 2:
                        I_VRLUserMatrix2d vector2d = new VRLUserMatrix2d();
                        vector2d.data(create2dCode(model.getCode()));
                        result = vector2d;
                        break;
                    case 3:
                        I_VRLUserMatrix3d vector3d = new VRLUserMatrix3d();
                        vector3d.data(create3dCode(model.getCode()));
                        result = vector3d;
                        break;
                    default:
                        System.out.println(">> UserMatrixType: UserData has invalid dimension!");
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }


        return createFinalUserData(result);
    }

    /**
     * May be used to create the final userdata object such as UserNumberPair.
     *
     * @param data
     * @return
     */
    protected Object createFinalUserData(I_UserMatrix data) {
        return data;
    }

    private String create1dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, 2,
                paramNames, UserData.returnTypes[2]);

        return code;
    }

    private String create2dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, 2,
                paramNames, UserData.returnTypes[2]);

        return code;
    }

    private String create3dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("z");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, 2,
                paramNames, UserData.returnTypes[2]);

        return code;
    }

    private static I_ConstUserMatrix createMatrix(int dim) {
        if (dim == 1) {
            return new ConstUserMatrix1d();
        } else if (dim == 2) {
            return new ConstUserMatrix2d();
        } else if (dim == 3) {
            return new ConstUserMatrix3d();
        }

        return null;
    }

    private static I_ConstUserMatrix arrayToUserMatrix(Double[][] data) {

        I_ConstUserMatrix result = createMatrix(data.length);

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[j].length; i++) {
                result.set_entry(i, j, data[i][j]);
            }
        }

        return result;
    }

    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }

    @Override
    public void evaluateCustomParamData() {
        super.evaluateCustomParamData();

        System.out.println("UserMatrixType.evaluateCustomParamData():");

        UserDataModel tmp = (UserDataModel) this.getCustomData().get(model.getModelKey());

        if (tmp != null) {
            Double[][] data = (Double[][]) tmp.getData();

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    System.out.println("data[" + i + "][" + j + "] = " + data[i][j]);
                }
            }

            if (data != null) {
                model.setData(data);
            }

            String code = tmp.getCode();
            
            System.out.println("code = "+ code);

            if (code != null) {
                model.setCode(code);
            }
        }
    }
}
