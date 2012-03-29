/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.visual;

import basic.math.elements.interfaces.VisualMatrixInterface;
import basic.math.elements.interfaces.VisualScalarInterface;
import basic.math.elements.Scalar;
import basic.math.elements.interfaces.ElementInterface;
import basic.math.elements.interfaces.MatrixInterface;
import basic.math.elements.interfaces.ScalarInterface;
import basic.math.elements.interfaces.TensorInterface;
import basic.math.elements.interfaces.VectorInterface;
import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.elements.interfaces.VisualTensorInterface;
import basic.math.elements.interfaces.VisualVectorInterface;
import basic.math.elements.mappings.Mapping;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;

/**<p>
 * VisualScalar contains additional information of a Scalar.
 * A MathML description which is used for output name.
 * A short name which is used int the title of an InputWindow.
 * A boolean which contains the information if the Scalar is clickable and
 * an InputWindow is shown if it is clicked.
 * </p>
 * @author Night
 */
@ObjectInfo(name = "VisualScalar")
//@ComponentInfo(name = "VisualScalar", category = "BasicMath")
public class VisualScalar extends VisualElement implements VisualScalarInterface {

    private static final long serialVersionUID = 1;

    /**
     * Needed for XML- -Encoder / -Decoder
     * DO NOT USE IT TO GET AN ELEMENT !!!
     * IT´S DO NOTHING !!!
     */
    public VisualScalar() {
        super();
    }

    /**
     * Generates a new VisualScalar depending on the parameters.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "s" or "sca" (no xml)
     * @param clickable true, if the tensor should be able to show its data values. False else.
     * @param dataType ths class object of the stored data entries
     */
    public VisualScalar(String xmlCoding, String shortName, Boolean clickable, Class<?> dataType) {

        super(xmlCoding, shortName, clickable, new int[]{1}, dataType);
    }

    /**
     * Combine an existing data elemnent with this new visual element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "s" or "sca" (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param element that is visualized/representat by this VisualMatrix
     */
    public VisualScalar(String xmlCoding, String shortName, boolean clickable, ScalarInterface element) {
        super(xmlCoding, shortName, clickable, element);
    }

    /**
     * DON T USE this constructore. Use instead
     * VisualScalar(String xmlCoding, String shortName, boolean clickable, ScalarInterface element)
     * which have the same affect.
     * This constructor is just for logical compability created.
     * It calls super(xmlCoding, shortName, clickable, element).

     * @param visualMapping IS IGNORED, NOT USED
     */
    public VisualScalar(String xmlCoding, String shortName, boolean clickable, ElementInterface element, Mapping visualMapping) {
        super(xmlCoding, shortName, clickable, element);
    }

    /**
     * {@inheritDoc}
     *
     * @return new Scalar (data element)
     */
    @MethodInfo(hide = true)
    @Override
    public ElementInterface getNewElementInstance(int[] dimensions, Class<?> dataType) {
        if ((dimensions.length == 1) && (dimensions[0] == 1)) {
            return new Scalar(dataType);
        } else {
            throw new IllegalArgumentException("dimensions is " + dimensions.length + " long." +
                    " Need to be 1 and the only entry need to be 1 too.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry() {
        return super.getEntry(0);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Object value) {
        super.setEntry(0, value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Object getEntry(Integer row) {
        return getEntry();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setEntry(Integer row, Object value) {
        setEntry(value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Object getEntry(Integer row, Integer col) {
        return getEntry();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setEntry(Integer row, Integer col, Object value) {
        setEntry(value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public Object getEntry(Integer row, Integer col, Integer deep) {
        return getEntry();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setEntry(Integer row, Integer col, Integer deep, Object value) {
        setEntry(value);
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
    public ScalarInterface getScalar() {
        return (ScalarInterface) element;
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
    @MethodInfo(hide = false, callOptions = "autoinvoke", name="getScalar")
    @Override
    public VisualScalarInterface getReferenceAsVisualScalar() {
        return this;
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
    @MethodInfo(hide = true, name="getVector")
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
    @MethodInfo(hide = true, noGUI = true)
    @Override
    public void setDataType(Class<?> dataType) {
        element.setDataType(dataType);
    }
}
