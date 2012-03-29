/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.visual;

import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.elements.interfaces.VisualMatrixInterface;
import basic.math.elements.interfaces.VisualTensorInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.Vector;
import basic.math.elements.interfaces.ElementInterface;
import basic.math.elements.interfaces.MatrixInterface;
import basic.math.elements.interfaces.TensorInterface;
import basic.math.elements.interfaces.VectorInterface;
import basic.math.elements.mappings.Mapping;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;

/**<p>
 * VisualVector contains additional information of a Vector.
 * A MathML description which is used for output name.
 * A short name which is used int the title of an InputWindow.
 * A boolean which contains the information if the Vector is clickable and
 * an InputWindow is shown if it is clicked.
 * </p>
 * @author Night
 */
@ObjectInfo(name="VisualVector")
@ComponentInfo(name = "VisualVector", category = "BasicMath/VisualElement")
public class VisualVector extends VisualElement implements  VisualVectorInterface {

    private static final long serialVersionUID = 1L;

    /**
     * Needed for XML- -Encoder / -Decoder
     * DO NOT USE IT TO GET AN ELEMENT !!!
     * IT´S DO NOTHING !!!
     */
    public VisualVector() {
        super();
    }


    /**
     * Generates a new VisualVector depending on the parameters.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "v" or "Vec" (no xml)
     * @param clickable true, if the tensor should be able to show its data values. False else.
     * @param row number of rows
     * @param dataType ths class object of the stored data entries
     */
    public VisualVector(String xmlCoding, String shortName, Boolean clickable,
            Integer row, Class<?> dataType) {
        super(xmlCoding, shortName, clickable, new int[]{row}, dataType);
    }

    /**
     * Combine an existing data elemnent with this new visual element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "v" or "Vec" (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param element that is visualized/representat by this VisualVector
     */
    public VisualVector(String xmlCoding, String shortName, boolean clickable, VectorInterface element) {
        super(xmlCoding, shortName, clickable, element);
    }

    /**
     * Combine an existing data elemnent with this new visual element
     * and setting the visual mapping that should be used on the data element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "v" or "Vec" (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param element that is visualized/representat by this VisualVector
     * @param visualMapping is an extra mapping that can be used for things like returning
     *          just the diagonal entries of a matrix on the same data element
     */
    public VisualVector(String xmlCoding, String shortName, boolean clickable, ElementInterface element, Mapping visualMapping) {
        super(xmlCoding, shortName, clickable, element, visualMapping);
    }

    /**
     * {@inheritDoc}
     *
     * @return new Vector (data element)
     */
    @MethodInfo(hide = true)
    @Override
    public ElementInterface getNewElementInstance(int[] dimensions, Class<?> dataType) {
        if (dimensions.length == 1) {
            return new Vector(dimensions[0], dataType);
        } else {
            throw new IllegalArgumentException("dimensions is " + dimensions.length + " long." +
                    " Need to be 1");
        }
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(Integer row) {
        return super.getEntry(new int[]{row});
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Integer row, Object value) {
        super.setEntry(new int[]{row}, value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Object getEntry(Integer row, Integer col) {
        return super.getEntry(new int[]{row});
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setEntry(Integer row, Integer col, Object value) {
        super.setEntry(new int[]{row}, value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Object getEntry(Integer row, Integer col, Integer deep) {
        return super.getEntry(new int[]{row});
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setEntry(Integer row, Integer col, Integer deep, Object value) {
        super.setEntry(new int[]{row}, value);
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
    @MethodInfo(hide = true,valueStyle="array")
    @Override
    public int[] getDimensions() {
        return super.getDimensions();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public VectorInterface getVector() {
        return (VectorInterface) element;
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
    @MethodInfo(hide = true, name="getElement")
    @Override
    public VisualElementInterface getReferenceAsVisualElement() {
        return super.getReferenceAsVisualElement();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, name="getTensor")
    @Override
    public VisualTensorInterface getReferenceAsVisualTensor() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, name="getMatrix")
    @Override
    public VisualMatrixInterface getReferenceAsVisualMatrix() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = false, callOptions = "autoinvoke", name="getVector")
    @Override
    public VisualVectorInterface getReferenceAsVisualVector() {
        return this;
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
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI=true)
    @Override
    public void setDataType(Class<?> dataType) {
        element.setDataType(dataType);
    }

}
