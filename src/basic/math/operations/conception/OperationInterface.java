/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations.conception;

import basic.math.elements.interfaces.VisualElementInterface;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.util.ArrayList;

/**
 * The basic interface for all mathematic operations which are used in
 * basic.math package.
 *
 * @author night
 */
public interface OperationInterface {

    /**
     * Contains the concrete specialized classes which are involved in this Operation.
     */
    public ArrayList<Class> getParameterClasses();

    /**
     * If you want two or more different implementation for one Operation,
     * you can keep them apart by their implementationInfo.
     * E.g. add in java in for loop component-by-component
     * or in a specialized way in a nother way.
     */
    public String getImplementationInfo();

    /**
     * Calculates the result depending on the implementation of the operation.
     *
     * @param mathML the mathML code for the result name (if used)
     * @param shortName a string which contain the result name (if used)
     * * @param visualElements the involved elements in the operation
     * @return the calculated result
     */
    public VisualElementInterface execute(
            @ParamInfo(name = "MathML", nullIsValid = true, style="editor")String mathML,
            @ParamInfo(name = "Result Name")String shortName,
            VisualElementInterface... visualElements);


}
