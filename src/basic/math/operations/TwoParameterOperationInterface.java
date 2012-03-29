/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.operations;

import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.helpers.ParametersOperationSelection;
import eu.mihosoft.vrl.annotation.ParamInfo;

/**
 * This is the base interface for all classes which are specialized on two
 * paramter based operations.
 * Additionally these class allows to switch between the different operation
 * by selecting one representative for the wanted operation from a selection list.
 *
 * @author Night
 */
public interface TwoParameterOperationInterface {

    /**
     * Calculates the result depending on the operation which is used.
     *
     * @param a the first parameter
     * @param op a list of basic operations like + or -
     * @param b the third parameter
     * @param mathML the mathML code for the result name (if used)
     * @param shortName a string which contain the result name (if used)
     * @return the calculated result
     */
    public VisualElementInterface execute(
            VisualElementInterface a,
            @ParamInfo(name=" ", options="hideConnector=true") ParametersOperationSelection op,
            VisualElementInterface b,
            @ParamInfo(name = "MathML", nullIsValid = true)String mathML,
            @ParamInfo(name = "Result Name")String shortName);

    /**
     * Generates the MathML code for the result depending on the names of the
     * involved elements and the two parameter strings,
     * which can contain a user defined name for the calculated result.
     *
     * If the mathML String is not null its input is used for the result name.
     * Else:
     * If the shortName String is not null its input is used for the result name.
     * Else:
     * The result name is a combination of the name of the first element the used
     * operation and the name of the second element.
     *
     *
     * @param a the first element
     * @param b the second element
     * @param mathML the mathML code for the result name (if used)
     * @param shortName a string which contain the result name (if used)
     * @return the generate result operationSymbol
     */
    public String generateMathMLName(VisualElementInterface a,
            VisualElementInterface b, String mathML, String shortName);

    /**
     * Check dimension the way they were needed for an addition.
     *
     * @param a first element
     * @param b second element
     * @return true if dimensions of the elements would allow an addition of these elements
     */
    public Boolean checkDimensionsForAddition(VisualElementInterface a, VisualElementInterface b);

    /**
     * Check dimension the way they were needed for an multiplication.
     *
     * @param a first element
     * @param b second element
     * @return true if dimensions of the elements would allow an multication of these elements
     */
    public Boolean checkDimensionsForMultiplication(VisualElementInterface a, VisualElementInterface b);

}
