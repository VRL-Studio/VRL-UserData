/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.elements.interfaces;

import eu.mihosoft.vrl.annotation.MethodInfo;
import java.io.Serializable;

/**
 * This is the basic interface for all kind of <code>VisualTensor</code>s.
 *
 * @author night
 */
public interface VisualTensorInterface extends Serializable, TensorInterface, VisualElementInterface {

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    Integer getArrayLength();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, valueStyle = "array")
    @Override
    int[] getDimensions();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    Object getEntry(Integer row, Integer col, Integer deep);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    ElementInterface getNewElementInstance(int[] dimensions, Class<?> dataType);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide=true)
    @Override
    VisualElementInterface getReferenceAsVisualElement();

    /**
     * @return this visual element as VisualTensorInterface
     */
    @MethodInfo(hide = false)
    VisualTensorInterface getReferenceAsVisualTensor();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = false)
    @Override
    TensorInterface getTensor();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    void setEntry(Integer row, Integer col, Integer deep, Object value);

}
