/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations;

import basic.math.elements.interfaces.VisualMatrixInterface;
import basic.math.elements.interfaces.VisualScalarInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.helpers.MathMLShortCuts;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.MethodRequest;
import eu.mihosoft.vrl.visual.Canvas;

/**
 * This class calculates the norm of a vector.
 * It is possible to calculate a scaled norm if a matrix is used.
 * E.g. (a^t * m * a)^(1/2)
 *
 * @author night
 */
@ObjectInfo(name = "Norm")
@ComponentInfo(name = "Norm", category = "BasicMath/Operations")
public class Norm extends BilinearFormen {

    private static final long serialVersionUID = 1;

    /**
     * Calculates the Vector Norm depending on the scalar product
     * which is conected with the used Matrix.
     * If no matrix is used the standard norm is calculated.
     *
     * E.g. (a^t * m * a)^(1/2)
     *
     * @param a the vector which norm we want to know
     * @param m the matrix that is used to
     * @param mathML the mathML code for the result name (if used)
     * @param shortName a string which contain the result name (if used)
     * @return the calculated norm
     */
    @MethodInfo(hide = false)
    public VisualScalarInterface execute(
            MethodRequest methodRequest,
             VisualVectorInterface a,
            @ParamInfo(nullIsValid = true) VisualMatrixInterface m,
            @ParamInfo(name = "MathML") String mathML,
            @ParamInfo(name = "Result Name") String shortName) {

        VisualScalarInterface scalarProduct = super.execute(methodRequest,
                a, m, a, generateMathMLName(a, m,mathML, shortName), shortName);

        if (scalarProduct != null) {
            // value = (value)^(1/2)
            scalarProduct.setEntry(Math.sqrt((Double)scalarProduct.getScalar().getEntry()));
        }
        return scalarProduct;
    }

    /**
     * Generates the result name depending on the names of the involved elements
     * and if there is a short result name given which should be used.
     * @param a the first element
     * @param m the used matrix
     * @param mathML the mathML code which should be used for the result name if
     *          not null
     * @param shortName  the short name for the result if not null
     *
     * @return the generated result name
     */
    @MethodInfo(hide = true, noGUI = true)
    public String generateMathMLName(VisualVectorInterface a, VisualMatrixInterface m, String mathML, String shortName) {
        String result = null;

        if (mathML.equals("")) {
            if (shortName.equals("")) {

                if (m != null) {

                    result = "<ci>||</ci>" + a.getXmlCoding() +
                            "<ci>||</ci>" + "<apply> <selector/> <ci></ci> <cn> " +
                            m.getXmlCoding() + "</cn></apply>";
                } else {
                    result = "<ci>||</ci>" + a.getXmlCoding() + "<ci>||</ci>";
                }
            } else {
                result = MathMLShortCuts.textBegin + shortName +MathMLShortCuts.textEnd;;
            }
        } else {
            result = mathML;
        }
        return result;
    }

    /**
     * Sets the main canvas object.
     * @param mainCanvas the main canvas object
     */
    @MethodInfo(noGUI = true, hide = true, callOptions = "assign-canvas")
    @Override
    public void setMainCanvas(Canvas mainCanvas) {
        this.mainCanvas = (VisualCanvas) mainCanvas;
    }

    
}
