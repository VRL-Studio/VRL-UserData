/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.UserNumberModel;
import edu.gcsc.vrl.userdata.UserNumberWindow;
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
@TypeInfo(type = I_UserNumber.class, input = true, output = false, style = "default")
public class UserNumberType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;

//    /**
//     * @return the MODEL_KEY
//     */
//    public static String getMODEL_KEY() {
//        return MODEL_KEY;
//    }
    private UserNumberWindow window;
    private UserNumberModel model;
//    private static final String MODEL_KEY = "UserNumberType:model";

    public UserNumberType() {

        model = new UserNumberModel();

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

                window = new UserNumberWindow(model,
                        UserNumberType.this, "User Data Input", getMainCanvas());

//                customParamData2Window();
                window.modelData2WindowData(model, window);
                
                //add InputWindow to canvas
                getMainCanvas().addWindow(window);
            }
        });

    }

//    private UserNumberWindow getWindow() {
//        if (window == null) {
//            window = new UserNumberWindow(model,
//                    UserNumberType.this, "User Data Input", getMainCanvas());
//        }
//        return window;
//    }

//    private void customParamData2Window() {
//        if (getCustomData() != null) {
//            Object o = getCustomData().get(model.getModelKey());
//
//            if (o instanceof UserNumberModel) {
//                UserNumberModel model =  (UserNumberModel) o;
//                getWindow().setModel(model);
//            }
//        }
//    }

    @Override
    public Object getViewValue() {

        I_UserNumber result = null;

//        UserNumberModel model = null;

//        customParamData2Window();
//        model = getWindow().getModel();

        try {
            boolean isConst = model.isConstData();

            if (isConst) {
                I_ConstUserNumber number = new ConstUserNumber(model.getData());

                result = number;
            } else {

                switch (model.getDimension()) {
                    case 1:
                        I_VRLUserNumber1d number1d = new VRLUserNumber1d();
                        number1d.data(create1dCode(model.getCode()));
                        result = number1d;
                        break;
                    case 2:
                        I_VRLUserNumber2d number2d = new VRLUserNumber2d();
                        number2d.data(create2dCode(model.getCode()));
                        result = number2d;
                        break;
                    case 3:
                        I_VRLUserNumber3d number3d = new VRLUserNumber3d();
                        number3d.data(create3dCode(model.getCode()));
                        result = number3d;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            //
        }

        return createFinalUserData(result);
    }

    /**
     * May be used to create the final userdata object such as UserNumberPair.
     *
     * @param data
     * @return
     */
    protected Object createFinalUserData(I_UserNumber data) {
        return data;
    }

    private String create1dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, 0,
                paramNames, UserData.returnTypes[0]);

        return code;
    }

    private String create2dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, 0,
                paramNames, UserData.returnTypes[0]);

        return code;
    }

    private String create3dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("z");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, 0,
                paramNames, UserData.returnTypes[0]);

        return code;
    }

    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }
}
