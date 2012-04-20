/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.CondUserNumberPair;
import edu.gcsc.vrl.userdata.CondUserNumberWindow;
import eu.mihosoft.vrl.reflection.RepresentationType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.ConnectionResult;
import eu.mihosoft.vrl.visual.ConnectionStatus;
import eu.mihosoft.vrl.visual.VButton;
import eu.mihosoft.vrl.visual.VTextField;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class CondUserNumberPairType extends CondUserNumberType implements Serializable {

    private static final long serialVersionUID = 1;
    private CondUserNumberWindow window;
    private VTextField input = new VTextField("");
    static final String MODEL_KEY = "CondUserType:model";

    public CondUserNumberPairType() {
        
        super();

        setType(CondUserNumberPair.class);

        input.setMinimumSize(new Dimension(80, input.getHeight()));
        input.setPreferredSize(new Dimension(80, input.getHeight()));
        input.setSize(new Dimension(80, input.getHeight()));

        setInputComponent(input);

        add(input);



    }
    
    @Override
    protected Object createFinalUserData(I_CondUserNumber data) {
        // additional
        CondUserNumberPair finalResult =
                new CondUserNumberPair(
                input.getText(), (I_CondUserNumber) data);
        
        return finalResult;
    }

    @Override
    public Object getValue() {

        return super.getValue();
    }

    @Override
    public void setValue(Object o) {
        
        // custom convert method (we allow  I_CondUserNumber as input)
        if (o instanceof I_CondUserNumber) {
            o = new CondUserNumberPair(input.getText(), (I_CondUserNumber) o);
        }

        super.setValue(o);
    }

    @Override
    public void setViewValue(Object o) {
        if (o instanceof CondUserNumberPair) {
            CondUserNumberPair pair = (CondUserNumberPair) o;
            input.setText(pair.getSubset());
        }
    }

    @Override
    public ConnectionResult compatible(TypeRepresentationBase tRep) {
        ConnectionResult result = super.compatible(tRep);

        // we allow connectiosn from I_CondUserNumber as we can convert
        // regular CondUserNumbers to CondUserNumberPairs
        if (result.getStatus() != ConnectionStatus.VALID) {
            if (I_CondUserNumber.class.isAssignableFrom(tRep.getType())) {
                result = new ConnectionResult(
                        null, ConnectionStatus.VALID);
            }
        }

        return result;
    }
    
    @Override
    public String getValueAsCode() {
        // TODO this is ony to prevent warnings that are irrelevant for lectures 2012 (this must be solved!!!)
        return "null as " + getType().getName();
    }
}
