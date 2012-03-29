/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.ug.types;

import eu.mihosoft.vrl.types.DoubleType;
import groovy.lang.Script;
import java.io.Serializable;

/**
 *
 * This TypeRepresentations allows to use default values for Doubles
 * by evaluating a groovy script.
 *
 * @author night
 */
public class CustomDoubleType extends DoubleType implements Serializable{
    private static final long serialVersionUID = 1L;

    private String defaultValue;
//    private TransparentLabel defaultLabel = new TransparentLabel("");

    public CustomDoubleType() {
        super();

//        BoxLayout loayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
//        setLayout(loayout);

        add(nameLabel);

        add(input);

//        add(defaultLabel);

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
//                defaultLabel.setText(" default = " + property.toString());
                defaultValue = property.toString();
                setViewValue(defaultValue);
            }
        }
    }

}
