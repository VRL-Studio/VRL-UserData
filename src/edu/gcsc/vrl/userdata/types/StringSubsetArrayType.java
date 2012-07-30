/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;
import java.lang.annotation.Annotation;

/**
 *
 * @author Andreas Vogel <andreas.vogel@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type=String[].class, input=true, output=false, style="ugx-subset-selection-array")
public class StringSubsetArrayType extends ArrayBaseType {

    public StringSubsetArrayType() {
      setValueName("Subset Array");

        setElementInputInfo(new ParamInfo() {

            @Override
            public String name() {
                return "";
            }

            @Override
            public String style() {
                return "ugx-subset-selection";
            }

            @Override
            public boolean nullIsValid() {
                return false;
            }

            @Override
            public String options() {
                return StringSubsetArrayType.this.getValueOptions();
            }

            @Override
            public String typeName() {
                return String.class.getName();
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        });
    }
}
