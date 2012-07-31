/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.UserDataTuple;
import eu.mihosoft.vrl.annotation.TypeInfo;
import groovy.lang.Script;
import java.io.Serializable;

/**
 *
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = String.class, input = true, output = false, style = "ugx-subset-selection")
public class StringSubsetType extends UserDataTupleType implements Serializable {

    private static final long serialVersionUID = 1;

    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }

    @Override
    public void setValueOptions(String valueOptions) {

        if (valueOptions == null) {
            valueOptions = "";
        }

        if (!valueOptions.isEmpty()) {
            valueOptions = valueOptions + ";";
        }

        String buttonName = "Subset";
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
            if (!valueOptions.contains("type=\"s:")) {
                throw new RuntimeException("StringSubsetType: no external 'type' setting allowed.");
            }
        } else {
            valueOptions = valueOptions + " type=\"s:" + buttonName + "\"";
        }

        super.setValueOptions(valueOptions);
    }

    @Override
    public void setViewValue(Object o) {

        if (o instanceof String) {
            if (datas.size() == 1) {
                datas.get(0).model.setData(o);
            }
        }
    }

    @Override
    public Object getViewValue() {
        UserDataTuple tuple = (UserDataTuple)super.getViewValue();
        return tuple.getSubset(0);
   }
}
