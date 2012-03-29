/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package basic.math.elements.interfaces;

import eu.mihosoft.vrl.annotation.MethodInfo;

/**
 * This is the basic interface for all kind of data <code>Vector</code>s.
 * @author night
 */
public interface VectorInterface extends MatrixInterface {

    /**
     * Get entry from the position that is coded by (row).
     *
     * @param row the index of the value we want
     * @return the value at index row
     */
    @MethodInfo(hide = true)
    public Object getEntry(Integer row);

    /**
     * Sets the value at position(row).
     * 
     * @param row the row index
     * @param value the value to set
     */
    @MethodInfo(hide = true)
    public void setEntry(Integer row, Object value);

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
     * @return the element as VectorInterface
     */
    @MethodInfo(hide = false)
    public VectorInterface getVector();
}
