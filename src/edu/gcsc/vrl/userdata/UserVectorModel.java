/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.ConstUserVector1d;
import edu.gcsc.vrl.ug.api.ConstUserVector2d;
import edu.gcsc.vrl.ug.api.ConstUserVector3d;
import edu.gcsc.vrl.ug.api.I_ConstUserNumber;
import edu.gcsc.vrl.ug.api.I_ConstUserVector;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.I_VRLUserNumber;
import edu.gcsc.vrl.ug.api.I_VRLUserVector;
import edu.gcsc.vrl.ug.api.VRLUserVector1d;
import edu.gcsc.vrl.ug.api.VRLUserVector2d;
import edu.gcsc.vrl.ug.api.VRLUserVector3d;
import edu.gcsc.vrl.userdata.util.DimensionUtil;
import eu.mihosoft.vrl.lang.VLangUtils;
import eu.mihosoft.vrl.system.VMessage;
import javax.swing.table.TableModel;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserVectorModel extends UserMathDataModel {

    private Double[] data;

    public UserVectorModel() {
        category = UserDataModel.Category.VECTOR;

        Double[] defaultdata = {0.0, 0.0};
        data = defaultdata;
    }

    /**
     * @return the data
     */
    @Override
    public Double[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Double[] data) {
        this.data = data;
    }

    @Override
    public void setData(Object data) {

        int arrayDim = DimensionUtil.getArrayDimension(data);
        if (arrayDim == 1) {
            setData((Double[]) data);

        } else {
            throw new RuntimeException("UserVectorModel.setData(Object data): "
                    + "Data has wrong size:" + arrayDim);
        }

    }

    @Override
    public void setDataFromTable(TableModel tableModel) {

        data = new Double[tableModel.getRowCount()];

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            data[i] = (Double) tableModel.getValueAt(i, 0);
        }
    }

    @Override
    public Status adjustDataForDimension(int dim) {
        Status bConsistent = adjustConstDataForDimension(dim);

        if (getInputType() == InputType.CONSTANT) {
            if (getStatus() == Status.VALID) {
                return bConsistent;
            } else {
                return getStatus();
            }
        } else {
            return Status.VALID;
        }
    }

    protected Status adjustConstDataForDimension(int dim) {

        int oldLength = data.length;

        if (dim == oldLength) {
            return Status.VALID;
        }

        Double[] newData = new Double[dim];
        int maxLength = Math.min(dim, oldLength);

        // copy old
        for (int i = 0; i < maxLength; i++) {
            newData[i] = data[i];
        }

        boolean bSameData = true;
        double d = data[0];
        for (int i = 0; i < data.length; ++i) {
            if (d != data[i]) {
                bSameData = false;
            }
        }

        // fill zeros
        for (int i = maxLength; i < newData.length; i++) {
            if (bSameData) {
                newData[i] = d;
            } else {
                newData[i] = 0.0;
            }
        }

        data = newData;

        if (oldLength > dim || bSameData) {
            return Status.VALID;
        }

        return Status.WARNING;
    }

    @Override
    public String getToolTipText() {

        String toolTip = "";

        switch (getInputType()) {
            case CONSTANT:
                toolTip = "<html><table align=\"center\">";
                for (int j = 0; j < data.length; j++) {
                    toolTip += "<tr>";
                    toolTip += data[j];
                    toolTip += "</tr>";
                }
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

        I_VRLUserVector result = null;

        int dim = getDimension();
        int type = 1; //means Vector, see docu of createCode()

        switch (dim) {
            case 1:
                result = new VRLUserVector1d();
                break;
            case 2:
                result = new VRLUserVector2d();
                break;
            case 3:
                result = new VRLUserVector3d();
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

        I_ConstUserVector result = null;

        int dim = getDimension();

        switch (dim) {
            case 1:
                result = new ConstUserVector1d();
                break;
            case 2:
                result = new ConstUserVector2d();
                break;
            case 3:
                result = new ConstUserVector3d();
                break;
            default:
                throw new RuntimeException(this.getClass().getSimpleName()
                        + ": UserData has invalid dimension!");
        }

        for (int i = 0; i < data.length; i++) {
            result.set_entry(i, data[i]);
        }

        return result;
    }

    @Override
    public String checkUserData() {
        if (getInputType() == InputType.CODE) {
            int type = 1; //means Vector, see docu of createCode()
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
        Double[] data = getData();

        //writes a call into code of the specific factory which generates/recreate for us a copy of the wanted userdata
        sb.append(UserDataCopyFactoryVector.class.getName())
                .append(".createUserDataCopy(")
                .append(dim).append(",")
                .append('"').append(inputType).append('"').append(",")
                .append('"').append(code).append('"').append(",")
                .append('"').append('"').append(",")
                .append("[");

        for (int i = 0; i < data.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(data[i]);

        }
        sb.append("] as double[]")
                .append(")");

        //        return VLangUtils.addEscapesToCode(sb.toString());
        return sb.toString();
    }

}
