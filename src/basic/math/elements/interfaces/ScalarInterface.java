/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basic.math.elements.interfaces;

import eu.mihosoft.vrl.annotation.MethodInfo;

/**
 * This is the basic interface for all kind of data <code>Scalar</code>s.
 * @author night
 */
public interface ScalarInterface extends VectorInterface {

    /**
     * @return the value that is stored
     */
    @MethodInfo(hide = true)
    public Object getEntry();

    /**
     * Sets the value that should be stored.
     * @param value that should be stored
     */
    @MethodInfo(hide = true)
    public void setEntry(Object value);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int[] getDimensions();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract Integer getArrayLength();

    /**
     * A specialization of getElement().
     *
     * @return the element as ScalarInterface
     */
    @MethodInfo(hide = false)
    public ScalarInterface getScalar();
}
