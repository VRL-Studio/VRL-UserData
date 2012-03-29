/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.elements.interfaces;

import basic.math.elements.mappings.Mapping;
import eu.mihosoft.vrl.annotation.MethodInfo;
import java.io.Serializable;

/**
 * This is the basic interface for all kind of <code>VisualElement</code>s.
 *
 * @author night
 */
public interface VisualElementInterface extends ElementInterface, Serializable {

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    void fillAllEntriesWith(Object value);

    /**
     * {@inheritDoc}
     */
    @Override
    Integer getArrayLength();

    /**
     * @return the clickable
     */
    @MethodInfo(hide = true)
    Boolean getClickable();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    Object getDataArray();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    Class<?> getDataType();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    String getDefaultName();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    int[] getDimensions();

    /**
     * {@inheritDoc}
     *
     * Each VisualElement should make specialization by creating a new method
     *   SpecInterface getSpec() .
     *
     *
     * @return the element
     */
    @MethodInfo(hide = true)
    @Override
    ElementInterface getElement();

    /**
     *
     * @param element is the data element which should be used of this visual element
     */
    void setElement(ElementInterface element);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    Object getEntry(int[] position);

    /**
     * {@inheritDoc}
     */
    @Override
    Object getEntry(int index);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    Mapping getMapping();

    /**
     * This method generates in each specialization the depending Element.
     * This means a VisualTensor returns a new instance of Tensor and
     * a VisualScalar a new instance of Scalar.
     */
    @MethodInfo(hide = true)
    ElementInterface getNewElementInstance(int[] dimensions, Class<?> dataType);

    /**
     *
     * @return this VisualElement
     */
    @MethodInfo(hide = true)
    VisualElementInterface getReferenceAsVisualElement();


    /**
     * @return the shortName
     */
    @MethodInfo(hide = true)
    String getShortName();

    /**
     * @return the xmlCoding
     */
    @MethodInfo(hide = true)
    String getXmlCoding();

    /**
     * @param clickable the clickable to set
     */
    @MethodInfo(hide = true)
    void setClickable(Boolean clickable);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    void setEntry(int[] position, Object value);

     /**
     * {@inheritDoc}
     */
    @Override
    void setEntry(int index, Object value);

     /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    void setMapping(Mapping mapping);

    /**
     * @param shortName the shortName to set
     */
    @MethodInfo(hide = true)
    void setShortName(String shortName);

    /**
     * @param xmlCoding the xmlCoding to set
     */
    @MethodInfo(hide = true)
    void setXmlCoding(String xmlCoding);

    /**
     * @return the visaulMapping if not null else returns the mapping of element
     */
    @MethodInfo(hide = true)
    public Mapping getVisualMapping();

    /**
     * @param visualMapping the visaulMapping to set
     */
    @MethodInfo(hide = true)
    public void setVisualMapping(Mapping visualMapping);

    /**
     * @return the visualDimensions if not null else returns dimensions[] of element
     */
    @MethodInfo(hide = true)
    public int[] getVisualDimensions();

    /**
     * @param visualDimensions the visualDimensions to set
     */
    @MethodInfo(hide = true)
    public void setVisualDimensions(int[] visualDimensions);
}
