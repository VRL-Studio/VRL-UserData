/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.*;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;
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
    public String getValueAsCode() {

        Object obj = getValue();

        System.out.println("cunt");
        System.out.println(" DD " + getClass().getSimpleName() + ".getValueAsCode() obj.getClass() = " + obj.getClass());

        if (obj instanceof ConstUserNumber) {

            ConstUserNumber value = (ConstUserNumber) obj;
            System.out.println("dim = " + value.const__get_dim());
            System.out.println("type = " + value.const__type());
//            System.out.println("get = "+ value.get());// WIESO ein set aber KEIN GET ???

            //wie kommt TYPE an das MODEL mit den daten???
//            value.
//            datas.get(0) <- so ??
            System.out.println("D datas.size() = " + datas.size());
            for (int i = 0; i < datas.size(); i++) {
                System.out.println(" " + i + " = " + datas.get(i));
            }

            if (obj instanceof ConstUserNumber1d) {
                System.out.println("ConstUserNumber1d");
//                new ConstUserNumber1d(value.get());

            } else if (obj instanceof ConstUserNumber2d) {
                System.out.println("ConstUserNumber2d");

            } else if (obj instanceof ConstUserNumber3d) {
                System.out.println("ConstUserNumber3d");
            }
        }
        //         UserNumber value = (UserNumber) obj;
        //        if (value == null) {
        return "null as " + getType().getName();
//        }
//
////        // to get the idea how to implement the string version
////         new UserDataTuple().add(((UserDataTuple)getValue()).getData(0));
//        
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("new ").append(getClass().getSimpleName()).append("()");
//
//        for (int i = 0; i < value.size(); i++) {
//            //type cast is save because  we know getValue() is not null
////            sb.append(".add(((UserDataTuple)getValue()).getData(").append(i).append("))");
//            sb.append(".add(((").append(getClass().getSimpleName()).append(")getValue()).getData(").append(i).append("))");
//        }
//        sb.append(";");
//
//        return VLangUtils.addEscapesToCode(sb.toString());

    }

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
