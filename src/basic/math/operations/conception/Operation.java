/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations.conception;

import basic.math.elements.interfaces.VisualElementInterface;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The basic abstract class for all mathematic operations which are used in
 * basic.math package.
 *
 * @author night
 */
public abstract class Operation implements OperationInterface, Serializable {

    private static final long serialVersionUID = 1;
    /**
     * This should be a short name or a character which discripes this operation.
     * E.g. for an addition: +
     *      for exponential function: exp
     */
    protected String implementationInfo;
    /**
     * This is a list where all classes of the involved parameters,
     * in this operation, can and should be stored.
     */
    protected ArrayList<Class> parameterClasses;

//    public Operation(String implementationInfo)
//    {
//        this.implementationInfo = implementationInfo;
//    }
    public Operation() {
    }

    @Override
    public ArrayList<Class> getParameterClasses() {
        return parameterClasses;
    }

    /**
     * @param parameterClasses the parameterClasses to set
     */
    public void setParameterClasses(ArrayList<Class> parameterClasses) {
        this.parameterClasses = parameterClasses;
    }

    @Override
    public String getImplementationInfo() {
        return implementationInfo;
    }

    /**
     * @param implementationInfo the implementationInfo to set
     */
    public void setImplementationInfo(String implementationInfo) {
        this.implementationInfo = implementationInfo;
    }

    @Override
    public abstract VisualElementInterface execute(String mathML, String shortName, VisualElementInterface... visualElements);

//     in einer abstrakten klasse auslagern/anbitten
    protected abstract Boolean checkDimensions(VisualElementInterface... visualElements);
}
