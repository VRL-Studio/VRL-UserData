/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.operations.conception;

import basic.math.elements.Matrix;
import basic.math.elements.Scalar;
import basic.math.elements.Tensor;
import basic.math.elements.Vector;
import basic.math.elements.interfaces.ElementInterface;
import basic.math.elements.interfaces.MatrixInterface;
import basic.math.elements.interfaces.ScalarInterface;
import basic.math.elements.interfaces.TensorInterface;
import basic.math.elements.interfaces.VectorInterface;
import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.mappings.Mapping;

import basic.math.elements.visual.VisualMatrix;
import basic.math.elements.visual.VisualScalar;
import basic.math.elements.visual.VisualTensor;
import basic.math.elements.visual.VisualVector;
import basic.math.helpers.ErrorMessageWriter;
import basic.math.helpers.MathMLAnalyser;
import basic.math.helpers.MathMLShortCuts;
import basic.math.helpers.ParametersOperationSelection;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.visual.Canvas;
import java.io.Serializable;

/**
 * This class allows to perform an addition of as many Elements with the same
 * dimensions and datatype as wanted.
 *
 * @author night
 */
@ObjectInfo(name = "AddOperation")
@ComponentInfo(name = "AddOperation", category = "BasicMath/Concept")
public class AddOperation extends Operation implements Serializable {

    private static final long serialVersionUID = 1;
    private transient VisualCanvas mainCanvas;

    /**
     * Sets the main canvas object.
     * @param mainCanvas the main canvas object
     */
    @MethodInfo(noGUI = true, hide = true, callOptions = "assign-canvas")
    public void setMainCanvas(Canvas mainCanvas) {
        this.mainCanvas = (VisualCanvas) mainCanvas;
    }

    public AddOperation() {
        super();
    }

    /**
     * Adds all elements which are in the parameter/array visualElements.
     *
     * @param mathML the mathML code for the result (if wanted else shortName is used)
     * @param shortName a simple string with the name for the result
     * @param visualElements An array with the elements which should be added
     * @return the result of all added elements
     */
    @MethodInfo(hide = true)
    @Override
    public VisualElementInterface execute(
            @ParamInfo(name = "MathML", nullIsValid = true) String mathML,
            @ParamInfo(name = "Result") String shortName,
            @ParamInfo(style = "array", options = "minArraySize=2") VisualElementInterface... visualElements) {

        VisualElementInterface result = null;
        ElementInterface element = null;

        if (visualElements != null) {

            if (checkDimensions(visualElements)) {

                int[] dims = visualElements[0].getDimensions();
                Class<?> elementClass = visualElements[0].getClass();
                Class<?> dataType = visualElements[0].getDataType();



                if (elementClass.equals(VisualScalar.class)) {

                    element = new Scalar(dataType);

                    result = new VisualScalar(
                            generateMathMLName(visualElements, mathML, shortName),
                            shortName, Boolean.TRUE, (ScalarInterface) element);

                } else if (elementClass.equals(VisualVector.class)) {

                    element = new Vector(dims[0], dataType);

                    result = new VisualVector(
                            generateMathMLName(visualElements, mathML, shortName),
                            shortName, Boolean.TRUE, (VectorInterface) element);

                } else if (elementClass.equals(VisualMatrix.class)) {

                    element = new Matrix(dims[0], dims[1], dataType);

                    result = new VisualMatrix(
                            generateMathMLName(visualElements, mathML, shortName),
                            shortName, Boolean.TRUE, (MatrixInterface) element);

                } else if (elementClass.equals(VisualTensor.class)) {

                    element = new Tensor(dims[0], dims[1], dims[2], dataType);

                    result = new VisualTensor(
                            generateMathMLName(visualElements, mathML, shortName),
                            shortName, Boolean.TRUE, (TensorInterface) element);
                }

                if (dataType.equals(Integer.class)) {
                    element.fillAllEntriesWith(new Integer(0));
                }// 
                else if (dataType.equals(Float.class)) {
                    element.fillAllEntriesWith(new Float(0));
                }// 
                else if (dataType.equals(Long.class)) {
                    element.fillAllEntriesWith(new Long(0));
                }// 
                else //(dataType.equals(Double.class)){
                {
                    // assumption: data is compatibale with Double
                    //assumption dataType is Double default value 0
                    element.fillAllEntriesWith(new Double(0));
                }


                //Assume all involved elements have the same mapping <- NOT USED
                //NOW start mapping init below mapping changed
                Mapping visualMapping = visualElements[0].getVisualMapping();

                //in checkDims() all elements have same length
                int visualLength = visualMapping.getVisualLength();

//                System.out.println(AddOperation.class.getSimpleName() +
//                        " visualElements.length=" + visualElements.length);
//                System.out.println(AddOperation.class.getSimpleName() +
//                        " visualLength = " + visualMapping.getVisualLength());

                for (int i = 0; i < visualElements.length; i++) {
                    VisualElementInterface tmpElement = (VisualElementInterface) visualElements[i];

                    // NOT assumed that involved elements have the same mapping
                    visualMapping = visualElements[i].getVisualMapping();

                    for (int j = 0; j < visualLength; j++) {

                        //element has new element size
                        //tmpElement has different size and need a convertion

                        if (dataType.equals(Double.class)) {
                            element.setEntry(j, (Double) ((Double) element.getEntry(j) +
                                    (Double) tmpElement.getEntry(j)));
                        } else if (dataType.equals(Float.class)) {
                            element.setEntry(j, (Float) ((Float) element.getEntry(j) +
                                    (Float) tmpElement.getEntry(j)));
                        } else if (dataType.equals(Long.class)) {
                            element.setEntry(j, (Long) ((Long) element.getEntry(j) +
                                    (Long) tmpElement.getEntry(j)));
                        } else if (dataType.equals(Integer.class)) {
                            element.setEntry(j, (Integer) ((Integer) element.getEntry(j) +
                                    (Integer) tmpElement.getEntry(j)));
                        }
                    }
                }
            }//end if(check dims)
            else {
                ErrorMessageWriter.writeErrorMessage(mainCanvas,
                        "check dimensions failed",
                        "In " + AddOperation.class.getSimpleName() + " the input parameters have different dimensions.");
            }

        }//end if(vElement null)

        return result;

    }

    /**
     * Combines all elements which are in the parameter/array visualElements that
     * way how it is selected in the operation selection.
     *
     * @param mathML the mathML code for the result (if wanted else shortName is used)
     * @param shortName a simple string with the name for the result
     * @param visualElements An array with the elements which should be used
     * @return the result of calculation
     */
    @MethodInfo(hide = true)
    public VisualElementInterface executeOperation(
            @ParamInfo(name = "MathML", nullIsValid = true) String mathML,
            @ParamInfo(name = "Result") String shortName,
            @ParamInfo(options = "hideConnector=true") ParametersOperationSelection operations,
            @ParamInfo(style = "array", options = "minArraySize=2") VisualElementInterface... visualElements) {

        VisualElementInterface result = null;
        ElementInterface element = null;

        if (visualElements != null) {

            if (checkDimensions(visualElements)) {

                int[] dims = visualElements[0].getDimensions();
                Class<?> elementClass = visualElements[0].getClass();
                Class<?> dataType = visualElements[0].getDataType();



                if (elementClass.equals(VisualScalar.class)) {

                    element = new Scalar(dataType);

                    result = new VisualScalar(
                            generateMathMLNameWithOperationSelection(visualElements, mathML, shortName, operations),
                            shortName, Boolean.TRUE, (ScalarInterface) element);

                } else if (elementClass.equals(VisualVector.class)) {

                    element = new Vector(dims[0], dataType);

                    result = new VisualVector(
                            generateMathMLNameWithOperationSelection(visualElements, mathML, shortName, operations),
                            shortName, Boolean.TRUE, (VectorInterface) element);

                } else if (elementClass.equals(VisualMatrix.class)) {

                    element = new Matrix(dims[0], dims[1], dataType);

                    result = new VisualMatrix(
                            generateMathMLNameWithOperationSelection(visualElements, mathML, shortName, operations),
                            shortName, Boolean.TRUE, (MatrixInterface) element);

                } else if (elementClass.equals(VisualTensor.class)) {

                    element = new Tensor(dims[0], dims[1], dims[2], dataType);

                    result = new VisualTensor(
                            generateMathMLNameWithOperationSelection(visualElements, mathML, shortName, operations),
                            shortName, Boolean.TRUE, (TensorInterface) element);
                }

                if (dataType.equals(Integer.class)) {
                    element.fillAllEntriesWith(new Integer(0));
                }//
                else if (dataType.equals(Float.class)) {
                    element.fillAllEntriesWith(new Float(0));
                }//
                else if (dataType.equals(Long.class)) {
                    element.fillAllEntriesWith(new Long(0));
                }//
                else //(dataType.equals(Double.class)){
                {
                    // assumption: data is compatibale with Double
                    //assumption dataType is Double default value 0
                    element.fillAllEntriesWith(new Double(0));
                }

                //Assume all involved elements have the same mapping <- NOT USED
                //NOW start mapping init below mapping changed
                Mapping visualMapping = visualElements[0].getVisualMapping();

                //in checkDims() all elements have same length
                int visualLength = visualMapping.getVisualLength();

                System.out.println(AddOperation.class.getSimpleName() +
                        " visualElements.length=" + visualElements.length);
                System.out.println(AddOperation.class.getSimpleName() +
                        " visualLength = " + visualMapping.getVisualLength());

                for (int i = 0; i < visualElements.length; i++) {
                    VisualElementInterface tmpElement = (VisualElementInterface) visualElements[i];

                    // NOT assumed that involved elements have the same mapping
//                    visualMapping = visualElements[i].getVisualMapping();

                    if (operations.getSelectedObject().equals("+")) {

                        addToFirst(element, tmpElement, visualLength, dataType);

                    } else if (operations.getSelectedObject().equals("-")) {

                        //element is init with 0´s, there in first step copy value
                        //of first tmpElement into the (result) element
                        if (i == 0) {
                            for (int j = 0; j < visualLength; j++) {
                                element.setEntry(i, tmpElement.getEntry(i));
                            }
                        } else {
                            subFromFirst(element, tmpElement, visualLength, dataType);
                        }

                    }

                }
            }//end if(check dims)
            else {
                ErrorMessageWriter.writeErrorMessage(mainCanvas,
                        "check dimensions failed",
                        "In " + AddOperation.class.getSimpleName() + " the input parameters have different dimensions.");
            }

        }//end if(vElement null)

        return result;

    }

    /**
     * Adds all Double containing Vectors which are in the parameter/array visualElements.
     * This method is specialized test version of execute(....) .
     *
     * @param mathML the mathML code for the result (if wanted else shortName is used)
     * @param shortName a simple string with the name for the result
     * @param visualElements An array with the vectors which should be added
     * @return the result of all added vectors
     */
    @MethodInfo(hide = true)
    public VisualVectorInterface executeVector(
            @ParamInfo(name = "MathML", nullIsValid = true) String mathML,
            @ParamInfo(name = "Result") String shortName,
            @ParamInfo(style = "array", options = "minArraySize=2") VisualVectorInterface... visualElements) {

        VisualVector result = null;
        Vector element = null;

        if (visualElements != null) {

            if (checkDimensions(visualElements)) {

                int[] dims = visualElements[0].getDimensions();
                Class<?> dataType = visualElements[0].getDataType();

                element = new Vector(dims[0], dataType);

                if (dataType.equals(Integer.class)) {
                    element.fillAllEntriesWith(new Integer(0));
                }// 
                else if (dataType.equals(Float.class)) {
                    element.fillAllEntriesWith(new Float(0));
                }// 
                else if (dataType.equals(Long.class)) {
                    element.fillAllEntriesWith(new Long(0));
                }// 
                else //(dataType.equals(Double.class)){
                {
                    // assumption: data is compatibale with Double
                    //assumption dataType is Double default value 0
                    element.fillAllEntriesWith(new Double(0));
                }

                result = new VisualVector(
                        generateMathMLName(visualElements, mathML, shortName),
                        shortName, Boolean.TRUE, element);

                //Assume all involved elements have the same mapping <- NOT USED
                //NOW start mapping init below mapping changed
                Mapping visualMapping = visualElements[0].getVisualMapping();

                //in checkDims() all elements have same length
                int visualLength = visualMapping.getVisualLength();

//                System.out.println(AddOperation.class.getSimpleName() +
//                        " visualElements.length=" + visualElements.length);
//                System.out.println(AddOperation.class.getSimpleName() +
//                        " visualLength = " + visualMapping.getVisualLength());

                for (int i = 0; i < visualElements.length; i++) {
                    VisualElementInterface tmpElement = (VisualElementInterface) visualElements[i];

                    // NOT assumed that involved elements have the same mapping
                    visualMapping = visualElements[i].getVisualMapping();

                    for (int j = 0; j < visualLength; j++) {

                        //element has new element size
                        //tmpElement has diffrent size and need a convertion

                        if (dataType.equals(Double.class)) {
                            element.setEntry(j, (Double) ((Double) element.getEntry(j) +
                                    (Double) tmpElement.getEntry(j)));
                        } else if (dataType.equals(Float.class)) {
                            element.setEntry(j, (Float) ((Float) element.getEntry(j) +
                                    (Float) tmpElement.getEntry(j)));
                        } else if (dataType.equals(Long.class)) {
                            element.setEntry(j, (Long) ((Long) element.getEntry(j) +
                                    (Long) tmpElement.getEntry(j)));
                        } else if (dataType.equals(Integer.class)) {
                            element.setEntry(j, (Integer) ((Integer) element.getEntry(j) +
                                    (Integer) tmpElement.getEntry(j)));
                        }
                    }
                }
            }//end if(check dims)
            else {
                ErrorMessageWriter.writeErrorMessage(mainCanvas,
                        "check dimensions failed",
                        "In " + AddOperation.class.getSimpleName() + " the input parameters have different dimensions.");
            }

        }//end if(vElement null)

        return result;

    }

    /**
     * checks the dimensions of involved elements
     * 
     * @param visualElements the elements to be checked
     * @return true if all elements have the same visual dimensions
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    protected Boolean checkDimensions(VisualElementInterface... visualElements) {
        Boolean result = Boolean.TRUE;
        Class<?> dataType = visualElements[0].getDataType();

        for (int i = 0; i < visualElements.length - 1; i++) {

            VisualElementInterface vElem1 = visualElements[i];
            VisualElementInterface vElem2 = visualElements[i + 1];

            if (!(dataType.isAssignableFrom(vElem2.getDataType()))) {

                System.out.println(AddOperation.class + " visualElements[" + i + "].getDataType() = " + visualElements[i].getDataType());
                System.out.println(AddOperation.class + " visualElements[" + (i + 1) + "].getDataType() = " + visualElements[i + 1].getDataType());
                throw new IllegalArgumentException("All visualElements need to have the compatibale data " + visualElements[i].getDataType());
            }

            for (int j = 0; j < vElem1.getVisualDimensions().length; j++) {
                if (vElem2.getVisualDimensions()[j] != vElem1.getVisualDimensions()[j]) {

                    System.out.println(AddOperation.class.getSimpleName() +
                            " vElem1.getVisualDimensions()[" + j + "]=" +
                            vElem1.getVisualDimensions()[j]);
                    System.out.println(AddOperation.class.getSimpleName() +
                            " vElem2.getVisualDimensions()[" + j + "]=" +
                            vElem2.getVisualDimensions()[j]);

                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * Generates the result name depending on the names of the involved elements
     * and if there is a short name or a mathML name for the result which should
     * be used.
     * @param visualElements is an array of visualElements
     * @param mathML the mathML code which should be used for the result name if
     *          not null
     * @param shortName  the short name for the result if not null
     * @return the generate result operationSymbol
     */
    @MethodInfo(hide = true, noGUI = true)
    private String generateMathMLName(VisualElementInterface[] visualElements, String mathML, String shortName) {
        String result = null;

//        if (mathML.equals("")) {
//
//            if (shortName.equals("")) {
//
//                for (int i = 0; i < visualElements.length - 1; i++) {
//                    result += visualElements[i].getXmlCoding() + "<ci>+</ci>";
//                }
//
//                result += visualElements[visualElements.length - 1].getXmlCoding();
//
//            } else {
//                result = MathMLShortCuts.textBegin + shortName + MathMLShortCuts.textEnd;
//            }
//        } else {
//            result = mathML;
//        }
        if (mathML.equals("")) {

            if (shortName.equals("")) {

                for (int i = 0; i < visualElements.length - 1; i++) {
                    result += MathMLAnalyser.setBrackets(visualElements[i].getXmlCoding()) +
                            "<ci>+</ci>";
                }

                result += MathMLAnalyser.setBrackets( visualElements[visualElements.length - 1].getXmlCoding() );

            } else {
                result = MathMLShortCuts.textBegin + shortName + MathMLShortCuts.textEnd;
            }
        } else {
            result = mathML;
        }

        return result;
    }

    /**
     * Generates the result name depending on the names of the involved elements
     * and if there is a short name or a mathML name for the result which should
     * be used.
     * @param visualElements is an array of visualElements
     * @param mathML the mathML code which should be used for the result name if
     *          not null
     * @param shortName  the short name for the result if not null
     * @return the generate result operationSymbol
     */
    @MethodInfo(hide = true, noGUI = true)
    private String generateMathMLNameWithOperationSelection(VisualElementInterface[] visualElements,
            String mathML, String shortName, ParametersOperationSelection operations) {

        String result = null;

        if (mathML.equals("")) {

            if (shortName.equals("")) {

                for (int i = 0; i < visualElements.length - 1; i++) {
                    result += MathMLAnalyser.setBrackets( visualElements[i].getXmlCoding() )+
                            "<ci>" + operations.getSelectedObject() + "</ci>";
                }

                result += MathMLAnalyser.setBrackets(visualElements[visualElements.length - 1].getXmlCoding());

            } else {
                result = MathMLShortCuts.textBegin + shortName + MathMLShortCuts.textEnd;
            }
        } else {
            result = mathML;
        }

        return result;
    }

    /**
     * Helper function wich adds the values of the second element to the
     * values of the first element
     *
     * @param element the element to which values will be added
     * @param tmpElement the element which values will be added to first element
     * @param visualLength the length of the involved elements
     * @param dataType the type of the involved data/values e.g. Double
     */
    @MethodInfo(hide = true)
    public void addToFirst(ElementInterface element, VisualElementInterface tmpElement,
            int visualLength, Class<?> dataType) {

        for (int j = 0; j < visualLength; j++) {

            if (dataType.equals(Double.class)) {
                element.setEntry(j, (Double) ((Double) element.getEntry(j) +
                        (Double) tmpElement.getEntry(j)));

            } else if (dataType.equals(Float.class)) {
                element.setEntry(j, (Float) ((Float) element.getEntry(j) +
                        (Float) tmpElement.getEntry(j)));

            } else if (dataType.equals(Long.class)) {
                element.setEntry(j, (Long) ((Long) element.getEntry(j) +
                        (Long) tmpElement.getEntry(j)));

            } else if (dataType.equals(Integer.class)) {
                element.setEntry(j, (Integer) ((Integer) element.getEntry(j) +
                        (Integer) tmpElement.getEntry(j)));
            }
        }//for visualLength
    }

    /**
     * Helper function wich subtracts the values of the second element from the
     * values of the first element
     *
     * @param element the element from which values will be subtracted
     * @param tmpElement the element which values will be subtracted to first element
     * @param visualLength the length of the involved elements
     * @param dataType the type of the involved data/values e.g. Double
     */
    @MethodInfo(hide = true)
    public void subFromFirst(ElementInterface element, VisualElementInterface tmpElement,
            int visualLength, Class<?> dataType) {

        System.err.println("subFirst()");

        for (int j = 0; j < visualLength; j++) {

            if (dataType.equals(Double.class)) {
                element.setEntry(j, (Double) ((Double) element.getEntry(j) -
                        (Double) tmpElement.getEntry(j)));

                System.err.println("Double");
                System.out.println("element.get(" + j + ")" + element.getEntry(j));
                System.out.println("tmpElement.get(" + j + ")" + tmpElement.getEntry(j));

            } else if (dataType.equals(Float.class)) {
                element.setEntry(j, (Float) ((Float) element.getEntry(j) -
                        (Float) tmpElement.getEntry(j)));
                System.err.println("Float");

            } else if (dataType.equals(Long.class)) {
                element.setEntry(j, (Long) ((Long) element.getEntry(j) -
                        (Long) tmpElement.getEntry(j)));
                System.err.println("Long");

            } else if (dataType.equals(Integer.class)) {
                element.setEntry(j, (Integer) ((Integer) element.getEntry(j) -
                        (Integer) tmpElement.getEntry(j)));
                System.err.println("Integer");
            }
        }//for visualLength

    }
}
