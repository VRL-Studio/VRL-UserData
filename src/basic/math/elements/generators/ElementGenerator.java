/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.generators;

import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.elements.visual.VisualElement;
import basic.math.helpers.ClassSelection;
import basic.math.helpers.ErrorMessageWriter;
import basic.math.helpers.MathMLShortCuts;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.DefaultMethodRepresentation;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.types.CanvasRequest;
import eu.mihosoft.vrl.types.MethodRequest;
import eu.mihosoft.vrl.visual.Connector;
import eu.mihosoft.vrl.visual.VSwingUtil;
import java.io.Serializable;

/**
 * This class is a blue print for generating an VisualElement
 * and adding it to canvas if all parameters are correct, which are needed for
 * creating a new VisualElement.
 *
 * @author night
 */
@ObjectInfo(name = "Element Generater")
public abstract class ElementGenerator implements Serializable {

    private static final long serialVersionUID = 1;
    /**
     * The canvas to which all visual elements will be added;
     */
    protected transient VisualCanvas mainCanvas;

    /**
     * Checks if each dimension size is at least ONE and returns TRUE if so.
     * If one dimensions is smaller than 1, FALSE will be returned.
     *
     * A 1 x 1 x 1 Element is a Scalar. 
     * A m x 1 x 1 Element is a Vector.
     * A m x n x 1 Element is a Matrix.
     * A m x n x p Element is a Tensor.
     *
     * @param dimensions array which contains the dimension sizes.
     *          dimensions.length says how many dimensions there are.
     * @return TRUE if all sizes are bigger than zero, FALSE else.
     */
    protected Boolean checkDimensionSizes(MethodRequest methodRequest, Integer[] dimensions) {

        Boolean result = Boolean.TRUE;

        DefaultMethodRepresentation mRep = methodRequest.getMethod();
//                mainCanvas.getMethodRepresentation(this, visualID, "generateElement",
//                new Class[]{Integer[].class, String.class, String.class, VisualCanvas.class});

        String message = " Dimensions must be at least 1 long and "
                + "the entry needs to be at least 1.\n";
        String title = "Dimension value is null or smaller than 1.";

        if (dimensions == null) {
            Connector c = mRep.getParameter(0).getConnector();
            ErrorMessageWriter.writeErrorMessage(mainCanvas, title,
                    message + "dims is null", c);
            result = Boolean.FALSE;
        } else {
            for (int i = 0; i < dimensions.length; i++) {

                if (0 >= dimensions[i]) {
                    Connector c = mRep.getParameter(0).getConnector();
                    ErrorMessageWriter.writeErrorMessage(mainCanvas, title,
                            message + "dims[" + i + "] is smaller than l", c);
                    result = Boolean.FALSE;
                }
            }
        }

        return result;
    }

    /**
     * Adds a Element to the main canvas.
     *
     * @param visualElement  the ElementAddOn which contains the element
     * which should be added
     */
//    @MethodInfo(valueStyle = "canvas", valueOptions = "hideConnector=true", valueName = " ")
    protected void assignVisualElementToCanvas(final VisualElementInterface visualElement) {
        VSwingUtil.invokeLater(new Runnable() {

            @Override
            public void run() {
                mainCanvas.addObject(visualElement);
//        return visualElement;
            }
        });

    }

    /**
     * Call this method if you want to generate a new VisualElement and
     * adding it to canvas.
     *
     * This method should be the only visual representation of ElementGenerator
     * on the canvas.
     *
     * The inputs of this method were checked for plausibillity as far as possible.
     * See docu of checkDimensionSizes().
     *
     * @param methodRequest 
     * @param dimensions dim[i-1] the size of the i-th dimension
     * @param dataType is a selection/list of possible data types which could be
     * stored. By selecting one component of the list the generated element can
     * only store data of this kind. Default is Double.
     * @param mathML contains the MathML code for the NAme of the generated element
     * @param shortName a short String name like "elem" for Element  (no xml)
     * @param canvas to which the generated element should be added
     */
    @MethodInfo(name = "create")
    public void generateElement(
            MethodRequest methodRequest,
            @ParamInfo(name = "dimensions", style = "array", options = "hideConnector=true") Integer[] dimensions,
            ClassSelection dataType,
            @ParamInfo(name = "mathML", nullIsValid = true) String mathML,
            @ParamInfo(name = "name") String shortName,
            //            @ParamInfo(name = " ", style = "current") VisualCanvas canvas
            CanvasRequest canvasRequest) {

        mainCanvas = canvasRequest.getCanvas();

        Class<?> clazz = null;
        VisualElementInterface visualElement = null;

        if (checkDimensionSizes(methodRequest, dimensions)) {

            mathML = checkMathML(mathML, shortName);
            clazz = dataType.getSelectedClass();

            visualElement = getNewVisualElementInstance(methodRequest,
                    dimensions, clazz, mathML, shortName, true);
            assignVisualElementToCanvas(visualElement);
        }

        //Default value that is fill for all entries is 0 (ZERO) if the stored
        // data is compatible to a number format
        {
//            System.err.println("------- Used Class for data: " + clazz);
//            System.err.println(" ->  ->  -> DEFAULT VALUE IS SET in" + VisualElement.class + " for <-  <-  <- ");

            if (clazz.equals(Double.class)) {
                visualElement.fillAllEntriesWith(new Double(0));
//                System.err.println("Double");

            } else if (clazz.equals(Float.class)) {
                visualElement.fillAllEntriesWith(new Float(0));
//                System.err.println("Float");

            } else if (clazz.equals(Long.class)) {
                visualElement.fillAllEntriesWith(new Long(0));
//                System.err.println("Long");

            } else if (clazz.equals(Integer.class)) {
                visualElement.fillAllEntriesWith(new Integer(0));
//                System.err.println("Integer");

                //all data in java is an object so be carefore with code the below
                //this code could stand above and be overridden of a more concret data typ
//            } else if (clazz.isAssignableFrom(Object.class)) {
////                System.err.println("Object");
            }
        }// END DEFAULT SET
    }

    /**
     * This method generates in each specialization the depending VisualElement.
     * This means a TensorGenarator returns a new instance of VisualTensor and
     * a ScalarGenerator a new instance of VisualScalar.
     *
     * @param methodRequest 
     * @param dimensions of the new element
     * @param dataType is the type of data which is stored in the element
     * @param mathML is the code for the name of the element in MathML
     * @param shortName just a string containing the name like "vec" for a vector
     * @param clickable decides if an InputWindow appears and the entries can be
     * modiefied if the user clicks on the element name
     * @return based on the params the new created element
     */
    protected abstract VisualElement getNewVisualElementInstance(
            MethodRequest methodRequest,
            Integer[] dimensions, Class<?> dataType,
            String mathML, String shortName, Boolean clickable);

    /**
     * Checks if mathML is null and
     * removes white spaces from begin and end of mathML String
     * and check if there is something left if not
     * there were only unnecessary white space and a mathML code is created
     * on base of the shortName
     *
     * @param mathML the to check MathML string
     * @param shortName is non MathML code contain name for a result/element
     * @return the checked and corrected mathML string
     */
    protected String checkMathML(String mathML, String shortName) {
        if (mathML == null) {
            mathML = "";
        }

        if (mathML.trim().equals("")) {
            mathML =  MathMLShortCuts.textBegin 
                    + shortName 
                    + MathMLShortCuts.textEnd;
            // MathMLShortCuts.mathBegin + ... + MathMLShortCuts.mathEnd not necessary
            // because it is added surround the variable <code> mathML </code> in
            // MathMLType.setViewValue()
            
//            System.out.println(this.getClass().getName()+ " checkMathML() " + mathML );
        }

        return mathML;
    }
}
// //  CODE SNIPPED
//    /**
//     * Sets the main canvas object.
//     * Each Generator needs an implementations with the corresponding MethodInfo,
//     * because of only then the Generators will get an reference on the used canvas.
//     *
//     * This method is called from VRL automatically if a Generator is
//     * added to the mainCanvas.
//     *
//     * The implementation should look like this:
//     * <code>
//     * @MethodInfo(noGUI = true, callOptions = "assign-canvas")
//     * public abstract void setMainCanvas(Canvas mainCanvas)
//     * {     this.mainCanvas = (VisualCanvas) mainCanvas;    }
//     * </code>
//     *
//     * @param mainCanvas the main canvas object
//     */
//    @MethodInfo(noGUI = true, callOptions = "assign-canvas")
//    public abstract void setMainCanvas(Canvas mainCanvas);
////    {     this.mainCanvas = (VisualCanvas) mainCanvas;    }