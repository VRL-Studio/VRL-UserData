/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.*;
import eu.mihosoft.vrl.annotation.TypeInfo;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = I_CondUserNumber.class, input = true, output = false, style = "default")
public class CondUserNumberType extends UserDataTupleType implements Serializable {

    private static final long serialVersionUID = 1;

//    should be now done by super class with getValueAsCodeHelperClassName()
//    @Override
//    public String getValueAsCode() {
//        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
//        return "null as " + getType().getName();
//    }

    @Override
    public void setValueOptions(String valueOptions) {

        if (valueOptions == null) {
            valueOptions = "";
        }

        if (!valueOptions.isEmpty()) {
            valueOptions = valueOptions + ";";
        }

        String buttonName = "CondNumber";
        if (getParamInfo().name() != null && !getParamInfo().name().isEmpty()) {
            buttonName = getParamInfo().name();
        }

        if (valueOptions.contains("name")) {
            String[] tokens = valueOptions.split(";");
            for (String token : tokens) {
                if (token.contains("name")) {
                    String val = token.substring(token.indexOf("="));
                    val = val.replace("\"", "");
                    val = val.replace("=", "");
                    buttonName = val.trim();
                }
            }
        }

        if (valueOptions.contains("type")) {
            if (!valueOptions.contains("type=\"c:")) {
                throw new RuntimeException("CondUserNumberType: no external 'type' setting allowed.");
            }
        } else {
            valueOptions = valueOptions + " type=\"c:" + buttonName + "\"";
        }

        super.setValueOptions(valueOptions);
    }

    @Override
    public void setViewValue(Object o) {

        if (o instanceof I_CondUserNumber) {
            if (datas.size() == 1) {
                datas.get(0).model.setData(o);
            }
        }
    }

    @Override
    public Object getViewValue() {
        return datas.get(0).model.createUserData();
    }
}
