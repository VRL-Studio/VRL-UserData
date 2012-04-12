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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

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

        add(nameLabel);

//        setStyleName("default");

        addSupportedRepresentationType(RepresentationType.INPUT);

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {


                    window = new UserNumberWindow(
                            UserNumberType.this, "User Data Input", getMainCanvas());

                    //add InputWindow to canvas
                    getMainCanvas().addWindow(window);
                    
                    if (getCustomData()!=null) {
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
        try {
            boolean isConst = window.getModel().isConstData();

            if (isConst) {
                I_ConstUserNumber number = new ConstUserNumber(window.getModel().getData());

                result = number;
            } else {

                switch (window.getModel().getDimension()) {
                    case 1:
                        I_VRLUserNumber1d number1d = new VRLUserNumber1d();
                        number1d.userNumber(create1dCode(window.getModel().getCode()));
                        result = number1d;
                        break;
                    case 2:
                        I_VRLUserNumber2d number2d = new VRLUserNumber2d();
                        number2d.userNumber(create2dCode(window.getModel().getCode()));
                        result = number2d;
                        break;
                    case 3:
                        I_VRLUserNumber2d number3d = new VRLUserNumber2d();
                        number3d.userNumber(create3dCode(window.getModel().getCode()));
                        result = number3d;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
        System.out.println("VALUE: " + result);
        
        return result;
    }

    private String create1dCode(String code) {
        
        ArrayList<String> paramNames = new ArrayList<String>();
        
        paramNames.add("x");
        paramNames.add("t");
        
        code = UserDataCompiler.getUserDataImplCode(code, 0,
                    paramNames, UserData.returnTypes[0]);
        
        System.out.println("1D-CODE:\n" + code);
        
        return code;
    }
    
    private String create2dCode(String code) {
        
        ArrayList<String> paramNames = new ArrayList<String>();
        
        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("t");
        
        code = UserDataCompiler.getUserDataImplCode(code, 0,
                    paramNames, UserData.returnTypes[0]);
        
        System.out.println("2D-CODE:\n" + code);
        
        return code;
    }
    
    private String create3dCode(String code) {
        
        ArrayList<String> paramNames = new ArrayList<String>();
        
        paramNames.add("x");
        paramNames.add("y");
        paramNames.add("z");
        paramNames.add("t");
        
        code = UserDataCompiler.getUserDataImplCode(code, 0,
                    paramNames, UserData.returnTypes[0]);
        
        System.out.println("3D-CODE:\n" + code);
        
        return code;
    }
}
