/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.I_UserVector;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;
import java.lang.annotation.Annotation;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@TypeInfo(type=I_UserVector[].class, input=true, output=false, style="array")
public class UserVectorArrayType extends ArrayBaseType {

    public UserVectorArrayType() {
       setValueName("Vector Array");

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
                return UserVectorArrayType.this.getValueOptions();
            }

            @Override
            public String typeName() {
                return I_UserVector.class.getName();
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        });
    }
}
