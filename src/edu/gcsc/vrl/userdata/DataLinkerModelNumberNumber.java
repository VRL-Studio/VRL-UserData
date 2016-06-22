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
import edu.gcsc.vrl.ug.api.I_ConstUserNumber;
import edu.gcsc.vrl.ug.api.I_UserNumber;
import edu.gcsc.vrl.ug.api.I_VRLUserLinkerNumberNumber;
import edu.gcsc.vrl.ug.api.I_VRLUserNumber;
import edu.gcsc.vrl.ug.api.VRLUserLinkerNumberNumber1d;
import edu.gcsc.vrl.ug.api.VRLUserLinkerNumberNumber2d;
import edu.gcsc.vrl.ug.api.VRLUserLinkerNumberNumber3d;
import edu.gcsc.vrl.userdata.util.DimensionUtil;
import eu.mihosoft.vrl.lang.VLangUtils;

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
            throw new RuntimeException(getClass().getSimpleName() + ".setData(Object data): "
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

    public I_VRLUserLinkerNumberNumber dataAndDeriv(I_VRLUserLinkerNumberNumber r) {
        int type = 0; //means Number, see docu of createCode()

        I_UserNumber result = null;
        int dim = getDimension();
        int numArgs = 1;

        r.data(createCode(getTheCode(0), dim, type, false), numArgs);
        r.deriv(0, createCode(getTheCode(1), dim, type, false));

        return r;
    }

    @Override
    public String getModelAsCode() {
        StringBuilder sb = new StringBuilder();

        //the data of the userdata we want to copy
        int dim = getDimension();
        UserMathDataModel.InputType inputType = UserMathDataModel.InputType.LINKER;//getInputType();
        String codeData = VLangUtils.addEscapesToCode(getTheCode(0));
        String codeDeriv = VLangUtils.addEscapesToCode(getTheCode(1));
        Double data = getData();

        //writes a call into code of the specific factory which generates/recreate for us a copy of the wanted userdata
        sb.append("new ").append(UserDataCopyFactoryNumber.class.getName())
                .append("().createUserDataCopy(")
                .append(dim).append(",")
                .append('"').append(inputType).append('"').append(",")
                .append('"').append(codeData).append('"').append(",")
                .append('"').append(codeDeriv).append('"').append(",")
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
//                .append("()");
//
//        sb.append("");
//
//        //see createVRLUserData() for plausiblity
//        if (obj instanceof I_VRLUserLinkerNumberNumber) {
//            // for orientation example from createUserData()
//            //             r.data(createCode(getTheCode(0), dim, type, false), numArgs);
//            //
//            sb.append(".data(createCode(")
//                    .append('"')
//                    .append(VLangUtils.addEscapesToCode(getTheCode(0)))
//                    .append('"')
//                    .append(",")
//                    .append(getDimension()).append(",")
//                    //0 means Number, see docu of createCode()
//                    .append(0).append(",")
//                    .append("false").append("), 1)");
//
//            // for orientation example from createUserData()
//            //             r.deriv(0, createCode(getTheCode(1), dim, type, false));
//            //
//            sb.append(".deriv(0, createCode(")
//                    .append('"')
//                    .append(VLangUtils.addEscapesToCode(getTheCode(1)))
//                    .append('"')
//                    .append(",")
//                    .append(getDimension()).append(",")
//                    //0 means Number, see docu of createCode()
//                    .append(0).append(",")
//                    .append("false").append("))");
//
//        }
//
////        return VLangUtils.addEscapesToCode(sb.toString());
//        return sb.toString();
//    }
}
