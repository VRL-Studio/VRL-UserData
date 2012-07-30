/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata;

import edu.gcsc.vrl.ug.api.ConstUserNumber;
import edu.gcsc.vrl.ug.api.I_IUserData;
import edu.gcsc.vrl.ug.api.I_VRLUserNumber;
import edu.gcsc.vrl.ug.api.VRLUserNumber1d;
import edu.gcsc.vrl.ug.api.VRLUserNumber2d;
import edu.gcsc.vrl.ug.api.VRLUserNumber3d;
import edu.gcsc.vrl.userdata.managers.DimensionManager;
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

        int arrayDim = DimensionManager.getArrayDimension(data);
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
    public boolean adjustDataForDimension(int dim) {
        // nothing to do
        return true;
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
                toolTip += getCode();
                break;
            default:
                throw new RuntimeException("UserMathDataView: type not found.");
        }
        return toolTip;
    }

    @Override
    protected I_IUserData createVRLUserData() {

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
    protected I_IUserData createConstUserData() {
        return new ConstUserNumber(data);
    }
}
