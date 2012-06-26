/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.CondUserDataCompiler;
import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.CondUserNumberWindow;
import edu.gcsc.vrl.userdata.UserDataModel;
import edu.gcsc.vrl.userdata.UserNumberModel;
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
@TypeInfo(type = I_CondUserNumber.class, input = true, output = false, style = "default")
public class CondUserNumberType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * @return the MODEL_KEY
     */
    public static String getMODEL_KEY() {
        return MODEL_KEY;
    }
    private CondUserNumberWindow window;
    private static final String MODEL_KEY = "CondUserType:model";

    public CondUserNumberType() {

//        setType(I_CondUserNumber.class);

        setName("");

        VButton btn = new VButton("edit");

        add(btn);

        add(nameLabel);

//        setStyleName("default");
//        addSupportedRepresentationType(RepresentationType.INPUT);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                customParamData2Window();
                
                //add InputWindow to canvas
                getMainCanvas().addWindow(getWindow());
                              
            }
        });

    }

    private CondUserNumberWindow getWindow() {
        if (window == null) {
            window = new CondUserNumberWindow(
                    CondUserNumberType.this, "User Data Input", getMainCanvas());
        }
        return window;
    }
    
    private void customParamData2Window() {
        if (getCustomData() != null) {
            Object o = getCustomData().get(getMODEL_KEY());

            if (o instanceof UserNumberModel) {
                UserNumberModel model = (UserNumberModel) o;
                getWindow().setModel(model);
            }
        }
    }

    @Override
    public Object getViewValue() {

        I_CondUserNumber result = null;

        UserNumberModel model = null;

        customParamData2Window();
        model = getWindow().getModel();

        try {
            switch (model.getDimension()) {
                case 1:
                    I_VRLCondUserNumber1d vector1d = new VRLCondUserNumber1d();
                    vector1d.data(create1dCode(model.getCode()));
                    result = vector1d;
                    break;
                case 2:
                    I_VRLCondUserNumber2d vector2d = new VRLCondUserNumber2d();
                    vector2d.data(create2dCode(model.getCode()));
                    result = vector2d;
                    break;
                case 3:
                    I_VRLCondUserNumber3d vector3d = new VRLCondUserNumber3d();
                    vector3d.data(create3dCode(model.getCode()));
                    result = vector3d;
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            //
        }

        Object finalResult = createFinalUserData(result);

        return finalResult;
    }

    /**
     * May be used to create the final userdata object such as UserNumberPair.
     *
     * @param data
     * @return
     */
    protected Object createFinalUserData(I_CondUserNumber data) {
        return data;
    }

    private String create1dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("t");
        paramNames.add("si");

        code = CondUserDataCompiler.getUserDataImplCode(code,
                paramNames);

        return code;
    }

    private String create2dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("t");
        paramNames.add("si");

        code = CondUserDataCompiler.getUserDataImplCode(code,
                paramNames);

        return code;
    }

    private String create3dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("z");
        paramNames.add("t");
        paramNames.add("si");

        code = CondUserDataCompiler.getUserDataImplCode(code,
                paramNames);

        return code;
    }

    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }
}
