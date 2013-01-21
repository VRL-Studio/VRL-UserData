/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.types.ArrayBaseType;
import java.lang.annotation.Annotation;

/**
 * Base Class for all Array Types in this plugin. The difference to 
 * the original ArrayBaseType class is that parent value options are 
 * accessible through a custom element param info.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UserDataArrayBaseType extends ArrayBaseType {

    public UserDataArrayBaseType() {
        setElementInputInfo(null);
    }

    /**
     * Default implementation has to be overriden as
     * <code>options</code> returns custom string that is defined in this
     * plugin.
     *
     * @param pInf param info (not used)
     */
    @Override
    public void setElementInputInfo(ParamInfo pInf) {
        super.setElementInputInfo(new ParamInfo() {
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
                return getValueOptions();
            }

            @Override
            public String typeName() {
                return getElementTypeName();
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        });
    }
    
    /**
     * Returns the element type name.
     * @return the element type name
     */
    protected String getElementTypeName() {
        return super.getType().getComponentType().getName();
    }
}
