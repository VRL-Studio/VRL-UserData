/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.visual;

import basic.math.elements.mappings.DiagonalMapping;
import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.elements.interfaces.VisualMatrixInterface;
import basic.math.elements.Matrix;
import basic.math.elements.interfaces.ElementInterface;
import basic.math.elements.interfaces.MatrixInterface;
import basic.math.elements.interfaces.TensorInterface;
import basic.math.elements.interfaces.VisualTensorInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.mappings.Mapping;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;

/**<p>
 * VisualMatrix contains additional information of a Matrix.
 * A MathML description which is used for output name.
 * A short name which is used int the title of an InputWindow.
 * A boolean which contains the information if the Matrix is clickable and
 * an InputWindow is shown if it is clicked.
 * </p>
 * @author Night
 */
@ObjectInfo(name = "VisualMatrix")
//@ComponentInfo(name = "VisualMatrix", category = "BasicMath")
public class VisualMatrix extends VisualElement implements VisualMatrixInterface {

    private static final long serialVersionUID = 1;

    /**
     * Needed for XML- -Encoder / -Decoder
     * DO NOT USE IT TO GET AN ELEMENT !!!
     * IT´S DO NOTHING !!!
     */
    public VisualMatrix() {
        super();
    }


    /**
     * Generates a new VisualMatrix depending on the parameters.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "M" or "Mat" (no xml)
     * @param clickable true, if the tensor should be able to show its data values. False else.
     * @param row number of rows
     * @param col number of columns
     * @param dataType ths class object of the stored data entries
     */
    public VisualMatrix(String xmlCoding, String shortName, Boolean clickable,
            Integer row, Integer col, Class<?> dataType) {
        super(xmlCoding, shortName, clickable, new int[]{row, col}, dataType);
    }

    /**
     * Combine an existing data elemnent with this new visual element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "M" or "Mat" (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param element that is visualized/representat by this VisualMatrix
     */
    public VisualMatrix(String xmlCoding, String shortName, boolean clickable, MatrixInterface element) {
        super(xmlCoding, shortName, clickable, element);
    }

    /**
     * Combine an existing data elemnent with this new visual element
     * and setting the visual mapping that should be used on the data element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "M" or "Mat" (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param element that is visualized/representat by this VisualMatrix
     * @param visualMapping is an extra mapping that can be used for things like returning
     *          just the diagonal entries of a matrix on the same data element
     */
    public VisualMatrix(String xmlCoding, String shortName, boolean clickable, ElementInterface element, Mapping visualMapping) {
        super(xmlCoding, shortName, clickable, element, visualMapping);
    }

    /**
     * {@inheritDoc}
     *
     * @return new Matrix (data element)
     */
    @MethodInfo(hide = true)
    @Override
    public ElementInterface getNewElementInstance(int[] dimensions, Class<?> dataType) {
        if (dimensions.length == 2) {
            return new Matrix(dimensions[0], dimensions[1], dataType);
        } else {
            throw new IllegalArgumentException("dimensions is " + dimensions.length + " long." +
                    " Need to be 2");
        }
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(Integer row, Integer col) {
        return super.getEntry(new int[]{row, col});
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Integer row, Integer col, Object value) {
        super.setEntry(new int[]{row, col}, value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Object getEntry(Integer row, Integer col, Integer deep) {
        return super.getEntry(new int[]{row, col});
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setEntry(Integer row, Integer col, Integer deep, Object value) {
        super.setEntry(new int[]{row, col}, value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Integer getArrayLength() {
        return super.getArrayLength();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, valueStyle = "array")
    @Override
    public int[] getDimensions() {
        return super.getDimensions();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public MatrixInterface getMatrix() {
        return (MatrixInterface) element;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public TensorInterface getTensor() {
        return (TensorInterface) element;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(callOptions = "autoinvoke", name="getMatrix")
    @Override
    public VisualMatrixInterface getReferenceAsVisualMatrix() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, name="getTensor")
    @Override
    public VisualTensorInterface getReferenceAsVisualTensor() {
        return (VisualTensorInterface) this;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, name="getElement")
    @Override
    public VisualElementInterface getReferenceAsVisualElement() {
        return super.getReferenceAsVisualElement();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Mapping createDefaultMapping() {
        return element.createDefaultMapping();
    }

    /**
     * Notice that the returned diag vector has direct access to the data of
     * the matrix. Changing values in the diag vector means to change the
     * diagonal entries of the matrix.
     *
     * Diagonal vector and matrix are sharing the same data element!
     *
     * @return the diagonal of the matrix.
     */
    @MethodInfo(hide = true)
    public VisualVectorInterface getDiagonal() {
        
        DiagonalMapping diagMapping = new DiagonalMapping(getDimensions());

        VisualVectorInterface result = new VisualVector(
                getXmlCoding() + "<mtext>_diag</mtext>",
                getShortName() + "_diag", true,
                element, diagMapping);

        result.setVisualDimensions(new int[]{diagMapping.getVisualLength()});

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI=true)
    @Override
    public void setDataType(Class<?> dataType) {
        element.setDataType(dataType);
    }
}
