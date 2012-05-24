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
import eu.mihosoft.vrl.reflection.RepresentationType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.VButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type=I_UserNumber.class, input=true, output=false, style="default")
public class UserNumberType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * @return the MODEL_KEY
     */
    public static String getMODEL_KEY() {
        return MODEL_KEY;
    }
    private UserNumberWindow window;
    private static final String MODEL_KEY = "UserNumberType:model";

    public UserNumberType() {

//        setType(I_UserNumber.class);

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

                window = new UserNumberWindow(
                        UserNumberType.this, "User Data Input", getMainCanvas());

                //add InputWindow to canvas
                getMainCanvas().addWindow(window);

                if (getCustomData() != null) {
                    Object o = getCustomData().get(getMODEL_KEY());

                    if (o instanceof UserNumberModel) {
                        UserNumberModel model =
                                (UserNumberModel) o;
                        window.setModel(model);
                    }
                }
            }
        });

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {

                    window = new UserNumberWindow(
                            UserNumberType.this, "User Data Input", getMainCanvas());

                    //add InputWindow to canvas
                    getMainCanvas().addWindow(window);

                    if (getCustomData() != null) {
                        Object o = getCustomData().get(getMODEL_KEY());

                        if (o instanceof UserNumberModel) {
                            UserNumberModel model =
                                    (UserNumberModel) o;
                            window.setModel(model);
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

    }

    @Override
    public Object getViewValue() {

        I_UserNumber result = null;

        UserNumberModel model = null;

        if (window == null && getCustomData() != null) {
            Object o = getCustomData().get(getMODEL_KEY());

            if (o instanceof UserNumberModel) {
                model = (UserNumberModel) o;

            }
        }

        if (window != null) {
            model = window.getModel();
        }
        if (model == null) {
            System.err.println(" >> UserNumberType.getViewValue(): model == null");
        }

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
