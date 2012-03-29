/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations;

import basic.math.elements.Vector;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.visual.VisualVector;
import basic.math.helpers.ErrorMessageWriter;
import basic.math.helpers.MathMLAnalyser;
import basic.math.helpers.MathMLShortCuts;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.DefaultMethodRepresentation;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.MethodRequest;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.Connector;
import java.io.Serializable;

/**
 * Calculates the crossproduct for two vectors of lenght 3.
 *
 * @author night
 */
@ObjectInfo(name = "Crossproduct")
@ComponentInfo(name = "CrossProduct", category = "BasicMath")
public class CrossProduct implements Serializable {

    private static final long serialVersionUID = 1;
    private transient VisualCanvas mainCanvas;

    public VisualVectorInterface execute(
            MethodRequest methodRequest,
            VisualVectorInterface a,
            VisualVectorInterface b,
            @ParamInfo(name = "MathML") String mathML,
            @ParamInfo(name = "Result Name") String name) {

        if (checkForDimensions(methodRequest, a, b)) {

            Vector.Doubles element = new Vector.Doubles(a.getVisualDimensions()[0]);

            Double a0 = (Double) a.getEntry(0);
            Double a1 = (Double) a.getEntry(1);
            Double a2 = (Double) a.getEntry(2);
            Double b0 = (Double) b.getEntry(0);
            Double b1 = (Double) b.getEntry(1);
            Double b2 = (Double) b.getEntry(2);


            element.setEntry(0, (Double) (a1 * b2 - a2 * b1));
            element.setEntry(1, (Double) (a2 * b0 - a0 * b2));
            element.setEntry(2, (Double) (a0 * b1 - a1 * b0));

            VisualVectorInterface c = new VisualVector(generateMathMLName(a, b, mathML, name), name, true, element);
            return c;
        }

        throw new IllegalArgumentException(CrossProduct.class + " checkForDimensions() failed");
    }

    /**
     * Checks if dimensions of a and b are 3 if not an error message will be
     * return at the message box.
     *
     * @param a first parameter
     * @param b second parameter
     * @return true if dimension of a and b is 3
     */
    @MethodInfo(hide = true, noGUI = true)
    private Boolean checkForDimensions(MethodRequest methodRequest,
            VisualVectorInterface a, VisualVectorInterface b) {

        Boolean result = true;
        String title = "Wrong Vector dimension";

        DefaultMethodRepresentation mRep = methodRequest.getMethod();
//                mainCanvas.getMethodRepresentation(this, visualID, "execute",
//                new Class[]{VisualVectorInterface.class, VisualVectorInterface.class, String.class, String.class});

        // checks if dimensions of a equals 3
        if (!(a.getVisualDimensions()[0] == 3)) {

            Connector c1 = mRep.getParameter(0).getConnector();
            ErrorMessageWriter.writeErrorMessage(mainCanvas, title,
                    "The dimension of vector " + a.getShortName() + " must be 3 !", c1);

            result = false;

        } else // checks if dimensions of b equals 3
        if (!(b.getVisualDimensions()[0] == 3)) {

            Connector c2 = mRep.getParameter(1).getConnector();
            ErrorMessageWriter.writeErrorMessage(mainCanvas, title,
                    "The dimension of vector " + b.getShortName() + " must be 3 !", c2);

            result = false;
        }

        return result;
    }

    /**
     * Generates the result name depanding on the names of the involved elements
     * and if there is a short result name given which should be used.
     * @param a the first element
     * @param b the second element
     * @param mathML the mathML code which should be used for the result name if
     *          not null
     * @param shortName  the short name for the result if not null
     * @return the generate result operationSymbol
     */
    @MethodInfo(hide = true, noGUI = true)
    private String generateMathMLName(VisualVectorInterface a, VisualVectorInterface b, String mathML, String shortName) {
        String result = null;

//        if (mathML.equals("")) {
//            if (shortName.equals("")) {
//                result = "<ci>(</ci>" + a.getXmlCoding() +
//                        "<apply><vectorproduct/> <ci> ) </ci> <ci> ( </ci> </apply>" +
//                        b.getXmlCoding() + "<ci>)</ci>";
//            } else {
//                result = MathMLShortCuts.textBegin + shortName + MathMLShortCuts.textEnd;
//            }
//        } else {
//            result = mathML;
//        }
        if (mathML.equals("")) {
            if (shortName.equals("")) {
                result = "<apply><vectorproduct/>"+
                        MathMLAnalyser.setBrackets( a.getXmlCoding() )+
                        "</apply>" +
                        MathMLAnalyser.setBrackets(b.getXmlCoding() );
            } else {
                result = MathMLShortCuts.textBegin + shortName + MathMLShortCuts.textEnd;
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
    @MethodInfo(noGUI = true, callOptions = "assign-canvas")
    public void setMainCanvas(Canvas mainCanvas) {
        this.mainCanvas = (VisualCanvas) mainCanvas;
    }
}
