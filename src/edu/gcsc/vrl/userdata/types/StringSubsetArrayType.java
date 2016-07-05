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
public class StringSubsetArrayType extends UserDataArrayBaseType {

    public StringSubsetArrayType() {
      setValueName("Subset Array");
      setElementStyleName("ugx-subset-selection");
    }
}
