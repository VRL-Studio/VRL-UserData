/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations;

import basic.math.elements.Vector;
import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.elements.interfaces.VisualMatrixInterface;
import basic.math.elements.interfaces.VisualScalarInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.visual.VisualScalar;
import basic.math.helpers.ErrorMessageWriter;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.DefaultMethodRepresentation;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.MethodRequest;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.Connector;

/**
 * This class calculates the the bilinearform of the two input vector depending
 * on the used or not used matrix.
 *
 * vector: a, b
 * matrix: M
 * scalar: result
 * transpose: ^t
 *
 * result = a^t * M * b
 *
 * @author night
 */
@ObjectInfo(name = "Bilinearform")
@ComponentInfo(name = "Bilinearform", category = "BasicMath/Operations")
public class BilinearFormen extends ThreeParameterOperationAbstractBase {

    protected transient VisualCanvas mainCanvas;
    private static final long serialVersionUID = 1;

    /**
     * Sets the main canvas object.
     * @param mainCanvas the main canvas object
     */
    @MethodInfo(noGUI = true, hide = true, callOptions = "assign-canvas")
    public void setMainCanvas(Canvas mainCanvas) {
        this.mainCanvas = (VisualCanvas) mainCanvas;
    }

    /**
     * Calculates the result of a^t * m * b. The symbol ^t means transpose.
     * If the matrix is null there will be the standard scalar product calculated.
     *
     * The result name creation is done like followed:
     * 1) check if there is a mathML code if so use it for result name if not got to 2)
     * 2) check if ther is a short name (string) for the result name if so use it if not go to 3)
     * 3) create depended on which elements are involved and which operation is done
     *    the result name from this combination
     *
     * This method is a specialization of VisualElementInterface execute(
     *      VisualElementInterface a, VisualElementInterface m, VisualElementInterface b,
     *      String mathML, String shortName)
     *
     * @param a the first vector
     * @param m the matrix
     * @param b the second vector
     * @param mathML the mathML code for the result if used
     * @param shortName the new result name if used
     * @return the calculate scalar value
     */
    public VisualScalarInterface execute(
            MethodRequest methodRequest,
            VisualVectorInterface a,
            @ParamInfo( nullIsValid = true) VisualMatrixInterface m,
             VisualVectorInterface b,
            @ParamInfo(name = "MathML") String mathML,
            @ParamInfo(name = "Result") String shortName) {

        if (checkForDimensions(methodRequest, a, m, b)) {

            VisualScalarInterface finalResult = new VisualScalar(
                    generateMathMLName(a, m, b, mathML, shortName),
                    shortName, true, Double.class);

            finalResult.fillAllEntriesWith(0.0);

            Double d00 = 0.0;
            Double d01 = 0.0;
            Double d02 = 0.0;
            Double d = 0.0;

            if (m != null) {

                Vector.Doubles tmpResult = new Vector.Doubles(a.getVisualDimensions()[0]);

            //Matrix * Vector (m*b)
            for (int i = 0; i < m.getVisualDimensions()[0]; i++) {
                for (int j = 0; j < m.getVisualDimensions()[1]; j++) {

                    d00 = (Double) tmpResult.getEntry(i);
                    d01 = ((Double) b.getEntry(j));
                    d02 = ((Double) m.getEntry(i, j));
                    d = d00 + d02 * d01;
                    tmpResult.setEntry(i, d);
                }
            }

                //vec * vec (a*tmpVec)
                for (int i = 0; i < a.getVisualDimensions()[0]; i++) {

                    d00 = (Double) finalResult.getEntry();
                    d01 = (Double) a.getEntry(i);
                    d02 = (Double) tmpResult.getEntry(i);

                    finalResult.setEntry(d00 + d01 * d02);
//                    finalResult += a.getEntry(i) * tmpResult.getEntry(i);
                }

                System.out.println("finalResult = "+ finalResult.getEntry());

                return finalResult;

            }//standard scalarproduct if( m == null )
            else {

                for (int i = 0; i < a.getVisualDimensions()[0]; i++) {

                    d00 = (Double) finalResult.getEntry();
                    d01 = (Double) a.getEntry(i);
                    d02 = (Double) b.getEntry(i);

                    finalResult.setEntry(d00 + d01 * d02);
//                    finalResult += a.getEntry(i) * b.getEntry(i);
                }
            }

            return finalResult;
        }

        return null;//only if checkForDimensions goes wrong
    }

    /**
     * Checks if vector a.length equals m.row and m.column equals b.length if
     * m is not null else check if a and b have same size.
     * 
     * If they have diffrent sizes report the result of the check on the message 
     * box of VRL-Studio.
     *
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Boolean checkForDimensions(MethodRequest methodRequest,
            VisualElementInterface a,
            VisualElementInterface m, VisualElementInterface b) {

        if (m != null) {

            if (!m.getDataType().equals(b.getDataType())) {
                throw new IllegalArgumentException(BilinearFormen.class +
                        " m & b have diffrent data types");
            }
            if (!m.getDataType().equals(a.getDataType())) {
                throw new IllegalArgumentException(BilinearFormen.class +
                        " m & a have diffrent data types");
            }
        }
        if (!a.getDataType().equals(b.getDataType())) {
            throw new IllegalArgumentException(BilinearFormen.class +
                    " a & b have diffrent data types");
        }

        DefaultMethodRepresentation mRep = methodRequest.getMethod();

//        MethodRepresentation mRep = mainCanvas.getMethodRepresentation(
//                this, visualID, "execute", new Class[]{
//            VisualVectorInterface.class, VisualMatrixInterface.class, VisualVectorInterface.class,
//            String.class, String.class});
//        System.out.println("Method: " + mRep);

        String title = "Dimension value doesn´t fit.";

        //if matrix is null check only the vectors for corresponding dimensions
        if (m != null) {

            //check if a^t length is equal with mod´s row length
            if (!(a.getVisualDimensions()[0] == m.getVisualDimensions()[1])) {

                System.out.println("a.dim[0] == m.dim[1]): " + 
                        (a.getVisualDimensions()[0] == m.getVisualDimensions()[1]));
                System.out.println("a.dim[0]: " + a.getVisualDimensions()[0]);
                System.out.println("m.dim[1]: " + m.getVisualDimensions()[1]);

                Connector c1 = mRep.getParameter(0).getConnector();
                Connector c2 = mRep.getParameter(1).getConnector();

                ErrorMessageWriter.writeErrorMessage(mainCanvas, title,
                        "The dimensions of vector a and matrix m doesn´t fit together.",
                        c1, c2);

                return false;
            }

            //check if mod´s col length is equal with b´s length
            if (!(m.getVisualDimensions()[0] == b.getVisualDimensions()[0])) {

                System.out.println("m.dim[0] == m.dim[0]): " + 
                        (m.getVisualDimensions()[0] == b.getVisualDimensions()[0]));
                System.out.println("m.dim[1]: " + m.getVisualDimensions()[1]);
                System.out.println("b.dim[1]: " + b.getVisualDimensions()[0]);


                Connector c1 = mRep.getParameter(1).getConnector();
                Connector c2 = mRep.getParameter(2).getConnector();

                ErrorMessageWriter.writeErrorMessage(mainCanvas, title,
                        "The dimensions of matrix m and of vector b doesn´t fit together.",
                        c1, c2);

                return false;
            }
        }

        // check if a and b have the same length
        if (!(a.getVisualDimensions()[0] == b.getVisualDimensions()[0])) {

            System.out.println("a.dim[0] == b.dim[0]): " + 
                    (a.getVisualDimensions()[0] == b.getVisualDimensions()[0]));
            System.out.println("a.dim[0]: " + a.getVisualDimensions()[0]);
            System.out.println("b.dim[0]: " + b.getVisualDimensions()[0]);

            Connector c1 = mRep.getParameter(0).getConnector();
            Connector c2 = mRep.getParameter(2).getConnector();

            ErrorMessageWriter.writeErrorMessage(mainCanvas, title,
                    "The dimensions of vector a and vector b doesn´t fit together.",
                    c1, c2);

            return false;
        }

        return true;
    }

    /**
     * DON'T USE ! 
     * USE better directly:
     *      VisualScalar execute(VisualVector a, VisualMatrix m, VisualVector b,
     *                              String mathML, String shortName)
     * {@inheritDoc}
     */
    @Override
    @MethodInfo(hide = true, noGUI=true)
    public VisualElementInterface execute(
            VisualElementInterface a, VisualElementInterface m, VisualElementInterface b,
            String mathML, String shortName) {

        if( (a instanceof VisualVectorInterface) &&
                (m instanceof VisualMatrixInterface)&&
                (b instanceof VisualVectorInterface) ){

            return execute((VisualVectorInterface)a, (VisualMatrixInterface)m,
                    (VisualVectorInterface) b, mathML, shortName);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }
}

