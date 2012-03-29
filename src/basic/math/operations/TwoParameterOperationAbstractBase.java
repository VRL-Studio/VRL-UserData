/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations;

import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.helpers.MathMLAnalyser;
import basic.math.helpers.ParametersOperationSelection;
import eu.mihosoft.vrl.annotation.MethodInfo;
import java.io.Serializable;
import java.util.Arrays;

/**
 * This abstract class is a basic class for two parameter based operation with some
 * implemented methods like generateMathMLName() and checkDimensionsForAddition()
 * to save some implementation work in more specialized operations.
 *
 * @author Night
 */
public abstract class TwoParameterOperationAbstractBase implements
        TwoParameterOperationInterface, Serializable {

    private static final long serialVersionUID = 1;
    public ParametersOperationSelection operationSelection;

//    public TwoParameterOperationAbstractBase() {
//    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public String generateMathMLName(VisualElementInterface a, 
            VisualElementInterface b, String mathML, String shortName) {

        String result = null;

//        if (shortName.equals("")) {
//
//            result = "<ci>(</ci>" + a.getXmlCoding() + "<ci>)</ci>" +
//                    "<ci>" + operationSelection.getSelectedObject() + "</ci> " +
//                    "<ci>(</ci>" + b.getXmlCoding() + "<ci>)</ci>";
//        } else {
//            result = shortName;
//        }
        if (shortName.equals("")) {

            result = MathMLAnalyser.setBrackets( a.getXmlCoding() ) +
                    "<ci>" + operationSelection.getSelectedObject() + "</ci> " +
                    MathMLAnalyser.setBrackets( b.getXmlCoding() );
        } else {
            result = shortName;
        }

        return result;
    }  

    /**
     * {@inheritDoc}
     *
     * Checks the dimension of the two parameters if there is an addition
     * possible with this two element.
     *
     * @param a the first element
     * @param b the second element
     * @return true if addition should be from point of dimensions possible
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Boolean checkDimensionsForAddition(VisualElementInterface a, VisualElementInterface b) {
        Boolean result = new Boolean(false);

        if (Arrays.equals(a.getVisualDimensions(), b.getVisualDimensions())) {
            result = Boolean.TRUE;
        }

        return result;
    }

}
