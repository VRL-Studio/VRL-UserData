/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.ConstUserNumber;
import edu.gcsc.vrl.ug.api.I_CondUserNumber;
import edu.gcsc.vrl.ug.api.I_ConstUserNumber;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.I_VRLCondUserNumber;
import edu.gcsc.vrl.ug.api.I_VRLUserNumber;
import edu.gcsc.vrl.ug.api.VRLCondUserNumber1d;
import edu.gcsc.vrl.ug.api.VRLCondUserNumber2d;
import edu.gcsc.vrl.ug.api.VRLCondUserNumber3d;
import eu.mihosoft.vrl.lang.VLangUtils;
import javax.swing.table.TableModel;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class CondUserNumberModel extends UserMathDataModel {

    private static final long serialVersionUID = 1L;

    public CondUserNumberModel() {
        category = UserDataModel.Category.COND_NUMBER;

        setCode("return new Cond(true, 0.0);");

        setInputType(UserMathDataModel.InputType.CODE);
        condition = true;
    }

    @Override
    protected I_UserDataInfo createVRLUserData() {

        /*
         * TODO: Ask why there is no I_VRLCondUserNumber which has also access
         * to the method data(String).
         */
        I_VRLCondUserNumber result = null;

        int dim = getDimension();
        int type = 0; //means Number, see docu of createCode()

        switch (dim) {
            case 1:
                result = new VRLCondUserNumber1d();
                break;
            case 2:
                result = new VRLCondUserNumber2d();
                break;
            case 3:
                result = new VRLCondUserNumber3d();
                break;
            default:
                System.out.println(">> " + this.getClass().getSimpleName()
                        + ": UserData has invalid dimension!");
                break;
        }

        result.data(createCode(getCode(), dim, type, true));

        return result;
    }

    @Override
    protected I_UserDataInfo createConstUserData() {
        return new ConstUserNumber((Double) getData());
    }

    /**
     * Dummy methode. DO NOTHING.
     * @return the data 
     */
    @Override
    public Double getData() {
        return 0.0;
    }

    /**
     * Dummy methode. DO NOTHING.
     *
     * @param data Dummy parameter. DO NOTHING.
     */
    @Override
    public void setData(Object data) {
    }

    @Override
    public void setDataFromTable(TableModel tableModel) {
        throw new RuntimeException("ConstUserDataModel: cannot set data from table");
    }

    @Override
    public Status adjustDataForDimension(int dimension) {
        return getStatus();
        // do nothing
    }

    @Override
    public String getToolTipText() {

        String toolTip = "<html><pre>";
        toolTip += getCode();
        toolTip += "</pre><html>";
        return toolTip;
    }

    @Override
    public String checkUserData() {
        if (getInputType() == InputType.CODE) {
            int type = 0; //means Number, see docu of createCode()
            int dim = getDimension();

            String codeText = getCode();
            codeText.trim();
            if (codeText.isEmpty()) {
                return "No code specified.";
            }

            String theCode = createCode(getCode(), dim, type, true);
            try {
                UserDataCompiler.compile(theCode, dim);
            } catch (Exception ex) {

                return "User Data Code (Vector) cannot be compiled:<br>" + ex.getMessage();
            }
        }

        return "";
    }

    @Override
    public String getModelAsCode() {
        StringBuilder sb = new StringBuilder();

        //the data of the userdata we want to copy
        int dim = getDimension();
        UserMathDataModel.InputType inputType = getInputType();
        String code = getCode();
        Double data = getData();

        //writes a call into code of the specific factory which generates/recreate for us a copy of the wanted userdata
        sb.append(UserDataCopyFactoryCondNumber.class.getName())
                .append(".createUserDataCopy(")
                .append(dim).append(",")
                .append('"').append(inputType).append('"').append(",")
                .append('"').append(code).append('"').append(",")
                .append('"').append('"').append(",")
                .append(data)
                .append(")");

//        return VLangUtils.addEscapesToCode(sb.toString());
        return sb.toString();
    }
//    public String getModelAsCode() {
//
//        StringBuilder sb = new StringBuilder();
//
//        Object obj = this.createUserData();
//
//        sb.append("new ")
//                .append(obj.getClass().getName())
//                .append("(");
//        //see constructor or createConstUserData() for parameter in case of a ConstUserNumber
//        if (obj instanceof I_CondUserNumber) {
//            sb.append(getData());
//        }
//
//        sb.append(")");
//
//        //see createVRLUserData() for plausiblity
//        if (obj instanceof I_VRLCondUserNumber) {
//            sb.append(".data(createCode(")
//                    .append(getCode()).append(",")
//                    .append(getDimension()).append(",")
//                    //0 means Number, see docu of createCode()
//                    .append(0).append(",")
//                    .append("false").append("))");
//        }
//        
//
//        return VLangUtils.addEscapesToCode(sb.toString());
//    }
}
