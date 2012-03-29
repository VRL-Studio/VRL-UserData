/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations;

import basic.math.elements.interfaces.VisualElementInterface;

/**
 * This is the base interface for all classes which are specialized on one
 * paramter based operations.
 *
 * @author Night
 */
public interface OneParameterOperationInterface {

    /**
     * Calculates the result depending on the implementation of the operation.
     *
     * @param a the first parameter
     * @param mathML the mathML code for the result name (if used)
     * @param shortName a string which contain the result name (if used)
     * @return the calculated result
     */
    public VisualElementInterface execute(VisualElementInterface a, String mathML, String shortName);

}
