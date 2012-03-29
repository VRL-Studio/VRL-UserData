/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.visual;

import basic.math.elements.interfaces.VisualElementInterface;
import basic.math.elements.Element;
import basic.math.elements.interfaces.ElementInterface;
import basic.math.elements.mappings.Mapping;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ObjectInfo;

/**
 * The idea for this class is that this class should be a decorator for all
 * supclasses of Element and add new abilities to it, which are needed if you
 * to work with the data elemnt visual.
 *
 * e.g. adding a mathML name that would be illustrated in the visual representation
 *
 * @author Night
 */
@ObjectInfo(name = "VisualElement")
public abstract class VisualElement implements VisualElementInterface {

    private static final long serialVersionUID = 1L;

    /**
     *  contains the MathML discription of what should be written.
     * e.g. <code> <math><mfrac> <mi>d</mi> <mi>dt</mi> </mfrac> </code>
     *      for "d/dt"
     */
    protected String xmlCoding;
    /**
     *  the short name for for an operation or name of an element
     * e.g. "d/dt" instead for
     *      <code> <math><mfrac> <mi>d</mi> <mi>dt</mi> </mfrac> </code>
     *
     */
    protected String shortName;
    /**
     * Contains the information if by clicking on the name of the viual element
     * an InputWindow should appear and  than the entries of the element can be
     * modified or not.
     */
    protected Boolean clickable;
    /**
     * The data element which is represented by this VisualElement.
     */
    protected ElementInterface element;
    /**
     * Mapping that is used for extras like return the diagonal of the matrix.
     */
    protected Mapping visualMapping;
    /**
     * Dimensions that is used for extras like return the diagonal of the matrix.
     */
    private int[] visualDimensions;

    /**
     * Needed for XML- -Encoder / -Decoder
     * DO NOT USE IT TO GET AN ELEMENT !!!
     * IT´S DO NOTHING !!!
     */
    public VisualElement() {
    }

    /**
     * Should be overridden by each specialization VisualSpec(... , Spec spec)
     * so you can combine an existing data elemnent with this new visual element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like elem for Element  (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param element that is visualized/representat by this VisualElemnt
     */
    public VisualElement(String xmlCoding, String shortName, Boolean clickable, ElementInterface element) {

        this.element = element;

        init(xmlCoding, shortName, clickable);
    }

    /**
     * Should be overridden by each specialization VisualSpec(... , Spec spec)
     * so you can combine an existing data elemnent with this new visual element
     * and setting the visual mapping that should be used on the data element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like elem for Element  (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param element that is visualized/representat by this VisualElement
     * @param visualMapping is an extra mapping that can be used for things like returning
     *          just the diagonal entries of a matrix on the same data element
     */
    public VisualElement(String xmlCoding, String shortName, Boolean clickable, ElementInterface element, Mapping visualMapping) {

        this.element = element;
        this.visualMapping = visualMapping;

        init(xmlCoding, shortName, clickable);
    }

    /**
     * Generetes a new data element that is used of this visual element.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like elem for Element  (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     * @param dimensions dim[i] is the size of the i-th dimension
     * @param dataType the class object of the stored data entries (Double.class for Double values)
     */
    public VisualElement(
            String xmlCoding, String shortName, Boolean clickable,
            int[] dimensions, Class<?> dataType) {

        this.element = getNewElementInstance(dimensions, dataType);

        init(xmlCoding, shortName, clickable);

    }

    /**
     * Checks if all parameters and the used element are not null.
     *
     * @param xmlCoding contains the MathML code
     * @param shortName a short String name like elem for Element  (no xml)
     * @param clickable true, if the element should be able to show its data values. False else.
     */
    @MethodInfo(hide = true)
    private void init(String xmlCoding, String shortName, Boolean clickable) {

        this.xmlCoding = xmlCoding;
        this.shortName = shortName;
        this.clickable = clickable;

        if (element == null) {
            String message = "in " + this.getClass() + ": " + "element" + " is null";
            System.err.println(">> " + message);
            throw new IllegalArgumentException(message);
        }

        if (xmlCoding == null) {
            String message = "in " + this.getClass() + ": " + "xmlCoding" + " is null";
            System.err.println(">> " + message);
            throw new IllegalArgumentException(message);
        }

        if (shortName == null) {
            String message = "in " + this.getClass() + ": " + "shortName" + " is null";
            System.err.println(">> " + message);
            throw new IllegalArgumentException(message);
        }

        if (clickable == null) {
            String message = "in " + this.getClass() + ": " + "clickable" + " is null. Set by default to false.";
            System.err.println(">> " + message);
        }
    }

    /**
     * Creates a new general data element based on the used parameters.
     *
     * @param dimensions number (dim.length) and size (dim[i]) of the dimensions
     * @param dataType the class of the data that should be stored in this Element
     * @return a new general Element, created dependent on the used paramerters
     */
    public static ElementInterface newElement(int[] dimensions, Class<?> dataType) {
        return new Element(dimensions, dataType);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public ElementInterface getElement() {
        return (ElementInterface) element;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setElement(ElementInterface element) {
        this.element = element;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public String getShortName() {
        return shortName;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public String getXmlCoding() {
        return xmlCoding;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setXmlCoding(String xmlCoding) {
        this.xmlCoding = xmlCoding;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Boolean getClickable() {
        return clickable;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setClickable(Boolean clickable) {
        this.clickable = clickable;
    }

    /**
     * {@inheritDoc}
     *
     * This is done with the help of (visual) mapping.
     *
     */
    @MethodInfo(hide = true)
    @Override
    public void setEntry(int[] position, Object value) {
        element.setEntry(getVisualMapping().getIndex(position, getDimensions()), value);
    }

    /**
     * {@inheritDoc}
     *
     * This is done with the help of (visual) mapping.
     *
     */
    @MethodInfo(hide = true)
    @Override
    public Object getEntry(int[] position) {
        return element.getEntry(getVisualMapping().getIndex(position, getDimensions()));
    }

    /**
     * {@inheritDoc}
     *
     * This is done with the help of (visual) mapping.
     *
     */
    @Override
    public Object getEntry(int index) {
        return element.getEntry(getVisualMapping().visualIndexToArrayIndex(index));
    }

    /**
     * {@inheritDoc}
     *
     * This is done with the help of (visual) mapping.
     *
     */
    @Override
    public void setEntry(int index, Object value) {
        element.setEntry(getVisualMapping().visualIndexToArrayIndex(index), value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Mapping getMapping() {
        return element.getMapping();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setMapping(Mapping mapping) {
        element.setMapping(mapping);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Class<?> getDataType() {
        return element.getDataType();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public int[] getDimensions() {
        return element.getDimensions();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void fillAllEntriesWith(Object value) {
        element.fillAllEntriesWith(value);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public String getDefaultName() {
        return element.getDefaultName();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Object getDataArray() {
        return element.getDataArray();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Integer getArrayLength() {
        return element.getArrayLength();
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, callOptions = "autoinvoke", name="getElement")
    @Override
    public VisualElementInterface getReferenceAsVisualElement() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public Mapping getVisualMapping() {
        if (visualMapping != null) {
            return visualMapping;
        } else {
            return getMapping();
        }
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setVisualMapping(Mapping visualMapping) {
        this.visualMapping = visualMapping;
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public int[] getVisualDimensions() {
        if (visualDimensions != null) {
            return visualDimensions;
        } else {
            return getDimensions();
        }
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setVisualDimensions(int[] visualDimensions) {
        this.visualDimensions = visualDimensions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDimensions(int[] dimensions) {
        element.setDimensions( dimensions);
    }

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    public void setArrayLength(Integer arrayLength) {
        element.setArrayLength( arrayLength);
    }

    /**
     * @param dataArray the dataArray to set
     */
    @MethodInfo(hide = true)
    @Override
    public void setDataArray(Object dataArray) {
        element.setDataArray( dataArray);
    }
}
