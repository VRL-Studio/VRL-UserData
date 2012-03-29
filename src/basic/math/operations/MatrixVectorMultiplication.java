/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations;

import basic.math.elements.Vector;
import basic.math.elements.interfaces.VectorInterface;
import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.elements.interfaces.VisualMatrixInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.visual.VisualVector;
import basic.math.helpers.MathMLAnalyser;
import basic.math.helpers.MathMLShortCuts;
import basic.math.helpers.ParametersOperationSelection;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;

/**
 * A classical implementation of a matrix vector multiplication.
 *
 * @author night
 */
@ObjectInfo(name = "MatrixVectorMultiplication")
@ComponentInfo(name = "MatrixVectorMultiplication", category = "BasicMath/Operations")
public class MatrixVectorMultiplication extends TwoParameterOperationAbstractBase {

    private static final long serialVersionUID = 1;

    @MethodInfo(hide = true, noGUI = true)
    public Boolean checkForDimensionsMultiplication(VisualMatrixInterface a, VisualVectorInterface b) {
        Boolean result = Boolean.FALSE;

        if (a.getVisualDimensions()[1] == b.getVisualDimensions()[0]) {
            result = Boolean.TRUE;
        }

        return result;
    }

    /**
     * Calculates the result of M*b
     *
     * @param m the matrix that is used
     * @param b the vector that is used
     * @param mathML the mathML code which should be used for the result name if
     *          not null
     * @param shortName  the short name for the result if not null
     *
     * @return the calculated result
     */
    @MethodInfo(hide = false)
    public VisualVectorInterface execute(
            VisualMatrixInterface m,
            VisualVectorInterface b,
            @ParamInfo(name = "MathML", nullIsValid = true) String mathML,
            @ParamInfo(name = "Result Name") String shortName) {

        VisualVectorInterface result = null;
        VectorInterface element = null;

        if (checkForDimensionsMultiplication(m, b)) {

            element = new Vector.Doubles(m.getVisualDimensions()[1]);
            result = new VisualVector(
                    generateMathMLName(m, b, mathML, shortName), shortName, true, element);

            Double tmp00 = 0.0;
            Double tmp01 = 0.0;
            Double tmp02 = 0.0;
            Double d = 0.0;

            //Matrix * Vector (m*b)
            for (int i = 0; i < m.getVisualDimensions()[0]; i++) {

                for (int j = 0; j < m.getVisualDimensions()[1]; j++) {

                    tmp00 = (Double) result.getEntry(i);
                    tmp01 = ((Double) b.getEntry(j));
                    tmp02 = ((Double) m.getEntry(i, j));

                    d = tmp00 + tmp02 * tmp01;

                    result.setEntry(i, d);
                }
            }
        }
        return result;
    }

    /**
     * Generates the result name depanding on the names of the involved elements
     * and if there is a short result name given which should be used.
     * @param mat the matrix
     * @param b the vector
     * @param mathML the mathML code which should be used for the result name if
     *          not null
     * @param shortName  the short name for the result if not null
     *
     * @return the generate result operationSymbol
     */
    @MethodInfo(hide = true, noGUI = true)
    public String generateMathMLName(VisualMatrixInterface mat, VisualVectorInterface b, String mathML, String shortName) {
        String result = null;

//        if (mathML.equals("")) {
//            if (shortName.equals("")) {
//
//                if ((mat != null) && (b != null)) {
//
//                    result = "<ci>(</ci>" + mat.getXmlCoding() + "<ci>)</ci>" +
//                            "<ci>*</ci>" +
//                            "<ci>(</ci>" + b.getXmlCoding() + "<ci>)</ci>";
//                }
//            } else {
//                result = MathMLShortCuts.textBegin + shortName + MathMLShortCuts.textEnd;
//            }
//        } else {
//            result = mathML;
//        }

        if (mathML.equals("")) {
            if (shortName.equals("")) {

                if ((mat != null) && (b != null)) {

                    result = MathMLAnalyser.setBrackets(  mat.getXmlCoding() ) +
                            "<ci>*</ci>" +
                            MathMLAnalyser.setBrackets( b.getXmlCoding() );
                }
            } else {
                result = MathMLShortCuts.textBegin + shortName + MathMLShortCuts.textEnd;
            }
        } else {
            result = mathML;
        }
        return result;
    }

    /**
     * DON'T USE
     * 
     * {@inheritDoc}
     * For this class use better directly the followed method:
     * execute((VisualMatrixInterface)a,(VisualVectorInterface) b, mathML, shortName);
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public VisualElementInterface execute(VisualElementInterface a,
            ParametersOperationSelection op, VisualElementInterface b,
            String mathML, String shortName) {

        if ((a instanceof VisualMatrixInterface) &&
                (op.getSelectedObject().equals("*")) &&
                (b instanceof VisualVectorInterface)) {

            return execute((VisualMatrixInterface) a,
                    (VisualVectorInterface) b, mathML, shortName);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * DON'T USE
     * 
     * {@inheritDoc}
     * For this class use better directly the followed method:
     * generateMathMLName((VisualMatrixInterface)a,(VisualVectorInterface) b, mathML, shortName);
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public String generateMathMLName(VisualElementInterface a,
            VisualElementInterface b, String mathML, String shortName) {

        if ((a instanceof VisualMatrixInterface) &&
                (b instanceof VisualVectorInterface)) {

            return generateMathMLName((VisualMatrixInterface) a,
                    (VisualVectorInterface) b, mathML, shortName);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * DON'T USE
     * 
     * {@inheritDoc}
     * For this class use better directly the followed method:
     * checkForDimensionsMultiplication(VisualMatrixInterface a, VisualVectorInterface b)
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Boolean checkDimensionsForMultiplication(
            VisualElementInterface a, VisualElementInterface b) {

        if ((a instanceof VisualMatrixInterface) &&
                (b instanceof VisualVectorInterface)) {

            return checkDimensionsForMultiplication((VisualMatrixInterface) a,
                    (VisualVectorInterface) b);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }
}
