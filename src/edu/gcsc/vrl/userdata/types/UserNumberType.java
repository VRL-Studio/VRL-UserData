/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.UserDataTuple;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;
import java.io.Serializable;
import java.util.IllegalFormatException;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = I_UserNumber.class, input = true, output = false, style = "default")
public class UserNumberType extends UserDataTupleType implements Serializable {

    private static final long serialVersionUID = 1;

    protected Data getDataOfUserData() {
        if (getDatas().size() == 1) {
            return getDatas().get(0);
        } else {
            String message = "UserDatas as User- Number/Vector/Matrix have to got one entry in datas array.";
            System.err.println(message);
            throw new RuntimeException(message);
        }
    }

    @Override
    public String getValueAsCode() {
            return getDataOfUserData().model.getModelAsCode();
    }

    @Override
    public void setValueOptions(String valueOptions) {

        if (valueOptions == null) {
            valueOptions = "";
        }

        if (!valueOptions.isEmpty()) {
            valueOptions = valueOptions + ";";
        }

        String buttonName = "Number";
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
            if (!valueOptions.contains("type=\"n:")) {
                throw new RuntimeException("UserNumberType: no external 'type' setting allowed.");
            }
        } else {
            valueOptions = valueOptions + " type=\"n:" + buttonName + "\"";
        }

        super.setValueOptions(valueOptions);
    }

    @Override
    public void setViewValue(Object o) {

        if (o instanceof I_UserNumber) {
            // todo: think how this might be implemented
        }
    }

    @Override
    public Object getViewValue() {
        UserDataTuple tuple = (UserDataTuple) super.getViewValue();
        return tuple.getNumberData(0);
    }
}
