/* 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009–2012 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2012 by Michael Hoffer
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 */
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

        //the data of the userdata we want to copy
        int dim = getDimension();
        UserMathDataModel.InputType inputType = getInputType();
        String code = getCode();
        Double[][] data = getData();

        //writes a call into code of the specific factory which generates/recreate for us a copy of the wanted userdata
        sb.append("new ").append(UserDataCopyFactoryMatrix.class.getName())
                .append("().createUserDataCopy(")
                .append(dim).append(",")
                .append('"').append(inputType).append('"').append(",")
                .append('"').append(VLangUtils.addEscapeCharsToCode(code)).append('"').append(",")
                .append('"').append('"').append(",")
                .append("[");

        for (int i = 0; i < data.length; i++) {
                
                if (i > 0) {
                        sb.append(", ");
                    }
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
                }//for j end
                
            }// for i end

            sb.append("] as Double[][])");

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
//                .append("()");
//        //see constructor or createConstUserData() for parameter in case of a ConstUserNumber
//        if (obj instanceof I_ConstUserMatrix) {
//            sb.append(".setData([");
//
//            for (int i = 0; i < data.length; i++) {
//                sb.append("[");
//                for (int j = 0; j < data[i].length; j++) {
//
//                    if (j == 0) {
//                        sb.append("[");
//                    }
//                    if (j > 0) {
//                        sb.append(", ");
//                    }
//                    sb.append(data[i][j]);
//
//                    if (j == data[i].length - 1) {
//                        sb.append("]");
//                    }
//                }
//                sb.append("]");
//            }
//
//            sb.append("] as double[][])");
//        }
//
//        //see createVRLUserData() for plausiblity
//        if (obj instanceof I_VRLUserMatrix) {
//            sb.append(".data(createCode(")
//                    .append(getCode()).append(",")
//                    .append(getDimension()).append(",")
//                    // 2 means Matrix, see docu of createCode()
//                    .append(2).append(",")
//                    .append("false").append("))");
//        }
//
//        return VLangUtils.addEscapesToCode(sb.toString());
//    }
}
