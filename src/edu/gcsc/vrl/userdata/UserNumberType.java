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
public class UserNumberType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;
    private UserNumberWindow window;
    static final String MODEL_KEY = "UserNumberType:model";

    public UserNumberType() {

        setType(I_UserNumber.class);

        setName("");

        VButton btn = new VButton("edit");

        add(btn);

        add(nameLabel);

//        setStyleName("default");

        addSupportedRepresentationType(RepresentationType.INPUT);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                window = new UserNumberWindow(
                        UserNumberType.this, "User Data Input", getMainCanvas());

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

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {

                    window = new UserNumberWindow(
                            UserNumberType.this, "User Data Input", getMainCanvas());

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

        Object result = null;

        UserNumberModel model = null;

        if (window == null && getCustomData() != null) {
            Object o = getCustomData().get(MODEL_KEY);

            if (o instanceof UserNumberModel) {
                model =
                        (UserNumberModel) o;

            }
        }

        if (window != null) {
            model = window.getModel();
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

        return result;
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
}
