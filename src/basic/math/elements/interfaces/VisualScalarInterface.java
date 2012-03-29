/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.elements.interfaces;

import eu.mihosoft.vrl.annotation.MethodInfo;
import java.io.Serializable;

/**
 * This is the basic interface for all kind of <code>VisualScalar</code>s.
 *
 * @author night
 */
public interface VisualScalarInterface extends ScalarInterface, Serializable, VisualVectorInterface {

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
    Object getEntry();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    Object getEntry(Integer row);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    Object getEntry(Integer row, Integer col);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    Object getEntry(Integer row, Integer col, Integer deep);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    MatrixInterface getMatrix();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    ElementInterface getNewElementInstance(int[] dimensions, Class<?> dataType);

    /**
     * @return this visual element as VisualScalarInterface
     */
    @MethodInfo(hide = false)
    VisualScalarInterface getReferenceAsVisualScalar();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    VisualVectorInterface getReferenceAsVisualVector();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    VisualMatrixInterface getReferenceAsVisualMatrix();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    VisualTensorInterface getReferenceAsVisualTensor();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide=true)
    @Override
    VisualElementInterface getReferenceAsVisualElement();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = false)
    @Override
    ScalarInterface getScalar();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    TensorInterface getTensor();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    VectorInterface getVector();

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true)
    @Override
    void setEntry(Object value);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    void setEntry(Integer row, Object value);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    void setEntry(Integer row, Integer col, Object value);

    /**
     * {@inheritDoc}
     */
    @MethodInfo(hide = true, noGUI = true)
    @Override
    void setEntry(Integer row, Integer col, Integer deep, Object value);
}
