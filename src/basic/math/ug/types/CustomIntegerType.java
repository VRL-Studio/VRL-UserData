/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.ug.types;

import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.IntegerType;
import groovy.lang.Script;
import java.io.Serializable;

/**
 *
 * This TypeRepresentations allows to use default values for Integers
 * by evaluating a groovy script.
 *
 * @author night
 */
public class CustomIntegerType extends IntegerType implements Serializable {

    private static final long serialVersionUID = 1L;
    private String defaultValue;

    public CustomIntegerType() {
        super();

        add(nameLabel);
        add(input);

        //        setSupportedStyle("custom");
        setStyleName("custom");
    }

    /**
     * Writtes the default value in the edit field if a the connection
     * with a double returning method is removed.
     */
    @Override
    public void emptyView() {
        super.emptyView();
        setViewValue(defaultValue);
    }

    /**
     *
     * Checks if there is a groovy script and then if there is a variable which stores
     * the default value which should the shown and used in the edit field.
     *
     * @param script is a groovy script which contains the default value
     * which is written in the edit field.
     */
    @Override
    protected void evaluationRequest(Script script) {
        Object property = null;

        if (getValueOptions() != null) {

            if (getValueOptions().contains("defaultValue")) {
                property = script.getProperty("defaultValue");
            }

            if (property != null) {

                defaultValue = property.toString();
                setViewValue(defaultValue);
            }
        }
    }

}
