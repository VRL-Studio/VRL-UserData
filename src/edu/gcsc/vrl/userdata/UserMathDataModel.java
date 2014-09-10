/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.CondUserDataCompiler;
import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.UGXFileInfo;
import java.util.ArrayList;
import javax.swing.table.TableModel;

/**
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public abstract class UserMathDataModel extends UserDataModel {

    public static enum InputType {

        SCALAR_CONSTANT,
        CONSTANT,
        CODE,
        LINKER
    }

    private static final long serialVersionUID = 1L;
    protected InputType inputType;
    protected int dimension;
    protected String code;
    /**
     * Boolean to differ between normal UserDataModel and an UserDataModel which
     * should represent a Condition, which has no const data and therefore no
     * visualization for const data will be available in an UserDataWindow.
     */
    protected boolean condition;

    public UserMathDataModel() {

        // default values
        inputType = InputType.CONSTANT;

        dimension = 2;

        code = "";

        condition = false;
    }

    /**
     * @return the constData
     */
    public InputType getInputType() {
        return inputType;
    }

    /**
     * @param constData the constData to set
     */
    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the dimension
     */
    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    /**
     * @param dimension the dimension to set
     */
    public Status setDimensionWithAdjust(int dim) {
        this.dimension = dim;
        return adjustDataForDimension(dim);
    }

    public abstract void setDataFromTable(TableModel tableModel);

    /**
     * @return the condition
     */
    public boolean isCondition() {
        return condition;
    }

    /**
     * sets the condition
     */
    public void setCondition(boolean isCondition) {
        condition = isCondition;
    }

    @Override
    public void adjustData(UGXFileInfo info) {

        if (info != null) {
            if (getStatus() == Status.INVALID) {
                setStatus(Status.WARNING);
            }
            int dim = info.const__grid_world_dimension(0);
            setStatus(setDimensionWithAdjust(dim));
        } else {
            setStatus(Status.INVALID);
        }
    }

    public abstract Status adjustDataForDimension(int dim);

    @Override
    public void setModel(UserDataModel model) {
        if (model instanceof UserMathDataModel) {

            UserMathDataModel m = (UserMathDataModel) model;

            setDimensionWithAdjust(m.getDimension());
            setInputType(m.getInputType());
            setCondition(m.isCondition());
            setStatus(m.getStatus());

            setData(m.getData());
            setCode(m.getCode());

        } else {
            throw new RuntimeException("UserData could be set from other UserDataModel.");

        }
    }

    @Override
    public Object createUserData() {
//        public Object createUserData() and NOT public I_UserDataInfo createUserData() 
//        BECAUSE UserSubsetModel returns String

        I_UserDataInfo result = null;

        switch (getInputType()) {
            case CONSTANT:
                result = createConstUserData();
                break;
            case CODE:
                result = createVRLUserData();
                break;
        }

        return result;
    }

    protected abstract I_UserDataInfo createVRLUserData();

    protected abstract I_UserDataInfo createConstUserData();

    public abstract String getToolTipText();

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
    protected static String createCode(String code, int dim, int type, boolean cond) {

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
}
