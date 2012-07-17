/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.userdata.types;

import edu.gcsc.vrl.ug.api.I_IUserData;
import edu.gcsc.vrl.ug.api.I_UserVector;
import edu.gcsc.vrl.userdata.UserVectorPair;
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
@TypeInfo(type = UserVectorPair.class, input = true, output = false, style = "default")
public class UserVectorPairType extends UserVectorType implements Serializable {

    private static final long serialVersionUID = 1;
    private VTextField input = new VTextField("");

    public UserVectorPairType() {

        super();

//        setType(UserVectorPair.class);

        input.setMinimumSize(new Dimension(80, input.getHeight()));
        input.setPreferredSize(new Dimension(80, input.getHeight()));
        input.setSize(new Dimension(80, input.getHeight()));

        setInputComponent(input);

        add(input);
    }

    @Override
    protected Object createFinalUserData(I_IUserData userData) {
        return new UserVectorPair(input.getText(), userData);
    }

    @Override
    public void setValue(Object o) {

        // custom convert method (we allow  I_UserVector as input)
        if (o instanceof I_UserVector) {
            o = new UserVectorPair(input.getText(), (I_UserVector) o);
        }

        super.setValue(o);
    }

    @Override
    public void setViewValue(Object o) {
        if (o instanceof UserVectorPair) {
            UserVectorPair pair = (UserVectorPair) o;
            input.setText(pair.getSubset());
        }
    }

    @Override
    public ConnectionResult compatible(TypeRepresentationBase tRep) {
        ConnectionResult result = super.compatible(tRep);

        // we allow connectiosn from I_UserVector as we can convert
        // regular UserVectors to UserVectorPairs
        if (result.getStatus() != ConnectionStatus.VALID) {
            if (I_UserVector.class.isAssignableFrom(tRep.getType())) {
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
