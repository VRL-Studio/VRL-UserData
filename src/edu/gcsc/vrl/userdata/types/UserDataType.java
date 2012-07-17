/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.CondUserDataCompiler;
import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.I_IIPData;

import edu.gcsc.vrl.userdata.UserDataModel;
import edu.gcsc.vrl.userdata.UserDataWindow;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.Canvas;
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
@TypeInfo(type = I_IIPData.class, input = true, output = false, style = "default")
public abstract class UserDataType extends TypeRepresentationBase implements Serializable {

    private static final long serialVersionUID = 1;
    protected UserDataWindow window;
    protected UserDataModel model;

    public UserDataType() {

        model = createUserDataModel();

        evaluateCustomParamData();

        setName("");

        nameLabel.setAlignmentX(LEFT_ALIGNMENT);

        add(nameLabel);

        VButton btn = new VButton("edit");

        add(btn);

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                window = new UserDataWindow(model,
                        UserDataType.this, "User Data Input", getMainCanvas());

                window.updateWindow(model);

                //add InputWindow to canvas
                getMainCanvas().addWindow(window);
            }
        });
    }

    @Override
    public Object getViewValue() {

        I_IIPData result = createUserDataFromModel(model);

        return createFinalUserData(result);
    }

    /**
     * A hook that could be used to decorate an UserData object like e.g
     * I_UserNumber with UserNumberPair.
     * If this method is not overloaded it returns the same instance as
     * put in the parameter
     * <code>userData</code>.
     *
     * @param userData a reference of e.g. I_UserNumber
     *
     * @return reference on the maybe decorated instance
     */
    protected Object createFinalUserData(I_IIPData userData) {
        return userData;
    }

    /**
     *
     * @param code that is written in the editor of a UserDataWindow
     * @param dim is an Integer between 1 and 3 indicating the space dimension
     * @param type is an Integer between 0 and 3,<br>
     * 0 = CondNumber or Number,<br>
     * 1 = Vector, <br>
     * 2 = Matrix, <br>
     * 3 = Tensor <br>
     * @param cond if true CondUserDataCompiler is used else UserDataCompiler
     *
     * @return the code that is returned by one of the above named ug-compilers
     */
    protected String createCode(String code, int dim, int type, boolean cond) {

        ArrayList<String> paramNames = new ArrayList<String>();

        if (dim >= 1) {
            paramNames.add("x");
        }
        if (dim >= 2) {
            paramNames.add("y");
        }
        if (dim >= 3) {
            paramNames.add("z");
        }
        paramNames.add("t");
        paramNames.add("si");

        if (cond) {
            
            code = CondUserDataCompiler.getUserDataImplCode(code, paramNames);

        } else {         
            code = UserDataCompiler.getUserDataImplCode(code, type,
                    paramNames, UserData.returnTypes[type]);
        }
        return code;
    }

    /**
     * Check if there is CustomData in this Typerepresentation and if so update
     * the corresponding UserDataModel to the values in the custom data.
     * 
     * <p>
     * <b>See Also</b> {@see TypeRepresentationBase#evaluateCustomParamData()} <br>
     * 
     *  {@inheritDoc}
     * </p>
     */
    @Override
    public void evaluateCustomParamData() {
        super.evaluateCustomParamData();

//        System.out.println(this.getClass().getSimpleName()+".evaluateCustomParamData():");

        UserDataModel tmp = (UserDataModel) this.getCustomData().get(model.getModelKey());

        if (tmp != null) {
            
            Object data = tmp.getData();

            if (data != null) {
                model.setData(data);
            }

            String code = tmp.getCode();

            if (code != null) {
                model.setCode(code);
            }
            
            model.setDimension(tmp.getDimension());
            model.setConstData(tmp.isConstData());
        }
    }

    /**
     * Creates a new specific instance of an UserDataModel.
     * Specific means (Cond-)UserNumber-, UserVector-, UserMatrix-Model.
     *
     * @return a new specific instance of UserDataModel
     */
    protected abstract UserDataModel createUserDataModel();

//    /**
//     * Creates a new specific instance of an UserDataWindow.
//     * Specific means (Cond-)UserNumber-, UserVector-, UserMatrix-Window.
//     *
//     * @param userDataModel which contains the data to fill the window with
//     * @param userDataType for that the window should be created
//     * @param title of the window
//     * @param mainCanvas to which the window should be added
//     *
//     * @return a new specific instance of UserDataWindow
//     */
//    protected abstract UserDataWindow createUserDataWindow(
//            UserDataModel userDataModel,
//            UserDataType userDataType,
//            String title,
//            Canvas mainCanvas);

    /**
     * Creates an UserData like e.g UserNumber from the code that is stored in
     * corresponding UserDataModel.
     * Throw a NullPointerException if No UserData could be created from an 
     * UserDataModel.
     *
     * @param model that should be used to create the UserData
     * 
     * @return the created UserData
     */
    protected abstract I_IIPData createVRLUserDataFromModel(UserDataModel model);

    /**
     * Creates an UserData like e.g UserNumber from the table data that is stored
     * in corresponding UserDataModel.
     * Throw a NullPointerException if No UserData could be created from an 
     * UserDataModel.
     *
     * @param model that should be used to create the UserData
     * 
     * @return the created UserData
     */
    protected abstract I_IIPData createConstUserDataFromModel(UserDataModel model);

    /**
     * Creates an UserData like e.g UserNumber from the corresponding UserDataModel.
     * Throw a NullPointerException if No UserData could be created from an 
     * UserDataModel.
     * 
     * The creation is done by delegating the creation to one of the following
     * abstract methods:
     * @see #createConstUserDataFromModel(UserDataModel) 
     * @see #createVRLUserDataFromModel(UserDataModel) 
     *
     * @param model that should be used to create the UserData
     * 
     * @return the created UserData or null
     */
    protected I_IIPData createUserDataFromModel(UserDataModel model) {

        I_IIPData result = null;

        if (model.isConstData()) {

            result = createConstUserDataFromModel(model);

        } else {

            result = createVRLUserDataFromModel(model);
        }

        if (result == null) {
            throw new NullPointerException("No UserData could be created from an UserDataModel.");
        }

        return result;
    }
    
    
    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }
}
