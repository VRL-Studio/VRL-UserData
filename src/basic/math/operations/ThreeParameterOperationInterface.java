/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations;

import basic.math.elements.interfaces.VisualElementInterface;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.types.MethodRequest;

/**
 * This is the base interface for all classes which are specialized on three
 * paramter based operations.
 *
 * @author Night
 */
public interface ThreeParameterOperationInterface {

    /**
     * Calculates the result depending on the operation which is used.
     *
     * @param a the first parameter
     * @param m the second parameter
     * @param b the third parameter
     * @param mathML the mathML code for the result name (if used)
     * @param shortName a string which contain the result name (if used)
     * @return the calculated result
     */
    public VisualElementInterface execute(
            VisualElementInterface a,
            VisualElementInterface m,
            VisualElementInterface b,
            @ParamInfo(name = "MathML", nullIsValid = true)String mathML,
            @ParamInfo(name = "Result Name")String shortName);

    /**
     * Check if all involved elements are compatible to each other in the way
     * they were used in the mathematic operation.
     *
     * @param methodRequest 
     * @param a the first parameter
     * @param m the second parameter
     * @param b the third parameter
     * @return true if all elements have the correct dimensions
     */
//    public Boolean checkForDimensions(VisualElementInterface a,
//            VisualElementInterface m, VisualElementInterface b);
    public Boolean checkForDimensions(MethodRequest methodRequest,
            VisualElementInterface a,
            VisualElementInterface m, VisualElementInterface b);

}
