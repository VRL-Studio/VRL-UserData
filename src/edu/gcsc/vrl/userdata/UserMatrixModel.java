/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.UserDataCompiler;
import edu.gcsc.vrl.ug.api.ConstUserMatrix1d;
import edu.gcsc.vrl.ug.api.ConstUserMatrix2d;
import edu.gcsc.vrl.ug.api.ConstUserMatrix3d;
import edu.gcsc.vrl.ug.api.I_ConstUserMatrix;
import edu.gcsc.vrl.ug.api.I_ConstUserVector;
import edu.gcsc.vrl.ug.api.I_UserDataInfo;
import edu.gcsc.vrl.ug.api.I_VRLUserMatrix;
import edu.gcsc.vrl.ug.api.I_VRLUserVector;
import edu.gcsc.vrl.ug.api.VRLUserMatrix1d;
import edu.gcsc.vrl.ug.api.VRLUserMatrix2d;
import edu.gcsc.vrl.ug.api.VRLUserMatrix3d;
import edu.gcsc.vrl.userdata.util.DimensionUtil;
import eu.mihosoft.vrl.lang.VLangUtils;
import javax.swing.table.TableModel;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserMatrixModel extends UserMathDataModel {

    private Double[][] data;

    public UserMatrixModel() {

        category = UserDataModel.Category.MATRIX;

        Double[][] defaultdata = {{0.0, 0.0}, {0.0, 0.0}};
        data = defaultdata;
    }

    /**
     * @return the data
     */
    @Override
    public Double[][] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Double[][] data) {
        this.data = data;
    }

    @Override
    public void setData(Object data) {

        int arrayDim = DimensionUtil.getArrayDimension(data);
        if (arrayDim == 2) {
            setData((Double[][]) data);

        } else {
            throw new RuntimeException("UserMatrixModel.setData(Object data): "
                    + "Data has wrong size : " + arrayDim);
        }

    }

    @Override
    public void setDataFromTable(TableModel tableModel) {

        data = new Double[tableModel.getRowCount()][tableModel.getColumnCount()];

        for (int j = 0; j < tableModel.getColumnCount(); j++) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                data[i][j] = (Double) tableModel.getValueAt(i, j);
            }
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

        int oldRowSize = data.length;
        int oldColSize = data[0].length;

        // if nothing changes, data must be ok
        if (dim == oldRowSize && dim == oldColSize) {
            return Status.VALID;
        }

        Double[][] newData = new Double[dim][dim];
        int maxRowSize = Math.min(dim, data.length);
        int maxColSize = Math.min(dim, data[0].length);

        // fill zeros
        for (int j = 0; j < dim; j++) {
            for (int i = 0; i < dim; i++) {
                newData[i][j] = 0.0;
            }
        }

        // if was diagonal, create a diagonal one in this dimension
        if (isScalarDiagonal(data)) {
            for (int i = 0; i < dim; i++) {
                newData[i][i] = data[0][0];
            }
            data = newData;
            return Status.VALID;
        }

        // copy old
        for (int j = 0; j < maxColSize; j++) {
            for (int i = 0; i < maxRowSize; i++) {
                newData[i][j] = data[i][j];
            }
        }

        data = newData;

        // if only smaller matrix, data may be ok
        if (oldRowSize > dim && oldColSize > dim) {
            return Status.VALID;
        }

        // maybe we have to do something
        return Status.WARNING;
    }

    protected boolean isScalarDiagonal(Double[][] data) {

        double diagValue = data[0][0];

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[0].length; i++) {

                if (i != j) {
                    if (data[i][j] != 0.0) {
                        return false;
                    }
                } else {
                    if (data[i][j] != diagValue) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected I_UserDataInfo createVRLUserData() {

        I_VRLUserMatrix result = null;

        int dim = getDimension();
        int type = 2; //means Matrix, see docu of createCode()

        switch (dim) {
            case 1:
                result = new VRLUserMatrix1d();
                break;
            case 2:
                result = new VRLUserMatrix2d();
                break;
            case 3:
                result = new VRLUserMatrix3d();
                break;
            default:
                throw new RuntimeException(this.getClass().getSimpleName()
                        + ": UserData has invalid dimension!");
        }

        result.data(createCode(getCode(), dim, type, false));

        return result;
    }

    @Override
    public String getToolTipText() {

        String toolTip = "";

        switch (getInputType()) {
            case CONSTANT:
                toolTip = "<html><table>";
                for (int i = 0; i < data.length; i++) {
                    toolTip += "<tr>";
                    for (int j = 0; j < data[i].length; j++) {
                        toolTip += "<td align=\"center\">";
                        toolTip += data[i][j];
                        toolTip += "</td>";
                    }
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
    protected I_UserDataInfo createConstUserData() {

        I_ConstUserMatrix result = null;

        switch (getDimension()) {
            case 1:
                result = new ConstUserMatrix1d();
                break;
            case 2:
                result = new ConstUserMatrix2d();
                break;
            case 3:
                result = new ConstUserMatrix3d();
                break;
            default:
                throw new RuntimeException(this.getClass().getSimpleName()
                        + ": UserData has invalid dimension!");
        }

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[j].length; i++) {
                result.set_entry(i, j, data[i][j]);
            }
        }

        return result;
    }

    @Override
    public String checkUserData() {
        if (getInputType() == InputType.CODE) {
            int type = 2; //means Matrix, see docu of createCode()
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

                return "User Data Code (Matrix) cannot be compiled:<br>" + ex.getMessage();
            }
        }

        return "";
    }

    @Override
    public String getModelAsCode() {

        StringBuilder sb = new StringBuilder();

        Object obj = this.createUserData();

        sb.append("new ")
                .append(obj.getClass().getName())
                .append("()");
        //see constructor or createConstUserData() for parameter in case of a ConstUserNumber
        if (obj instanceof I_ConstUserMatrix) {
            sb.append(".setData([");

            for (int i = 0; i < data.length; i++) {
                sb.append("[");
                for (int j = 0; j < data[i].length; j++) {

                    if (j == 0) {
                        sb.append("[");
                    }
                    if (j > 0) {
                        sb.append(", ");
                    }
                    sb.append(data[i][j]);

                    if (j == data[i].length - 1) {
                        sb.append("]");
                    }
                }
                sb.append("]");
            }

            sb.append("] as double[][])");
        }

        //see createVRLUserData() for plausiblity
        if (obj instanceof I_VRLUserMatrix) {
            sb.append(".data(createCode(")
                    .append(getCode()).append(",")
                    .append(getDimension()).append(",")
                    // 2 means Matrix, see docu of createCode()
                    .append(2).append(",")
                    .append("false").append("))");
        }

        return VLangUtils.addEscapesToCode(sb.toString());
    }
}
