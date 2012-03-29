/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.visual;

import basic.math.elements.interfaces.VisualTensorInterface;
import basic.math.elements.Tensor;
import basic.math.elements.interfaces.ElementInterface;
import basic.math.elements.interfaces.TensorInterface;
import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.elements.mappings.Mapping;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;

/**<p>
 * VisualTensor contains additional information of a Tensor.
 * A MathML description which is used for output name.
 * A short name which is used int the title of an InputWindow.
 * A boolean which contains the information if the Tensor is clickable and
 * an InputWindow is shown if it is clicked.
 * </p>
 * @author Night
 */
@ObjectInfo(name = "VisualTensor")
//@ComponentInfo(name = "VisualTensor", category = "BasicMath")
public class VisualTensor extends VisualElement implements VisualTensorInterface {

    private static final long serialVersionUID = 1;

    /**
     * Needed for XML- -Encoder / -Decoder
     * DO NOT USE IT TO GET AN ELEMENT !!!
     * IT´S DO NOTHING !!!
     */
    public VisualTensor() {
       super();
    }

    /**
     * Generates a new VisualTensor depending on the parameters.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "T" or "Ten" (no xml)
     * @param clickable true, if the tensor should be able to show its data values. False else.
     * @param row number of rows
     * @param col number of columns
     * @param deep number of the third dimension
     * @param dataType ths class object of the stored data entries
     */
    public VisualTensor(String xmlCoding, String shortName, Boolean clickable,
            Integer row, Integer col, Integer deep, Class<?> dataType) {

        super(xmlCoding, shortName, clickable, new int[]{row, col, deep}, dataType);
    }

    /**
     * Combine an existing data elemnent with this new visual element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "T" or "Ten" (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param element that is visualized/representat by this VisualTensor
     */
    public VisualTensor(String xmlCoding, String shortName, Boolean clickable, TensorInterface element) {
        super(xmlCoding, shortName, clickable, element);
    }

    /**
     * Combine an existing data elemnent with this new visual element
     * and setting the visual mapping that should be used on the data element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like "T" or "Ten" (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param element that is visualized/representat by this VisualTensor
     * @param visualMapping is an extra mapping that can be used for things like returning
     *          just the diagonal entries of a matrix on the same data element
     */
    public VisualTensor(String xmlCoding, String shortName, boolean clickable, ElementInterface element, Mapping visualMapping) {
        super(xmlCoding, shortName, clickable, element, visualMapping);
    }

    /**
     * {@inheritDoc}
     *
     * @return new Tensor (data element)
     */
    @MethodInfo(hide = true)
    @Override
    public ElementInterface getNewElementInstance(int[] dimensions, Class<?> dataType) {
        if (dimensions.length == 3) {
            return new Tensor(dimensions[0], dimensions[1], dimensions[2], dataType);
        } else {
            throw new IllegalArgumentException("dimensions is " + dimensions.length + " long." +
                    " Need to be 3");
        }
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(Integer row, Integer col, Integer deep) {
        return super.getEntry(new int[]{row, col, deep});
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(Integer row, Integer col, Integer deep, Object value) {
        super.setEntry(new int[]{row, col, deep}, value);
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
    public TensorInterface getTensor() {
        return (TensorInterface) element;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(callOptions = "autoinvoke", name="getTensor")
    @Override
    public VisualTensorInterface getReferenceAsVisualTensor() {
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
