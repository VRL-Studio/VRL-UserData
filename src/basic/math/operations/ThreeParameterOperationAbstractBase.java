/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations;

import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.helpers.MathMLAnalyser;
import basic.math.helpers.MathMLShortCuts;
import java.io.Serializable;

/**
 * This abstract class is a basic class for three parameter based operation with
 * the implemented method generateMathMLName()
 * to save some implementation work in more specialized operations.
 *
 * @author Night
 */
public abstract class ThreeParameterOperationAbstractBase implements
        ThreeParameterOperationInterface, Serializable {

    private static final long serialVersionUID = 1;

    /**
     * Generates the result name depanding on the names of the involved elements
     * and if there is a short result name given which should be used.
     * @param a the first element
     * @param mod the representation/construction matrix ("Darstellungsmatrix")
     * @param b the second element
     * @param mathML the mathML code for the result name (if used)
     * @param shortName a string which contain the result name (if used)
     * @return the generate result operationSymbol
     */
    public String generateMathMLName(
            VisualElementInterface a, VisualElementInterface mod,
            VisualElementInterface b, String mathML, String shortName) {

        String result = null;

//        if (mathML.equals("")) {
//            if (shortName.equals("")) {
//
//                if (mod != null) {
//                    result = "<ci>(</ci>" + a.getXmlCoding() +
//                            "<apply><transpose/>" + "<ci>)</ci>" + "</apply> " + "<ci>(</ci>" +
//                            mod.getXmlCoding() + "<ci>)</ci>" + "<ci>(</ci>" +
//                            b.getXmlCoding() + "<ci>)</ci>";
//                } else {
//                    result = "<ci>(</ci>" + a.getXmlCoding() +
//                            "<apply><transpose/>" + "<ci>)</ci>" + "</apply> " + "<ci>(</ci>" +
//                            b.getXmlCoding() + "<ci>)</ci>";
//
//                }
//            } else {
//                result = MathMLShortCuts.textBegin + shortName +MathMLShortCuts.textEnd;
//            }
//        } else {
//            result = mathML;
//        }

        if (mathML.equals("")) {
            if (shortName.equals("")) {

                if (mod != null) {
                    result = "<apply><transpose/>" +
                            MathMLAnalyser.setBrackets( a.getXmlCoding() ) +
                            "</apply> " +
                            MathMLAnalyser.setBrackets(mod.getXmlCoding() )+
                            MathMLAnalyser.setBrackets(b.getXmlCoding() );
                } else {
                    result = "<apply><transpose/>" +
                            MathMLAnalyser.setBrackets( a.getXmlCoding() )+
                            "</apply> " +
                            MathMLAnalyser.setBrackets( b.getXmlCoding() );

                }
            } else {
                result = MathMLShortCuts.textBegin + shortName +MathMLShortCuts.textEnd;
            }
        } else {
            result = mathML;
        }

        return result;
    }
}
