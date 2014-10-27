/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.ConstUserNumber;
import edu.gcsc.vrl.ug.api.ConstUserNumber1d;
import edu.gcsc.vrl.ug.api.ConstUserNumber2d;
import edu.gcsc.vrl.ug.api.ConstUserNumber3d;
import edu.gcsc.vrl.ug.api.I_ConstUserNumber;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.I_VRLUserNumber;
import edu.gcsc.vrl.ug.api.VRLUserNumber1d;
import edu.gcsc.vrl.ug.api.VRLUserNumber2d;
import edu.gcsc.vrl.ug.api.VRLUserNumber3d;
import edu.gcsc.vrl.userdata.util.DimensionUtil;
import eu.mihosoft.vrl.lang.VLangUtils;
import javax.swing.table.TableModel;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserNumberModel extends UserMathDataModel {

    private static final long serialVersionUID = 1L;
    private Double data;

    public UserNumberModel() {
        category = UserDataModel.Category.NUMBER;

        data = 0.0;

        setCode("return 0.0;");
    }

    /**
     * @return the data
     */
    @Override
    public Double getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Double data) {
        this.data = data;
    }

    @Override
    public void setData(Object data) {

        int arrayDim = DimensionUtil.getArrayDimension(data);
        if (arrayDim == 0) {
            setData((Double) data);
        } else {
            throw new RuntimeException("UserNumberModel.setData(Object data): "
                    + "Data has wrong size: " + arrayDim);
        }

    }

    @Override
    public void setDataFromTable(TableModel tableModel) {

        data = (Double) tableModel.getValueAt(0, 0);
    }

    @Override
    public Status adjustDataForDimension(int dim) {
        // nothing to do
        return getStatus();
    }

    @Override
    public String getToolTipText() {

        String toolTip = "";

        switch (getInputType()) {
            case CONSTANT:
                toolTip = "<html><table align=\"center\">";
                toolTip += data;
                toolTip += "</table><html>";
                break;
            case CODE:
                toolTip = "<html><pre>";
                toolTip += getCode();
                toolTip += "</pre><html>";
                break;
            default:
                throw new RuntimeException("UserMathDataView: type not found.");
        }
        return toolTip;
    }

    @Override
    protected I_UserDataInfo createVRLUserData() {

        int type = 0; //means Number, see docu of createCode()

        I_VRLUserNumber result = null;
        int dim = getDimension();

        switch (dim) {
            case 1:
                result = new VRLUserNumber1d();
                break;
            case 2:
                result = new VRLUserNumber2d();
                break;
            case 3:
                result = new VRLUserNumber3d();
                break;
            default:
                throw new RuntimeException(this.getClass().getSimpleName()
                        + ": UserData has invalid dimension!");
        }

        result.data(createCode(getCode(), dim, type, false));

        return result;
    }

    @Override
    protected I_UserDataInfo createConstUserData() {
        I_ConstUserNumber result = null;

        switch (getDimension()) {
            case 1:
                result = new ConstUserNumber1d(data);
                break;
            case 2:
                result = new ConstUserNumber2d(data);
                break;
            case 3:
                result = new ConstUserNumber3d(data);
                break;
            default:
                throw new RuntimeException(this.getClass().getSimpleName()
                        + ": UserData has invalid dimension!");
        }

        return result;
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

            String theCode = createCode(getCode(), dim, type, false);
            try {
                UserDataCompiler.compile(theCode, dim);
            } catch (Exception ex) {

                return "User Data Code (Number) cannot be compiled:<br>" + ex.getMessage();
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
        sb.append("new ").append(UserDataCopyFactoryNumber.class.getName())
                .append("().createUserDataCopy(")
                .append(dim).append(",")
                .append('"').append(inputType).append('"').append(",")
                .append('"').append(code).append('"').append(",")
                .append('"').append('"').append(",")
                .append(data).append(" as Double")        
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
//        if (obj instanceof I_ConstUserNumber) {
//            sb.append(data);
//        }
//
//        sb.append(")");
//
//        //see createVRLUserData() for plausiblity
//        if (obj instanceof I_VRLUserNumber) {
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
