/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.userdata.UserDataTuple;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;
import java.lang.annotation.Annotation;

/**
 *
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type=UserDataTuple[].class, input=true, output=false, style="array")
public class UserDataTupleArrayType extends ArrayBaseType {

    public UserDataTupleArrayType() {
       setValueName("Data Tuple Array");

        setElementInputInfo(new ParamInfo() {

            @Override
            public String name() {
                return "";
            }

            @Override
            public String style() {
                return "default";
            }

            @Override
            public boolean nullIsValid() {
                return false;
            }

            @Override
            public String options() {
                return UserDataTupleArrayType.this.getValueOptions();
            }

            @Override
            public String typeName() {
                return UserDataTuple.class.getName();
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        });
    }
}
