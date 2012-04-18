/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.*;
import eu.mihosoft.vrl.reflection.RepresentationType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.VButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class UserVectorType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;
    private UserVectorWindow window;
    static final String MODEL_KEY = "UserVectorType:model";

    public UserVectorType() {

        setType(I_UserVector.class);

        setName("");
        
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        add(nameLabel);

        VButton btn = new VButton("edit");

        add(btn);


//        setStyleName("default");

        addSupportedRepresentationType(RepresentationType.INPUT);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                window = new UserVectorWindow(
                        UserVectorType.this, "User Data Input", getMainCanvas());

                //add InputWindow to canvas
                getMainCanvas().addWindow(window);

                if (getCustomData() != null) {
                    Object o = getCustomData().get(MODEL_KEY);

                    if (o instanceof UserVectorModel) {
                        UserVectorModel model =
                                (UserVectorModel) o;
                        window.setModel(model);
                    }
                }
            }
        });
    }

    @Override
    public Object getViewValue() {

        I_UserVector result = null;

        UserVectorModel model = null;

        if (window == null && getCustomData() != null) {
            Object o = getCustomData().get(MODEL_KEY);

            if (o instanceof UserVectorModel) {
                model =
                        (UserVectorModel) o;

            }
        }

        if (window != null) {
            model = window.getModel();
        }

        try {
            boolean isConst = model.isConstData();

            if (isConst) {
                result = arrayToUserVector(model.getData());
            } else {

                switch (model.getDimension()) {
                    case 1:
                        I_VRLUserVector1d vector1d = new VRLUserVector1d();
                        vector1d.data(create1dCode(model.getCode()));
                        result = vector1d;
                        break;
                    case 2:
                        I_VRLUserVector2d vector2d = new VRLUserVector2d();
                        vector2d.data(create2dCode(model.getCode()));
                        result = vector2d;
                        break;
                    case 3:
                        I_VRLUserVector3d vector3d = new VRLUserVector3d();
                        vector3d.data(create3dCode(model.getCode()));
                        result = vector3d;
                        break;
                    default:
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
    protected Object createFinalUserData(I_UserVector data) {
        return data;
    }

    private String create1dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, 1,
                paramNames, UserData.returnTypes[1]);

        return code;
    }

    private String create2dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, 1,
                paramNames, UserData.returnTypes[1]);

        return code;
    }

    private String create3dCode(String code) {

        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("z");
        paramNames.add("t");
        paramNames.add("si");

        code = UserDataCompiler.getUserDataImplCode(code, 1,
                paramNames, UserData.returnTypes[1]);

        return code;
    }

    private static I_ConstUserVector createVector(int dim) {
        if (dim == 1) {
            return new ConstUserVector1d();
        } else if (dim == 2) {
            return new ConstUserVector2d();
        } else if (dim == 3) {
            return new ConstUserVector3d();
        }

        return null;
    }

    private static I_ConstUserVector arrayToUserVector(Double[] data) {

        I_ConstUserVector result = createVector(data.length);

        for (int i = 0; i < data.length; i++) {
            result.set_entry(i, data[i]);
        }

        return result;
    }
    
    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }
}
