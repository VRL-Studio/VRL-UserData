/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.*;
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
@TypeInfo(type=I_UserMatrix.class, input=true, output=false, style="default")
public class UserMatrixType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;
    private UserMatrixWindow window;
    static final public String MODEL_KEY = "UserMatrixType:model";

    public UserMatrixType() {

//        setType(I_UserMatrix.class);

        setName("");
        
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        add(nameLabel);

        VButton btn = new VButton("edit");

        add(btn);


//        setStyleName("default");
//        addSupportedRepresentationType(RepresentationType.INPUT);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                window = new UserMatrixWindow(
                        UserMatrixType.this, "User Data Input", getMainCanvas());

                //add InputWindow to canvas
                getMainCanvas().addWindow(window);

                if (getCustomData() != null) {
                    Object o = getCustomData().get(MODEL_KEY);

                    if (o instanceof UserMatrixModel) {
                        UserMatrixModel model =
                                (UserMatrixModel) o;
                        window.setModel(model);
                    }
                }
            }
        });
    }

    @Override
    public Object getViewValue() {

        I_UserMatrix result = null;

        UserMatrixModel model = null;

        if (window == null && getCustomData() != null) {
            Object o = getCustomData().get(MODEL_KEY);

            if (o instanceof UserMatrixModel) {
                model = (UserMatrixModel) o;

            }
        }

        if (window != null) {
            model = window.getModel();
        }
        
        if (model == null) {
            System.err.println(" >> UserMatrixType.getViewValue(): model == null");
        }

        try {
            boolean isConst = model.isConstData();

            if (isConst) {
                result = arrayToUserMatrix(model.getData());
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
                        break;
                }
            }
        } catch (Exception ex) {
//            ex.printStackTrace(System.err);
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
}
