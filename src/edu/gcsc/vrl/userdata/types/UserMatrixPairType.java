/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.I_IIPData;
import edu.gcsc.vrl.ug.api.I_UserMatrix;
import edu.gcsc.vrl.userdata.UserMatrixPair;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.visual.ConnectionResult;
import eu.mihosoft.vrl.visual.ConnectionStatus;
import eu.mihosoft.vrl.visual.VTextField;
import java.awt.Dimension;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
@TypeInfo(type = UserMatrixPair.class, input = true, output = false, style = "default")
public class UserMatrixPairType extends UserMatrixType implements Serializable {

    private static final long serialVersionUID = 1;
    
    private VTextField input = new VTextField("");

    public UserMatrixPairType() {

        super();

        input.setMinimumSize(new Dimension(80, input.getHeight()));
        input.setPreferredSize(new Dimension(80, input.getHeight()));
        input.setSize(new Dimension(80, input.getHeight()));

        setInputComponent(input);

        add(input);
    }

    @Override
    protected Object createFinalUserData(I_IIPData userData) {

        return new UserMatrixPair(input.getText(), userData);
    }

    @Override
    public void setValue(Object o) {

        // custom convert method (we allow  I_UserMatrix as input)
        if (o instanceof I_UserMatrix) {
            o = new UserMatrixPair(input.getText(), (I_UserMatrix) o);
        }

        super.setValue(o);
    }

    @Override
    public void setViewValue(Object o) {
        if (o instanceof UserMatrixPair) {
            UserMatrixPair pair = (UserMatrixPair) o;
            input.setText(pair.getSubset());
        }
    }

    @Override
    public ConnectionResult compatible(TypeRepresentationBase tRep) {
        ConnectionResult result = super.compatible(tRep);

        // we allow connectiosn from I_UserMatrix as we can convert
        // regular UserMatrixs to UserMatrixPairs
        if (result.getStatus() != ConnectionStatus.VALID) {
            if (I_UserMatrix.class.isAssignableFrom(tRep.getType())) {
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
