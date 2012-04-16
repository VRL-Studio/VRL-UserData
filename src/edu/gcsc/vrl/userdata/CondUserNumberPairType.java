/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.CondUserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.CondUserDataCompiler;
import edu.gcsc.vrl.ug.api.*;
import eu.mihosoft.vrl.reflection.RepresentationType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.ConnectionResult;
import eu.mihosoft.vrl.visual.ConnectionStatus;
import eu.mihosoft.vrl.visual.VButton;
import eu.mihosoft.vrl.visual.VTextField;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CondUserNumberPairType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;
    private CondUserNumberWindow window;
    private VTextField input = new VTextField("");
    static final String MODEL_KEY = "CondUserType:model";

    public CondUserNumberPairType() {

        setType(CondUserNumberPair.class);

        setName("");

        VButton btn = new VButton("edit");

        add(btn);

        add(nameLabel);

//        setStyleName("default");

        addSupportedRepresentationType(RepresentationType.INPUT);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                window = new CondUserNumberWindow(
                        CondUserNumberPairType.this, "User Data Input", getMainCanvas());

                //add InputWindow to canvas
                getMainCanvas().addWindow(window);

                if (getCustomData() != null) {
                    Object o = getCustomData().get(MODEL_KEY);

                    if (o instanceof UserNumberModel) {
                        UserNumberModel model =
                                (UserNumberModel) o;
                        window.setModel(model);
                    }
                }
            }
        });

        input.setMinimumSize(new Dimension(80, input.getHeight()));
        input.setPreferredSize(new Dimension(80, input.getHeight()));
        input.setSize(new Dimension(80, input.getHeight()));

        setInputComponent(input);

        add(input);



    }

    @Override
    public Object getViewValue() {

        Object result = null;

        UserNumberModel model = null;

        if (window == null && getCustomData() != null) {
            Object o = getCustomData().get(MODEL_KEY);

            if (o instanceof UserNumberModel) {
                model = (UserNumberModel) o;

            }
        }

        if (window != null) {
            model = window.getModel();
        }

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

        CondUserNumberPair finalResult =
                new CondUserNumberPair(
                input.getText(), (I_CondUserNumber) result);


        return finalResult;
    }

    @Override
    public Object getValue() {

        return super.getValue();
    }

    @Override
    public void setValue(Object o) {
        
        // custom convert method (we allow  I_CondUserNumber as input)
        if (o instanceof I_CondUserNumber) {
            o = new CondUserNumberPair(input.getText(), (I_CondUserNumber) o);
        }

        super.setValue(o);
    }

    @Override
    public void setViewValue(Object o) {
        if (o instanceof CondUserNumberPair) {
            CondUserNumberPair pair = (CondUserNumberPair) o;
            input.setText(pair.getSubset());
        }
    }

    @Override
    public ConnectionResult compatible(TypeRepresentationBase tRep) {
        ConnectionResult result = super.compatible(tRep);

        // we allow connectiosn from I_CondUserNumber as we can convert
        // regular CondUserNumbers to CondUserNumberPairs
        if (result.getStatus() != ConnectionStatus.VALID) {
            if (I_CondUserNumber.class.isAssignableFrom(tRep.getType())) {
                result = new ConnectionResult(
                        null, ConnectionStatus.VALID);
            }
        }

        return result;
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
}
