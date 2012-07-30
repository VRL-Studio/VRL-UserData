/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.ConstUserVector1d;
import edu.gcsc.vrl.ug.api.ConstUserVector2d;
import edu.gcsc.vrl.ug.api.ConstUserVector3d;
import edu.gcsc.vrl.ug.api.I_ConstUserVector;
import edu.gcsc.vrl.ug.api.I_IUserData;
import edu.gcsc.vrl.ug.api.I_VRLUserVector;
import edu.gcsc.vrl.ug.api.VRLUserVector1d;
import edu.gcsc.vrl.ug.api.VRLUserVector2d;
import edu.gcsc.vrl.ug.api.VRLUserVector3d;
import edu.gcsc.vrl.userdata.managers.DimensionManager;
import edu.gcsc.vrl.userdata.helpers.UserDataCategory;
import javax.swing.table.TableModel;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserVectorModel extends UserMathDataModel {

    private Double[] data;

    public UserVectorModel() {
        category = UserDataCategory.VECTOR;

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

        int arrayDim = DimensionManager.getArrayDimension(data);
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
    public boolean adjustDataForDimension(int dim) {
        boolean bConsistent = adjustConstDataForDimension(dim);

        if (getInputType() == InputType.CONSTANT) {
            return bConsistent;
        } else {
            return true;
        }
    }

    protected boolean adjustConstDataForDimension(int dim) {

        int oldLength = data.length;

        if (dim == oldLength) {
            return true;
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
            return true;
        }

        return false;
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
                toolTip += getCode();
                break;
            default:
                throw new RuntimeException("UserMathDataView: type not found.");
        }
        return toolTip;
    }

    @Override
    protected I_IUserData createVRLUserData() {

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
    protected I_IUserData createConstUserData() {

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
}
