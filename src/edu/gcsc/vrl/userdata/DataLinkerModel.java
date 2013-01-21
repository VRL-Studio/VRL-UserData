/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.UserData;
import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.UGXFileInfo;
import java.util.ArrayList;

/**
 *
 */
public abstract class DataLinkerModel extends UserDataModel {

    public ArrayList<String> getCode() {
        return code;
    }

    public void setCode(ArrayList<String> code) {
        this.code = code;
    }

    private static final long serialVersionUID = 1L;
    protected int dimension;
    protected ArrayList<String> code;

    public DataLinkerModel() {

        // default values

        code = new ArrayList<String>();
        dimension = 2;
    }

    /**
     * returns the number of elements in the tuple
     */
    public int size() {
        return code.size();
    }

    /**
     * returns the number of elements in the tuple
     */
    public void enlarge(int newSize) {
        while(code.size() < newSize){
            code.add(new String(""));
        }
    }

    /**
     * clears all data of the tuple. This also sets the size to zero.
     */
    public void clear() {
        code.clear();
    }

    /**
     * @return the code
     */
    public String getTheCode(int i) {
        return code.get(i);
    }

    /**
     * @param code the code to set
     */
    public void setTheCode(int i, String code) {
        this.code.set(i, code);
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
    

    @Override
    public void adjustData(UGXFileInfo info) {

        if (info != null) {
            if (getStatus() == Status.INVALID) {
                setStatus(Status.WARNING);
            }

        } else {
            setStatus(Status.INVALID);
        }
    }

    @Override
    public void setModel(UserDataModel model) {
        if (model instanceof DataLinkerModel) {

            DataLinkerModel m = (DataLinkerModel) model;

            setStatus(m.getStatus());
            for(int i = 0; i < size(); ++i){
                 setTheCode(i, m.getTheCode(i));
            }

        } else {
            throw new RuntimeException("UserData could be set from other UserDataModel.");

        }
    }

    public String getToolTipText() {

        String toolTip = "";

        toolTip = "<html><pre>";
        toolTip += getTheCode(0);
        toolTip += "</pre><html>";

        return toolTip;
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
    protected static String createCode(String code, int dim, int type, boolean cond) {

        
        // TODO: THIS IS NOT READY !!! HARD_CODED for one comp!!! GENERALIZE IN 
        //          DERIVED CLASSES, PLEASE !!!
        ArrayList<String> paramNames = new ArrayList<String>();

        paramNames.add("c");
          
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

        code = UserDataCompiler.getUserDataImplCode(code, type,
                paramNames, UserData.returnTypes[type]);
            
        return code;
    }
}
