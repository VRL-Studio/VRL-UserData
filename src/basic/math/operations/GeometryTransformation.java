/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations;

import basic.math.elements.Vector;
import basic.math.elements.Vector.Doubles;
import basic.math.elements.interfaces.VectorInterface;
import basic.math.elements.interfaces.VisualMatrixInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.visual.VisualVector;
import basic.math.helpers.ErrorMessageWriter;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.DefaultMethodRepresentation;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.MethodRequest;
import eu.mihosoft.vrl.v3d.Triangle;
import eu.mihosoft.vrl.v3d.TxT2Geometry;
import eu.mihosoft.vrl.v3d.VGeometry3D;
import eu.mihosoft.vrl.v3d.VTriangleArray;
import eu.mihosoft.vrl.visual.Canvas;
import eu.mihosoft.vrl.visual.Connector;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.vecmath.Point3f;

/**
 * This class loads a 3D geometry from file and calculates depended on the used
 * 3x3 matrix the linear transformt geometry which is shown as result.
 *
 * @author night
 */
@ObjectInfo(name = "GeometryTransformation")
@ComponentInfo(name = "GeometryTransformation", category = "BasicMath/Operations")
public class GeometryTransformation implements Serializable {

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
     * Load a 3D geometry from file and calculate the linear transformt geometry
     * depended on the used 3x3 matrix.
     *
     * @param methodRequest
     * @param f the file with the start geometry
     * @param m the matrix which contains the linear transformation
     * @return the transfomed geometry
     *
     * @throws IOException if file couldn´t be load
     */
    public VGeometry3D manipulate(
            MethodRequest methodRequest,
            @ParamInfo(style = "load-dialog") File f,
            VisualMatrixInterface m) throws IOException {

        if (checkDimensions(methodRequest, m)) {
            TxT2Geometry txt = new TxT2Geometry();
            VTriangleArray array = txt.loadAsVTriangleArray(f);

            for (Triangle triangle : array) {

                VectorInterface vec1element = new Vector.Doubles(3);
                VisualVectorInterface vec1 = new VisualVector("<mtext></mtext>",
                        "vec1", true, vec1element);
                Point3f p1 = triangle.getNodeOne().getLocation();

                vec1element.setEntry(0, new Double(p1.x));
                vec1element.setEntry(1, new Double(p1.y));
                vec1element.setEntry(2, new Double(p1.z));

                VectorInterface vec2element = new Vector.Doubles(3);
                VisualVectorInterface vec2 = new VisualVector("<mtext></mtext>",
                        "vec2", true, vec2element);
                Point3f p2 = triangle.getNodeTwo().getLocation();

                vec2element.setEntry(0, new Double(p2.x));
                vec2element.setEntry(1, new Double(p2.y));
                vec2element.setEntry(2, new Double(p2.z));


                VectorInterface vec3element = new Vector.Doubles(3);
                VisualVectorInterface vec3 = new VisualVector("<mtext></mtext>",
                        "vec3", true, vec3element);
                Point3f p3 = triangle.getNodeThree().getLocation();

                vec3element.setEntry(0, new Double(p3.x));
                vec3element.setEntry(1, new Double(p3.y));
                vec3element.setEntry(2, new Double(p3.z));


                MatrixVectorMultiplication mult = new MatrixVectorMultiplication();

                Vector.Doubles result1 =
                        (Doubles) mult.execute(m, vec1, "v", "v").getElement();
                Vector.Doubles result2 =
                        (Doubles) mult.execute(m, vec2, "v", "v").getElement();
                Vector.Doubles result3 =
                        (Doubles) mult.execute(m, vec3, "v", "v").getElement();

                float x = ((Double) result1.getEntry(0)).floatValue();
                float y = ((Double) result1.getEntry(1)).floatValue();
                float z = ((Double) result1.getEntry(2)).floatValue();

                triangle.getNodeOne().setLocation(new Point3f(x, y, z));

                x = ((Double) result2.getEntry(0)).floatValue();
                y = ((Double) result2.getEntry(1)).floatValue();
                z = ((Double) result2.getEntry(2)).floatValue();

                triangle.getNodeTwo().setLocation(new Point3f(x, y, z));

                x = ((Double) result3.getEntry(0)).floatValue();
                y = ((Double) result3.getEntry(1)).floatValue();
                z = ((Double) result3.getEntry(2)).floatValue();

                triangle.getNodeThree().setLocation(new Point3f(x, y, z));
            }

            return new VGeometry3D(array);
        }//end if( checkDim() )

        return null;
    }

    /**
     * Checks if the used matrix is a 3x3 matrix and send a message at the
     * message box if the matrix doesn´t fit.
     *
     * @param methodRequest
     * @param m the to be checked matrix
     * @return true if m is a 3x3 matrix else false
     */
    private boolean checkDimensions(MethodRequest methodRequest, 
            VisualMatrixInterface m) {

        Boolean result = true;

        DefaultMethodRepresentation mRep = methodRequest.getMethod();
//                mainCanvas.getMethodRepresentation(
//                this, visualID, "manipulate", new Class[]{
//                    File.class, VisualMatrixInterface.class});

        String title = "Dimension value doesn´t fit.";

        //check if m is 3x3 matrix
        if ((m.getVisualDimensions()[0] != 3) || (m.getVisualDimensions()[1] != 3)) {

            System.out.println("m.dim[0]: " + m.getVisualDimensions()[0]);
            System.out.println("m.dim[1]: " + m.getVisualDimensions()[1]);

            Connector c2 = mRep.getParameter(1).getConnector();

            ErrorMessageWriter.writeErrorMessage(mainCanvas, title,
                    "The dimensions of matrix m doesn´t fit. Need to be 3x3.", c2);

            return false;
        }

        return result;
    }
}
