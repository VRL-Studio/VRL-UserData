/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.I_UserNumber;
import edu.gcsc.vrl.ug.api.I_VRLUserNumber;
import edu.gcsc.vrl.ug.api.VRLUserLinkerNumberNumber1d;
import edu.gcsc.vrl.ug.api.VRLUserLinkerNumberNumber2d;
import edu.gcsc.vrl.ug.api.VRLUserLinkerNumberNumber3d;
import edu.gcsc.vrl.userdata.util.DimensionUtil;

/**
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class DataLinkerModelNumberNumber extends DataLinkerModel {

    private static final long serialVersionUID = 1L;
    private Double data;
//    
//    
    
    public DataLinkerModelNumberNumber() {
        category = UserDataModel.Category.NUMBER;
        
        data = 0.0;
//        

        clear();
        enlarge(2);
        setTheCode(0, "return 0.0;");
        setTheCode(1, "return 0.0;");
    }

    /**
     * @return the data
     */
    @Override
    public Double getData() {      
        return data;
//        throw new RuntimeException("not implemented");
    }

    /**
     * @param data the data to set
     */
    public void setData(Double data) {
        this.data = data;
//        
    }

    @Override
    public void setData(Object data) {

        int arrayDim = DimensionUtil.getArrayDimension(data);
        if (arrayDim == 0) {
            setData((Double) data);
        } else {
            throw new RuntimeException(getClass().getSimpleName()+".setData(Object data): "
                    + "Data has wrong size: " + arrayDim);
        }

    }

    @Override
    public Object createUserData() {

        int type = 0; //means Number, see docu of createCode()

        I_UserNumber result = null;
        int dim = getDimension();
        int numArgs = 1;
        
        switch (dim) {
            case 1:
                VRLUserLinkerNumberNumber1d r = new VRLUserLinkerNumberNumber1d();
                result = r;
                r.data(createCode(getTheCode(0), dim, type, false), numArgs);
                r.deriv(0, createCode(getTheCode(1), dim, type, false));
                break;
            case 2:
                VRLUserLinkerNumberNumber2d r2 = new VRLUserLinkerNumberNumber2d();
                result = r2;
                r2.data(createCode(getTheCode(0), dim, type, false), numArgs);
                r2.deriv(0, createCode(getTheCode(1), dim, type, false));
                break;
            case 3:
                VRLUserLinkerNumberNumber3d r3 = new VRLUserLinkerNumberNumber3d();
                result = r3;
                r3.data(createCode(getTheCode(0), dim, type, false), numArgs);
                r3.deriv(0, createCode(getTheCode(1), dim, type, false));
                break;
            default:
                throw new RuntimeException(this.getClass().getSimpleName()
                        + ": UserData has invalid dimension!");
        }

        return result;
    }

    @Override
    public String checkUserData() {

        int type = 0; //means Number, see docu of createCode()
        int dim = getDimension();

        String codeText = getTheCode(0);
        codeText.trim();
        if (codeText.isEmpty()) {
            return "No code specified.";
        }

        String theCode = createCode(getTheCode(0), dim, type, false);
        try {
            UserDataCompiler.compile(theCode, dim);
        } catch (Exception ex) {

            return "User Data Code (Number) cannot be compiled:<br>" + ex.getMessage();
        }

        return "";
    }

    @Override
    public String getModelAsCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
